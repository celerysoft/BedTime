package com.celerysoft.bedtime.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.celerysoft.bedtime.BuildConfig;
import com.celerysoft.bedtime.util.GlobalValue;

import java.util.List;

/**
 * Created by admin on 16/7/13.
 * Base application
 */
public class BaseApplication extends Application {
    private static final String TAG = BuildConfig.APPLICATION_ID.substring(0, 22);

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (GlobalValue.hasNotifications) {
                    if (!GlobalValue.isAppRunningForeground) {
                        cancelAllNotification();
                    }
                }

                GlobalValue.isAppRunningForeground = isRunningForeground();
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                GlobalValue.isAppRunningForeground = isRunningForeground();
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (runningAppProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    Log.v(TAG, "App turn to foreground.");
                    return true;
                }
            }
        }

        Log.v(TAG, "App turn to background.");
        return false;
    }

    private void cancelAllNotification() {
        NotificationManager manager = (NotificationManager) getSystemService((Context.NOTIFICATION_SERVICE));
        manager.cancelAll();

        GlobalValue.hasNotifications = false;
    }
}
