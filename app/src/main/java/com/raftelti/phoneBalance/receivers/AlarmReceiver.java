package com.raftelti.phoneBalance.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

public class AlarmReceiver extends BroadcastReceiver {

    private static PendingIntent mAlarmIntent;

    public static void startAlarm(Context context) {
        stopAlarm(context);
        if (Prefs.updatePeriodically.get()) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent receiverIntent = new Intent(context, AlarmReceiver.class);
            mAlarmIntent = PendingIntent.getBroadcast(context, 0, receiverIntent, 0);

            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + Prefs.updateInterval.get(),
                    Prefs.updateInterval.get(),
                    mAlarmIntent);
        }
    }

    public static void stopAlarm(Context context) {
        if (mAlarmIntent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(mAlarmIntent);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        BalanceManager.get().requestUpdate();
    }
}
