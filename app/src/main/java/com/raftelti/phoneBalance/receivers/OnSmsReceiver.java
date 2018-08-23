package com.raftelti.phoneBalance.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

public class OnSmsReceiver extends BroadcastReceiver {

    private String LOG_TAG = OnSmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Prefs.requestMode.get().equals("sms")) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[0]);

            if (message.getOriginatingAddress().equals(Prefs.responseSmsNumber.get())) {
                String text = message.getDisplayMessageBody();

                BalanceManager.get().saveRecord(text);
            }
        }
    }
}
