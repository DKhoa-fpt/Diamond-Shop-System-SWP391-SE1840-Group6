package vn.fpt.diamond_shop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.fpt.diamond_shop.constants.StatusOrder;
import vn.fpt.diamond_shop.exception.DiamondShopException;
import vn.fpt.diamond_shop.model.*;
import vn.fpt.diamond_shop.repository.*;
import vn.fpt.diamond_shop.request.*;
import vn.fpt.diamond_shop.response.*;
import vn.fpt.diamond_shop.service.OrderService;
import vn.fpt.diamond_shop.util.UUIDUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private JewelryRepository jewelryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private EndUserRepository endUserRepository;
    private static String ACTIVE_CART = "active";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public ResponseEntity<Object> orderList(GetListOrderRequest request) {
        if(request.getLimit() == null){
            request.setLimit(9);
        }
        if(request.getOffset() == null){
            request.setOffset(0);
        }
        if(!StringUtils.isEmpty(request.getPhoneNumber())) {
            EndUser endUserByPhoneNumber = endUserRepository.findEndUserByPhoneNumber(request.getPhoneNumber());
            if(endUserByPhoneNumber != null){
                request.setCustomerId(endUserByPhoneNumber.getAccountId());
            }
        }
        Page<Orders> ordersPage = null;
        ordersPage = ordersRepository.findAllOrderByCustomerIdOrderByCreatedAtDesc(request.getCustomerId(), PageRequest.of(request.getOffset()/ request.getLimit(), request.getLimit(), Sort.by(Sort.Direction.DESC, "id")));

        Page<OrderDetail> orderDetailsPage = null;
        if(StringUtils.isEmpty(request.getStatus())){
            orderDetailsPage = orderDetailRepository.findAllByCustomerIdOrderByCreatedAtDesc(request.getCustomerId(),PageRequest.of(request.getOffset()/ request.getLimit(), request.getLimit(), Sort.by(Sort.Direction.DESC, "id")));
        }else{
            orderDetailsPage = orderDetailRepository.findAllByCustomerIdAndStatusOrderByCreatedAtDesc(request.getCustomerId(), request.getStatus(), PageRequest.of(request.getOffset()/ request.getLimit(), request.getLimit(), Sort.by(Sort.Direction.DESC, "id")));
        }
        List<OrdersListAllUser> ordersListAllUsers = new ArrayList<>();
        for(Orders order : ordersPage){
            OrdersListAllUser ordersListAllUser = new OrdersListAllUser();
            BeanUtils.copyProperties(order, ordersListAllUser);
            List<OrderDetail> allByUniqueOrderId = orderDetailRepository.findAllByUniqueOrderId(order.getUniqueOrderId());
            ordersListAllUser.setOrderDetails(allByUniqueOrderId);
            ordersListAllUser.setDeliveryInfo(deliveryRepository.findAllByOrderId(order.getUniqueOrderId()));
            ordersListAllUser.setPhoneNumber(request.getPhoneNumber());
            ordersListAllUsers.add(ordersListAllUser);
        }
        Meta meta = new Meta(request.getRequestId(), 200, "success", HttpStatus.OK.toString());
        meta.setLimit(request.getLimit());
        meta.setOffset(request.getOffset());
        meta.setTotal(Integer.valueOf(String.valueOf(orderDetailsPage.getTotalElements()))) ;
        BaseResponse response = new BaseResponse(meta,ordersListAllUsers);
        return ResponseEntity.ok(response);
    }

    @Override
    public AddOrderResponse addOrder(AddOrderRequest request) {
        List<Cart> cartsById = cartRepository.findAllById(request.getCartIds());
        String uniqueOrderId = UUIDUtil.generateUUID();
        List<Long> listJewelris = new ArrayList<>();
        Orders orders = new Orders();
        if(!cartsById.isEmpty()){
            Long priceItems = 0L;
            for(Cart cart : cartsById){
                //insert order detail
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDate(new java.util.Date());
                orderDetail.setCreatedAt(new Date());
                orderDetail.setJewelryId(cart.getJewelryId());
                orderDetail.setCustomerId(cart.getUserId());
                orderDetail.setStatus(StatusOrder.CREATE_PAYMENT.getValue());
                orderDetail.setUniqueOrderId(uniqueOrderId);
                orderDetail.setQuantityNumber(cart.getQuantity());
                Optional<Jewelry> Jewelry = jewelryRepository.findById(cart.getJewelryId());
                Jewelry jewelryData = Jewelry.get();
                if(jewelryData != null){
                    orderDetail.setTotalPrice(cart.getQuantity()*jewelryData.getMaterialPrices());
                }
                priceItems += orderDetail.getTotalPrice();
                orderDetailRepository.save(orderDetail);
                listJewelris.add(cart.getJewelryId());
                cartRepository.deleteById(cart.getId());
                orders.setCustomerId(orderDetail.getCustomerId());
            }

            orders.setUniqueOrderId(uniqueOrderId);
            orders.setOrderDate(new Date());
            orders.setCreatedAt(new Date());
            orders.setStatus(StatusOrder.CREATE_PAYMENT.getValue());
            orders.setTotalPrice(priceItems);
            ordersRepository.save(orders);


            AddOrderResponse response = new AddOrderResponse();
            response.setTotalPrice(priceItems);
            response.setUniqueOrderId(uniqueOrderId);
            response.setJewelryId(listJewelris);

            return response;
        }else{
           throw new DiamondShopException(400, "Dont exist cart info!");
        }
    }

    @Override
    public Object listCart(GetListCartRequest request) {
        if(!StringUtils.isEmpty(request.getPhoneNumber())){
            List<ListCartResponse> listCartResponse = cartRepository.getListCartByPhoneNumberResponse(request.getPhoneNumber());
            return listCartResponse;
        }else{
            List<ListCartResponse> listCartResponse = cartRepository.getListCartResponse(request.getCustomerId());
            return listCartResponse;
        }
    }

    @Override
    public Boolean addCart(AddCartRequest request) {
        if(!StringUtils.isEmpty(request.getPhoneNumber())) {
            EndUser endUserByPhoneNumber = endUserRepository.findEndUserByPhoneNumber(request.getPhoneNumber());
            if(endUserByPhoneNumber != null){
                request.setCustomerId(endUserByPhoneNumber.getAccountId());
            }
        }
            Cart byCustomerIdAndAndJewelryId = cartRepository.findByUserIdAndAndJewelryId(request.getCustomerId(), request.getJewelryId());
            if (byCustomerIdAndAndJewelryId != null) {
                //update
                byCustomerIdAndAndJewelryId.setQuantity(byCustomerIdAndAndJewelryId.getQuantity() + request.getQuantity());
                byCustomerIdAndAndJewelryId.setUpdatedAt(new java.util.Date());
                cartRepository.updateByUserIdAndJewelryId(request.getCustomerId(), request.getJewelryId(), byCustomerIdAndAndJewelryId.getQuantity(), byCustomerIdAndAndJewelryId.getUpdatedAt(), byCustomerIdAndAndJewelryId.getStatus(), request.getSize());
            } else {
                //insert
                Cart cart = new Cart();
                cart.setJewelryId(request.getJewelryId());
                cart.setQuantity(request.getQuantity());
                cart.setUserId(request.getCustomerId());
                cart.setCreatedAt(new Date(new java.util.Date().getTime()));
                cart.setStatus(ACTIVE_CART);
                cart.setSize(request.getSize());
                cartRepository.save(cart);
            }

            return true;
        }


    @Override
    public Boolean updateCart(AddCartRequest request) {
        if(!StringUtils.isEmpty(request.getPhoneNumber())) {
            EndUser endUserByPhoneNumber = endUserRepository.findEndUserByPhoneNumber(request.getPhoneNumber());
            if(endUserByPhoneNumber != null){
                request.setCustomerId(endUserByPhoneNumber.getAccountId());
            }
        }
        Cart byCustomerIdAndAndJewelryId = cartRepository.findByUserIdAndAndJewelryId(request.getCustomerId(), request.getJewelryId());
        if(byCustomerIdAndAndJewelryId != null){
            //update
            byCustomerIdAndAndJewelryId.setQuantity(byCustomerIdAndAndJewelryId.getQuantity() + request.getQuantity());
            byCustomerIdAndAndJewelryId.setUpdatedAt(new java.util.Date());
            byCustomerIdAndAndJewelryId.setSize(request.getSize());
            if(!StringUtils.isEmpty(request.getStatus())){
                byCustomerIdAndAndJewelryId.setStatus(request.getStatus());
            }
            cartRepository.updateByUserIdAndJewelryId(request.getCustomerId(), request.getJewelryId(), byCustomerIdAndAndJewelryId.getQuantity(), byCustomerIdAndAndJewelryId.getUpdatedAt(), byCustomerIdAndAndJewelryId.getStatus(), request.getSize());
        }
        return true;
    }

    @Override
    public Boolean deleteCart(DeleteCartRequest request) {
        cartRepository.deleteById(request.getCartId());
        return true;
    }

    @Override
    public Object detail(GetOrderDetailRequest request) {
        Orders order = ordersRepository.findById(request.getOrderId()).orElseThrow(() -> new DiamondShopException(400, "Order not found"));

        List<OrdersListAllUser> ordersListAllUsers = new ArrayList<>();
            OrdersListAllUser ordersListAllUser = new OrdersListAllUser();
            BeanUtils.copyProperties(order, ordersListAllUser);
            List<OrderDetail> allByUniqueOrderId = orderDetailRepository.findAllByUniqueOrderId(order.getUniqueOrderId());
            ordersListAllUser.setOrderDetails(allByUniqueOrderId);
            ordersListAllUser.setDeliveryInfo(deliveryRepository.findAllByOrderId(order.getUniqueOrderId()));
            //ordersListAllUser.setPhoneNumber(request.getPhoneNumber());
            ordersListAllUsers.add(ordersListAllUser);

        Meta meta = new Meta(request.getRequestId(), 200, "success", HttpStatus.OK.toString());
        BaseResponse response = new BaseResponse(meta, ordersListAllUsers);

        return response;
    }

    @Override
    public ResponseEntity<Object> orderListAllUser(GetListOrderRequest request) {
        if(request.getLimit() == null){
            request.setLimit(10);
        }
        if(request.getOffset() == null){
            request.setOffset(0);
        }
        Page<Orders> ordersPage = null;
        if(StringUtils.isEmpty(request.getStatus())){
            ordersPage = ordersRepository.findAllOrderByOrderByCreatedAtDesc(PageRequest.of(request.getOffset()/request.getLimit(), request.getLimit(), Sort.by(Sort.Direction.DESC, "id")));
        }else{
            ordersPage = ordersRepository.findAllOrderByStatusOrderByCreatedAtDesc(request.getStatus(), PageRequest.of(request.getOffset()/request.getLimit(), request.getLimit(), Sort.by(Sort.Direction.DESC, "id")));

        }
        List<Orders> orderDetails = ordersPage.getContent();
        List<OrdersListAllUser> ordersListAllUsers = new ArrayList<>();
        for(Orders order : orderDetails){
            OrdersListAllUser ordersListAllUser = new OrdersListAllUser();
            BeanUtils.copyProperties(order, ordersListAllUser);
            List<OrderDetail> allByUniqueOrderId = orderDetailRepository.findAllByUniqueOrderId(order.getUniqueOrderId());
            ordersListAllUser.setOrderDetails(allByUniqueOrderId);
            ordersListAllUser.setDeliveryInfo(deliveryRepository.findAllByOrderId(order.getUniqueOrderId()));
            ordersListAllUsers.add(ordersListAllUser);
        }
        for(OrdersListAllUser ordersListAllUser : ordersListAllUsers){
            Optional<EndUser> endUserByAccountId = endUserRepository.findEndUserByAccountId(ordersListAllUser.getCustomerId());
            EndUser endUser = endUserByAccountId.get();
            ordersListAllUser.setPhoneNumber(endUser != null ? endUser.getPhoneNumber() : null);
        }
        Meta meta = new Meta(request.getRequestId(), 200, "success", HttpStatus.OK.toString());
        meta.setLimit(request.getLimit());
        meta.setOffset(request.getOffset());
        meta.setTotal(Integer.valueOf(String.valueOf(ordersPage.getTotalElements()))) ;
        BaseResponse response = new BaseResponse(meta,ordersListAllUsers);

        return ResponseEntity.ok(response);
    }

    @Override
    public Boolean updateOrder(UpdateOrderRequest request) {
        EndUser endUserByPhoneNumber = endUserRepository.findEndUserByPhoneNumber(request.getPhoneNumber());

//        ordersRepository.findAllOrderByOrderByCreatedAtDesc()
        return null;
    }
}
