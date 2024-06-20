package vn.fpt.diamond_shop.security.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.controller.BaseController;
import vn.fpt.diamond_shop.request.ChangeProfileRequest;
import vn.fpt.diamond_shop.security.AccountService;
import vn.fpt.diamond_shop.security.model.User;
import vn.fpt.diamond_shop.repository.UserRepository;
import vn.fpt.diamond_shop.security.CurrentUser;
import vn.fpt.diamond_shop.security.UserPrincipal;
import vn.fpt.diamond_shop.dto.UserResponse;

@RestController
@RequestMapping("/shop/user")
public class UserController extends BaseController {

    private final UserRepository userRepository;
    private final AccountService accountService;

    public UserController(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        return new UserResponse(user);
    }

    @PostMapping("/change-profile")
    public ResponseEntity<?> changeProfile(@CurrentUser UserPrincipal userPrincipal, @RequestBody ChangeProfileRequest changeProfileRequest) {
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        changeProfileRequest.setEmail(user.getEmail());
        accountService.changeProfile(changeProfileRequest);
        return ok("Change profile successfully");
    }
}