package com.raftelti.phoneBalance.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class BalanceProvider extends ContentProvider {

    public static final int BALANCE = 100;
    public static final int BALANCE_LAST = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BalanceDbHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BalanceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, BalanceContract.PATH_BALANCE, BALANCE);
        matcher.addURI(authority, BalanceContract.PATH_BALANCE + "/last", BALANCE_LAST);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new BalanceDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor ret;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case BALANCE:
                ret = mOpenHelper.getReadableDatabase().query(
                        BalanceContract.BalanceEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        BalanceContract.BalanceEntry.COLUMN_TIMESTAMP + " DESC",
                        null);
                break;
            case BALANCE_LAST:
                ret = mOpenHelper.getReadableDatabase().query(
                        BalanceContract.BalanceEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        BalanceContract.BalanceEntry.COLUMN_TIMESTAMP + " DESC",
                        "1");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        ret.setNotificationUri(getContext().getContentResolver(), uri);
        return ret;
    }

    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            default:
            case BALANCE:
                return BalanceContract.BalanceEntry.CONTENT_TYPE_DIR;
            case BALANCE_LAST:
                return BalanceContract.BalanceEntry.CONTENT_TYPE_ITEM;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case BALANCE:
                long _id = db.insert(BalanceContract.BalanceEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = BalanceContract.BalanceEntry.buildUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (selection == null) selection = "1";
        switch (match) {
            case BALANCE:
                rowsDeleted = db.delete(BalanceContract.BalanceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case BALANCE:
                rowsUpdated = db.update(BalanceContract.BalanceEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
