package vn.fpt.diamond_shop.request;

import lombok.Data;

@Data
public class ManagerModifyAccountRequest {
    private Long accountId;

    private String email;

    private String fullName;

    private Long roleId;

    private boolean deactivate = false;
}
