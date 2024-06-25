package vn.fpt.diamond_shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.constants.UrlConstants;
import vn.fpt.diamond_shop.request.GetListDeliveryRequest;
import vn.fpt.diamond_shop.security.CurrentUser;
import vn.fpt.diamond_shop.security.UserPrincipal;
import vn.fpt.diamond_shop.service.AdminService;
import vn.fpt.diamond_shop.util.logger.LogActivities;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(UrlConstants.BASIC_USER_URL)
public class AccountController extends BaseController {

    @Autowired
    private AdminService accountService;

    @GetMapping("list")
    @LogActivities
    public ResponseEntity<Object> list() {
        return ok(accountService.searchAccount());
    }

}
