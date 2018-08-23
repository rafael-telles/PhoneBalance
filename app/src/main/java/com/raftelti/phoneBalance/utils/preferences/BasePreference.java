package com.raftelti.phoneBalance.utils.preferences;

import com.raftelti.phoneBalance.App;

/**
 * Created by Rafael on 20/03/2015.
 */
public abstract class BasePreference<T> {
    private String mKey;
    private T mDefault;

    public BasePreference(int keyRes, T def) {
        this(App.get().getString(keyRes), def);
    }

    public BasePreference(String key, T def) {
        setKey(key);
        setDefault(def);
    }

    public abstract T get();

    public abstract void set(T value);

    String getKey() {
        return mKey;
    }

    void setKey(String mKey) {
        this.mKey = mKey;
    }

    T getDefault() {
        return mDefault;
    }

    void setDefault(T mDefault) {
        this.mDefault = mDefault;
    }
}
