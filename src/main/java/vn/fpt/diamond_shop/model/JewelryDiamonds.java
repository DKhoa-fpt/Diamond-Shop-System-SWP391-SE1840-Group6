package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "JEWELRY_DIAMONDS")
@Data
@NoArgsConstructor
public class JewelryDiamonds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jewelry_id")
    private Long jewelryId;

    @Column(name = "diamond_id")
    private Long diamondId;
}
