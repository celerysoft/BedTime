package com.celerysoft.bedtime.fragment.main.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

/**
 * Created by Celery on 16/4/14.
 */
public interface IViewMain {
    Context getContext();
    AppCompatTextView getTvLeftHour();
    AppCompatTextView getTvLeftHourLabel();
    AppCompatTextView getTvLeftMinute();
    AppCompatTextView getTvLeftMinuteLabel();
    AppCompatTextView getTvAction();
}
