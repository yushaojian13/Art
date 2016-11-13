package com.ysj.art.meizhi.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yushaojian on 11/13/16.
 */

public class MeizhisDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Meizhis.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    // @formatter:off
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MeizhiPersistenceContract.MeizhiEntry.TABLE_NAME + " (" +
                    MeizhiPersistenceContract.MeizhiEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    MeizhiPersistenceContract.MeizhiEntry.COLUMN_URL + TEXT_TYPE + COMMA_SEP +
                    MeizhiPersistenceContract.MeizhiEntry.COLUMN_WIDTH + INTEGER_TYPE + COMMA_SEP +
                    MeizhiPersistenceContract.MeizhiEntry.COLUMN_HEIGHT + INTEGER_TYPE +
                    " )";
    // @formatter:on

    public MeizhisDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
