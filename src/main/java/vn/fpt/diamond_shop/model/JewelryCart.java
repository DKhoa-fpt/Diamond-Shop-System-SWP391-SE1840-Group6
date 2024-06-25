package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "JEWELRY_CART")
@Data
public class JewelryCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "jewelry_id")
    private Long jewelryId;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
