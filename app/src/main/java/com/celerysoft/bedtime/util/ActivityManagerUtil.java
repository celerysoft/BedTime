package com.celerysoft.bedtime.util;

import com.celerysoft.bedtime.view.BaseActivity;

import java.util.ArrayList;

/**
 * Created by admin on 16/4/25.
 * util to manager activity
 */
public class ActivityManagerUtil {
    private ArrayList<BaseActivity> mActivities;

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
        mActivities = new ArrayList<>();
    }

    public void registerActivity(BaseActivity activity) {
        if (!mActivities.contains(activity)) {
            mActivities.add(activity);
        }
    }

    public void unregisterActivity(BaseActivity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }

    }

    public void exitApp(BaseActivity currentActivity) {
        mActivities.remove(currentActivity);

        int count = mActivities.size();
        for (int i = 0; i < count; ++i) {
            BaseActivity activity = mActivities.get(0);
            activity.finish();
        }

        currentActivity.finish();
    }
}
