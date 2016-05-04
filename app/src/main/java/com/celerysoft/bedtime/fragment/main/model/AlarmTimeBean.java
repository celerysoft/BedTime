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
}
