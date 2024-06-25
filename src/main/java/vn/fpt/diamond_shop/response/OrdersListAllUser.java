package vn.fpt.diamond_shop.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;
import vn.fpt.diamond_shop.model.Delivery;
import vn.fpt.diamond_shop.model.OrderDetail;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrdersListAllUser {
    private Long id;

    private String uniqueOrderId;

    private Date orderDate;

    private String status;

    private Long customerId;

    private Long totalPrice;

    private Date createdAt;

    private Date updatedAt;

    private String phoneNumber;

    private Delivery deliveryInfo;

    private List<OrderDetail> orderDetails;
}
