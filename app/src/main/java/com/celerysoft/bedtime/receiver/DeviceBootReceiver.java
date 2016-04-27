package com.celerysoft.bedtime.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;

import java.util.Calendar;

/**
 * Created by admin on 16/4/27.
 */
public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // After device boot completed, set current day's alarm.
            setCurrentDayAlarm(context);
        }
    }

    private void setCurrentDayAlarm(Context context) {
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        WakeupTimeModel wakeupTimeModel = new WakeupTimeModel(context);
        WakeupTimeBean wakeupTime = wakeupTimeModel.findBeanByDayOfTheWeek(currentDay);
        int wakeupHour = wakeupTime.getWakeupHour();
        int wakeupMinute = wakeupTime.getWakeupMinute();

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
}
