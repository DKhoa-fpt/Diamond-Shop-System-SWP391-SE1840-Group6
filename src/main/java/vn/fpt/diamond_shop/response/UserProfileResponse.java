package vn.fpt.diamond_shop.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.fpt.diamond_shop.model.Address;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String email;
    private String fullName;
    private String phoneNumber;
    private int age;
    private String dateOfBirth;
    private boolean isMale;
    private String avatar;
    private String bio;
    private Address address;
}
