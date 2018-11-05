package com.celerysoft.bedtime.util;

import android.content.Context;

import com.celerysoft.bedtime.BuildConfig;
import com.celerysoft.bedtime.R;

import java.util.Locale;

/**
 * Created by admin on 16/4/26.
 *
 */
public class Const {
    public static String DEFAULT_SHARED_PREFERENCES_FILE_NAME;

    public static final String EMPTY_STRING = "";

    public static final String FILE_PROVIDER_AUTHORITIES = BuildConfig.APPLICATION_ID + ".provider";

    @Deprecated
    public static final String NOTIFICATION_FILE_NAME = "notification.mp3";

    public static final String USER_AVATAR_FILE_NAME = "avatar.png";

    public static final String ABOUT_BED_TIME_URL = "https://www.celerysoft.com/product/1";

    public static final String BED_TIME_LOGO_URL = "http://githubstatic.celerysoft.com/BedTime108.png";

    public static final int NOTIFICATION_ID_GO_BED = 0;
    public static final int NOTIFICATION_ID_BED_TIME = 1;

    public static final String APP_SHORTCUT_ACTION_SET_WAKEUP_TIME = "APP_SHORTCUT_ACTION_SET_WAKEUP_TIME";
}
