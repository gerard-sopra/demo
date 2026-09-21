package sopra.steria.demo.exception;

import java.util.UUID;

public class SupplyRequestNotFoundException extends RuntimeException {

    public SupplyRequestNotFoundException(UUID id) {
        super("Supply request not found: " + id);
    }
}
