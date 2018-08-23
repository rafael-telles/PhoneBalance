package com.raftelti.phoneBalance.core.requests;

import android.content.Context;
import android.telephony.SmsManager;

import com.raftelti.phoneBalance.utils.preferences.Prefs;

import java.lang.reflect.Constructor;

/**
 * Created by Rafael on 19/03/2015.
 */
public class SmsBalanceRequest implements BalanceRequest {

    public static final String LOG_TAG = SmsBalanceRequest.class.getSimpleName();

    @Override
    public void execute(Context context) {
        String requestNumber = Prefs.requestSmsNumber.get();
        String responseNumber = Prefs.responseSmsNumber.get();
        String text = Prefs.smsText.get();
        if (!(text.isEmpty() || requestNumber.isEmpty() || responseNumber.isEmpty())) {
            sendSms(context, requestNumber, text);
        }
    }

    private void sendSms(Context context, String number, String text) {
        long subId = Long.parseLong(Prefs.simSlot.get()) + 1;
        SmsManager smsManager = getSmsManager(subId);
        smsManager.sendTextMessage(number, null, text, null, null);
    }

    private SmsManager getSmsManager(long subId) {
        Class<SmsManager> clazz = SmsManager.class;
        try {
            Constructor<SmsManager> constructor = clazz.getDeclaredConstructor(long.class);
            constructor.setAccessible(true);
            return constructor.newInstance(subId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SmsManager.getDefault();
    }
}
