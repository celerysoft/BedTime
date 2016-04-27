package com.celerysoft.bedtime.fragment.main.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.fragment.main.model.BedTimeBean;
import com.celerysoft.bedtime.fragment.main.model.BedTimeModel;
import com.celerysoft.bedtime.fragment.main.view.IViewMain;
import com.celerysoft.bedtime.receiver.BedTimeReceiver;
import com.celerysoft.bedtime.receiver.DeviceBootReceiver;

import java.util.Calendar;

/**
 * Created by Celery on 16/4/14.
 */
public class PresenterMain implements IPresenterMain {
    private IViewMain mView;

    private Context mContext;

    private WakeupTimeModel mWakeupTimeModel;
    private BedTimeModel mBedTimeModel;

    private Thread mCountDownThread;
    private boolean mIsCountDownThreadRun = false;


    public PresenterMain(IViewMain view) {
        mView = view;
        mContext = view.getContext();

        mWakeupTimeModel = new WakeupTimeModel(mContext);
        mBedTimeModel = new BedTimeModel(mContext);
    }

    @Override
    public void testNotification() {
        // TODO Delete test code below
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
    public void turnOnNotification() {
        enableAlarm(mContext);

        // Enable receive device boot completed even so that reset alarm
        enableBootCompletedReceiver();
    }

    @Override
    public void turnOffNotification() {
        disableAlarm();

        // Disable receive device boot completed even so that reset alarm
        disableBootCompletedReceiver();
    }

    /**
     * set current day (of week) alarm.
     * @param context context
     */
    public static void enableAlarm(Context context) {
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int nextDay = currentDay == Calendar.SATURDAY ? Calendar.SUNDAY : currentDay + 1;
        WakeupTimeModel wakeupTimeModel = new WakeupTimeModel(context);
        WakeupTimeBean wakeupTime = wakeupTimeModel.findWakeUpTimeByDayOfTheWeek(nextDay);
        int wakeupHour = wakeupTime.getHour();
        int wakeupMinute = wakeupTime.getMinute();

        //TODO handle sleep time

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, wakeupHour);
        calendar.set(Calendar.MINUTE, wakeupMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent setAlarmIntent = new Intent(context, BedTimeReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, setAlarmIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    /**
     * cancel alarm
     */
    private void disableAlarm() {
        AlarmManager alarmMgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, BedTimeReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        alarmMgr.cancel(alarmIntent);
    }

    private void enableBootCompletedReceiver() {
        ComponentName receiver = new ComponentName(mContext, DeviceBootReceiver.class);
        PackageManager pm = mContext.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableBootCompletedReceiver() {
        ComponentName receiver = new ComponentName(mContext, DeviceBootReceiver.class);
        PackageManager pm = mContext.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    public void startCountDownThread() {
        if (mCountDownThread == null) {
            mCountDownThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mIsCountDownThreadRun) {
                        Calendar calendarNow = Calendar.getInstance();
                        int currentDay = calendarNow.get(Calendar.DAY_OF_WEEK);
                        int currentHour = calendarNow.get(Calendar.HOUR_OF_DAY);
                        int currentMinute = calendarNow.get(Calendar.MINUTE);

                        BedTimeBean currentDayBedTime = mBedTimeModel.findBedTimeByDayOfTheWeek(currentDay);
                        WakeupTimeBean currentDayWakeupTime = mWakeupTimeModel.findWakeUpTimeByDayOfTheWeek(currentDay);

                        BedTimeBean nextDayBedTime = mBedTimeModel.findNextBedTimeByDayOfTheWeek(currentDay);
                        WakeupTimeBean nextDayWakeupTime = mWakeupTimeModel.findNextWakeUpTimeByDayOfTheWeek(currentDay);

                        Calendar calendarCurrentDayBedTime = Calendar.getInstance();
                        calendarCurrentDayBedTime.set(Calendar.DAY_OF_WEEK, currentDayBedTime.getActualDayOfWeek());
                        calendarCurrentDayBedTime.set(Calendar.HOUR_OF_DAY, currentDayBedTime.getHour());
                        calendarCurrentDayBedTime.set(Calendar.MINUTE, currentDayBedTime.getMinute());
                        calendarCurrentDayBedTime.set(Calendar.SECOND, 0);

                        Calendar calendarCurrentDayWakeupTime = Calendar.getInstance();
                        calendarCurrentDayWakeupTime.set(Calendar.DAY_OF_WEEK, currentDayWakeupTime.getDayOfTheWeek());
                        calendarCurrentDayWakeupTime.set(Calendar.HOUR_OF_DAY, currentDayWakeupTime.getHour());
                        calendarCurrentDayWakeupTime.set(Calendar.MINUTE, currentDayWakeupTime.getMinute());
                        calendarCurrentDayWakeupTime.set(Calendar.SECOND, 0);

                        Calendar calendarNextDayBedTime = Calendar.getInstance();
                        calendarNextDayBedTime.set(Calendar.DAY_OF_WEEK, nextDayBedTime.getActualDayOfWeek());
                        calendarNextDayBedTime.set(Calendar.HOUR_OF_DAY, nextDayBedTime.getHour());
                        calendarNextDayBedTime.set(Calendar.MINUTE, nextDayBedTime.getMinute());
                        calendarNextDayBedTime.set(Calendar.SECOND, 0);

                        Message msg = new Message();

                        int minuteUntilNotification;

                        if (calendarNow.before(calendarCurrentDayWakeupTime)) {
                            if (calendarNow.before(calendarCurrentDayBedTime)) {
                                msg.what = GO_BED;
                                minuteUntilNotification = (currentDayBedTime.getHour() - currentHour) * 60 + currentDayBedTime.getMinute() - currentMinute;
                            } else {
                                msg.what = GET_UP;
                                minuteUntilNotification = (currentDayWakeupTime.getHour() - currentHour) * 60 + currentDayWakeupTime.getMinute() - currentMinute;
                            }
                        } else {
                            int hour;
                            if (calendarNow.before(calendarNextDayBedTime)) {
                                msg.what = GO_BED;
                                if (nextDayBedTime.isBedTimeInPrevDay()) {
                                    hour = nextDayBedTime.getHour();
                                } else {
                                    hour = nextDayBedTime.getHour() + 24;
                                }
                                minuteUntilNotification = (hour - currentHour) * 60 + nextDayBedTime.getMinute() - currentMinute;
                            } else {
                                msg.what = GET_UP;
                                minuteUntilNotification = (24 + nextDayWakeupTime.getHour() - currentHour) * 60 + nextDayWakeupTime.getMinute() - currentMinute;
                            }
                        }

                        msg.arg1 = minuteUntilNotification / 60;
                        msg.arg2 = minuteUntilNotification % 60;
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
            mCountDownThread = null;
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
