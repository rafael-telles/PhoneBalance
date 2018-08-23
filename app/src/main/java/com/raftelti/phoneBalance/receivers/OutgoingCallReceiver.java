package com.raftelti.phoneBalance.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.core.requests.UssdBalanceRequest;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

public class OutgoingCallReceiver extends BroadcastReceiver {

    public static final String LOG_TAG = OutgoingCallReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (shouldUpdate(intent)) {
            BalanceManager balanceManager = BalanceManager.get();
            if (balanceManager.getRequest() instanceof UssdBalanceRequest) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    String number = Uri.encode(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
                    String ussd = Uri.encode(Prefs.ussdCode.<String>get());
                    if (!ussd.equals(number)) {
                        balanceManager.requestUpdate();
                    }
                }
            } else {
                balanceManager.requestUpdate();
            }
        }
    }

    private boolean shouldUpdate(Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        return Prefs.updateOnCall.get() && state.equals(TelephonyManager.EXTRA_STATE_IDLE);
    }
}