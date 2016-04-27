package com.celerysoft.bedtime.fragment.bedtime.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.celerysoft.bedtime.R;

import java.util.Calendar;

/**
 * Created by admin on 16/4/25.
 */
public class WakeupTimeModel {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public WakeupTimeModel(Context context) {
        mContext = context;

        String sharedPreferencesKey = context.getString(R.string.shared_preferences_key_wakeup_time);
        mSharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);
    }

    public WakeupTimeBean findWakeUpTimeByDayOfTheWeek(int dayOfTheWeek) {
        WakeupTimeBean bean = new WakeupTimeBean();
        bean.setDayOfTheWeek(dayOfTheWeek);

        int wakeupHour = 8;
        int wakeupMinute = 30;
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_minute), 30);
                break;
            case Calendar.TUESDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_minute), 30);
                break;
            case Calendar.WEDNESDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_minute), 30);
                break;
            case Calendar.THURSDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_minute), 30);
                break;
            case Calendar.FRIDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_minute), 30);
                break;
            case Calendar.SATURDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_minute), 30);
                break;
            case Calendar.SUNDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_minute), 30);
                break;
            default:
                break;
        }

        bean.setWakeupHour(wakeupHour);
        bean.setWakeupMinute(wakeupMinute);

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
        int wakeupHour = bean.getWakeupHour();
        int wakeupMinute = bean.getWakeupMinute();

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
                break;
        }
        editor.apply();
    }
}
