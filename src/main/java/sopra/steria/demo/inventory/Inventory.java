package sopra.steria.demo.inventory;

import jakarta.persistence.*;
import lombok.Data;
import sopra.steria.demo.base.Base;

import java.util.UUID;

@Entity
@Data
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    private Base base;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplyType supplyType;

    @Column(nullable = false)
    private int quantity;
}
