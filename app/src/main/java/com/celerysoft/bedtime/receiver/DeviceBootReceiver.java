package com.celerysoft.bedtime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.celerysoft.bedtime.fragment.main.presenter.PresenterMain;

/**
 * Created by admin on 16/4/27.
 * Receiver that receive device boot completed broadcast.
 */
public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // After device boot completed, set current day's alarm.
            PresenterMain.enableAlarm(context);
        }
    }
}
