package com.earth2me.essentials.utils;

import net.ess3.api.IEssentials;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import static com.earth2me.essentials.I18n.tl;


public class NumberUtil {
    static DecimalFormat twoDPlaces = new DecimalFormat("#,###.##");
    static DecimalFormat currencyFormat = new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US));
    static final NumberFormat commaSeparatedCurrencyFormat = NumberFormat.getInstance(Locale.US);

    public static String shortCurrency(final BigDecimal value, final IEssentials ess) {
        return ess.getSettings().getCurrencySymbol() + formatAsCurrency(value, ess);
    }

    public static String formatDouble(final double value) {
        twoDPlaces.setRoundingMode(RoundingMode.HALF_UP);
        return twoDPlaces.format(value);
    }

    public static String formatAsCurrency(final BigDecimal value, final IEssentials ess) {
        if (ess.getSettings().isCommaSeparatedMoney()) {
            commaSeparatedCurrencyFormat.setRoundingMode(RoundingMode.FLOOR);
            commaSeparatedCurrencyFormat.setGroupingUsed(true);
            commaSeparatedCurrencyFormat.setMinimumFractionDigits(2);
            commaSeparatedCurrencyFormat.setMaximumFractionDigits(2);
            String str = commaSeparatedCurrencyFormat.format(value);
            if (str.endsWith(".00")) {
                str = str.substring(0, str.length() - 3);
            }
            return str;
        }
        else {
            currencyFormat.setRoundingMode(RoundingMode.FLOOR);
            currencyFormat.setMinimumFractionDigits(2);
            currencyFormat.setMaximumFractionDigits(2);
            String str = currencyFormat.format(value);
            if (str.endsWith(".00")) {
                str = str.substring(0, str.length() - 3);
            }
            return str;
        }
    }

    public static String displayCurrency(final BigDecimal value, final IEssentials ess) {
        return tl("currency", ess.getSettings().getCurrencySymbol(), formatAsCurrency(value, ess));
    }

    public static String displayCurrencyExactly(final BigDecimal value, final IEssentials ess) {
        return tl("currency", ess.getSettings().getCurrencySymbol(), value.toPlainString());
    }

    public static boolean isInt(final String sInt) {
        try {
            Integer.parseInt(sInt);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
