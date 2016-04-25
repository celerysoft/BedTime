package com.celerysoft.bedtime.fragment.bedtime.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.celerysoft.bedtime.R;

/**
 * Created by admin on 16/4/25.
 */
public class WakeupTimeModel {

    SharedPreferences mSharedPreferences;

    public WakeupTimeModel(Context context) {
        String sharedPreferencesKey = context.getString(R.string.shared_preferences_key_wakeup_time);
        mSharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);
    }

    public WakeupTimeBean findBeanByDayOfTheWeek() {
        return null;
    }

    public void storeWakeupTime(WakeupTimeBean bean) {

    }

    private void updateWakeupTime(WakeupTimeBean bean) {

    }
}
