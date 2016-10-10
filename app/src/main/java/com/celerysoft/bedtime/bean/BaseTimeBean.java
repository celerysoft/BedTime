package com.celerysoft.bedtime.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Celery on 16/4/28.
 *
 */
public class BaseTimeBean implements Serializable {
    static final long serialVersionUID = -20160428140000L;

    private int mDayOfTheWeek;

    public int getDayOfTheWeek() {
        return mDayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        mDayOfTheWeek = dayOfTheWeek;
        mDayOfTheWeekDescription = deriveDayOfTheWeekDescription(dayOfTheWeek);
    }

    private String mDayOfTheWeekDescription;

    public String getDayOfTheWeekDescription() {
        return mDayOfTheWeekDescription;
    }

    // TODO remove redundant call.
    public void setDayOfTheWeekDescription(String dayOfTheWeekDescription) {
        mDayOfTheWeekDescription = dayOfTheWeekDescription;
    }

    private int mHour;

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        this.mHour = hour;
    }

    private int mMinute;

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        this.mMinute = minute;
    }

    private String deriveDayOfTheWeekDescription(int dayOfTheWeek) {
        switch (dayOfTheWeek) {
            case Calendar.MONDAY:
                return "MONDAY";
            case Calendar.TUESDAY:
                return "TUESDAY";
            case Calendar.WEDNESDAY:
                return "WEDNESDAY";
            case Calendar.THURSDAY:
                return "THURSDAY";
            case Calendar.FRIDAY:
                return "FRIDAY";
            case Calendar.SATURDAY:
                return "SATURDAY";
            case Calendar.SUNDAY:
                return "SUNDAY";
            default:
                return "WTF_DAY";
        }
    }
}
