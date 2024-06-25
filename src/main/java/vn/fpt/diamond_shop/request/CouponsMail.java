package vn.fpt.diamond_shop.request;

import lombok.Data;

@Data
public class CouponsMail {
    private String code;
    private String expiredDate;
    private Long percent;
}
