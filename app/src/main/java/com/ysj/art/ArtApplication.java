package com.ysj.art;

import android.app.Application;
import android.content.Context;

/**
 * Created by yushaojian on 8/8/16.
 */
public class ArtApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
