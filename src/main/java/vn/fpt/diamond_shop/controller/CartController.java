package vn.fpt.diamond_shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.fpt.diamond_shop.constants.UrlConstants;
import vn.fpt.diamond_shop.request.*;
import vn.fpt.diamond_shop.security.CurrentUser;
import vn.fpt.diamond_shop.security.UserPrincipal;
import vn.fpt.diamond_shop.service.OrderService;
import vn.fpt.diamond_shop.util.logger.LogActivities;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(UrlConstants.BASIC_CART_URL)
public class CartController extends BaseController {

    @Autowired
    private OrderService orderService;

    @PostMapping("list")
    @LogActivities
    public ResponseEntity<Object> list(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody GetListCartRequest request) {
        request.setCustomerId(userPrincipal == null ? null : userPrincipal.getId());
        return ok(orderService.listCart(request));
    }
    @PostMapping("add_card")
    @LogActivities
    public ResponseEntity<Object> addCart(@CurrentUser UserPrincipal userPrincipal,@Valid @RequestBody AddCartRequest request) {
        request.setCustomerId(userPrincipal == null ? null : userPrincipal.getId());
        return ok(orderService.addCart(request));
    }

    @PostMapping("update")
    @LogActivities
    public ResponseEntity<Object> updateCart(@CurrentUser UserPrincipal userPrincipal,@Valid @RequestBody AddCartRequest request) {
        request.setCustomerId(userPrincipal == null ? null : userPrincipal.getId());
        return ok(orderService.updateCart(request));
    }

    @PostMapping("delete")
    @LogActivities
    public ResponseEntity<Object> deleteCart(@CurrentUser UserPrincipal userPrincipal,@Valid @RequestBody DeleteCartRequest request) {
        return ok(orderService.deleteCart(request));
    }
}
