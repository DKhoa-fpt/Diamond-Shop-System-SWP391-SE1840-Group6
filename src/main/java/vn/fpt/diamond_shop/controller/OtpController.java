package vn.fpt.diamond_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.repository.UserRepository;
import vn.fpt.diamond_shop.security.exception.BadRequestException;
import vn.fpt.diamond_shop.service.Impl.OtpService;
import vn.fpt.diamond_shop.service.MailService;
import vn.fpt.diamond_shop.util.OTPUtils;
import vn.fpt.diamond_shop.util.logger.LogActivities;

@RestController
@RequestMapping("/shop/otp")
public class OtpController extends BaseController {

    @Autowired
    private MailService mailService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    @LogActivities
    public ResponseEntity<?> sendOtp(@RequestParam(required = true, name = "email") String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email address already in use.");
        }
        String otp = OTPUtils.gen();
        otpService.cacheOtp(email, otp);
        mailService.sendOtp(email, "Diamond Shop OTP - Register", otp);
        return ok("Send otp to " + email + " success", null);
    }

    @GetMapping("/forget")
    @LogActivities
    public ResponseEntity<?> sendOtpForget(@RequestParam(required = true, name = "email") String email) {
        String otp = OTPUtils.gen();
        otpService.cacheOtp(email, otp);
        mailService.sendOtp(email, "Diamond Shop OTP - Change password", otp);
        return ok("Send otp to " + email + " success", null);
    }
}
