package vn.fpt.diamond_shop.security.model;

public enum RoleEnum {

    ADMIN(1L),

    END_USER(2L),

    MANAGER(3L),

    SALE(4L);

    private Long id;

    RoleEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static RoleEnum getRoleEnumById(Long id) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getId().equals(id)) {
                return roleEnum;
            }
        }
        return null;
    }

}
