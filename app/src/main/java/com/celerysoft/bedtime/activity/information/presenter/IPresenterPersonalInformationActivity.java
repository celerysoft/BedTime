package com.celerysoft.bedtime.activity.information.presenter;

/**
 * Created by admin on 16/5/3.
 */
public interface IPresenterPersonalInformationActivity {
    String getNickname();
    void editNickname();
    String getAge();
    void editAge();
    String getSleepTime();
    void showSleepTimePickerDialog();
    void saveInformation();
}
