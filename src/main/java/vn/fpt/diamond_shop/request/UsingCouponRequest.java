package vn.fpt.diamond_shop.request;

import lombok.Data;

@Data
public class UsingCouponRequest {
    private Long userId;
    private String couponCode;
    private String orderId;
}
