package vn.fpt.diamond_shop.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProfileRequest {
    @JsonIgnore
    private String email;
    private String phoneNumber;
    private String password;
    private String fullName;
    private boolean isMale;
    private String dateOfBirth;
    private String province;
    private int age;
    private String district;
    private String city;
    private String ward;
    private String extra;
    private String bio;
}
