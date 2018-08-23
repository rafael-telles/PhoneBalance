package com.raftelti.phoneBalance.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

public class TelephonyUtils {
    public static boolean checkAvaliability(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    public static String getCarrierName(Context context) {
        return getCarrierName(context, 1);
    }

    public static String getCountryCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkCountryIso().toUpperCase();
    }

    public static int getSimCount(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<? extends TelephonyManager> clazz = telephony.getClass();
        try {
            Method method = clazz.getMethod("getSimState", int.class);
            int slot = 0;
            int state;
            do {
                state = (int) method.invoke(telephony, slot);
                slot++;
            } while (state == TelephonyManager.SIM_STATE_READY);
            return slot - 1;
        } catch (Exception ex) {
            return 1;
        }
    }

    public static String getCarrierName(Context context, int slot) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<? extends TelephonyManager> clazz = telephony.getClass();
        try {
            Method method = clazz.getMethod("getSimOperatorName", long.class);
            return (String) method.invoke(telephony, slot);
        } catch (Exception ex) {
            if (slot != 1) {
                return "";
            }
        }
        return telephony.getSimOperatorName();
    }
}
