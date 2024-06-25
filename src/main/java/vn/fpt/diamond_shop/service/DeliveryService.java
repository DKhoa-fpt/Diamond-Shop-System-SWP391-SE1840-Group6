package vn.fpt.diamond_shop.service;

import org.springframework.http.ResponseEntity;
import vn.fpt.diamond_shop.model.OrderDetail;
import vn.fpt.diamond_shop.request.*;
import vn.fpt.diamond_shop.response.AddOrderResponse;

public interface DeliveryService {
    ResponseEntity<Object> listDelivery(GetListDeliveryRequest request);

    ResponseEntity<Object> getListDeliver();

    Boolean updateDeliver(UpdateDeliverRequest request);

    Boolean addDelivery(AddDeliveryRequest request);
}
