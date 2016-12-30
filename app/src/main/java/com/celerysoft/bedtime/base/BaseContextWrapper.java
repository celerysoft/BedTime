package com.celerysoft.bedtime.base;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;

import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;

import java.util.Locale;

/**
 * Created by admin on 16/12/30.
 *
 */

public class BaseContextWrapper extends ContextWrapper {
    public BaseContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context) {
        SettingsModel model = new SettingsModel(context);
        Locale locale = model.getLocale();
        Configuration config = context.getResources().getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            //noinspection deprecation
            config.locale = locale;
            //noinspection deprecation
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }

        return new BaseContextWrapper(context);
    }

//    public static ContextWrapper wrap(Context context, String language) {
//        Configuration config = context.getResources().getConfiguration();
//        Locale sysLocale;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            sysLocale = config.getLocales().get(0);
//        } else {
//            //noinspection deprecation
//            sysLocale = config.locale;
//        }
//
//        if (!language.equals("") && !sysLocale.getLanguage().equals(language)) {
//            Locale locale = new Locale(language);
//            Locale.setDefault(locale);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                config.setLocale(locale);
//            } else {
//                //noinspection deprecation
//                config.locale = locale;
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                context = context.createConfigurationContext(config);
//            } else {
//                //noinspection deprecation
//                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
//            }
//        }
//
//        return new BaseContextWrapper(context);
//    }

}
