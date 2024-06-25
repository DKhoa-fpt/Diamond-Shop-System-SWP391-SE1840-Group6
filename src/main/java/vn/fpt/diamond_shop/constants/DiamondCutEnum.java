package vn.fpt.diamond_shop.constants;

public enum DiamondCutEnum {
    IDEAL(0, 0),
    EXCELLENT(1, 1),
    VERY_GOOD(2, 2),
    GOOD(3, 4),
    FAIR(5, 7),
    POOR(8, 10);

    private int pointFrom;

    private int pointTo;

    DiamondCutEnum(int pointF, int pointTo) {
        this.pointFrom = pointF;
        this.pointTo = pointTo;
    }

    public int getPointFrom() {
        return pointFrom;
    }

    public int getPointTo() {
        return pointTo;
    }

    public static DiamondCutEnum getDiamondCutEnum(int point) {
        for (DiamondCutEnum diamondCutEnum : DiamondCutEnum.values()) {
            if (point >= diamondCutEnum.getPointFrom() && point <= diamondCutEnum.getPointTo()) {
                return diamondCutEnum;
            }
        }
        return null;
    }

    public static DiamondCutEnum getDiamondCutEnum(String cut) {
        for (DiamondCutEnum diamondCutEnum : DiamondCutEnum.values()) {
            if (diamondCutEnum.name().equalsIgnoreCase(cut)) {
                return diamondCutEnum;
            }
        }
        return null;
    }
}
