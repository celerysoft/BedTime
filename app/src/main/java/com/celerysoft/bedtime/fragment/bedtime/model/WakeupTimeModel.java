package com.celerysoft.bedtime.fragment.bedtime.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.util.Const;

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

    public WakeupTimeBean findBeanByDayOfTheWeek(int dayOfTheWeek) {
        WakeupTimeBean bean = new WakeupTimeBean();
        bean.setDayOfTheWeek(dayOfTheWeek);

        int wakeupHour = 8;
        int wakeupMinute = 30;
        switch (dayOfTheWeek) {
            case Const.MONDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_minute), 30);
                break;
            case Const.TUESDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_minute), 30);
                break;
            case Const.WEDNESDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_minute), 30);
                break;
            case Const.THURSDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_minute), 30);
                break;
            case Const.FRIDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_minute), 30);
                break;
            case Const.SATURDAY:
                wakeupHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_hour), 8);
                wakeupMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_minute), 30);
                break;
            case Const.SUNDAY:
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

    public void storeWakeupTime(WakeupTimeBean bean) {
        int dayOfTheWeek = bean.getDayOfTheWeek();
        int wakeupHour = bean.getWakeupHour();
        int wakeupMinute = bean.getWakeupMinute();

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        switch (dayOfTheWeek) {
            case Const.MONDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_minute), wakeupMinute);
                break;
            case Const.TUESDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_minute), wakeupMinute);
                break;
            case Const.WEDNESDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_minute), wakeupMinute);
                break;
            case Const.THURSDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_minute), wakeupMinute);
                break;
            case Const.FRIDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_minute), wakeupMinute);
                break;
            case Const.SATURDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_minute), wakeupMinute);
                break;
            case Const.SUNDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_hour), wakeupHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_minute), wakeupMinute);
                break;
            default:
                break;
        }
        editor.apply();
    }

    private void updateWakeupTime(WakeupTimeBean bean) {
        int dayOfTheWeek = bean.getDayOfTheWeek();
        switch (dayOfTheWeek) {
            case Const.MONDAY:
                break;
            case Const.TUESDAY:
                break;
            case Const.WEDNESDAY:
                break;
            case Const.THURSDAY:
                break;
            case Const.FRIDAY:
                break;
            case Const.SATURDAY:
                break;
            case Const.SUNDAY:
                break;
            default:
                break;
        }
    }
}
