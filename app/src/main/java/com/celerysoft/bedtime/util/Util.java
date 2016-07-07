package com.celerysoft.bedtime.util;

import android.content.Context;

/**
 * Created by Celery on 16/6/29.
 *
 */
public class Util {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
