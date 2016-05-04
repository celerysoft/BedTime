package com.celerysoft.bedtime.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.main.presenter.PresenterMain;

/**
 * Created by Celery on 16/4/14.
 */
public class BedTimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        acquireWakeLock(context, 10 * 1000);

        Intent openIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification;
        Notification.Builder builder = new Notification.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);

        String action = intent.getAction();
        if (action.equals(context.getString(R.string.action_go_bed))) {
            builder.setContentTitle(context.getString(R.string.notification_bed_time_in_30_minutes_title))
                    .setContentText(context.getString(R.string.notification_bed_time_in_30_minutes) + getNickname(context))
                    .setTicker(context.getString(R.string.notification_bed_time_in_30_minutes) + getNickname(context))
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        } else if (action.equals(context.getString(R.string.action_bed_time))) {
            builder.setContentTitle(context.getString(R.string.notification_it_is_bed_time_title))
                    .setContentText(context.getString(R.string.notification_it_is_bed_time) + getNickname(context))
                    .setTicker(context.getString(R.string.notification_it_is_bed_time) + getNickname(context))
                    .setDefaults(Notification.DEFAULT_VIBRATE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }

        NotificationManager manager = (NotificationManager) context.getSystemService((Context.NOTIFICATION_SERVICE));
        manager.notify(1, notification);

        setAlarmOfNextDay(context);
    }

    private void acquireWakeLock(Context context, long timeout) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "BedTime Notification");
        wakeLock.acquire(timeout);
    }

    private void setAlarmOfNextDay(Context context) {
        PresenterMain.enableAlarm(context);
    }

    private String getNickname(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key_default), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.shared_preferences_key_personal_information_nickname), "BedTime");
    }
}
