package vn.fpt.diamond_shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "coupons_code", length = 100)
    private String couponsCode;

    @Column(name = "discount_percent", length = 100)
    private Long discountPercent;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "discount_type", length = 100)
    private String discountType;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Long value;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "created_at", length = 100)
    private Date createdAt;

    @Column(name = "updated_date", length = 100)
    private Date updatedDate;


}