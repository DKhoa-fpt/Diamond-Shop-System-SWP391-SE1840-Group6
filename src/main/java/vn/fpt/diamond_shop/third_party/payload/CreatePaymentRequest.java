package vn.fpt.diamond_shop.third_party.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePaymentRequest {
    private String requestId;
    private String orderId;
    private long amount;
    private String orderInfo;
    private String payType;
}
