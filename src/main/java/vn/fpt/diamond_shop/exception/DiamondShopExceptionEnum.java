package vn.fpt.diamond_shop.exception;

public enum DiamondShopExceptionEnum {
    DIAMOND_NOT_AVAILABLE(401, "Diamond not available");
    private final int businessCode;
    private final String message;

    DiamondShopExceptionEnum(int businessCode, String message) {
        this.businessCode = businessCode;
        this.message = message;
    }

    public int getBusinessCode() {
        return businessCode;
    }

    public String getMessage() {
        return message;
    }
}
