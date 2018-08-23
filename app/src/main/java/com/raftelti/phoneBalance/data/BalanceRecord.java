package com.raftelti.phoneBalance.data;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import com.raftelti.phoneBalance.R;
import com.raftelti.phoneBalance.utils.BalanceFormatter;

public class BalanceRecord {

    private Cursor mCursor;
    private long id;
    private double value;
    private double difference;
    private String message;
    private long timestamp;

    private BalanceRecord() {
    }

    public static BalanceRecord getFromCursor(Cursor cursor) throws CursorIndexOutOfBoundsException {
        BalanceRecord record = new BalanceRecord();

        record.mCursor = cursor;
        record.id = cursor.getLong(cursor.getColumnIndex(BalanceContract.BalanceEntry._ID));
        record.setValue(cursor.getDouble(cursor.getColumnIndex(BalanceContract.BalanceEntry.COLUMN_VALUE)));
        record.setMessage(cursor.getString(cursor.getColumnIndex(BalanceContract.BalanceEntry.COLUMN_MESSAGE)));
        record.setTimestamp(cursor.getLong(cursor.getColumnIndex(BalanceContract.BalanceEntry.COLUMN_TIMESTAMP)));

        if (cursor.moveToNext()) {
            double previousValue = cursor.getDouble(cursor.getColumnIndex(BalanceContract.BalanceEntry.COLUMN_VALUE));
            record.setDifference(record.getValue() - previousValue);
            cursor.moveToPrevious();
        }
        return record;
    }

    public void delete(Context context) {
        context.getContentResolver().delete(
                BalanceContract.BalanceEntry.CONTENT_URI,
                BalanceContract.BalanceEntry._ID + " = " + this.getId(),
                null);
    }

    public CharSequence getFormattedDifference() {
        return BalanceFormatter.formatBalance(Math.abs(getDifference()));
    }

    public int getDifferenceColor(Context context) {
        if (getDifference() >= 0) {
            return context.getResources().getColor(R.color.balance_up);
        } else {
            return context.getResources().getColor(R.color.balance_down);
        }
    }

    public CharSequence getFormattedValue() {
        return BalanceFormatter.formatBalance(value);
    }

    public double getValue() {
        return value;
    }

    private void setValue(double value) {
        this.value = value;
    }

    public double getDifference() {
        return difference;
    }

    private void setDifference(double difference) {
        this.difference = difference;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    private void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }
}
