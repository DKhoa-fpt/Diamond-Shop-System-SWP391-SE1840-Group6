package vn.fpt.diamond_shop.service.Impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Cacheable(value = "otpCache", key = "#email")
    public String getOtp(String email) {
        return null;
    }

    @CachePut(value = "otpCache", key = "#email")
    public String cacheOtp(String email, String otp) {
        return otp;
    }

    @CacheEvict(value = "otpCache", key = "#email")
    public void deleteOtp(String email) {
    }
}