package sopra.steria.demo.request;

import jakarta.persistence.*;
import lombok.Data;
import sopra.steria.demo.base.Base;
import sopra.steria.demo.inventory.SupplyType;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "supply_requests")
public class SupplyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private Base base;

    @ManyToOne
    @JoinColumn(name = "source_depot_id")
    private Base sourceDepot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplyType supplyType;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @Column(nullable = false)
    private Instant createdAt;
}
