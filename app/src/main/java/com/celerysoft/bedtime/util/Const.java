package com.celerysoft.bedtime.util;

import android.content.Context;

import com.celerysoft.bedtime.R;

/**
 * Created by admin on 16/4/26.
 */
public class Const {
    public static String getDefaultSharedPreferencesKey(Context context) {
        return context.getString(R.string.shared_preferences_key_default);
    }

    public static final String NOTIFICATION_FILE_NAME = "notification.mp3";

    public static final String USER_AVATAR_FILE_NAME = "avatar.png";

    public static final String ABOUT_BED_TIME_URL = "http://celerysoft.github.io/2016-05-05.html";

    public static final String BED_TIME_LOGO_URL = "http://7xpapo.com1.z0.glb.clouddn.com/BedTime108.png";
}
