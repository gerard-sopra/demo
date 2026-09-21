package sopra.steria.demo.exception;

import java.util.UUID;

public class BaseNotFoundException extends RuntimeException {

    public BaseNotFoundException(UUID id) {
        super("Base not found: " + id);
    }
}
