package com.celerysoft.bedtime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.celerysoft.bedtime.service.AlarmService;
import com.celerysoft.bedtime.util.AlarmUtil;

/**
 * Created by admin on 16/4/27.
 * Receiver that receive device boot completed broadcast.
 */
public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // After device boot completed, set current day's alarm.
            AlarmUtil.getInstance().setUpNextAlarm(context);

            Intent serviceIntent = new Intent(context, AlarmService.class);
            context.startService(serviceIntent);
        }
    }
}
