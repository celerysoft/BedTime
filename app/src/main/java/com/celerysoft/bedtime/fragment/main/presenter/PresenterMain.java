package com.celerysoft.bedtime.fragment.main.presenter;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.fragment.main.model.AlarmTimeBean;
import com.celerysoft.bedtime.fragment.main.model.AlarmTimeModel;
import com.celerysoft.bedtime.fragment.main.model.BedTimeBean;
import com.celerysoft.bedtime.fragment.main.model.BedTimeModel;
import com.celerysoft.bedtime.fragment.main.view.IViewMain;
import com.celerysoft.bedtime.receiver.BedTimeReceiver;
import com.celerysoft.bedtime.receiver.DeviceBootReceiver;
import com.celerysoft.bedtime.util.AlarmUtil;
import com.celerysoft.bedtime.util.Const;

import java.util.Calendar;
import java.util.Locale;

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

    private SharedPreferences mSharedPreferences;

    private static AlarmTimeBean mLastAlarm;

    public PresenterMain(IViewMain view) {
        mView = view;
        mContext = view.getContext();

        mWakeupTimeModel = new WakeupTimeModel(mContext);
        mBedTimeModel = new BedTimeModel(mContext);

        mSharedPreferences = mContext.getSharedPreferences(Const.getDefaultSharedPreferencesKey(mContext), Context.MODE_PRIVATE);
    }

    @Override
    public boolean getNotificationStatus() {
        return mSharedPreferences.getBoolean(mContext.getString(R.string.shared_preferences_key_open_notification), true);
    }

    @Override
    public void turnOnNotification() {
        mSharedPreferences.edit()
                .putBoolean(mContext.getString(R.string.shared_preferences_key_open_notification), true)
                .apply();

        enableAlarm();

        // Enable receive device boot completed even so that reset alarm
        enableBootCompletedReceiver();
    }

    @Override
    public void turnOffNotification() {
        mSharedPreferences.edit()
                .putBoolean(mContext.getString(R.string.shared_preferences_key_open_notification), false)
                .apply();

        disableAlarm();

        // Disable receive device boot completed even so that reset alarm
        disableBootCompletedReceiver();
    }

    /**
     * set next alarm.
     */
    public void enableAlarm() {
        AlarmUtil.getInstance().setUpNextAlarm(mContext);
    }

    /**
     * cancel alarm
     */
    private void disableAlarm() {
        AlarmUtil.getInstance().cancelAllAlarm(mContext);
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
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //msg.arg1 == hour, msg.arg2 == minute
            int actionStringResId;
            switch (msg.what) {
                case GO_BED:
                    actionStringResId = R.string.main_count_down_action_go_bed;
                    break;
                case GET_UP:
                    actionStringResId = R.string.main_count_down_action_get_up;
                    break;
                default:
                    actionStringResId = -1;
                    break;
            }
            mView.getTvAction().setText(mContext.getString(actionStringResId));
            mView.getTvLeftHour().setText(String.format(Locale.getDefault(), "%d", msg.arg1));
            mView.getTvLeftMinute().setText(String.format(Locale.getDefault(), "%d", msg.arg2));
        }
    };
}
