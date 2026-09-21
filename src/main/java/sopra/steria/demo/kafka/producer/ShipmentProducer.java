package sopra.steria.demo.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sopra.steria.demo.kafka.event.ShipmentCreatedEvent;

@Component
public class ShipmentProducer {

    private static final String TOPIC = "shipment.created";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ShipmentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ShipmentCreatedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.shipmentId().toString(),
                event
        );
    }
}
