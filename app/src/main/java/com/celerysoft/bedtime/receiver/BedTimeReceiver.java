package com.celerysoft.bedtime.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.util.AlarmUtil;
import com.celerysoft.bedtime.util.Const;
import com.celerysoft.bedtime.util.GlobalValue;

import java.io.File;

/**
 * Created by Celery on 16/4/14.
 *
 */
public class BedTimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (GlobalValue.isAppRunningForeground) {
            showNotifyingDialog(context, intent.getAction());
        } else {
            sendNotification(context, intent.getAction());
            GlobalValue.hasNotifications = true;
        }

        setNextAlarm(context);
    }

    private void showNotifyingDialog(Context context, String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog_Light);

        if (action.equals(context.getString(R.string.action_go_bed))) {
            builder.setTitle(context.getString(R.string.notification_bed_time_in_30_minutes_title))
                    .setMessage(context.getString(R.string.notification_bed_time_in_30_minutes) + getNickname(context));
        } else if (action.equals(context.getString(R.string.action_bed_time))) {
            builder.setTitle(context.getString(R.string.notification_it_is_bed_time_title))
                    .setMessage(context.getString(R.string.notification_it_is_bed_time) + getNickname(context));
        }

        builder.setPositiveButton(R.string.got_it, null)
                .show();
    }

    private void sendNotification(Context context, String action) {
        acquireWakeLock(context, 10 * 1000);

        Intent openIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_bedtime)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);

        int notifyId = 0;

        if (action.equals(context.getString(R.string.action_go_bed))) {

            File notificationsDirectory = context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS);
            if (notificationsDirectory != null) {
                String soundPath = notificationsDirectory.getPath() + File.separator + Const.NOTIFICATION_FILE_NAME;
                Uri uri = Uri.fromFile(new File(soundPath));
                builder.setSound(uri);
            }

            notifyId = Const.NOTIFICATION_ID_GO_BED;

            builder.setContentTitle(context.getString(R.string.notification_bed_time_in_30_minutes_title))
                    .setContentText(context.getString(R.string.notification_bed_time_in_30_minutes) + getNickname(context))
                    .setTicker(context.getString(R.string.notification_bed_time_in_30_minutes) + getNickname(context))
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        } else if (action.equals(context.getString(R.string.action_bed_time))) {
            notifyId = Const.NOTIFICATION_ID_BED_TIME;

            builder.setContentTitle(context.getString(R.string.notification_it_is_bed_time_title))
                    .setContentText(context.getString(R.string.notification_it_is_bed_time) + getNickname(context))
                    .setTicker(context.getString(R.string.notification_it_is_bed_time) + getNickname(context))
                    .setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            //noinspection deprecation
            notification = builder.getNotification();
        }

        NotificationManager manager = (NotificationManager) context.getSystemService((Context.NOTIFICATION_SERVICE));
        manager.notify(notifyId, notification);
    }

    private void acquireWakeLock(Context context, long timeout) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //noinspection deprecation
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "BedTime Notification");
        wakeLock.acquire(timeout);
    }

    private void setNextAlarm(Context context) {
        AlarmUtil.getInstance().setUpNextAlarm(context);
    }

    private String getNickname(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_key_default), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.shared_preferences_key_personal_information_nickname), "BedTime");
    }
}
