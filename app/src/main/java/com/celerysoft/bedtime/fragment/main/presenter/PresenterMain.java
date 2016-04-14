package com.celerysoft.bedtime.fragment.main.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.celerysoft.bedtime.fragment.main.view.IViewMain;
import com.celerysoft.bedtime.receiver.BedTimeReceiver;

/**
 * Created by Celery on 16/4/14.
 */
public class PresenterMain implements IPresenterMain {
    IViewMain mView;

    Context mContext;

    public PresenterMain(IViewMain view) {
        mView = view;
        mContext = view.getContext();
    }

    @Override
    public void turnOnNotification() {
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, BedTimeReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        5 * 1000, alarmIntent);
    }

    @Override
    public void turnOffNotification() {

    }
}
