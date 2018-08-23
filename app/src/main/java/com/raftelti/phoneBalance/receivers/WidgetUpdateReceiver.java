package com.raftelti.phoneBalance.receivers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.core.BalanceManager;
import com.raftelti.phoneBalance.data.BalanceRecord;
import com.raftelti.phoneBalance.ui.main.MainActivity;

public class WidgetUpdateReceiver extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        ComponentName thisWidget = new ComponentName(context, WidgetUpdateReceiver.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int allWidgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            BalanceRecord record = BalanceManager.get().getBalance();
            if (record != null) {
                remoteViews.setTextViewText(R.id.widget_balance, record.getFormattedValue());
            }

            Intent configIntent = new Intent(context, MainActivity.class);

            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

            remoteViews.setOnClickPendingIntent(R.id.widget_container, configPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
    }
}
