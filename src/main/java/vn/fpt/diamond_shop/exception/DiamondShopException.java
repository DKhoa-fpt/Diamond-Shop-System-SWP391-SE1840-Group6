package vn.fpt.diamond_shop.exception;

public class DiamondShopException extends RuntimeException {
    private int businessCode;
    private String message;

    public DiamondShopException(int businessCode, String message) {
        super(message);
        this.businessCode = businessCode;
        this.message = message;
    }

    public DiamondShopException(String message) {
        super(message);
        this.message = message;
        this.businessCode = 400;
    }

    public DiamondShopException(DiamondShopExceptionEnum e) {
        super(e.getMessage());
        this.businessCode = e.getBusinessCode();
        this.message = e.getMessage();
    }
}
