package com.celerysoft.bedtime.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;

/**
 * Created by Celery on 16/4/14.
 */
public class BedTimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        acquireWakeLock(context, 10 * 1000);

        Intent openIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification;
        Notification.Builder builder = new Notification.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notification_it_is_bed_time))
                .setTicker(context.getString(R.string.notification_it_is_bed_time))
                .setContentIntent(pendingIntent);
                //.setOngoing(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }

        NotificationManager manager = (NotificationManager) context.getSystemService((Context.NOTIFICATION_SERVICE));
        manager.notify(1, notification);
    }

    private void acquireWakeLock(Context context, long timeout) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "BedTime Notification");
        wakeLock.acquire(timeout);
    }
}
