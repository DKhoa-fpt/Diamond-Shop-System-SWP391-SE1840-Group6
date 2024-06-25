package vn.fpt.diamond_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.jobs.RenewImageJob;
import vn.fpt.diamond_shop.request.ManagerModifyAccountRequest;
import vn.fpt.diamond_shop.service.AdminService;
import vn.fpt.diamond_shop.util.logger.LogActivities;

@RestController
@RequestMapping("/shop/admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RenewImageJob renewImage;

    @GetMapping("/account")
    @LogActivities
    private ResponseEntity<?> listAccount() {
        return ok(adminService.listAccount());
    }

    @PutMapping("/account/modify")
    @LogActivities
    private ResponseEntity<?> modify(@RequestBody ManagerModifyAccountRequest request) {
        adminService.changeInforAccount(request);
        return ok("Change account success");
    }

    @PutMapping("/job/renew-image")
    @LogActivities
    private ResponseEntity<?> renewImage() {
        renewImage.renewImageTask();
        return noContent();
    }

    @PostMapping("/job/check-send-mail-coupon")
    @LogActivities
    private ResponseEntity<?> checkSendMailCoupon() {
        adminService.checkSendMailCoupon();
        return noContent();
    }
}
