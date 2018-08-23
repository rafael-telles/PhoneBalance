package com.raftelti.phoneBalance.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class BalanceContract {

    public static final String CONTENT_AUTHORITY = "com.raftelti.phoneBalance";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BALANCE = "balance";

    public static final class BalanceEntry implements BaseColumns {

        public static final String TABLE_NAME = "balance";

        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_MESSAGE = "message";
        public static final String COLUMN_TIMESTAMP = "timestamp";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BALANCE).build();

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String CONTENT_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BALANCE;
        public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BALANCE;
    }
}
