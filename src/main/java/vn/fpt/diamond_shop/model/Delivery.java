package vn.fpt.diamond_shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name = "DELIVERY")
@Data
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "deliver_id")
    private Long deliverId;

    @Column(name = "status")
    private String status;

    @Column(name = "status_date")
    private Date statusDate;

    @Column(name = "end_date_estimated")
    private Date endDateEstimated;

    @Column(name = "delivery_fee")
    private Long deliveryFee;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
