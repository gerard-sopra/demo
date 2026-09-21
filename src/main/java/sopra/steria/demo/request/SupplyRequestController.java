package sopra.steria.demo.request;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
public class SupplyRequestController {

    private final SupplyRequestService supplyRequestService;

    public SupplyRequestController(SupplyRequestService supplyRequestService) {
        this.supplyRequestService = supplyRequestService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplyRequest create(@Valid @RequestBody CreateSupplyRequest request) {
        return supplyRequestService.create(request);
    }

    @GetMapping
    public List<SupplyRequest> findAll() {
        return supplyRequestService.findAll();
    }

    @GetMapping("/{id}")
    public SupplyRequest findById(@PathVariable UUID id) {
        return supplyRequestService.findById(id);
    }
}
