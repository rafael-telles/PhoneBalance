package com.raftelti.phoneBalance.utils;

import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.ui.main.MainActivity;

/**
 * Created by Rafael on 18/03/2015.
 */
public class DeviceUtils {
    public static boolean isTablet(MainActivity activity) {
        return activity.getResources().getBoolean(R.bool.is_tablet);
    }
}
