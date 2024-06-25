package vn.fpt.diamond_shop.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@UtilityClass
public class DateTimeUtils {

    public static Date parse(String date, String pattern) {
        try {
            if (date == null) {
                return null;
            }
            if (pattern == null || pattern.isEmpty()) {
                pattern = "yyyy-MM-dd";
            }

            return new SimpleDateFormat(pattern).parse(date);
        } catch (Exception e) {
            if (!Objects.equals(pattern, "yyyy-MM-dd")) {
                pattern = "yyyy-MM-dd";
                return parse(date, pattern);
            } else if (!Objects.equals(pattern, "yyyy/MM/dd")) {
                pattern = "yyyy/MM/dd";
                return parse(date, pattern);
            } else if (!Objects.equals(pattern, "yyyy-MM-dd HH:mm:ss")) {
                pattern = "yyyy-MM-dd HH:mm:ss";
                return parse(date, pattern);
            } else if (!Objects.equals(pattern, "yyyy/MM/dd HH:mm:ss")) {
                pattern = "yyyy-MM-dd HH:mm:ss";
                return parse(date, pattern);
            } else {
                throw new RuntimeException("Invalid date format");
            }
        }
    }

    public static String format(Date date, String pattern) {
        try {
            if (date == null) {
                return null;
            }
            if (pattern == null || pattern.isEmpty()) {
                pattern = "yyyy-MM-dd";
            }
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            if (!Objects.equals(pattern, "yyyy-MM-dd")) {
                pattern = "yyyy-MM-dd";
                return format(date, pattern);
            } else if (!Objects.equals(pattern, "yyyy/MM/dd")) {
                pattern = "yyyy/MM/dd";
                return format(date, pattern);
            } else if (!Objects.equals(pattern, "yyyy-MM-dd HH:mm:ss")) {
                pattern = "yyyy-MM-dd HH:mm:ss";
                return format(date, pattern);
            } else if (!Objects.equals(pattern, "yyyy/MM/dd HH:mm:ss")) {
                pattern = "yyyy-MM-dd HH:mm:ss";
                return format(date, pattern);
            } else {
                throw new RuntimeException("Invalid date format");
            }
        }
    }

}
