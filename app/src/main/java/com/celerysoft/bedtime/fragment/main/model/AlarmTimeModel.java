package com.celerysoft.bedtime.fragment.main.model;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by admin on 16/4/28.
 *
 */
public class AlarmTimeModel {
    private static final long DAYS_OF_A_WEEK = 7;
    private static final long MILLISECONDS_OF_A_DAY = 24 * 60 * 60 * 1000;
    private static final long MILLISECOND_OF_30_MINUTES = 30 * 60 * 1000;

    private Context mContext;

    public AlarmTimeModel(Context context) {
        mContext = context;
    }

    /**
     * // TODO remove this method in later version.
     * @deprecated on 1.2.12, use {@link #findNextAlarm2()} to instead of.
     * @return next alarm
     */
    @Deprecated
    @SuppressWarnings("unused")
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
        calendarCurrentDayBedTime.set(Calendar.MILLISECOND, 0);
        if (currentDayBedTime.isBedTimeInPrevDay()) {
            calendarCurrentDayBedTime.setTimeInMillis(calendarCurrentDayBedTime.getTimeInMillis() - MILLISECONDS_OF_A_DAY);
        }

        if (calendarNow.before(calendarCurrentDayBedTime)) {
            Calendar calendar30minutesBeforeCurrentDayBedTime = (Calendar) calendarCurrentDayBedTime.clone();
            calendar30minutesBeforeCurrentDayBedTime.setTimeInMillis(calendarCurrentDayBedTime.getTimeInMillis() - MILLISECOND_OF_30_MINUTES);

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
//            calendar30minutesBeforeNextDayBedTime.set(Calendar.DAY_OF_WEEK, nextDayBedTime.getActualDayOfWeek());
            calendar30minutesBeforeNextDayBedTime.set(Calendar.HOUR_OF_DAY, nextDayBedTime.getHour());
            calendar30minutesBeforeNextDayBedTime.set(Calendar.MINUTE, nextDayBedTime.getMinute());
            calendar30minutesBeforeNextDayBedTime.set(Calendar.SECOND, 0);
            calendar30minutesBeforeNextDayBedTime.set(Calendar.MILLISECOND, 0);
            //noinspection WrongConstant
            if (nextDayBedTime.isBedTimeInPrevDay() && (calendar30minutesBeforeNextDayBedTime.get(Calendar.DAY_OF_WEEK) != nextDayBedTime.getActualDayOfWeek())) {
                calendar30minutesBeforeNextDayBedTime.setTimeInMillis(calendar30minutesBeforeNextDayBedTime.getTimeInMillis() - MILLISECONDS_OF_A_DAY - MILLISECOND_OF_30_MINUTES);
            } else {
                calendar30minutesBeforeNextDayBedTime.setTimeInMillis(calendar30minutesBeforeNextDayBedTime.getTimeInMillis() - MILLISECOND_OF_30_MINUTES);
            }

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

    /**
     * find next alarm
     * @return next alarm
     */
    public AlarmTimeBean findNextAlarm2() {
        boolean nextWeek = false;
        AlarmTimeBean alarmTime = new AlarmTimeBean();

        Calendar calendarNow = Calendar.getInstance();

        int dayOfWeek = calendarNow.get(Calendar.DAY_OF_WEEK);

        BedTimeModel bedTimeModel = new BedTimeModel(mContext);
        while (true) {
            BedTimeBean bedTime = bedTimeModel.findBedTimeByDayOfTheWeek(dayOfWeek);

            Calendar calendarBedTime = Calendar.getInstance();
            calendarBedTime.set(Calendar.DAY_OF_WEEK, bedTime.getActualDayOfWeek());
            calendarBedTime.set(Calendar.HOUR_OF_DAY, bedTime.getHour());
            calendarBedTime.set(Calendar.MINUTE, bedTime.getMinute());
            calendarBedTime.set(Calendar.SECOND, 0);
            calendarBedTime.set(Calendar.MILLISECOND, 0);
            if (nextWeek) {
                calendarBedTime.setTimeInMillis(calendarBedTime.getTimeInMillis() + DAYS_OF_A_WEEK * MILLISECONDS_OF_A_DAY);
            }

            Calendar calendar30MinutesBeforeBedTime = (Calendar) calendarBedTime.clone();
            calendar30MinutesBeforeBedTime.setTimeInMillis(calendar30MinutesBeforeBedTime.getTimeInMillis() - MILLISECOND_OF_30_MINUTES);

            if (calendarNow.before(calendar30MinutesBeforeBedTime)) {
                alarmTime.setDayOfTheWeek(calendar30MinutesBeforeBedTime.get(Calendar.DAY_OF_WEEK));
                alarmTime.setHour(calendar30MinutesBeforeBedTime.get(Calendar.HOUR_OF_DAY));
                alarmTime.setMinute(calendar30MinutesBeforeBedTime.get(Calendar.MINUTE));
                alarmTime.setType(AlarmTimeBean.Type.GO_BED);
                alarmTime.setCalendar(calendar30MinutesBeforeBedTime);
                break;
            }

            if (calendarNow.before(calendarBedTime)) {
                alarmTime.setDayOfTheWeek(bedTime.getActualDayOfWeek());
                alarmTime.setHour(bedTime.getHour());
                alarmTime.setMinute(bedTime.getMinute());
                alarmTime.setType(AlarmTimeBean.Type.BED_TIME);
                alarmTime.setCalendar(calendarBedTime);
                break;
            }

            if (dayOfWeek == Calendar.SATURDAY) {
                dayOfWeek = 1;
                nextWeek = true;
            } else {
                dayOfWeek++;
            }
        }

        return alarmTime;
    }
}
