package sopra.steria.demo.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sopra.steria.demo.kafka.event.ShipmentCreatedEvent;
import sopra.steria.demo.shipment.ShipmentService;

@Component
public class ShipmentConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ShipmentConsumer.class);

    private final ShipmentService shipmentService;

    public ShipmentConsumer(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @KafkaListener(
            topics = "shipment.created",
            groupId = "shipment-dispatch"
    )
    public void consume(ShipmentCreatedEvent event) {

        LOGGER.info(
                "Received shipment.created event for shipment {}",
                event.shipmentId()
        );

        shipmentService.dispatch(event.shipmentId());
    }
}
