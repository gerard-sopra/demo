package sopra.steria.demo.shipment;

import jakarta.persistence.*;
import lombok.Data;
import sopra.steria.demo.base.Base;
import sopra.steria.demo.inventory.SupplyType;
import sopra.steria.demo.request.SupplyRequest;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "supply_request_id", nullable = false, unique = true)
    private SupplyRequest supplyRequest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_base_id", nullable = false)
    private Base sourceBase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_base_id", nullable = false)
    private Base destinationBase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplyType supplyType;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @Column(nullable = false)
    private Instant createdAt;
}
