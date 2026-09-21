package sopra.steria.demo.kafka.event;

import sopra.steria.demo.inventory.SupplyType;
import sopra.steria.demo.request.Priority;

import java.time.Instant;
import java.util.UUID;

public record SupplyRequestedEvent(
        UUID requestId,
        UUID baseId,
        SupplyType supplyType,
        int quantity,
        Priority priority,
        Instant createdAt
) {
}
