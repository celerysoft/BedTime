package com.celerysoft.bedtime.fragment.main.model;

import com.celerysoft.bedtime.bean.BaseTimeBean;

/**
 * Created by admin on 16/4/28.
 */
public class AlarmTimeBean extends BaseTimeBean {
    public enum Type {
        GO_BED,
        BED_TIME
    }

    private Type mType;
    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    @Override
    public boolean equals(Object o) {
        AlarmTimeBean bean;

        if (o instanceof AlarmTimeBean) {
            bean = (AlarmTimeBean) o;
        } else {
            return false;
        }

        if (!bean.getType().equals(getType())) {
            return false;
        }

        if (bean.getDayOfTheWeek() != getDayOfTheWeek()) {
            return false;
        }

        if (bean.getHour() != getHour()) {
            return false;
        }

        if (bean.getMinute() != getMinute()) {
            return false;
        }

        return true;
    }
}
