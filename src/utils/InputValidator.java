package utils;


public class InputValidator {

    public static boolean isValidStationName(String stationName) {
        return stationName != null && !stationName.trim().isEmpty();
    }

    public static boolean isValidPaymentAmount(int amount) {
        return amount > 0;
    }
}
