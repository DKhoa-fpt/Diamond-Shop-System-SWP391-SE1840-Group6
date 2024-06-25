package vn.fpt.diamond_shop.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.fpt.diamond_shop.constants.CouponsConditionEnum;
import vn.fpt.diamond_shop.constants.DiscountType;
import vn.fpt.diamond_shop.constants.MailTypeEnum;
import vn.fpt.diamond_shop.constants.StatusOrder;
import vn.fpt.diamond_shop.model.Coupon;
import vn.fpt.diamond_shop.model.EndUser;
import vn.fpt.diamond_shop.repository.*;
import vn.fpt.diamond_shop.request.CouponsMail;
import vn.fpt.diamond_shop.security.model.User;
import vn.fpt.diamond_shop.service.MailService;
import vn.fpt.diamond_shop.util.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BackGroundJobService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EndUserRepository endUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailHistoryRepository mailHistoryRepository;

    @Autowired
    private OrdersRepository ordersRepository;


    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndSendCoupon() {
        Date now = new Date();
        List<EndUser> listAccountId = endUserRepository.findAll();
        List<User> listUser = userRepository.findAllByIdIn(listAccountId.stream().map(EndUser::getAccountId).collect(Collectors.toList()));

        //get all usable coupons by expiration date
        List<Coupon> coupons = couponRepository.findAllByExpirationDateBefore(now);
        coupons.forEach(coupon -> {

            //Mail param contents
            CouponsMail mail = new CouponsMail();
            mail.setCode(coupon.getCouponsCode());
            mail.setExpiredDate(DateTimeUtils.format(coupon.getExpirationDate(), "dd/MM/yyyy HH:mm:ss"));
            mail.setPercent(coupon.getDiscountPercent());

            //Send mail to all user
            if (coupon.getDiscountType().equals(DiscountType.ALL.name())) {
                listUser.forEach(user -> {
                    if (!mailHistoryRepository.existsByMailAndTypeAndValue(user.getEmail(), MailTypeEnum.COUPON.name(), coupon.getCouponsCode())) {
                        mailService.sendCoupon(user.getEmail(), "DIAMOND COUPON", mail);
                    }
                });
            } else if (coupon.getDiscountType().equals(DiscountType.PERSONAL.name())) {
                listUser.forEach(user -> {
                    if (!mailHistoryRepository.existsByMailAndTypeAndValue(coupon.getType(), MailTypeEnum.COUPON.name(), coupon.getCouponsCode())) {
                        //Send mail to qualified person
                        //Check user's spending money
                        if (coupon.getType().equals(CouponsConditionEnum.CUSTOMERS_LOYAL.name())) {
                            if (ordersRepository.getCustomerAmount(user.getId(), StatusOrder.DONE.getValue()) >= coupon.getValue()) {
                                mailService.sendCoupon(user.getEmail(), "DIAMOND COUPON", mail);
                            }
                        }
                        if (coupon.getType().equals(CouponsConditionEnum.ACCOUNT_AGE.name())) {

                            LocalDate var1 = Instant.ofEpochMilli(user.getCreatedAt().getTime())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();

                            LocalDate var2 = Instant.ofEpochMilli(now.getTime())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();

                            Period period = Period.between(var1, var2);
                            int dayBetween = period.getDays();
                            //Check account age

                            if (dayBetween >= coupon.getValue()) {
                                mailService.sendCoupon(user.getEmail(), "DIAMOND COUPON", mail);
                            }
                        }
                    }
                });
            }
        });
    }
}
