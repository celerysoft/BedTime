package com.celerysoft.bedtime.bean;

/**
 * Created by admin on 16/4/28.
 */
public class BaseTimeBean {
    private int mDayOfTheWeek;

    public int getDayOfTheWeek() {
        return mDayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.mDayOfTheWeek = dayOfTheWeek;
    }

    private String mDayOfTheWeekDescription;

    public String getDayOfTheWeekDescription() {
        return mDayOfTheWeekDescription;
    }

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
}
