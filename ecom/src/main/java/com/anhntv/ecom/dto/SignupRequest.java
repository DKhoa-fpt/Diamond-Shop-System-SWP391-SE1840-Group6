package com.anhntv.ecom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @Email(message = "Email is invalid format")
    @NotEmpty(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
}
