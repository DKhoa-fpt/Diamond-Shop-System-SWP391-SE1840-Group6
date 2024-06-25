package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

//@Entity
//@Table(name = "DIAMOND_SHOP_CONFIG_VALUE")
@Data
@NoArgsConstructor
public class ConfigValue {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "key", unique = true)
    private String key;
    @Column(name = "value")
    private String value;
    @Column(name = "description")
    private String description;
    @Column(name = "create_at")
    private OffsetDateTime createAt;

    @Column(name = "update_at")
    private OffsetDateTime updateAt;

    public ConfigValue(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.createAt = OffsetDateTime.now();
    }
}
