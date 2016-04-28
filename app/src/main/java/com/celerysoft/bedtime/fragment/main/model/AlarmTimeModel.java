package com.celerysoft.bedtime.fragment.main.model;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by admin on 16/4/28.
 */
public class AlarmTimeModel {
    private Context mContext;

    public AlarmTimeModel(Context context) {
        mContext = context;
    }

    public AlarmTimeBean findNextAlarm() {
        AlarmTimeBean alarmTime = new AlarmTimeBean();

        Calendar calendarNow = Calendar.getInstance();

        int currentDayOfWeek = calendarNow.get(Calendar.DAY_OF_WEEK);

        BedTimeModel bedTimeModel = new BedTimeModel(mContext);
        BedTimeBean currentDayBedTime = bedTimeModel.findBedTimeByDayOfTheWeek(currentDayOfWeek);

        Calendar calendarCurrentDayBedTime = Calendar.getInstance();
        calendarCurrentDayBedTime.set(Calendar.DAY_OF_WEEK, currentDayBedTime.getActualDayOfWeek());
        calendarCurrentDayBedTime.set(Calendar.HOUR_OF_DAY, currentDayBedTime.getHour());
        calendarCurrentDayBedTime.set(Calendar.MINUTE, currentDayBedTime.getMinute());
        calendarCurrentDayBedTime.set(Calendar.SECOND, 0);

        if (calendarNow.before(calendarCurrentDayBedTime)) {
            alarmTime.setDayOfTheWeek(currentDayBedTime.getActualDayOfWeek());
            alarmTime.setHour(currentDayBedTime.getHour());
            alarmTime.setMinute(currentDayBedTime.getMinute());
        } else {
            BedTimeBean nextDayBedTime = bedTimeModel.findNextBedTimeByDayOfTheWeek(currentDayOfWeek);
            alarmTime.setDayOfTheWeek(nextDayBedTime.getActualDayOfWeek());
            alarmTime.setHour(nextDayBedTime.getHour());
            alarmTime.setMinute(nextDayBedTime.getMinute());
        }

        return alarmTime;
    }
}
