package functions;

public class UtilInputVerificator {

    public static boolean isValidString(String str) {
        return !(str.isEmpty() || !UtilInputVerificator.isNumeric(str));
    }

    private static boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
    }

    public static boolean isValidDigit(String str) {
        return Integer.parseInt(str) > 0 && Integer.parseInt(str) <= 10;
    }

    public static boolean hasEqualsDigits(int digits, String str) {
        return str.length() == digits;
    }

    public static boolean startsWithCero(String str) {
        return str.startsWith("0");
    }

    public static String returnWithoutCero(String str) {
        return str.substring(1);
    }
}