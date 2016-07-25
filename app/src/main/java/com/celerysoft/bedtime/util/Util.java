package com.celerysoft.bedtime.util;

import android.content.Context;

/**
 * Created by Celery on 16/6/29.
 *
 */
public class Util {
    /**
     * get device's width of screen in pixels.
     * @param context Context
     * @return device's width of screen in pixels
     */
    public static int getScreentWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * get device's height of screen in pixels.
     * @param context Context
     * @return device's height of screen in pixels
     */
    public static int getScreentHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
