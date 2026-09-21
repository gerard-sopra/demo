package sopra.steria.demo.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sopra.steria.demo.kafka.event.SupplyRequestedEvent;

@Component
public class SupplyRequestProducer {

    private static final String TOPIC = "supply.requested";

    private final KafkaTemplate<String, SupplyRequestedEvent> kafkaTemplate;

    public SupplyRequestProducer(
            KafkaTemplate<String, SupplyRequestedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(SupplyRequestedEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.requestId().toString(),
                event
        );
    }
}
