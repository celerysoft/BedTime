package com.celerysoft.bedtime.fragment.main.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.fragment.main.view.IViewMain;
import com.celerysoft.bedtime.receiver.BedTimeReceiver;

import java.util.Calendar;

/**
 * Created by Celery on 16/4/14.
 */
public class PresenterMain implements IPresenterMain {
    private IViewMain mView;

    private Context mContext;

    private WakeupTimeModel mWakeupTimeModel;

    private Thread mCountDownThread;
    private boolean mIsCountDownThreadRun = false;


    public PresenterMain(IViewMain view) {
        mView = view;
        mContext = view.getContext();

        mWakeupTimeModel = new WakeupTimeModel(mContext);
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

    @Override
    public void startCountDownThread() {
        if (mCountDownThread == null) {
            mCountDownThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mIsCountDownThreadRun) {
                        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

                        final int sleepTimeMinute = 7 * 60 + 30;
                        WakeupTimeBean wakeupTime = mWakeupTimeModel.findBeanByDayOfTheWeek(currentDay);

                        int minuteUntilGoBed = (24 + wakeupTime.getWakeupHour() - currentHour) * 60
                                + wakeupTime.getWakeupMinute() - currentMinute - sleepTimeMinute;

                        Message msg = new Message();
                        int leftHour;
                        int leftMinute;

                        if (minuteUntilGoBed > 0) {
                            // Count down for go bed
                            msg.what = GO_BED;
                            leftHour = minuteUntilGoBed / 60;
                            leftMinute = minuteUntilGoBed % 60;
                        } else {
                            // Count down for get up
                            msg.what = GET_UP;
                            int minuteUntilGetUp = (24 + wakeupTime.getWakeupHour() - currentHour) * 60
                                    - wakeupTime.getWakeupMinute() - currentMinute;
                            leftHour = minuteUntilGetUp / 60;
                            leftMinute = minuteUntilGetUp % 60;
                        }

                        msg.arg1 = leftHour;
                        msg.arg2 = leftMinute;
                        mHandler.sendMessage(msg);

                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mCountDownThread.start();
        }

        mIsCountDownThreadRun = true;
    }

    @Override
    public void stopCountDownThread() {
        if (mCountDownThread != null) {
            mIsCountDownThreadRun = false;
        }
    }

    private static final int GO_BED = 1;
    private static final int GET_UP = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_BED:
                    mView.getTvAction().setText("Going Bed");
                    mView.getTvLeftHour().setText(Integer.toString(msg.arg1));
                    mView.getTvLeftMinute().setText(Integer.toString(msg.arg2));
                    break;
                case GET_UP:
                    mView.getTvAction().setText("Getting Up");
                    mView.getTvLeftHour().setText(Integer.toString(msg.arg1));
                    mView.getTvLeftMinute().setText(Integer.toString(msg.arg2));
                    break;
            }
        }
    };
}
