package utils;


public class InputValidator {

    /**
     * Validates if a string is a valid station name.
     * @param stationName The name of the station.
     * @return boolean indicating if the name is valid.
     */
    public static boolean isValidStationName(String stationName) {
        return stationName != null && !stationName.trim().isEmpty();
    }

    /**
     * Validates if the payment amount is valid (greater than zero).
     * @param amount The amount to check.
     * @return boolean indicating if the amount is valid.
     */
    public static boolean isValidPaymentAmount(int amount) {
        return amount > 0;
    }
}
