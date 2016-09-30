package com.celerysoft.bedtime.fragment.bedtime.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.celerysoft.bedtime.R;

import java.util.Calendar;

/**
 * Created by admin on 16/4/25.
 * Wakeup time model
 */
public class WakeupTimeModel {
    private static final String TAG = "WakeupTimeModel";

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public WakeupTimeModel(Context context) {
        mContext = context;

        String sharedPreferencesKey = context.getString(R.string.shared_preferences_key_default);
        mSharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);
    }

    public WakeupTimeBean findWakeUpTimeByDayOfTheWeek(int dayOfTheWeek) {
        WakeupTimeBean bean = new WakeupTimeBean();
        bean.setDayOfTheWeek(dayOfTheWeek);

        int wakeupHour = 8;
        int wakeupMinute = 30;
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                bean.setDayOfTheWeekDescription("MONDAY");
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_minute), 30);
                break;
            case Calendar.TUESDAY:
                bean.setDayOfTheWeekDescription("TUESDAY");
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_minute), 30);
                break;
            case Calendar.WEDNESDAY:
                bean.setDayOfTheWeekDescription("WEDNESDAY");
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_minute), 30);
                break;
            case Calendar.THURSDAY:
                bean.setDayOfTheWeekDescription("THURSDAY");
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_minute), 30);
                break;
            case Calendar.FRIDAY:
                bean.setDayOfTheWeekDescription("FRIDAY");
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_minute), 30);
                break;
            case Calendar.SATURDAY:
                bean.setDayOfTheWeekDescription("SATURDAY");
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_minute), 30);
                break;
            case Calendar.SUNDAY:
                bean.setDayOfTheWeekDescription("SUNDAY");
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_minute), 30);
                break;
            default:
                Log.w(TAG, "findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
                throw new RuntimeException("findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
        }

        bean.setHour(wakeupHour);
        bean.setMinute(wakeupMinute);

        return bean;
    }

    public WakeupTimeBean findNextWakeUpTimeByDayOfTheWeek(int dayOfTheWeek) {
        int nextDayOfTheWeek;
        if (dayOfTheWeek == Calendar.SATURDAY) {
            nextDayOfTheWeek = Calendar.SUNDAY;
        } else {
            nextDayOfTheWeek = dayOfTheWeek + 1;
        }
        return findWakeUpTimeByDayOfTheWeek(nextDayOfTheWeek);
    }

    public void storeWakeupTime(WakeupTimeBean bean) {
        int dayOfTheWeek = bean.getDayOfTheWeek();
        int wakeupHour = bean.getHour();
        int wakeupMinute = bean.getMinute();

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_minute), wakeupMinute);
                break;
            case Calendar.TUESDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_minute), wakeupMinute);
                break;
            case Calendar.WEDNESDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_minute), wakeupMinute);
                break;
            case Calendar.THURSDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_minute), wakeupMinute);
                break;
            case Calendar.FRIDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_minute), wakeupMinute);
                break;
            case Calendar.SATURDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_minute), wakeupMinute);
                break;
            case Calendar.SUNDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_minute), wakeupMinute);
                break;
            default:
                Log.w(TAG, "storeWakeupTime with an illegal weekday, please use Calendar.SUNDAY, etc.");
                throw new RuntimeException("storeWakeupTime with an illegal weekday, please use Calendar.SUNDAY, etc.");
        }
        editor.apply();
    }
}
