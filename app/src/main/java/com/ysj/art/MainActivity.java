package com.ysj.art;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 100;

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

        // 2. 构建通知。Builder模式的构建部分
        Notification notification = builder.build();

        // 3. 发送通知。一个通知对应一个id，后续可以通过该id更新或取消对应的通知
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
