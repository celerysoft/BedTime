package com.celerysoft.bedtime.util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.fragment.main.model.BedTimeBean;
import com.celerysoft.bedtime.fragment.main.model.BedTimeModel;
import com.celerysoft.bedtime.receiver.OnNotificationClickReceiver;

/**
 * Created by admin on 16/7/13.
 * Test util
 */
public class TestUtil {
    public static void createTestNotification(Context context) {
        int notifyId = (int) (Math.random() * 1000);
//        notifyId = 1;

        Intent openIntent = new Intent(context, OnNotificationClickReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            builder.setSmallIcon(R.mipmap.ic_notification_sdk_21);
            builder.setColor(context.getResources().getColor(R.color.colorPrimary));
        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.notification_it_is_bed_time_title))
                .setContentText(context.getString(R.string.notification_it_is_bed_time))
                .setTicker(context.getString(R.string.notification_it_is_bed_time))
                .setLights(context.getResources().getColor(R.color.colorPrimary), 500, 2000)
                .setVibrate(new long[] {0, 1000, 500, 1000, 500, 1000})
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }

        NotificationUtil.getInstance(context).sendNotification(notifyId, notification);
    }

    public static void createTestDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog_Light);
        builder.setTitle(context.getString(R.string.notification_bed_time_in_30_minutes_title))
                .setMessage(context.getString(R.string.notification_bed_time_in_30_minutes))
                .setPositiveButton(R.string.got_it, null)
                .show();
    }

    public static BedTimeBean[] deriveAllBedTime(Context context) {
        BedTimeBean[] bedTimes = new BedTimeBean[7];
        BedTimeModel model = new BedTimeModel(context);
        for (int i = 1; i <= 7; ++i) {
            bedTimes[i - 1] = model.findBedTimeByDayOfTheWeek(i);
        }
        return bedTimes;
    }

    public static WakeupTimeBean[] deriveAllWakeupTime(Context context) {
        WakeupTimeBean[] wakeupTimes = new WakeupTimeBean[7];
        WakeupTimeModel model = new WakeupTimeModel(context);
        for (int i = 1; i <= 7; ++i) {
            wakeupTimes[i - 1] = model.findWakeUpTimeByDayOfTheWeek(i);
        }
        return wakeupTimes;
    }
}
