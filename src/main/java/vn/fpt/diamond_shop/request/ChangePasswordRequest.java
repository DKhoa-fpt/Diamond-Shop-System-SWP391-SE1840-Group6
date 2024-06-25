package vn.fpt.diamond_shop.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String password;
    private String otp;
}
