package com.ysj.art;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ysj.art.android.NotificationUtils;
import com.ysj.log.L;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null) {
            return START_STICKY;
        }

        int id = intent.getIntExtra("extra", 100);

        NotificationUtils.notify(getApplicationContext(), id);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
