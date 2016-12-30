package com.celerysoft.bedtime.fragment.main.model;

import android.content.Context;
import android.util.Log;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.util.SPUtil;

import java.util.Calendar;

/**
 * Created by admin on 16/4/27.
 *
 */
public class BedTimeModel {
    private static final String TAG = "BedTimeModel";

    private Context mContext;

    private WakeupTimeModel mWakeupTimeModel;

    public BedTimeModel(Context context) {
        mContext = context;

        mWakeupTimeModel = new WakeupTimeModel(context);
    }

    public BedTimeBean findBedTimeByDayOfTheWeek(int dayOfTheWeek) {
        BedTimeBean bean = new BedTimeBean();
        bean.setDayOfTheWeek(dayOfTheWeek);

        int goBedHour;
        int goBedMinute;
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                bean.setDayOfTheWeekDescription("MONDAY");
                goBedMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_bed_time_monday_minute), 0);
                break;
            case Calendar.TUESDAY:
                bean.setDayOfTheWeekDescription("TUESDAY");
                goBedMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_bed_time_tuesday_minute), 0);
                break;
            case Calendar.WEDNESDAY:
                bean.setDayOfTheWeekDescription("WEDNESDAY");
                goBedMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_bed_time_wednesday_minute), 0);
                break;
            case Calendar.THURSDAY:
                bean.setDayOfTheWeekDescription("THURSDAY");
                goBedMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_bed_time_thursday_minute), 0);
                break;
            case Calendar.FRIDAY:
                bean.setDayOfTheWeekDescription("FRIDAY");
                goBedMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_bed_time_friday_minute), 0);
                break;
            case Calendar.SATURDAY:
                bean.setDayOfTheWeekDescription("SATURDAY");
                goBedMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_bed_time_saturday_minute), 0);
                break;
            case Calendar.SUNDAY:
                bean.setDayOfTheWeekDescription("SUNDAY");
                goBedMinute = SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_bed_time_sunday_minute), 0);
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

    private void storeBedTime(BedTimeBean bean) {
        int dayOfTheWeek = bean.getDayOfTheWeek();
        int bedHour = bean.getHour();
        int bedMinute = bean.getMinute();

        String keyOfBedHour;
        String keyOfBedMinute;
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                keyOfBedHour = mContext.getString(R.string.shared_preferences_key_bed_time_monday_hour);
                keyOfBedMinute = mContext.getString(R.string.shared_preferences_key_bed_time_monday_minute);
                break;
            case Calendar.TUESDAY:
                keyOfBedHour = mContext.getString(R.string.shared_preferences_key_bed_time_tuesday_hour);
                keyOfBedMinute = mContext.getString(R.string.shared_preferences_key_bed_time_tuesday_minute);
                break;
            case Calendar.WEDNESDAY:
                keyOfBedHour = mContext.getString(R.string.shared_preferences_key_bed_time_wednesday_hour);
                keyOfBedMinute = mContext.getString(R.string.shared_preferences_key_bed_time_wednesday_minute);
                break;
            case Calendar.THURSDAY:
                keyOfBedHour = mContext.getString(R.string.shared_preferences_key_bed_time_thursday_hour);
                keyOfBedMinute = mContext.getString(R.string.shared_preferences_key_bed_time_thursday_minute);
                break;
            case Calendar.FRIDAY:
                keyOfBedHour = mContext.getString(R.string.shared_preferences_key_bed_time_friday_hour);
                keyOfBedMinute = mContext.getString(R.string.shared_preferences_key_bed_time_friday_minute);
                break;
            case Calendar.SATURDAY:
                keyOfBedHour = mContext.getString(R.string.shared_preferences_key_bed_time_saturday_hour);
                keyOfBedMinute = mContext.getString(R.string.shared_preferences_key_bed_time_saturday_minute);
                break;
            case Calendar.SUNDAY:
                keyOfBedHour = mContext.getString(R.string.shared_preferences_key_bed_time_sunday_hour);
                keyOfBedMinute = mContext.getString(R.string.shared_preferences_key_bed_time_sunday_minute);
                break;
            default:
                Log.w(TAG, "findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
                throw new RuntimeException("findWakeUpTimeByDayOfTheWeek with an illegal weekday, please use Calendar.SUNDAY, etc.");
        }

        SPUtil.put(mContext, keyOfBedHour, bedHour);
        SPUtil.put(mContext, keyOfBedMinute, bedMinute);
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

    public int getSleepHour() {
        return SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_personal_information_sleep_hour), 7);
    }

    public int getSleepMinute() {
        return SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_personal_information_sleep_minute), 50);
    }
}
