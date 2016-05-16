package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Fragment;
import android.content.Intent;

/**
 * Created by Celery on 16/4/11.
 *
 */
public interface IPresenterMainActivity {
    void setMainFragment();
    void preExitApp();
    void exitApp();
    boolean readyToExitApp();
    void updateViewByFragmentInfo(Fragment fragment);
    void turnToMainFragment();
    void turnToBedTimeFragment();
    void turnToSettingsFragment();
    void sendFeedback();
    void startPersonalInformationActivity();
    String getNickname();
    String getSleepTime();
    boolean isNewToBedTime();
    boolean isNewVersion();
    void showWelcomeDialog();
    void showSocialSharingDialog();
    void copyAssetsFileToExternalStorage();
    void openAboutBedTimeActivity();
    void handleActivityResult(int requestCode, int resultCode, Intent data);
}
