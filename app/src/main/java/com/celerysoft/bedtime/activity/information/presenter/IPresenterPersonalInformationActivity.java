package com.celerysoft.bedtime.activity.information.presenter;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by admin on 16/5/3.
 */
public interface IPresenterPersonalInformationActivity {
    int REQUEST_CODE_CAMERA = 8001;
    int REQUEST_CODE_GALLERY = 8002;
    int REQUEST_CODE_CROP = 8003;

    String getNickname();
    void editNickname();
    String getAge();
    void editAge();
    String getSleepTime();
    void showSleepTimePickerDialog();
    void saveInformation();
    void showModifyAvatarDialog();
    void crop(Uri uri);
    void saveAvatar(Bitmap bitmap);
    Bitmap getAvatar();
    boolean isInEditMode();
    void cancelEditMode();
}
