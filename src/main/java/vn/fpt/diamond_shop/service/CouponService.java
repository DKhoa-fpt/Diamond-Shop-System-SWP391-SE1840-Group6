package vn.fpt.diamond_shop.service;

import vn.fpt.diamond_shop.model.Coupon;
import vn.fpt.diamond_shop.request.AddCouponRequest;
import vn.fpt.diamond_shop.request.ModifyCouponRequest;
import vn.fpt.diamond_shop.request.UsingCouponRequest;
import vn.fpt.diamond_shop.response.CouponsResponse;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupons();

    CouponsResponse addCoupon(AddCouponRequest request);

    void modifyCoupon(ModifyCouponRequest request);

    void usingCoupon(UsingCouponRequest usingCouponRequest);

    void deactivateCoupon(String couponCode);

    void activeCoupon(String couponCode);

    Coupon getCoupon(Long id, String code);

    Coupon checkCoupon(Long customerId, String couponCode);
}
