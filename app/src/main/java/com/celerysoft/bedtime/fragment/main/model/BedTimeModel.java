package com.celerysoft.bedtime.fragment.main.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;

import java.util.Calendar;

/**
 * Created by admin on 16/4/27.
 *
 */
public class BedTimeModel {
    private static final String TAG = "BedTimeModel";

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private WakeupTimeModel mWakeupTimeModel;

    public BedTimeModel(Context context) {
        mContext = context;

        String sharedPreferencesKey = context.getString(R.string.shared_preferences_key_default);
        mSharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);

        mWakeupTimeModel = new WakeupTimeModel(context);
    }

    public BedTimeBean findBedTimeByDayOfTheWeek(int dayOfTheWeek) {
        BedTimeBean bean = new BedTimeBean();
        bean.setDayOfTheWeek(dayOfTheWeek);

        int goBedHour = 1;
        int goBedMinute = 0;
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                bean.setDayOfTheWeekDescription("MONDAY");
                goBedHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_monday_hour), 1);
                goBedMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_monday_minute), 0);
                break;
            case Calendar.TUESDAY:
                bean.setDayOfTheWeekDescription("TUESDAY");
                goBedHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_tuesday_hour), 1);
                goBedMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_tuesday_minute), 0);
                break;
            case Calendar.WEDNESDAY:
                bean.setDayOfTheWeekDescription("WEDNESDAY");
                goBedHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_wednesday_hour), 1);
                goBedMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_wednesday_minute), 0);
                break;
            case Calendar.THURSDAY:
                bean.setDayOfTheWeekDescription("THURSDAY");
                goBedHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_thursday_hour), 1);
                goBedMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_thursday_minute), 0);
                break;
            case Calendar.FRIDAY:
                bean.setDayOfTheWeekDescription("FRIDAY");
                goBedHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_friday_hour), 1);
                goBedMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_friday_minute), 0);
                break;
            case Calendar.SATURDAY:
                bean.setDayOfTheWeekDescription("SATURDAY");
                goBedHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_saturday_hour), 1);
                goBedMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_saturday_minute), 0);
                break;
            case Calendar.SUNDAY:
                bean.setDayOfTheWeekDescription("SUNDAY");
                goBedHour = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_sunday_hour), 1);
                goBedMinute = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_bed_time_sunday_minute), 0);
                break;
            default:
                Log.w(TAG, "findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
                throw new RuntimeException("findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
        }

        if (goBedMinute < 0) {
            bean.setBedTimeInPrevDay(true);
            goBedHour = (24 * 60 + goBedMinute) / 60;
            goBedMinute = (24 * 60 + goBedMinute) % 60;
        } else {
            bean.setBedTimeInPrevDay(false);
            goBedHour = goBedMinute / 60;
            goBedMinute = goBedMinute % 60;
        }
        bean.setHour(goBedHour);
        bean.setMinute(goBedMinute);

        return bean;
    }

    public BedTimeBean findNextBedTimeByDayOfTheWeek(int dayOfTheWeek) {
        int nextDayOfTheWeek;
        if (dayOfTheWeek == Calendar.SATURDAY) {
            nextDayOfTheWeek = Calendar.SUNDAY;
        } else {
            nextDayOfTheWeek = dayOfTheWeek + 1;
        }
        return findBedTimeByDayOfTheWeek(nextDayOfTheWeek);
    }

    public void storeBedTime(BedTimeBean bean) {
        int dayOfTheWeek = bean.getDayOfTheWeek();
        int bedHour = bean.getHour();
        int bedMinute = bean.getMinute();

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_bed_time_monday_hour), bedHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_bed_time_monday_minute), bedMinute);
                break;
            case Calendar.TUESDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_bed_time_tuesday_hour), bedHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_bed_time_tuesday_minute), bedMinute);
                break;
            case Calendar.WEDNESDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_bed_time_wednesday_hour), bedHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_bed_time_wednesday_minute), bedMinute);
                break;
            case Calendar.THURSDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_bed_time_thursday_hour), bedHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_bed_time_thursday_minute), bedMinute);
                break;
            case Calendar.FRIDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_bed_time_friday_hour), bedHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_bed_time_friday_minute), bedMinute);
                break;
            case Calendar.SATURDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_bed_time_saturday_hour), bedHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_bed_time_saturday_minute), bedMinute);
                break;
            case Calendar.SUNDAY:
                editor.putInt(mContext.getString(R.string.shared_preferences_key_bed_time_sunday_hour), bedHour)
                        .putInt(mContext.getString(R.string.shared_preferences_key_bed_time_sunday_minute), bedMinute);
                break;
            default:
                Log.w(TAG, "findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
                throw new RuntimeException("findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
        }
        editor.apply();
    }

    public void refreshBedTime() {
        for (int i = Calendar.SUNDAY; i < Calendar.SATURDAY + 1; ++i) {
            refreshBedTimeByDayOfTheWeek(i);
        }
    }

    public void refreshBedTimeByDayOfTheWeek(int dayOfTheWeek) {
        final int sleepTimeHour = getSleepHour();
        final int sleepTimeMinute = getSleepMinute();

        WakeupTimeBean wakeupTime = mWakeupTimeModel.findWakeUpTimeByDayOfTheWeek(dayOfTheWeek);
        final int wakeupTimeHour = wakeupTime.getHour();
        final int wakeupTimeMinute = wakeupTime.getMinute();

        int differenceInMinute = wakeupTimeHour * 60 + wakeupTimeMinute - (sleepTimeHour * 60 + sleepTimeMinute);

        BedTimeBean bedTime = new BedTimeBean();
        bedTime.setDayOfTheWeek(dayOfTheWeek);
        bedTime.setMinute(differenceInMinute);

        storeBedTime(bedTime);
    }

    private int getSleepHour() {
        return mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_personal_information_sleep_hour), 7);
    }

    private int getSleepMinute() {
        return mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_personal_information_sleep_minute), 50);
    }
}
