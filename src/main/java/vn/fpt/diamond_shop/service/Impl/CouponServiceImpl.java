package vn.fpt.diamond_shop.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.fpt.diamond_shop.constants.CouponsConditionEnum;
import vn.fpt.diamond_shop.constants.DiscountType;
import vn.fpt.diamond_shop.constants.StatusOrder;
import vn.fpt.diamond_shop.exception.DiamondShopException;
import vn.fpt.diamond_shop.model.Coupon;
import vn.fpt.diamond_shop.model.CouponsHistory;
import vn.fpt.diamond_shop.repository.*;
import vn.fpt.diamond_shop.request.AddCouponRequest;
import vn.fpt.diamond_shop.request.ModifyCouponRequest;
import vn.fpt.diamond_shop.request.UsingCouponRequest;
import vn.fpt.diamond_shop.response.CouponsResponse;
import vn.fpt.diamond_shop.security.model.User;
import vn.fpt.diamond_shop.service.CouponService;
import vn.fpt.diamond_shop.util.DateTimeUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponsHistoryRepository couponsHistoryRepository;

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${test.mode:false}")
    private boolean testMode;

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon getCoupon(Long id, String code) {
        if (code != null) {
            return couponRepository.findByCouponsCode(code).orElseThrow(() -> new DiamondShopException("Coupon not found"));
        } else if (id != null) {
            return couponRepository.findById(id).orElseThrow(() -> new DiamondShopException("Coupon not found"));
        } else {
            throw new DiamondShopException("");
        }
    }

    @Override
    public Coupon checkCoupon(Long customerId, String couponCode) {
        Coupon coupon = couponRepository.findByCouponsCode(couponCode.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        if (coupon.getExpirationDate().before(new java.util.Date())) {
            throw new DiamondShopException("Coupon expired");
        }
        if (coupon.getQuantity() <= 0) {
            throw new DiamondShopException("Coupon out of stock");
        }
        if (couponsHistoryRepository.findByCouponsCodeAndCustomerId(couponCode, customerId).isPresent()) {
            throw new DiamondShopException("Coupon already used");
        }
        if (!coupon.getIsActive()) {
            throw new DiamondShopException("Coupon is deactivated");
        }
        if (coupon.getDiscountType().equals(DiscountType.PERSONAL.name())) {
            if (coupon.getType().equals(CouponsConditionEnum.CUSTOMERS_LOYAL.name())) {
                Long cusConsumeAmount = orderRepository.getCustomerAmount(customerId, StatusOrder.DONE.getValue());
                if (cusConsumeAmount < coupon.getValue()) {
                    throw new DiamondShopException("Customer not enough condition: Not spending enough money");
                }
            } else if (coupon.getType().equals(CouponsConditionEnum.ACCOUNT_AGE.name())) {
                Optional<User> var1 = userRepository.findById(customerId);
                User user = var1.orElseThrow(() -> new DiamondShopException("User not found"));
                Date now = new Date();
                Date accountAge = user.getCreatedAt();

                if (accountAge == null)
                    return coupon;
                LocalDate var2 = Instant.ofEpochMilli(accountAge.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                LocalDate var3 = Instant.ofEpochMilli(now.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                Period period = Period.between(var2, var3);
                int dayBetween = period.getDays();

                if (dayBetween < coupon.getValue()) {
                    throw new DiamondShopException("Customer not enough condition: Account age not enough");
                }
            }
        }
        return coupon;
    }

    @Override
    public CouponsResponse addCoupon(AddCouponRequest request) {
        if (couponRepository.findByCouponsCode(request.getCode()).isPresent()) {
            throw new DiamondShopException("Coupon code already exists");
        }
        Date expirationDate = DateTimeUtils.parse(request.getExpirationDate(), "yyyy-MM-dd");
        Date now = new Date();

        if (expirationDate.before(now)) {
            throw new DiamondShopException("Expiration date must be greater than current date");
        }
        if (request.getDiscountPercent() < 0 || request.getDiscountPercent() > 100) {
            throw new DiamondShopException("Discount percent must be between 0 and 100");
        }
        if (request.getQuantity() <= 0) {
            throw new DiamondShopException("Quantity must be greater than 0");
        }
        Coupon coupon = new Coupon();
        coupon.setCouponsCode(request.getCode().toUpperCase());
        coupon.setDiscountPercent(request.getDiscountPercent());
        coupon.setExpirationDate(expirationDate);
        coupon.setDiscountType(request.getDiscountType());
        coupon.setType(request.getType());
        coupon.setValue(request.getValue());
        coupon.setQuantity(request.getQuantity());
        coupon.setIsActive(true);
        coupon.setCreatedAt(new Date());

        Coupon var1 = couponRepository.save(coupon);
        CouponsResponse response = new CouponsResponse();
        BeanUtils.copyProperties(var1, response);

        return response;
    }

    @Override
    public void modifyCoupon(ModifyCouponRequest request) {
        Optional<Coupon> var1 = couponRepository.findById(request.getId());
        if (!var1.isPresent()) {
            throw new DiamondShopException("Coupon not found");
        }
        Date expirationDate = DateTimeUtils.parse(request.getExpirationDate(), "yyyy-MM-dd");
        Date now = new Date();

        if (expirationDate.before(now)) {
            throw new DiamondShopException("Expiration date must be greater than current date");
        }
        if (request.getDiscountPercent() < 0 || request.getDiscountPercent() > 100) {
            throw new DiamondShopException("Discount percent must be between 0 and 100");
        }
        if (request.getQuantity() <= 0) {
            throw new DiamondShopException("Quantity must be greater than 0");
        }
        Coupon coupon = var1.get();
        coupon.setCouponsCode(request.getCode().toUpperCase());
        coupon.setDiscountPercent(request.getDiscountPercent());
        coupon.setExpirationDate(DateTimeUtils.parse(request.getExpirationDate(), "yyyy-MM-dd"));
        coupon.setDiscountType(request.getDiscountType());
        coupon.setType(request.getType());
        coupon.setValue(request.getValue());
        coupon.setQuantity(request.getQuantity());
        coupon.setUpdatedDate(new Date());

        couponRepository.save(coupon);
    }

    @Override
    public void usingCoupon(UsingCouponRequest request) {
        if (testMode) {
            return;
        }
        Coupon coupon = couponRepository.findByCouponsCode(request.getCouponCode().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        this.checkCoupon(request.getUserId(), request.getCouponCode());

        coupon.setQuantity(coupon.getQuantity() - 1);
        couponRepository.save(coupon);

        CouponsHistory couponsHistory = new CouponsHistory();
        couponsHistory.setCouponsCode(request.getCouponCode());
        couponsHistory.setCustomerId(request.getUserId());
        couponsHistory.setOrderId(request.getOrderId());
        couponsHistory.setCreatedAt(new Date());
        couponsHistoryRepository.save(couponsHistory);

    }


    @Override
    public void deactivateCoupon(String couponCode) {
        Optional<Coupon> var1 = couponRepository.findByCouponsCode(couponCode);
        if (!var1.isPresent()) {
            throw new DiamondShopException("Coupon code not exists");
        }
        Coupon coupon = var1.get();
        coupon.setIsActive(false);
        couponRepository.save(coupon);
    }

    @Override
    public void activeCoupon(String couponCode) {
        Optional<Coupon> var1 = couponRepository.findByCouponsCode(couponCode);
        if (!var1.isPresent()) {
            throw new DiamondShopException("Coupon code not exists");
        }
        Coupon coupon = var1.get();
        coupon.setIsActive(true);
        couponRepository.save(coupon);
    }

}
