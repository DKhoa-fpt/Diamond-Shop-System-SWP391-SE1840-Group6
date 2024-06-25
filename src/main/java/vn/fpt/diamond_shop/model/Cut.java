package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import vn.fpt.diamond_shop.constants.DiamondCutEnum;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "CUT")
public class Cut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cut", unique = true)
    private DiamondCutEnum cut;
    @Column(name = "point")
    private int point;
    @Column(name = "create_at")
    private OffsetDateTime createAt;
    @Column(name = "update_at")
    private OffsetDateTime updateAt;

    public Cut(DiamondCutEnum cut, int point) {
        this.cut = cut;
        this.point = point;
        this.createAt = OffsetDateTime.now();
    }
}
