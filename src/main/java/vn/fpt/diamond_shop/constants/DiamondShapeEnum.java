package vn.fpt.diamond_shop.constants;

import lombok.Getter;

@Getter
public enum DiamondShapeEnum {

    ROUND(10),
    PRINCESS(11),
    OVAL(12),
    MARQUISE(13),
    HEART(14),
    EMERALD(15),
    PEAR(17),
    ASSHER(18),
    CUSHION(19),
    RADIANT(20);
    private final int price;

    DiamondShapeEnum(int price) {
        this.price = price;
    }

}
