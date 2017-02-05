package com.ysj.art.android;

import static android.content.Context.NOTIFICATION_SERVICE;

import com.ysj.art.DetailActivity;
import com.ysj.art.PushService;
import com.ysj.art.R;
import com.ysj.log.L;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

/**
 * Created by yushaojian on 2/4/17.
 */

public class NotificationUtils {
    private static Intent sLastIntent;
    private static PendingIntent sLastPendingIntent;

    public static void notify(Context context, int id) {
        Intent resultIntent = new Intent(context, PushService.class);
        resultIntent.putExtra(DetailActivity.EXTRA, id);
//        resultIntent.setAction(String.valueOf(id));

        L.e(resultIntent + " " + sLastIntent + " " + resultIntent.filterEquals(sLastIntent) + " " + id);

        sLastIntent = resultIntent;

//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent resultPendingIntent = PendingIntent.getService(context, id, resultIntent,
                                                                      PendingIntent.FLAG_UPDATE_CURRENT);
        L.e(resultPendingIntent + " " + sLastPendingIntent);
        sLastPendingIntent = resultPendingIntent;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(context.getString(R.string.app_name)).setContentTitle(String.valueOf(id))
                .setContentText("Hello World!").setContentIntent(resultPendingIntent).setAutoCancel(true);

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(id, mBuilder.build());
    }
}
