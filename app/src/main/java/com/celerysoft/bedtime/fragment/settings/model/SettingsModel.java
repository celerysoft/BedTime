package com.celerysoft.bedtime.fragment.settings.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.celerysoft.bedtime.R;

import java.util.Locale;

/**
 * Created by admin on 16/4/29.
 */
public class SettingsModel {
    private static final int FOLLOW_SYSTEM = 0;
    private static final int CHINESE = 1;
    private static final int ENGLISH = 2;

    private Context mContext;

    private SharedPreferences mSharedPreferences;

    public SettingsModel(Context context, SharedPreferences sharedPreferences) {
        mContext = context;
        mSharedPreferences = sharedPreferences;
    }

    public String[] getLanguageStrings() {
        String[] languages = new String[3];

        languages[0] = mContext.getString(R.string.settings_fragment_language_follow_system);
        languages[1] = mContext.getString(R.string.settings_fragment_language_chinese);
        languages[2] = mContext.getString(R.string.settings_fragment_language_english);

        return languages;
    }

    public int getAppLanguage() {
        return mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_settings_language), 0);
    }

    public void setAppLanguage(int language) {
        mSharedPreferences.edit()
                .putInt(mContext.getString(R.string.shared_preferences_key_settings_language), language)
                .apply();
    }

    public Locale getLocale() {
        Locale locale = Locale.getDefault();

        int language = getAppLanguage();
        switch (language) {
            case FOLLOW_SYSTEM:
                break;
            case CHINESE:
                locale = Locale.CHINESE;
                break;
            case ENGLISH:
                locale = locale.ENGLISH;
                break;
            default:
                break;
        }

        return locale;
    }
}
