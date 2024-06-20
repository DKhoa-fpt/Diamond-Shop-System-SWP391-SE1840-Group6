package vn.fpt.diamond_shop.security.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.controller.BaseController;
import vn.fpt.diamond_shop.request.ChangeProfileRequest;
import vn.fpt.diamond_shop.security.AccountService;
import vn.fpt.diamond_shop.security.exception.ResourceNotFoundException;
import vn.fpt.diamond_shop.security.model.User;
import vn.fpt.diamond_shop.repository.UserRepository;
import vn.fpt.diamond_shop.security.CurrentUser;
import vn.fpt.diamond_shop.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/shop/user")
public class UserController extends BaseController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping("/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping("/change-profile")
    public ResponseEntity<?> changeProfile(@CurrentUser UserPrincipal userPrincipal, @RequestBody ChangeProfileRequest changeProfileRequest) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        changeProfileRequest.setEmail(user.getEmail());
        accountService.changeProfile(changeProfileRequest);
        return ok("Change profile successfully");
    }
}
