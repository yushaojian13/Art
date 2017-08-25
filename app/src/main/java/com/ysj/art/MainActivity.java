package com.ysj.art;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

public class MainActivity extends BaseActivity {

    private static final int NOTIFICATION_ID = 100;
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClick(View view) {
        sendNotification();
    }

    /**
     * 发送通知。分为三步：
     * 1. 设置通知属性
     * 2. 构建通知
     * 3. 发送通知
     */
    private void sendNotification() {
        // 1. 设置通知属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        // @formatter:off
        // Builder模式，流式调用，设置一条通知最基本的3个属性
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Hello World!");
        // @formatter:on

        // 指定一个操作：点击通知时打开ResultActivity。
        // 虽然通知操作是可选的，但至少应向通知添加一个操作。
//        Intent resultIntent = new Intent(this, ResultActivity.class);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);

        // 可以指定打开两个Activity：点击通知时打开数组中的最后一个，然后在它退出后打开前一个
//        Intent firstIntent = new Intent(this, MainActivity.class); // 如果MainActivity在Manifest中指定launchMode为singleTask，则如果点击通知时MainActivity在前台，会在调用ResultActivity的onCreate之前会连续调用MainActivity的onPause、onResume和onPause
//        Intent secondIntent = new Intent(this, ResultActivity.class);
//        PendingIntent resultPendingIntent = PendingIntent.getActivities(this, REQUEST_CODE,
//                                                                        new Intent[] { firstIntent, secondIntent },
//                                                                        PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);

        Intent firstIntent = new Intent(this, MainActivity.class); // 如果点击通知时MainActivity在任务堆栈中，则点击通知时MainActivity会destroy，不论其launchMode为何，也不论其是否在前台
        Intent secondIntent = new Intent(this, ResultActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(firstIntent);
        stackBuilder.addNextIntent(secondIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

                // 2. 构建通知。Builder模式的构建部分
        Notification notification = builder.build();

        // 3. 发送通知。一个通知对应一个id，后续可以通过该id更新或取消对应的通知
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
