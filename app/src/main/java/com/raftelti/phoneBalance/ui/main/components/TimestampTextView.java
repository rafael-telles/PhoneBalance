package com.raftelti.phoneBalance.ui.main.components;

import android.content.Context;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.raftelti.phoneBalance.data.BalanceRecord;

/**
 * Created by Rafael on 30/03/2015.
 */
public class TimestampTextView extends TextView {

    final private Handler mHandler = new Handler();
    private Runnable mTask;
    private long mTimestamp;
    private String mFormat = "%s";

    public TimestampTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRecord(BalanceRecord record) {
        setTimestamp(record.getTimestamp());
    }

    public void setTimestamp(final long timestamp) {
        if (mTask != null) {
            mHandler.removeCallbacks(mTask);
        }

        mTimestamp = timestamp;
        mTask = new Runnable() {
            @Override
            public void run() {
                setText(getTimestampText());

                long delay = getDelay();
                if (mTask == this && delay > 0) {
                    mHandler.postDelayed(this, delay);
                }
            }
        };

        mHandler.post(mTask);
    }

    private long getDelay() {
        final long elapsed = System.currentTimeMillis() - mTimestamp;
        final long[] intervals = {DateUtils.MINUTE_IN_MILLIS, DateUtils.HOUR_IN_MILLIS, DateUtils.DAY_IN_MILLIS};
        for (int i = 0; i < intervals.length - 1; i++) {
            if (elapsed < intervals[i + 1]) {
                return elapsed % intervals[i];
            }
        }
        return -1;
    }

    private CharSequence getTimestampText() {
        return String.format(mFormat, DateUtils.getRelativeTimeSpanString(mTimestamp, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL));
    }

    public void setFormat(String format) {
        this.mFormat = format;
    }
}
