package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import vn.fpt.diamond_shop.constants.DiamondPolishEnum;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "POLISH")
@NoArgsConstructor
public class Polish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "polish", unique = true)
    private DiamondPolishEnum polish;

    @Column(name = "create_at")
    private OffsetDateTime createAt;

    @Column(name = "update_at")
    private OffsetDateTime updateAt;

    public Polish(DiamondPolishEnum polish) {
        this.polish = polish;
        this.createAt = OffsetDateTime.now();
    }

}
