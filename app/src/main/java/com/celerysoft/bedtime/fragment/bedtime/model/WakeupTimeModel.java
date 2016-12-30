package com.celerysoft.bedtime.fragment.bedtime.model;

import android.content.Context;
import android.util.Log;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.util.SPUtil;

import java.util.Calendar;

/**
 * Created by admin on 16/4/25.
 * Wakeup time model
 */
public class WakeupTimeModel {
    private static final String TAG = "WakeupTimeModel";

    private Context mContext;

    public WakeupTimeModel(Context context) {
        mContext = context;
    }

    public WakeupTimeBean findWakeUpTimeByDayOfTheWeek(int dayOfTheWeek) {
        WakeupTimeBean bean = new WakeupTimeBean();
        bean.setDayOfTheWeek(dayOfTheWeek);

        int wakeupHour;
        int wakeupMinute;
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                bean.setDayOfTheWeekDescription("MONDAY");
                wakeupHour = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_hour), 8);
                wakeupMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_minute), 30);
                break;
            case Calendar.TUESDAY:
                bean.setDayOfTheWeekDescription("TUESDAY");
                wakeupHour = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_hour), 8);
                wakeupMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_minute), 30);
                break;
            case Calendar.WEDNESDAY:
                bean.setDayOfTheWeekDescription("WEDNESDAY");
                wakeupHour = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_hour), 8);
                wakeupMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_minute), 30);
                break;
            case Calendar.THURSDAY:
                bean.setDayOfTheWeekDescription("THURSDAY");
                wakeupHour = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_hour), 8);
                wakeupMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_minute), 30);
                break;
            case Calendar.FRIDAY:
                bean.setDayOfTheWeekDescription("FRIDAY");
                wakeupHour = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_hour), 8);
                wakeupMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_minute), 30);
                break;
            case Calendar.SATURDAY:
                bean.setDayOfTheWeekDescription("SATURDAY");
                wakeupHour = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_hour), 8);
                wakeupMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_minute), 30);
                break;
            case Calendar.SUNDAY:
                bean.setDayOfTheWeekDescription("SUNDAY");
                wakeupHour = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_hour), 8);
                wakeupMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_minute), 30);
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

        String keyOfHour;
        String keyOfMinute;
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                keyOfHour = mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_hour);
                keyOfMinute = mContext.getString(R.string.shared_preferences_key_wakeup_time_monday_minute);
                break;
            case Calendar.TUESDAY:
                keyOfHour = mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_hour);
                keyOfMinute = mContext.getString(R.string.shared_preferences_key_wakeup_time_tuesday_minute);
                break;
            case Calendar.WEDNESDAY:
                keyOfHour = mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_hour);
                keyOfMinute = mContext.getString(R.string.shared_preferences_key_wakeup_time_wednesday_minute);
                break;
            case Calendar.THURSDAY:
                keyOfHour = mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_hour);
                keyOfMinute = mContext.getString(R.string.shared_preferences_key_wakeup_time_thursday_minute);
                break;
            case Calendar.FRIDAY:
                keyOfHour = mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_hour);
                keyOfMinute = mContext.getString(R.string.shared_preferences_key_wakeup_time_friday_minute);
                break;
            case Calendar.SATURDAY:
                keyOfHour = mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_hour);
                keyOfMinute = mContext.getString(R.string.shared_preferences_key_wakeup_time_saturday_minute);
                break;
            case Calendar.SUNDAY:
                keyOfHour = mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_hour);
                keyOfMinute = mContext.getString(R.string.shared_preferences_key_wakeup_time_sunday_minute);
                break;
            default:
                Log.w(TAG, "storeWakeupTime with an illegal weekday, please use Calendar.SUNDAY, etc.");
                throw new RuntimeException("storeWakeupTime with an illegal weekday, please use Calendar.SUNDAY, etc.");
        }
        SPUtil.put(mContext, keyOfHour, wakeupHour);
        SPUtil.put(mContext, keyOfMinute, wakeupMinute);
    }
}
