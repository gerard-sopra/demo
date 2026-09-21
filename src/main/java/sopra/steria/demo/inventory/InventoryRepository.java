package sopra.steria.demo.inventory;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    List<Inventory> findByBaseId(UUID baseId);

    Optional<Inventory> findByBaseIdAndSupplyType(
            UUID baseId,
            SupplyType supplyType
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT i
            FROM Inventory i
            WHERE i.base.id = :baseId
            AND i.supplyType = :supplyType
            """)
    Optional<Inventory> findByBaseIdAndSupplyTypeForUpdate(
            @Param("baseId") UUID baseId,
            @Param("supplyType") SupplyType supplyType
    );
}
