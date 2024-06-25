package vn.fpt.diamond_shop.request;

import lombok.Data;

@Data
public class AddCouponRequest {
    private String code;
    private Long discountPercent;
    private String discountType;
    private String type;
    private Long value;
    private String expirationDate;
    private Integer quantity;
}
