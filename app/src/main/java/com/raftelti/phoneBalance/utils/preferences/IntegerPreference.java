package com.raftelti.phoneBalance.utils.preferences;

/**
 * Created by Rafael on 20/03/2015.
 */
public class IntegerPreference extends BasePreference<Integer> {

    public IntegerPreference(String key, Integer def) {
        super(key, def);
    }

    public IntegerPreference(int keyRes, Integer def) {
        super(keyRes, def);
    }

    @Override
    public Integer get() {
        try {
            return Prefs.getPreferences().getInt(getKey(), getDefault());
        } catch (ClassCastException ex) {
            return Integer.parseInt(Prefs.getPreferences().getString(getKey(), getDefault() + ""));
        }
    }

    @Override
    public void set(Integer value) {
        Prefs.getPreferences().edit().putInt(getKey(), value).apply();
    }
}
