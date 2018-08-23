package com.raftelti.phoneBalance.core.requests;

import android.content.Context;

import com.raftelti.phoneBalance.utils.preferences.Prefs;

/**
 * Created by Rafael on 19/03/2015.
 */
public class BalanceRequestFactory {
    public BalanceRequest create(Context context) {
        if ("ussd".equals(Prefs.requestMode.get())) {
            return new UssdBalanceRequest();
        }
        return new SmsBalanceRequest();
    }
}
