package vn.fpt.diamond_shop.util;

import java.text.DecimalFormat;
import java.util.Random;

public class OTPUtils {
    private OTPUtils() {
    }

    public static String gen() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

}
