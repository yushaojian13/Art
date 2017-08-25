package com.ysj.art;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Activity基类，打印部分Activity生命周期
 * Created by yushaojian on 2017/8/25.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Notification", "onCreate: " + getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Notification", "onResume: " + getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Notification", "onPause: " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Notification", "onDestroy: " + getClass().getSimpleName());
    }
}
