package util;

public class CustomStringFormatter {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public static String truncate(String str, int length) {
        assert length >= 3 : length;
        length = length - 3;
        if (str.length() <= length)
            return str;
        else
            return str.substring(0, length) + "...";
    }
}
