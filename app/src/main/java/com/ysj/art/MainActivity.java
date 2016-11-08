package com.ysj.art;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onInstallShortcut(View view) {
        // Adding shortcut for MainActivity on Home screen
        ComponentName componentName = new ComponentName(getPackageName(), getClass().getCanonicalName());

        ShortcutUtils.installShortcut(getApplicationContext(), getString(R.string.app_name), R.mipmap.ic_launcher,
                                      componentName);
    }

    public void onUninstallShortcut(View view) {
        ComponentName componentName = new ComponentName(getPackageName(), getClass().getCanonicalName());
        ShortcutUtils.uninstallShortcut(getApplicationContext(), getString(R.string.app_name), componentName);
    }
}
