package vn.fpt.diamond_shop.security;


import vn.fpt.diamond_shop.repository.UserRoleRepository;
import vn.fpt.diamond_shop.security.exception.ResourceNotFoundException;
import vn.fpt.diamond_shop.security.model.Role;
import vn.fpt.diamond_shop.security.model.RoleEnum;
import vn.fpt.diamond_shop.security.model.User;
import vn.fpt.diamond_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.fpt.diamond_shop.security.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );
        List<UserRole> userRoleList = userRoleRepository.findAllByAccountId(user.getId());
        if (userRoleList.isEmpty()) throw new RuntimeException("User has no role");
        List<String> roles = new ArrayList<>();
        userRoleList.forEach(e -> {
            roles.add(Objects.requireNonNull(RoleEnum.getRoleEnumById(e.getRoleId())).name());
        });
        return UserPrincipal.create(user, roles);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        List<UserRole> userRoleList = userRoleRepository.findAllByAccountId(id);
        if (userRoleList.isEmpty()) throw new RuntimeException("User has no role");
        List<String> roles = new ArrayList<>();
        userRoleList.forEach(e -> {
            roles.add(Objects.requireNonNull(RoleEnum.getRoleEnumById(e.getRoleId())).name());
        });
        return UserPrincipal.create(user, roles);
    }
}