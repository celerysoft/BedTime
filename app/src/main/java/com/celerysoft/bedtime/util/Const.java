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
}
