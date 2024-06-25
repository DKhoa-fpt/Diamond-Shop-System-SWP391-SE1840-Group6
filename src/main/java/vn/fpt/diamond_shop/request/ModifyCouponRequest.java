package vn.fpt.diamond_shop.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyCouponRequest extends AddCouponRequest {
    private Long id;
}
