package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import vn.fpt.diamond_shop.constants.DiamondColorEnum;

import javax.persistence.*;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "jewelry_type")
@Data
@NoArgsConstructor
public class JewelryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jewelry_type_name")
    private String jewelryTypeName;

    @Column(name = "jewelry_type_code")
    private String jewelryTypeCode;

    @Column(name = "id_guide")
    private Integer idGuide;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
