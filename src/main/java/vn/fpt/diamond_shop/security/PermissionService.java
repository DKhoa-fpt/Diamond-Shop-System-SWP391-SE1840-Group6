package vn.fpt.diamond_shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.fpt.diamond_shop.security.model.Role;
import vn.fpt.diamond_shop.security.model.RoleEnum;
import vn.fpt.diamond_shop.security.model.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class PermissionService {

    @Autowired
    private RoleRepository roleRepository;

    public Set<Role> roleChecker(String strRoles) {
        List<Role> listRoles = roleRepository.findAll();
        Set<Role> roles = new HashSet<>();
        switch (strRoles) {
            case "ADMIN":
                Role adminRole = roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(RuntimeException::new);
                if (!Objects.isNull(adminRole)) {
                    roles.addAll(listRoles);
                }
                break;
            case "SALE":
                Role staffRole = roleRepository.findByName(RoleEnum.SALE).orElseThrow(RuntimeException::new);
                if (!Objects.isNull(staffRole)) roles.add(staffRole);
                break;
            case "MANAGER":
                Role managerRole = roleRepository.findByName(RoleEnum.MANAGER).orElseThrow(RuntimeException::new);
                if (!Objects.isNull(managerRole)) roles.add(managerRole);
                break;
            case "END_USER":
                Role userRole = roleRepository.findByName(RoleEnum.END_USER).orElseThrow(RuntimeException::new);
                if (!Objects.isNull(userRole)) roles.add(userRole);
                break;
            default:
                roles.add(new Role());
        }
        return roles;
    }

}
