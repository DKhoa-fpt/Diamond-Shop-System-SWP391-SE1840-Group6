package vn.fpt.diamond_shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.diamond_shop.constants.UrlConstants;
import vn.fpt.diamond_shop.request.AddDeliveryRequest;
import vn.fpt.diamond_shop.request.GetListCartRequest;
import vn.fpt.diamond_shop.request.GetListDeliveryRequest;
import vn.fpt.diamond_shop.request.UpdateDeliverRequest;
import vn.fpt.diamond_shop.security.CurrentUser;
import vn.fpt.diamond_shop.security.UserPrincipal;
import vn.fpt.diamond_shop.service.DeliveryService;
import vn.fpt.diamond_shop.util.logger.LogActivities;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(UrlConstants.BASIC_DELIVERY_URL)
public class DeliveryController extends BaseController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("list")
    @LogActivities
    public ResponseEntity<Object> list(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody GetListDeliveryRequest request) {
        return deliveryService.listDelivery(request);
    }


    @PostMapping("add-update")
    @LogActivities
    public ResponseEntity<Object> add(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody AddDeliveryRequest request) {
        return ok(deliveryService.addDelivery(request));
    }

    @GetMapping("list-deliver")
    @LogActivities
    public ResponseEntity<Object> listDeliver() {
        return deliveryService.getListDeliver();
    }

    @PostMapping("update-deliver")
    @LogActivities
    public ResponseEntity<Object> updateDeliver(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody UpdateDeliverRequest request) {
        return ok(deliveryService.updateDeliver(request));
    }
}
