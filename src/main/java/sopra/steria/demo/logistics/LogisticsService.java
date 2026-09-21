package sopra.steria.demo.logistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sopra.steria.demo.base.Base;
import sopra.steria.demo.base.BaseRepository;
import sopra.steria.demo.base.BaseType;
import sopra.steria.demo.inventory.Inventory;
import sopra.steria.demo.inventory.InventoryRepository;
import sopra.steria.demo.kafka.event.ShipmentCreatedEvent;
import sopra.steria.demo.kafka.event.SupplyRequestedEvent;
import sopra.steria.demo.kafka.producer.ShipmentProducer;
import sopra.steria.demo.request.RequestStatus;
import sopra.steria.demo.request.SupplyRequest;
import sopra.steria.demo.request.SupplyRequestRepository;
import sopra.steria.demo.shipment.Shipment;
import sopra.steria.demo.shipment.ShipmentRepository;
import sopra.steria.demo.shipment.ShipmentStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LogisticsService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LogisticsService.class);

    private final BaseRepository baseRepository;
    private final InventoryRepository inventoryRepository;
    private final SupplyRequestRepository supplyRequestRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShipmentProducer shipmentProducer;

    public LogisticsService(
            BaseRepository baseRepository,
            InventoryRepository inventoryRepository,
            SupplyRequestRepository supplyRequestRepository,
            ShipmentRepository shipmentRepository,
            ShipmentProducer shipmentProducer
    ) {
        this.baseRepository = baseRepository;
        this.inventoryRepository = inventoryRepository;
        this.supplyRequestRepository = supplyRequestRepository;
        this.shipmentRepository = shipmentRepository;
        this.shipmentProducer = shipmentProducer;
    }

    @Transactional
    public void processSupplyRequest(SupplyRequestedEvent event) {

        SupplyRequest request = supplyRequestRepository
                .findByIdForUpdate(event.requestId())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Supply request not found: " + event.requestId()
                        )
                );

        // Idempotency check
        if (request.getStatus() != RequestStatus.PENDING) {
            LOGGER.info(
                    "Supply request {} already processed with status {}. Ignoring event.",
                    request.getId(),
                    request.getStatus()
            );

            return;
        }

        List<Base> depots =
                baseRepository.findByType(BaseType.SUPPLY_DEPOT);

        Optional<Inventory> sourceInventory = depots.stream()
                .map(depot ->
                        inventoryRepository.findByBaseIdAndSupplyTypeForUpdate(
                                depot.getId(),
                                event.supplyType()
                        )
                )
                .flatMap(Optional::stream)
                .filter(inventory ->
                        inventory.getQuantity() >= event.quantity()
                )
                .findFirst();

        if (sourceInventory.isEmpty()) {
            request.setStatus(RequestStatus.REJECTED);
            request.setSourceDepot(null);
            return;
        }

        Inventory inventory = sourceInventory.get();

        inventory.setQuantity(
                inventory.getQuantity() - event.quantity()
        );

        request.setSourceDepot(inventory.getBase());
        request.setStatus(RequestStatus.APPROVED);

        Shipment shipment = new Shipment();

        shipment.setSupplyRequest(request);
        shipment.setSourceBase(inventory.getBase());
        shipment.setDestinationBase(request.getBase());
        shipment.setSupplyType(event.supplyType());
        shipment.setQuantity(event.quantity());
        shipment.setStatus(ShipmentStatus.PREPARING);
        shipment.setCreatedAt(Instant.now());

        shipmentRepository.save(shipment);

        ShipmentCreatedEvent shipmentCreatedEvent =
                new ShipmentCreatedEvent(shipment.getId());

        shipmentProducer.send(shipmentCreatedEvent);

        LOGGER.info(
                "Supply request {} APPROVED - shipment {} created from {} to {}",
                request.getId(),
                shipment.getId(),
                shipment.getSourceBase().getName(),
                shipment.getDestinationBase().getName()
        );
    }
}