package utils;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter {

    // RMB / China locale
    private static final NumberFormat RMB = NumberFormat.getCurrencyInstance(Locale.CHINA);

    public static String formatCurrency(int amount) {
        return RMB.format(amount);
    }
}
