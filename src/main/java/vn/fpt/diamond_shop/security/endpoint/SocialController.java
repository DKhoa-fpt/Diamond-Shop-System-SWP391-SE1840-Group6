package vn.fpt.diamond_shop.security.endpoint;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import vn.fpt.diamond_shop.controller.BaseController;
import vn.fpt.diamond_shop.repository.UserRepository;
import vn.fpt.diamond_shop.request.LoginRequest;
import vn.fpt.diamond_shop.request.SignUpRequest;
import vn.fpt.diamond_shop.response.AuthResponse;
import vn.fpt.diamond_shop.security.AccountService;
import vn.fpt.diamond_shop.security.TokenProvider;
import vn.fpt.diamond_shop.security.UserPrincipal;
import vn.fpt.diamond_shop.security.model.LoginResponse;
import vn.fpt.diamond_shop.security.model.TokenDto;
import vn.fpt.diamond_shop.security.model.User;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/social")
public class SocialController extends BaseController {


    private String email;


//    @Value("${google.id}")
    private String idClient = "872531465200-phv9i3mh1h7vu01dtq1erpvld7s06vrd.apps.googleusercontent.com";

//    @Value("${mySecret.password}")
    private String password = "123456";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;

    //http://localhost:8080/social/google
    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody TokenDto tokenDto) throws Exception {
//        System.out.println("pass " + password);
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder ver =
                new GoogleIdTokenVerifier.Builder(transport,factory)
                        .setAudience(Collections.singleton(idClient));
        GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(),tokenDto.getToken());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        email = payload.getEmail();
        String name = payload.get("name").toString();

        User user = new User();
        if (!userRepository.existsByEmail(email)) {
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setName(name);
            signUpRequest.setEmail(email);
            signUpRequest.setPassword(password);
            accountService.registerV2(signUpRequest);
        }
        user = userRepository.findByEmail(email).get();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(user.getEmail());
        loginRequest.setPassword(password);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userRepository.findById(userPrincipal.getId()).get().isActive()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account is deaactive");
        }

        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) userPrincipal.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token, role));
    }
    @GetMapping("/create-user")
    public ResponseEntity<?> registerUsers() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("admin");
        signUpRequest.setEmail("admin@gmail.com");
        signUpRequest.setPassword(password);
        accountService.registerV2(signUpRequest);

        signUpRequest.setName("manager");
        signUpRequest.setEmail("manager@gmail.com");
        accountService.registerV2(signUpRequest);

        signUpRequest.setName("sale");
        signUpRequest.setEmail("sale@gmail.com");
        accountService.registerV2(signUpRequest);

        signUpRequest.setName("delivery");
        signUpRequest.setEmail("delivery@gmail.com");
        accountService.registerV2(signUpRequest);



        return ok("User okeee");
    }
}
