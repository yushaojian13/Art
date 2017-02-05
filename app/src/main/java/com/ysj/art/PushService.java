package com.ysj.art;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ysj.log.L;

public class PushService extends Service {

    public static final String EXTRA = "extra";

    public PushService() {
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

        int id = intent.getExtras().getInt(EXTRA);
        L.d(id);
        Intent resultIntent = new Intent(getApplicationContext(), DetailActivity.class);
//        resultIntent.setAction(String.valueOf(id)); // 杀掉应用后多条消息只有一条能打开DetailActivity，因为系统认为多个任务是等价的，加上这个冗余信息可以多次打开
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra(DetailActivity.EXTRA, id);
        getApplicationContext().startActivity(resultIntent);

        return super.onStartCommand(intent, flags, startId);
    }
}
