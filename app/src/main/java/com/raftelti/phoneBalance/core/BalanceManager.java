package com.raftelti.phoneBalance.core;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.raftelti.phoneBalance.App;
import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.core.requests.BalanceRequest;
import com.raftelti.phoneBalance.core.requests.BalanceRequestFactory;
import com.raftelti.phoneBalance.data.BalanceRecord;
import com.raftelti.phoneBalance.receivers.WidgetUpdateReceiver;
import com.raftelti.phoneBalance.ui.main.MainActivity;
import com.raftelti.phoneBalance.utils.preferences.Prefs;

import static com.raftelti.phoneBalance.data.BalanceContract.BalanceEntry;

/**
 * Created by Rafael on 19/03/2015.
 */
public class BalanceManager {

    public static final String LOG_TAG = BalanceManager.class.getSimpleName();
    private static BalanceManager instance;
    final private Context mContext;

    private BalanceManager(Context context) {
        mContext = context;
    }

    public static BalanceManager get() {
        if (instance == null) {
            instance = new BalanceManager(App.get());
        }
        return instance;
    }

    private static void notifyWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, WidgetUpdateReceiver.class);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        Intent intent = new Intent(context, WidgetUpdateReceiver.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
        context.sendBroadcast(intent);
    }

    public BalanceRequest getRequest() {
        return new BalanceRequestFactory().create(mContext);
    }

    public void requestUpdate() {
        Toast.makeText(mContext, mContext.getString(R.string.requesting_balance_text), Toast.LENGTH_SHORT).show();
        getRequest().execute(mContext);
    }

    public boolean isMessageValid(CharSequence message) {
        return BalanceExtractor.isValid(message);
    }

    public void saveRecord(String text) {
        double newValue = BalanceExtractor.extract(text);
        double oldValue = 0;

        BalanceRecord record = getBalance();
        if (record != null) {
            oldValue = record.getValue();
        }
        if (newValue != oldValue) {
            ContentValues values = new ContentValues();
            values.put(BalanceEntry.COLUMN_MESSAGE, text);
            values.put(BalanceEntry.COLUMN_VALUE, newValue);
            values.put(BalanceEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());

            mContext.getContentResolver().insert(BalanceEntry.CONTENT_URI, values);

            notifyWidgets(mContext);
            if (record != null) {
                sendNotification(mContext);
            }
        }

        Prefs.lastRequestTimestamp.set(System.currentTimeMillis());
    }

    public BalanceRecord getBalance() {
        Uri uri = BalanceEntry.CONTENT_URI.buildUpon().appendQueryParameter("LIMIT", "1").build();
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        BalanceRecord record = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            record = BalanceRecord.getFromCursor(cursor);
        }
        cursor.close();
        return record;
    }

        private void sendNotification(Context context) {
        BalanceRecord record = getBalance();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .setColor(record.getDifferenceColor(context))
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setSubText(record.getFormattedDifference())
                        .setContentTitle(context.getString(R.string.notification_title));
        if (record.getDifference() >= 0) {
            mBuilder = mBuilder
                    .setContentText(String.format(
                            context.getString(R.string.notification_text_up),
                            record.getFormattedValue()))
                    .setSubText(String.format(
                            context.getString(R.string.notification_subtext_up),
                            record.getFormattedDifference()));
        } else {
            mBuilder = mBuilder
                    .setContentText(String.format(
                            context.getString(R.string.notification_text_down),
                            record.getFormattedValue()))
                    .setSubText(String.format(
                            context.getString(R.string.notification_subtext_down),
                            record.getFormattedDifference()));
        }

        Intent resultIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
