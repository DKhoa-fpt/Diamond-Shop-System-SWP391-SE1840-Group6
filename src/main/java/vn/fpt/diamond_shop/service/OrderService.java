package vn.fpt.diamond_shop.service;

import org.springframework.http.ResponseEntity;
import vn.fpt.diamond_shop.model.OrderDetail;
import vn.fpt.diamond_shop.request.*;
import vn.fpt.diamond_shop.response.AddOrderResponse;
import vn.fpt.diamond_shop.response.ListCartResponse;

import java.util.List;

public interface OrderService {
    ResponseEntity<Object> orderList(GetListOrderRequest request);


    AddOrderResponse addOrder(AddOrderRequest request);

    Object listCart(GetListCartRequest request);

    Boolean addCart(AddCartRequest request);

    Boolean updateCart(AddCartRequest request);

    Boolean deleteCart(DeleteCartRequest request);

    Object detail(GetOrderDetailRequest request);

    ResponseEntity<Object> orderListAllUser(GetListOrderRequest request);


    Boolean updateOrder(UpdateOrderRequest request);
}
