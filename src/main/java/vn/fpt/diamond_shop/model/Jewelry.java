package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import vn.fpt.diamond_shop.constants.JewelryTypeEnum;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "JEWELRY")
@Data
@NoArgsConstructor
public class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jewelry_code")
    private String jewelryCode;

    @Column(name = "id_diamond")
    private Long idDiamond;

    @Column(name = "jewelry_type_id")
    private Long jewelryTypeId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "material_prices")
    private Long materialPrices;

    @Column(name = "id_guide")
    private Long idGuide;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "gold_weight")
    private Float goldWeight;
}
