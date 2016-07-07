package com.celerysoft.bedtime.activity.information.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.main.model.BedTimeModel;
import com.celerysoft.bedtime.util.AlarmUtil;
import com.celerysoft.bedtime.util.Const;
import com.celerysoft.bedtime.util.FileUtil;

import java.io.File;

/**
 * Created by admin on 16/5/3.
 */
public class PersonalInformationModel {
    private Context mContext;

    private SharedPreferences mSharedPreferences;

    BedTimeModel mBedTimeModel;

    public PersonalInformationModel(Context context) {
        mContext = context;

        mSharedPreferences = context.getSharedPreferences(mContext.getString(R.string.shared_preferences_key_default), Context.MODE_PRIVATE);

        mBedTimeModel = new BedTimeModel(context);
    }

    public String getNickname() {
        return mSharedPreferences.getString(mContext.getString(R.string.shared_preferences_key_personal_information_nickname), "BedTime");
    }

    public void setNickname(String nickname) {
        mSharedPreferences.edit()
                .putString(mContext.getString(R.string.shared_preferences_key_personal_information_nickname), nickname)
                .apply();

    }

    public String getAge() {
        int age = mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_personal_information_age), 18);
        return String.valueOf(age);
    }

    public void setAge(String age) {
        Integer ageInt = Integer.valueOf(age);

        mSharedPreferences.edit()
                .putInt(mContext.getString(R.string.shared_preferences_key_personal_information_age), ageInt)
                .apply();
    }

    public String getSleepTime() {
        int hour = getSleepTimeHour();
        int minute = getSleepTimeMinute();

        String hourString = hour > 1 ? mContext.getString(R.string.personal_information_hours_text) : mContext.getString(R.string.personal_information_hour_text);
        hourString = hour + hourString;
        String minuteString;
        if (minute == 0) {
            minuteString = "";
        } else {
            minuteString = minute > 1 ? mContext.getString(R.string.personal_information_minutes_text) : mContext.getString(R.string.personal_information_minute_text);
            minuteString = minute + minuteString;
        }

        return hourString + minuteString;
    }

    public int getSleepTimeHour() {
        return mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_personal_information_sleep_hour), 7);
    }

    public int getSleepTimeMinute() {
        return mSharedPreferences.getInt(mContext.getString(R.string.shared_preferences_key_personal_information_sleep_minute), 30);
    }

    public void setSleepTime(int hour, int minute) {
        setSleepTimeHour(hour);
        setSleepTimeMinute(minute);

        mBedTimeModel.refreshBedTime();

        AlarmUtil.getInstance().setUpNextAlarm(mContext);
    }

    private void setSleepTimeHour(int hour) {
        mSharedPreferences.edit()
                .putInt(mContext.getString(R.string.shared_preferences_key_personal_information_sleep_hour), hour)
                .apply();
    }

    private void setSleepTimeMinute(int minute) {
        mSharedPreferences.edit()
                .putInt(mContext.getString(R.string.shared_preferences_key_personal_information_sleep_minute), minute)
                .apply();
    }

    public void setAvatar(Bitmap bitmap) {
        FileUtil.getInstance().saveAvatarToExternalStorage(mContext, bitmap);
    }

    public Bitmap getAvatar() {
        File file = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (file == null) {
            return null;
        }

        String path = file.getPath() + File.separator + Const.USER_AVATAR_FILE_NAME;

        Bitmap bitmap = null;
        try {
            File avatarFile = new File(path);
            if(avatarFile.exists()) {
                bitmap = BitmapFactory.decodeFile(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


}
