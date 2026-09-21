package sopra.steria.demo.shipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ShipmentService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ShipmentService.class);

    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional
    public void dispatch(UUID shipmentId) {

        Shipment shipment = shipmentRepository
                .findById(shipmentId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Shipment not found: " + shipmentId
                        )
                );

        if (shipment.getStatus() != ShipmentStatus.PREPARING) {

            LOGGER.info(
                    "Shipment {} already has status {}. Ignoring dispatch.",
                    shipment.getId(),
                    shipment.getStatus()
            );

            return;
        }

        shipment.setStatus(ShipmentStatus.DISPATCHED);

        LOGGER.info(
                "Shipment {} DISPATCHED from {} to {}",
                shipment.getId(),
                shipment.getSourceBase().getName(),
                shipment.getDestinationBase().getName()
        );
    }
}
