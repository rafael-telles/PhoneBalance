package com.raftelti.phoneBalance.utils.preferences;

import com.raftelti.phoneBalance.App;

/**
 * Created by Rafael on 20/03/2015.
 */
public class StringPreference extends BasePreference<String> {

    public StringPreference(int keyRes, int def) {
        super(keyRes, App.get().getString(def));
    }

    public StringPreference(String key, String def) {
        super(key, def);
    }

    public StringPreference(int keyRes, String def) {
        super(keyRes, def);
    }

    @Override
    public String get() {
        return Prefs.getPreferences().getString(getKey(), getDefault());
    }

    @Override
    public void set(String value) {
        Prefs.getPreferences().edit().putString(getKey(), value).apply();
    }
}
