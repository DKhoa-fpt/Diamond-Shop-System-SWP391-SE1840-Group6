package vn.fpt.diamond_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.request.AddCouponRequest;
import vn.fpt.diamond_shop.request.ModifyCouponRequest;
import vn.fpt.diamond_shop.request.UsingCouponRequest;
import vn.fpt.diamond_shop.security.CurrentUser;
import vn.fpt.diamond_shop.security.UserPrincipal;
import vn.fpt.diamond_shop.service.CouponService;
import vn.fpt.diamond_shop.util.logger.LogActivities;

@RestController
@RequestMapping("/shop/coupon")
public class CouponController extends BaseController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/all")
    @LogActivities
    public ResponseEntity<?> getAllCoupons() {
        return ok(couponService.getAllCoupons());
    }

    @GetMapping("")
    @LogActivities
    public ResponseEntity<?> getCoupon(@RequestParam(name = "id", required = false) Long id,
                                       @RequestParam(name = "code", required = false) String code) {
        return ok(couponService.getCoupon(id, code));
    }

    @PostMapping("/add")
    @LogActivities
    public ResponseEntity<?> addCoupon(@RequestBody AddCouponRequest addCouponRequest) {
        return ok(couponService.addCoupon(addCouponRequest));
    }

    @PutMapping("/use")
    @LogActivities
    public ResponseEntity<?> useCoupon(@CurrentUser UserPrincipal userPrincipal, @RequestBody UsingCouponRequest usingCouponRequest) {
        usingCouponRequest.setUserId(userPrincipal.getId());
        couponService.usingCoupon(usingCouponRequest);
        return ok("Used coupon successfully");
    }

    @GetMapping("/check")
    @LogActivities
    public ResponseEntity<?> checkCoupon(@CurrentUser UserPrincipal userPrincipal, @RequestParam(name = "code", required = true) String couponCode) {
        return ok(couponService.checkCoupon(userPrincipal.getId(), couponCode));
    }

    @PutMapping("/modify")
    @LogActivities
    public ResponseEntity<?> modifyCoupon(@RequestBody ModifyCouponRequest addCouponRequest) {
        couponService.modifyCoupon(addCouponRequest);
        return ok("Modified coupon successfully");
    }

    @DeleteMapping("/deactivate")
    @LogActivities
    public ResponseEntity<?> deactivateCoupon(@RequestParam(name = "code", required = true) String couponCode) {
        couponService.deactivateCoupon(couponCode);
        return ok("Deactivated coupon successfully");
    }
}
