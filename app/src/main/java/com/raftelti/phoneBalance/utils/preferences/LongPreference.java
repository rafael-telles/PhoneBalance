package com.raftelti.phoneBalance.utils.preferences;

/**
 * Created by Rafael on 20/03/2015.
 */
public class LongPreference extends BasePreference<Long> {

    public LongPreference(String key, Long def) {
        super(key, def);
    }

    public LongPreference(int keyRes, Long def) {
        super(keyRes, def);
    }

    @Override
    public Long get() {
        try {
            return Prefs.getPreferences().getLong(getKey(), getDefault());
        } catch (ClassCastException ex) {
            return Long.parseLong(Prefs.getPreferences().getString(getKey(), getDefault() + ""));
        }
    }

    @Override
    public void set(Long value) {
        Prefs.getPreferences().edit().putLong(getKey(), value).apply();
    }
}
