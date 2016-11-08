package com.ysj.art;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by yushaojian on 11/8/16.
 */

public class ShortcutUtils {

    private static final String INSTALL_SHORTCUT   = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String UNINSTALL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    public static void installShortcut(@NonNull Context context, @NonNull String title, @DrawableRes int resId,
                                       @NonNull ComponentName target) {
        if (isShortcutExist(context, title)) {
            return;
        }

        // Intent targetIntent = new Intent(Intent.ACTION_MAIN);
        // targetIntent.setComponent(target);
        Intent targetIntent = new Intent(context, MainActivity.class);
        targetIntent.setAction(Intent.ACTION_MAIN);

        Intent intent = new Intent(INSTALL_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, resId));
        intent.putExtra("duplicate", false); // may it's already there so don't duplicate
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent);

        context.sendBroadcast(intent);
    }

    public static void uninstallShortcut(@NonNull Context context, @NonNull String title,
                                         @NonNull ComponentName target) {
        Intent targetIntent = new Intent(Intent.ACTION_MAIN);
        targetIntent.setComponent(target);

        Intent intent = new Intent(UNINSTALL_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, targetIntent);

        context.sendBroadcast(intent);
    }

    /**
     * 检查快捷方式是否存在 <br/>
     * <font color=red>注意：</font> 有些手机无法判断是否已经创建过快捷方式<br/>
     * 因此，在创建快捷方式时，请添加<br/>
     * shortcutIntent.putExtra("duplicate", false);// 不允许重复创建<br/>
     * 最好使用{@link #isShortCutExist(Context, String, Intent)}
     * 进行判断，因为可能有些应用生成的快捷方式名称是一样的的<br/>
     */
    public static boolean isShortcutExist(Context context, String title) {
        boolean result = false;
        try {
            ContentResolver cr = context.getContentResolver();
            Uri uri = getUriFromLauncher(context);
            Cursor c = cr.query(uri, new String[]{"title"}, "title=? ", new String[]{title}, null);
            if (c != null && c.getCount() > 0) {
                result = true;
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 不一定所有的手机都有效，因为国内大部分手机的桌面不是系统原生的<br/>
     * 更多请参考{@link #isShortcutExist(Context, String)}<br/>
     * 桌面有两种，系统桌面(ROM自带)与第三方桌面，一般只考虑系统自带<br/>
     * 第三方桌面如果没有实现系统响应的方法是无法判断的，比如GO桌面<br/>
     */
    public static boolean isShortcutExist(Context context, String title, Intent intent) {
        boolean result = false;
        try {
            ContentResolver cr = context.getContentResolver();
            Uri uri = getUriFromLauncher(context);
            Cursor c = cr.query(uri, new String[]{"title", "intent"}, "title=?  and intent=?",
                    new String[]{title, intent.toUri(0)}, null);
            if (c != null && c.getCount() > 0) {
                result = true;
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        }
        return result;
    }

    private static Uri getUriFromLauncher(Context context) {
        StringBuilder uriStr = new StringBuilder();
        String authority = LauncherUtil.getAuthorityFromPermissionDefault(context);
        if (authority == null || authority.trim().equals("")) {
            authority = LauncherUtil.getAuthorityFromPermission(context, LauncherUtil.getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS");
        }
        uriStr.append("content://");
        if (TextUtils.isEmpty(authority)) {
            int sdkInt = android.os.Build.VERSION.SDK_INT;
            if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                uriStr.append("com.android.launcher.settings");
            } else if (sdkInt < 19) {// Android 4.4以下
                uriStr.append("com.android.launcher2.settings");
            } else {// 4.4以及以上
                uriStr.append("com.android.launcher3.settings");
            }
        } else {
            uriStr.append(authority);
        }
        uriStr.append("/favorites?notify=true");
        return Uri.parse(uriStr.toString());
    }

}
