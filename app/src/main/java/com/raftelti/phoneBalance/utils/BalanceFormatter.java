package com.raftelti.phoneBalance.utils;

import com.github.mikephil.charting.utils.ValueFormatter;

/**
 * Created by Rafael on 23/03/2015.
 */
public class BalanceFormatter implements ValueFormatter {
    public static String formatBalance(double value) {
        return String.format("$ %.2f", value);
    }

    @Override
    public String getFormattedValue(float value) {
        return formatBalance(value);
    }
}
