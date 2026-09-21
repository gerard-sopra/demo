package sopra.steria.demo.inventory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> findByBase(UUID baseId) {
        return inventoryRepository.findByBaseId(baseId);
    }
}
