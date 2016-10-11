package com.celerysoft.bedtime.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.celerysoft.bedtime.util.ActivityManagerUtil;
import com.celerysoft.bedtime.util.GlobalValue;
import com.celerysoft.bedtime.util.NotificationUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * Created by admin on 16/4/25.
 *
 */
public class BaseActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    protected ActivityManagerUtil mActivityManagerUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivityManagerUtil = ActivityManagerUtil.getInstance();

        mActivityManagerUtil.registerActivity(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!GlobalValue.isAppRunningForeground) {
            if (NotificationUtil.getInstance(this).hasNotifications()) {
                NotificationUtil.getInstance(this).cancelAllNotification();
            }

            GlobalValue.isAppRunningForeground = true;
            Log.v(TAG, "App turn to foreground.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);

        mActivityManagerUtil.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        GlobalValue.isAppRunningForeground = isRunningForeground();

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mActivityManagerUtil.getCurrentActivity() == this) {
            mActivityManagerUtil.setCurrentActivity(null);
        }
    }

    @Override
    protected void onDestroy() {
        mActivityManagerUtil.unregisterActivity(this);

        super.onDestroy();
    }

    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                if (runningAppProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.v(TAG, "App is still running foreground.");
                    return true;
                }
            }
        }

        Log.v(TAG, "App turn to background.");
        return false;
    }
}
