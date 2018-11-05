package com.celerysoft.bedtime.test;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.NotificationCompat;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.fragment.main.model.BedTimeBean;
import com.celerysoft.bedtime.fragment.main.model.BedTimeModel;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;
import com.celerysoft.bedtime.receiver.OnNotificationClickReceiver;
import com.celerysoft.bedtime.util.NotificationUtil;

import java.util.ArrayList;

/**
 * Created by admin on 16/7/13.
 * Test util
 */
public class TestUtil {
    public static void createTestNotification(final Context context, long delay) {
        final int notifyId = (int) (Math.random() * 1000);
//        notifyId = 1;

        Intent openIntent = new Intent(context, OnNotificationClickReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Notification notification;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        } else {
            builder.setSmallIcon(R.drawable.ic_notification_sdk_21);
            builder.setColor(context.getResources().getColor(R.color.colorPrimary));
        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.notification_it_is_bed_time_title))
                .setContentText(context.getString(R.string.notification_it_is_bed_time))
                .setTicker(context.getString(R.string.notification_it_is_bed_time))
                .setLights(context.getResources().getColor(R.color.colorPrimary), 500, 2000)
                .setVibrate(new long[] {0, 1000, 500, 1000, 500, 1000});

        SettingsModel settingsModel = new SettingsModel(context);
        String uriString = settingsModel.getSoundUri();
        Uri soundUri;
        if (uriString == null || uriString.length() == 0) {
            soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification);
        } else {
            soundUri = Uri.parse(uriString);
        }
        builder.setSound(soundUri);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setFullScreenIntent(pendingIntent, false);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NotificationUtil.getInstance(context).sendNotification(notifyId, notification);
            }
        }, delay);

    }

    public static void createTestDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_Dialog_Light);
        builder.setTitle(context.getString(R.string.notification_bed_time_in_30_minutes_title))
                .setMessage(context.getString(R.string.notification_bed_time_in_30_minutes))
                .setPositiveButton(R.string.got_it, null)
                .show();
    }

    public static void gotoIgnoreBatteryOptimizationSettings(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            context.startActivity(intent);
        }
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

    public static void systemSoundList(Activity activity) {
        RingtoneManager manager = new RingtoneManager(activity);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int columnCount = cursor.getColumnCount();
            String columnName;
            for (int i = 0; i < columnCount; ++i) {
                columnName = cursor.getColumnName(i);
                Bundle b = cursor.getExtras();
                String a = "";
            }

            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            list.add(id + "#" + title + "#" + uri);
        }

        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
//        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        activity.startActivityForResult(intent, 6);
    }
}
