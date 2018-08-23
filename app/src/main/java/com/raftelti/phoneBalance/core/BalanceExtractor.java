package com.raftelti.phoneBalance.core;

import com.raftelti.phoneBalance.utils.preferences.Prefs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rafael on 19/03/2015.
 */
public class BalanceExtractor {
    private static final Pattern PATTERN = Pattern.compile("([0-9\\.,]+\\d)");

    public static double extract(CharSequence text) {
        final Matcher m = PATTERN.matcher(text);
        long rightPlace = Prefs.extractorRightPlace.get();
        for (int i = 0; i <= rightPlace - 1; i++) m.find();
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(',', '.'));
        }

        m.reset();
        if (m.find()) {
            return Double.parseDouble(m.group(1).replace(',', '.'));
        }
        return 0.0;
    }

    public static boolean isValid(CharSequence text) {
        final Matcher m = PATTERN.matcher(text);
        return m.find();
    }

    public static Pattern getPattern() {
        return PATTERN;
    }
}
