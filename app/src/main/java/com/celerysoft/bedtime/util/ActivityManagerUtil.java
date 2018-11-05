package com.celerysoft.bedtime.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;

import java.util.Stack;

/**
 * Created by admin on 16/4/25.
 * util to manager activity
 */
public class ActivityManagerUtil {
    private Stack<BaseActivity> mActivities;

    private static volatile ActivityManagerUtil sInstance = null;

    public static ActivityManagerUtil getInstance() {
        ActivityManagerUtil instance = sInstance;
        if (instance == null) {
            synchronized (ActivityManagerUtil.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new ActivityManagerUtil();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private ActivityManagerUtil() {
        mActivities = new Stack<>();
    }

    public void registerActivity(BaseActivity activity) {
        if (mActivities.empty()) {
            initAppLanguage(activity);
        }

        mActivities.push(activity);
    }

    public void unregisterActivity(BaseActivity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    private BaseActivity mCurrentActivity;
    public BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }
    public void setCurrentActivity(BaseActivity currentActivity) {
        mCurrentActivity = currentActivity;
    }

    public void exitApp() {
        mActivities.remove(mCurrentActivity);

        int count = mActivities.size();
        for (int i = 0; i < count; ++i) {
            BaseActivity activity = mActivities.get(i);
            activity.finish();
        }

        mCurrentActivity.finish();
        GlobalValue.isAppRunningForeground = false;
    }

    private void initAppLanguage(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        SettingsModel model = new SettingsModel(context);
        config.locale = model.getLocale();
        resources.updateConfiguration(config, dm);
    }
}
