package vn.fpt.diamond_shop.response;

import lombok.Data;

import java.util.Date;


@Data
public class CouponsResponse {
    private int id;
    private String couponsCode;
    private String type;
    private String discountType;
    private int discountPercent;
    private int value;
    private int quantity;
    private String expirationDate;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
}
