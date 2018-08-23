package com.raftelti.phoneBalance.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootReceiver extends BroadcastReceiver {

    public static final String LOG_TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmReceiver.startAlarm(context);
    }
}
