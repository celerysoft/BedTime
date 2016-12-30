package com.celerysoft.bedtime.fragment.settings.model;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.settings.bean.Sound;
import com.celerysoft.bedtime.util.Const;
import com.celerysoft.bedtime.util.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 16/4/29.
 *
 */
public class SettingsModel {
    private static final int FOLLOW_SYSTEM = 0;
    private static final int CHINESE = 1;
    private static final int ENGLISH = 2;

    private Context mContext;

    private List<Sound> mSoundList;

    public SettingsModel(Context context) {
        mContext = context;
    }

    public String[] getLanguageStrings() {
        String[] languages = new String[3];

        languages[0] = mContext.getString(R.string.settings_fragment_language_follow_system);
        languages[1] = mContext.getString(R.string.settings_fragment_language_chinese);
        languages[2] = mContext.getString(R.string.settings_fragment_language_english);

        return languages;
    }

    public int getAppLanguage() {
        return SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_settings_language), 0);
    }

    public void setAppLanguage(int language) {
        SPUtil.put(mContext, mContext.getString(R.string.shared_preferences_key_settings_language), language);
    }

    public Locale getLocale() {
        Locale locale = Const.SYSTEM_DEFAULT_LOCALE;

        int language = getAppLanguage();
        switch (language) {
            case FOLLOW_SYSTEM:
                break;
            case CHINESE:
                locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case ENGLISH:
                locale = Locale.ENGLISH;
                break;
            default:
                break;
        }

        return locale;
    }

    public String getAppLanguageString() {
        String languageString = mContext.getString(R.string.settings_fragment_language_follow_system);

        int language = getAppLanguage();
        switch (language) {
            case FOLLOW_SYSTEM:
                break;
            case CHINESE:
                languageString = mContext.getString(R.string.settings_fragment_language_chinese);
                break;
            case ENGLISH:
                languageString = mContext.getString(R.string.settings_fragment_language_english);
                break;
            default:
                break;
        }

        return languageString;
    }

    public String getAppSoundString() {
        String uri = getSoundUri();
        Sound sound = findSoundByUri(uri);
        if (sound == null) {
            return "";
        } else {
            return sound.getTitle();
        }
    }

    private List<Sound> getSoundList() {
        if (mSoundList != null) {
            return mSoundList;
        }

        RingtoneManager manager = new RingtoneManager(mContext);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        mSoundList = new ArrayList<>();

        int index = 0;
        Sound appDefaultSound = new Sound();
        appDefaultSound.setIndex(index);
        appDefaultSound.setId("666");
        appDefaultSound.setTitle(mContext.getString(R.string.settings_fragment_sound_use_default));
        appDefaultSound.setUri("android.resource://" + mContext.getPackageName() + "/" + R.raw.notification);
        mSoundList.add(appDefaultSound);
        index++;

        Sound SystemDefaultSound = new Sound();
        SystemDefaultSound.setIndex(index);
        SystemDefaultSound.setId("999");
        SystemDefaultSound.setTitle(mContext.getString(R.string.settings_fragment_language_follow_system));
        SystemDefaultSound.setUri(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString());
        mSoundList.add(SystemDefaultSound);
        index++;

        while (cursor.moveToNext()) {
            String id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);

            Sound sound = new Sound();
            sound.setIndex(index);
            sound.setId(id);
            sound.setTitle(title);
            sound.setUri(uri + "/" + id);

            mSoundList.add(sound);
            index++;
        }

        return mSoundList;
    }

    public String[] getSoundStrings() {
        List<Sound> list = getSoundList();

        String[] sounds = new String[list.size()];

        for (int i = 0; i < sounds.length; ++i) {
            sounds[i] = list.get(i).getTitle();
        }

        return sounds;
    }

    public Sound findSoundByIndex(int index) {
        List<Sound> soundList = getSoundList();
        if (soundList == null || index >= soundList.size()) {
            return null;
        }

        return soundList.get(index);
    }

    private Sound findSoundByUri(String uri) {
        List<Sound> soundList = getSoundList();
        if (soundList == null) {
            return null;
        }

        int size = soundList.size();
        for (int i = 0; i < size; ++i) {
            Sound sound = soundList.get(i);
            if (uri.equals(sound.getUri())) {
                return sound;
            }
        }

        return null;
    }

    private int findSoundIndexByUri(String uri) {
        Sound sound = findSoundByUri(uri);
        if (sound == null) {
            return 0;
        } else {
            return sound.getIndex();
        }
    }

    public String getSoundUri() {
        return SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_settings_sound), "android.resource://" + mContext.getPackageName() + "/" + R.raw.notification);
    }

    private void setSoundUri(String soundUri) {
        SPUtil.put(mContext, mContext.getString(R.string.shared_preferences_key_settings_sound), soundUri);
    }

    public int getAppSoundIndex() {
        String uri = getSoundUri();
        return findSoundIndexByUri(uri);
    }

    public void setAppSound(int sound) {
        if (mSoundList == null || mSoundList.size() <= sound) {
            return;
        }

        setSoundUri(mSoundList.get(sound).getUri());
    }

    public void apply24HourTime(boolean applied) {
        SPUtil.put(mContext, mContext.getString(R.string.shared_preferences_key_settings_24_hour_time), applied);
    }

    public boolean is24HourTime() {
        return SPUtil.get(mContext, mContext.getString(R.string.shared_preferences_key_settings_24_hour_time), true);
    }
}
