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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
            boolean isEnabledAlarm = getSharedPreferences(context).getBoolean(context.getString(R.string.shared_preferences_key_open_notification), true);
            if (!isEnabledAlarm) {
                return;
            }
        }

        AlarmTimeModel alarmTimeModel = new AlarmTimeModel(context);
        AlarmTimeBean nextAlarm = alarmTimeModel.findNextAlarm2();

        if (mLastAlarm != null && mLastAlarm.equals(nextAlarm)) {
            return;
        }

        Calendar calendar = nextAlarm.getCalendar();

//        Calendar calendar = Calendar.getInstance();
//
//        nextAlarm.getDayOfTheWeek();
//        nextAlarm.getHour();
//        nextAlarm.getMinute();
//
//        int differentInMinute;
//        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//        int currentMinute = calendar.get(Calendar.MINUTE);
//        if (currentDayOfWeek != nextAlarm.getDayOfTheWeek()) {
//            differentInMinute = ((nextAlarm.getDayOfTheWeek() - currentDayOfWeek) * 24 + nextAlarm.getHour() - currentHour) * 60 + nextAlarm.getMinute() - currentMinute;
//        } else {
//            differentInMinute = (nextAlarm.getHour() - currentHour) * 60 + nextAlarm.getMinute() - currentMinute;
//        }
////        calendar.set(Calendar.DAY_OF_WEEK, nextAlarm.getDayOfTheWeek());
////        calendar.set(Calendar.HOUR_OF_DAY, nextAlarm.getHour());
////        calendar.set(Calendar.MINUTE, nextAlarm.getMinute());
//        calendar.add(Calendar.MINUTE, differentInMinute);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }

        mLastAlarm = nextAlarm;

        writeDebugLog2ExternalStorage(context, calendar);
    }

    public void cancelAllAlarm(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BedTimeReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.cancel(alarmIntent);
    }

    private void writeDebugLog2ExternalStorage(Context context, Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String content = simpleDateFormat.format(new Date());
        content += " set up next alarm ringed on: ";
        content += simpleDateFormat.format(calendar.getTime());

        FileUtil.getInstance().writeToExternalCache(context, "Alarm", null, content);
    }

}
