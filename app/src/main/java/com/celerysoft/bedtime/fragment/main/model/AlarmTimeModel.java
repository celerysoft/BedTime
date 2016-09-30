package com.celerysoft.bedtime.fragment.main.model;

import android.content.Context;

import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;

import java.util.Calendar;

/**
 * Created by admin on 16/4/28.
 *
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
            Calendar calendar30minutesBeforeCurrentDayBedTime = Calendar.getInstance();
            calendar30minutesBeforeCurrentDayBedTime.setTimeInMillis(calendarCurrentDayBedTime.getTimeInMillis());
            calendar30minutesBeforeCurrentDayBedTime.add(Calendar.MINUTE, -30);

            if (calendarNow.before(calendar30minutesBeforeCurrentDayBedTime)) {
                alarmTime.setDayOfTheWeek(calendar30minutesBeforeCurrentDayBedTime.get(Calendar.DAY_OF_WEEK));
                alarmTime.setHour(calendar30minutesBeforeCurrentDayBedTime.get(Calendar.HOUR_OF_DAY));
                alarmTime.setMinute(calendar30minutesBeforeCurrentDayBedTime.get(Calendar.MINUTE));
                alarmTime.setType(AlarmTimeBean.Type.GO_BED);
            } else {
                alarmTime.setDayOfTheWeek(currentDayBedTime.getActualDayOfWeek());
                alarmTime.setHour(currentDayBedTime.getHour());
                alarmTime.setMinute(currentDayBedTime.getMinute());
                alarmTime.setType(AlarmTimeBean.Type.BED_TIME);
            }
        } else {
            BedTimeBean nextDayBedTime = bedTimeModel.findNextBedTimeByDayOfTheWeek(currentDayOfWeek);

            Calendar calendar30minutesBeforeNextDayBedTime = Calendar.getInstance();
            calendar30minutesBeforeNextDayBedTime.set(Calendar.DAY_OF_WEEK, nextDayBedTime.getActualDayOfWeek());
            calendar30minutesBeforeNextDayBedTime.set(Calendar.HOUR_OF_DAY, nextDayBedTime.getHour());
            calendar30minutesBeforeNextDayBedTime.set(Calendar.MINUTE, nextDayBedTime.getMinute());
            calendar30minutesBeforeNextDayBedTime.set(Calendar.SECOND, 0);
            calendar30minutesBeforeNextDayBedTime.add(Calendar.MINUTE, -30);

            if (calendarNow.before(calendar30minutesBeforeNextDayBedTime)) {
                alarmTime.setDayOfTheWeek(calendar30minutesBeforeNextDayBedTime.get(Calendar.DAY_OF_WEEK));
                alarmTime.setHour(calendar30minutesBeforeNextDayBedTime.get(Calendar.HOUR_OF_DAY));
                alarmTime.setMinute(calendar30minutesBeforeNextDayBedTime.get(Calendar.MINUTE));
                alarmTime.setType(AlarmTimeBean.Type.GO_BED);
            } else {
                alarmTime.setDayOfTheWeek(nextDayBedTime.getActualDayOfWeek());
                alarmTime.setHour(nextDayBedTime.getHour());
                alarmTime.setMinute(nextDayBedTime.getMinute());
                alarmTime.setType(AlarmTimeBean.Type.BED_TIME);
            }
        }

        return alarmTime;
    }
}
