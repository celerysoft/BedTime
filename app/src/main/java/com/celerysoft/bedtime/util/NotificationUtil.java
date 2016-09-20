package com.celerysoft.bedtime.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

/**
 * Created by admin on 16/9/20.
 * Notification Util
 */
public class NotificationUtil {
    private static final String TAG = "NotificationUtil";
    private static volatile NotificationUtil sInstance = null;
    private static Context sContext;
    /** indicate if the app notified some notifications **/
    private static boolean sHasNotifications = true;

    public static NotificationUtil getInstance(Context context) {
        sContext = context;
        NotificationUtil instance = sInstance;
        if (instance == null) {
            synchronized (NotificationUtil.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new NotificationUtil();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private NotificationUtil() {}

    public void sendNotification(int id, Notification notification) {
        NotificationManager manager = (NotificationManager) sContext.getSystemService((Context.NOTIFICATION_SERVICE));
        manager.notify(id, notification);

        sHasNotifications = true;
    }

    public void cancelAllNotification() {
        Log.d(TAG, "cancelAllNotification() called.");

        NotificationManager manager = (NotificationManager) sContext.getSystemService((Context.NOTIFICATION_SERVICE));
        manager.cancelAll();

        sHasNotifications = false;
    }

    public boolean hasNotifications() {
        return sHasNotifications;
    }
}
