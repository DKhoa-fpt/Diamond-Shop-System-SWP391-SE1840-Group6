package vn.fpt.diamond_shop.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private String province;
    private String district;
    private String city;
    private String ward;
    private String extra;
    private String phoneNumber;
    private String fullName;
    @NotBlank
    private String otp;

}
