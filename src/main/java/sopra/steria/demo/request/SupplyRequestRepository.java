package sopra.steria.demo.request;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SupplyRequestRepository
        extends JpaRepository<SupplyRequest, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT r
            FROM SupplyRequest r
            WHERE r.id = :id
            """)
    Optional<SupplyRequest> findByIdForUpdate(
            @Param("id") UUID id
    );
}
