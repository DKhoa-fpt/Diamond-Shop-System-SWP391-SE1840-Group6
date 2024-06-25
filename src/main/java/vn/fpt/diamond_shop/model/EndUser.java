package vn.fpt.diamond_shop.model;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "END_USER")
@Data
public class EndUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "isMale")
    private boolean isMale;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "create_at")
    private OffsetDateTime createAt;

    @Column(name = "update_at")
    private OffsetDateTime updateAt;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "address")
    private Long address;
}
