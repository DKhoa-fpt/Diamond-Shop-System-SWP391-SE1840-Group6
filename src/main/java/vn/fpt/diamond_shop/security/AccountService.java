package vn.fpt.diamond_shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.fpt.diamond_shop.model.Address;
import vn.fpt.diamond_shop.request.ChangePasswordRequest;
import vn.fpt.diamond_shop.model.EndUser;
import vn.fpt.diamond_shop.request.ChangeProfileRequest;
import vn.fpt.diamond_shop.request.SignUpRequest;
import vn.fpt.diamond_shop.repository.AddressRepository;
import vn.fpt.diamond_shop.repository.EndUserRepository;
import vn.fpt.diamond_shop.repository.UserRepository;
import vn.fpt.diamond_shop.repository.UserRoleRepository;
import vn.fpt.diamond_shop.response.ImageInformation;
import vn.fpt.diamond_shop.response.UserProfileResponse;
import vn.fpt.diamond_shop.security.exception.BadRequestException;
import vn.fpt.diamond_shop.security.model.*;
import vn.fpt.diamond_shop.service.ImageService;
import vn.fpt.diamond_shop.service.Impl.OtpService;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Date;

@Service
public class AccountService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private EndUserRepository endUserRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private TokenProvider tokenProvider;

    @Transactional
    public void register(SignUpRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        if (otpService.getOtp(registerRequest.getEmail()) == null ||
                !registerRequest.getOtp().equals(otpService.getOtp(registerRequest.getEmail()))) {
            throw new BadRequestException("Invalid OTP");
        }
        otpService.deleteOtp(registerRequest.getEmail());

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedAt(new Date());
        Long userId = userRepository.save(user).getId();

        UserRole userRole = new UserRole();
        userRole.setRoleId(RoleEnum.END_USER.getId());
        userRole.setAccountId(userId);
        userRoleRepository.save(userRole);

        Address address = new Address();
        address.setProvince(registerRequest.getProvince());
        address.setDistrict(registerRequest.getDistrict());
        address.setCity(registerRequest.getCity());
        address.setWard(registerRequest.getWard());
        address.setExtra(registerRequest.getExtra());
        Long addressId = addressRepository.save(address).getId();

        EndUser endUser = new EndUser();
        endUser.setPhoneNumber(registerRequest.getPhoneNumber());
        endUser.setFullName(registerRequest.getName());
        endUser.setCreateAt(OffsetDateTime.now());
        endUser.setAccountId(userId);
        endUser.setAddress(addressId);
        endUserRepository.save(endUser);

    }

    @Transactional
    public void registerV2(SignUpRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Long userId = userRepository.save(user).getId();

        UserRole userRole = new UserRole();
        userRole.setRoleId(RoleEnum.END_USER.getId());
        userRole.setAccountId(userId);
        userRoleRepository.save(userRole);

        Address address = new Address();
        address.setProvince(registerRequest.getProvince());
        address.setDistrict(registerRequest.getDistrict());
        address.setCity(registerRequest.getCity());
        address.setWard(registerRequest.getWard());
        address.setExtra(registerRequest.getExtra());
        Long addressId = addressRepository.save(address).getId();

        EndUser endUser = new EndUser();
        endUser.setPhoneNumber(registerRequest.getPhoneNumber());
        endUser.setFullName(registerRequest.getName());
        endUser.setCreateAt(OffsetDateTime.now());
        endUser.setAccountId(userId);
        endUser.setAddress(addressId);
        endUserRepository.save(endUser);

    }

    public void changeProfile(ChangeProfileRequest changeProfileRequest) {

        User account = userRepository.findByEmail(changeProfileRequest.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));
        EndUser endUser = endUserRepository.findEndUserByAccountId(account.getId()).orElseThrow(() -> new BadRequestException("EndUser not found"));
        Address address = addressRepository.findById(endUser.getAddress()).orElseGet(Address::new);

        address.setProvince(changeProfileRequest.getProvince());
        address.setDistrict(changeProfileRequest.getDistrict());
        address.setCity(changeProfileRequest.getCity());
        address.setWard(changeProfileRequest.getWard());
        address.setExtra(changeProfileRequest.getExtra());
        addressRepository.save(address);

        endUser.setFullName(changeProfileRequest.getFullName());
        endUser.setPhoneNumber(changeProfileRequest.getPhoneNumber());
        endUser.setDateOfBirth(changeProfileRequest.getDateOfBirth());


        // if user has not address, set address = accountId to add new address
        if (endUser.getAddress() == null) {
            endUser.setAddress(account.getId());
        }
        endUser.setAge(changeProfileRequest.getAge());
        endUser.setUpdateAt(OffsetDateTime.now());
        endUserRepository.save(endUser);
    }

    public UserProfileResponse profile(Long accountId) {
        User account = userRepository.findById(accountId).orElseThrow(() -> new BadRequestException("User not found"));
        EndUser endUser = endUserRepository.findEndUserByAccountId(accountId).orElseThrow(() -> new BadRequestException("EndUser not found"));
        Address address = addressRepository.findById(endUser.getAddress()).orElseThrow(() -> new BadRequestException("Address not found"));

        UserProfileResponse response = new UserProfileResponse();
        response.setEmail(account.getEmail());
        response.setFullName(endUser.getFullName());
        response.setPhoneNumber(endUser.getPhoneNumber());
        response.setAge(endUser.getAge() == null ? 0 : endUser.getAge());
        response.setDateOfBirth(endUser.getDateOfBirth());
        response.setAvatar(account.getImageUrl());
        response.setAddress(address);

        return response;
    }

    public void updateAvt(Long accountId, MultipartFile file) {
        User account = userRepository.findById(accountId).orElseThrow(() -> new BadRequestException("User not found"));
        ImageInformation imageInformation = imageService.push(file);
        account.setImageUrl(imageInformation.getImageName());
        userRepository.save(account);
    }

    public void changePass(ChangePasswordRequest request) {
        User account = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));
        if (passwordEncoder.matches(account.getPassword(), request.getPassword())) {
            throw new BadRequestException("New password must be different from old password");
        }
        if (otpService.getOtp(request.getEmail()) == null ||
                !otpService.getOtp(request.getEmail()).equals(request.getOtp())) {
            throw new BadRequestException("Invalid OTP");
        }
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(account);
        otpService.deleteOtp(request.getEmail());
    }
}
