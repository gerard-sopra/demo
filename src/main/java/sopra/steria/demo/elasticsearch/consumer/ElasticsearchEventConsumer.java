package sopra.steria.demo.elasticsearch.consumer;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sopra.steria.demo.elasticsearch.document.LogisticsEventDocument;
import sopra.steria.demo.elasticsearch.repository.LogisticsEventRepository;
import sopra.steria.demo.kafka.event.ShipmentCreatedEvent;
import sopra.steria.demo.kafka.event.SupplyRequestedEvent;

import java.util.UUID;

@Component
@Profile({"docker", "openshift"})
public class ElasticsearchEventConsumer {

    private final LogisticsEventRepository logisticsEventRepository;

    public ElasticsearchEventConsumer(
            LogisticsEventRepository logisticsEventRepository) {
        this.logisticsEventRepository = logisticsEventRepository;
    }

    @KafkaListener(
            topics = "supply.requested",
            groupId = "elasticsearch-indexer"
    )
    public void consumeSupplyRequested(SupplyRequestedEvent event) {

        LogisticsEventDocument document = new LogisticsEventDocument();

        document.setId(UUID.randomUUID().toString());
        document.setEventType("SUPPLY_REQUESTED");
        document.setRequestId(event.requestId());
        document.setBaseId(event.baseId());
        document.setSupplyType(event.supplyType().name());
        document.setQuantity(event.quantity());
        document.setPriority(event.priority().name());
        document.setCreatedAt(event.createdAt());

        logisticsEventRepository.save(document);
    }

    @KafkaListener(
            topics = "shipment.created",
            groupId = "elasticsearch-indexer"
    )
    public void consumeShipmentCreated(ShipmentCreatedEvent event) {

        LogisticsEventDocument document = new LogisticsEventDocument();

        document.setId(UUID.randomUUID().toString());
        document.setEventType("SHIPMENT_CREATED");
        document.setShipmentId(event.shipmentId());

        logisticsEventRepository.save(document);
    }
}
