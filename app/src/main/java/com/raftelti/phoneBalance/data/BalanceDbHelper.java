package com.raftelti.phoneBalance.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BalanceDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "main.db";

    public BalanceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_BALANCE_TABLE = "CREATE TABLE " + BalanceContract.BalanceEntry.TABLE_NAME + " ("
                + BalanceContract.BalanceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BalanceContract.BalanceEntry.COLUMN_VALUE + " REAL,"
                + BalanceContract.BalanceEntry.COLUMN_MESSAGE + " TEXT NOT NULL,"
                + BalanceContract.BalanceEntry.COLUMN_TIMESTAMP + " INTEGER NOT NULL"
                + ");";

        db.execSQL(SQL_CREATE_BALANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
