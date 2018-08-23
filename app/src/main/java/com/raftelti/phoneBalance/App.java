package com.raftelti.phoneBalance;

import android.app.Application;

/**
 * Created by Rafael on 20/03/2015.
 */
public class App extends Application {
    public static boolean runningTutorial = false;
    private static App instance;

    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
