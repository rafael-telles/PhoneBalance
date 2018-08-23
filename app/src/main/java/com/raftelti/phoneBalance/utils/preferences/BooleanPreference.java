package com.raftelti.phoneBalance.utils.preferences;

import com.raftelti.phoneBalance.App;

/**
 * Created by Rafael on 20/03/2015.
 */
public class BooleanPreference extends BasePreference<Boolean> {
    public BooleanPreference(String key, Boolean def) {
        super(key, def);
    }

    public BooleanPreference(int keyRes, Boolean def) {
        super(keyRes, def);
    }

    public BooleanPreference(int keyRes, int def) {
        super(keyRes, App.get().getResources().getBoolean(def));
    }

    @Override
    public Boolean get() {
        try {
            return Prefs.getPreferences().getBoolean(getKey(), getDefault());
        } catch (ClassCastException ex) {
            return Boolean.parseBoolean(Prefs.getPreferences().getString(getKey(), getDefault() + ""));
        }
    }

    @Override
    public void set(Boolean value) {
        Prefs.getPreferences().edit().putBoolean(getKey(), value).apply();
    }
}
