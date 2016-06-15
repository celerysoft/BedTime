package com.celerysoft.bedtime.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.main.model.AlarmTimeBean;
import com.celerysoft.bedtime.fragment.main.model.AlarmTimeModel;
import com.celerysoft.bedtime.receiver.BedTimeReceiver;

import java.util.Calendar;

/**
 * Created by admin on 16/5/16.
 * Alarm util
 */
public class AlarmUtil {

    private static volatile AlarmUtil sInstance = null;

    public static AlarmUtil getInstance() {
        AlarmUtil instance = sInstance;
        if (instance == null) {
            synchronized (AlarmUtil.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new AlarmUtil();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private AlarmUtil() {}

    private AlarmTimeBean mLastAlarm;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences getSharedPreferences(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key_default), Context.MODE_PRIVATE);
        }

        return mSharedPreferences;
    }

    public void setUpNextAlarm(Context context) {
        if (getSharedPreferences(context) != null) {
            boolean isNotificationOpened = getSharedPreferences(context).getBoolean(context.getString(R.string.shared_preferences_key_open_notification), true);
            if (!isNotificationOpened) {
                return;
            }
        }

        AlarmTimeModel alarmTimeModel = new AlarmTimeModel(context);
        AlarmTimeBean nextAlarm = alarmTimeModel.findNextAlarm();

        if (mLastAlarm != null && mLastAlarm.equals(nextAlarm)) {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, nextAlarm.getDayOfTheWeek());
        calendar.set(Calendar.HOUR_OF_DAY, nextAlarm.getHour());
        calendar.set(Calendar.MINUTE, nextAlarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent setAlarmIntent = new Intent(context, BedTimeReceiver.class);
        if (nextAlarm.getType() == AlarmTimeBean.Type.GO_BED) {
            setAlarmIntent.setAction(context.getString(R.string.action_go_bed));
        } else if (nextAlarm.getType() == AlarmTimeBean.Type.BED_TIME) {
            setAlarmIntent.setAction(context.getString(R.string.action_bed_time));
        }
        alarmIntent = PendingIntent.getBroadcast(context, 0, setAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmMgr.cancel(alarmIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }

        mLastAlarm = nextAlarm;
    }

    public void cancelAllAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BedTimeReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.cancel(alarmIntent);
    }

}
