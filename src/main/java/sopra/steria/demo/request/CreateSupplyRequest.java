package sopra.steria.demo.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import sopra.steria.demo.inventory.SupplyType;

import java.util.UUID;

public record CreateSupplyRequest(
        @NotNull
        UUID baseId,

        @NotNull
        SupplyType supplyType,

        @Positive
        int quantity,

        @NotNull
        Priority priority
) {
}
