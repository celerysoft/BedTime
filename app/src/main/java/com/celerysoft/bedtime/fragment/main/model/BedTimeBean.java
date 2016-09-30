package com.celerysoft.bedtime.fragment.main.model;

import com.celerysoft.bedtime.bean.BaseTimeBean;

import java.util.Calendar;

/**
 * Created by admin on 16/4/27.
 *
 */
public class BedTimeBean extends BaseTimeBean {
    private boolean mIsBedTimeInPrevDay;

    public boolean isBedTimeInPrevDay() {
        return mIsBedTimeInPrevDay;
    }

    public void setBedTimeInPrevDay(boolean bedTimeInPrevDay) {
        mIsBedTimeInPrevDay = bedTimeInPrevDay;

        int actualDayOfWeek;
        if (bedTimeInPrevDay) {
            actualDayOfWeek = getDayOfTheWeek() == Calendar.SUNDAY ? Calendar.SATURDAY : getDayOfTheWeek() - 1;
        } else {
            actualDayOfWeek = getDayOfTheWeek();
        }

        setActualDayOfWeek(actualDayOfWeek);
    }

    private int mActualDayOfWeek;

    public int getActualDayOfWeek() {
        return mActualDayOfWeek;
    }

    private void setActualDayOfWeek(int actualDayOfWeek) {
        this.mActualDayOfWeek = actualDayOfWeek;
    }
}
