package vn.fpt.diamond_shop.service;

import vn.fpt.diamond_shop.model.EndUser;
import vn.fpt.diamond_shop.request.ManagerModifyAccountRequest;
import vn.fpt.diamond_shop.response.ManagerListAccountResponse;
import vn.fpt.diamond_shop.security.model.Role;

import java.util.List;

public interface AdminService {
    void changeInforAccount(ManagerModifyAccountRequest request);

    List<ManagerListAccountResponse> listAccount();


    void setRole(Long accountId, Long roleId);

    List<EndUser> searchAccount();

    void checkSendMailCoupon();
}
