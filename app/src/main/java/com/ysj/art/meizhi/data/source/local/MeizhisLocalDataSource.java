package com.ysj.art.meizhi.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.ysj.art.meizhi.data.Meizhi;
import com.ysj.art.meizhi.data.source.local.MeizhiPersistenceContract.MeizhiEntry;

import com.ysj.art.meizhi.data.source.MeizhisDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 妹纸本地数据源.
 * Created by yushaojian on 10/29/16.
 */

public class MeizhisLocalDataSource implements MeizhisDataSource {

    private static MeizhisLocalDataSource INSTANCE;

    private MeizhisDbHelper mDbHelper;

    // Prevent direct instantiation.
    private MeizhisLocalDataSource(@NonNull Context context) {
        mDbHelper = new MeizhisDbHelper(context);
    }

    public static MeizhisLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MeizhisLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getLatestMeizhis(@NonNull LoadMeizhisCallback callback) {
        List<Meizhi> tasks = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = { MeizhiEntry.COLUMN_URL, MeizhiEntry.COLUMN_WIDTH, MeizhiEntry.COLUMN_HEIGHT };

        Cursor c = db.query(MeizhiEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String url = c.getString(c.getColumnIndexOrThrow(MeizhiEntry.COLUMN_URL));
                int width = c.getInt(c.getColumnIndexOrThrow(MeizhiEntry.COLUMN_WIDTH));
                int height = c.getInt(c.getColumnIndexOrThrow(MeizhiEntry.COLUMN_HEIGHT));
                Meizhi task = new Meizhi(url, width, height);
                tasks.add(task);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (tasks.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onMeizhisLoaded(tasks);
        }
    }

    @Override
    public void saveMeizhi(@NonNull Meizhi meizhi) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MeizhiEntry.COLUMN_URL, meizhi.mUrl);
        values.put(MeizhiEntry.COLUMN_WIDTH, meizhi.mWidth);
        values.put(MeizhiEntry.COLUMN_HEIGHT, meizhi.mHeight);

        db.insert(MeizhiEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void saveMeizhis(@NonNull List<Meizhi> meizhis) {
        for (Meizhi meizhi : meizhis) {
            saveMeizhi(meizhi);
        }
    }

    @Override
    public void refreshMeizhis() {
        // Not required because the {@link MeizhisRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllMeizhis() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(MeizhiEntry.TABLE_NAME, null, null);

        db.close();
    }
}
