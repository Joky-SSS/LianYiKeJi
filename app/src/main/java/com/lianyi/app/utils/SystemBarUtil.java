package com.lianyi.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class SystemBarUtil {
    private static ITint tint = new LollipopTint();

    public static void setStatusBarColor(Activity activity, int color) {
        if (tint != null) tint.setStatusBarColor(activity, color);
    }

    public static void translucentStatusBar(Activity activity, boolean hideShelter) {
        if (tint != null) tint.translucentStatusBar(activity, hideShelter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class LollipopTint implements ITint {
        @Override
        public void setStatusBarColor(Activity activity, int color) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            keepChildView(window);
        }

        @Override
        public void translucentStatusBar(Activity activity, boolean hideShelter) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (hideShelter) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            keepChildView(window);
        }

        private void keepChildView(Window window) {
            View childView = ((ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
            if (childView != null) {
                childView.setFitsSystemWindows(false);
                ViewCompat.requestApplyInsets(childView);
            }
        }
    }

    public interface ITint {
        void setStatusBarColor(Activity activity, int color);

        void translucentStatusBar(Activity activity, boolean hideShelter);
    }

    /** 设置字体图标样式 */
    public static boolean setStatusBarDarkMode(Activity activity, boolean darkMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = activity.getWindow().getDecorView();
                if (darkMode) decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                else decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                return true;
            } else if (setMIUIStatusBarDarkMode(activity, darkMode)) {
                return true;
            } else if (setFlymeStatusBarDarkIcon(activity, darkMode)) {
                return true;
            }
        }
        return false;
    }

    /** 小米修改MIUI */
    private static boolean setMIUIStatusBarDarkMode(Activity activity, boolean darkMode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /** 魅族修改Flyme */
    private static boolean setFlymeStatusBarDarkIcon(Activity activity, boolean darkMode) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkMode) value |= bit;
            else value &= ~bit;
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }
}