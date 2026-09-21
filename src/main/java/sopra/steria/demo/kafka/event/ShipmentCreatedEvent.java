package sopra.steria.demo.kafka.event;

import java.util.UUID;

public record ShipmentCreatedEvent(
        UUID shipmentId
) {
}
