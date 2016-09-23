package com.celerysoft.bedtime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.celerysoft.bedtime.activity.launch.LaunchActivity;

/**
 * Created by admin on 16/9/21.
 *
 */

public class OnNotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent nextIntent = new Intent(context, LaunchActivity.class);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        nextIntent.putExtra(LaunchActivity.KEY_LOADING_DELAY, 250L);
        context.startActivity(nextIntent);
    }
}
