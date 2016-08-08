package com.celerysoft.bedtime.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;

import java.io.File;

/**
 * Created by admin on 16/7/13.
 * Test util
 */
public class TestUtil {
    public static void createTestNotification(Context context) {
        int notifyId = (int) (Math.random() * 1000);

        Intent openIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_bedtime)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.notification_it_is_bed_time_title))
                .setContentText(context.getString(R.string.notification_it_is_bed_time))
                .setTicker(context.getString(R.string.notification_it_is_bed_time))
                .setLights(context.getResources().getColor(R.color.colorPrimary), 1000, 1000)
                .setVibrate(new long[] {0, 1000, 500, 1000, 500, 1000})
                .setVibrate(new long[] {0, 100})
                .setSound(FileUtil.getInstance().getNotificationSoundUri(context));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }

        NotificationManager manager = (NotificationManager) context.getSystemService((Context.NOTIFICATION_SERVICE));
        manager.notify(notifyId, notification);
    }

    public static void createTestDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog_Light);
        builder.setTitle(context.getString(R.string.notification_bed_time_in_30_minutes_title))
                .setMessage(context.getString(R.string.notification_bed_time_in_30_minutes))
//                .setMessage("洗漱")
                .setPositiveButton(R.string.got_it, null)
                .show();
    }
}
