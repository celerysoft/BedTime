package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Fragment;
import android.content.Intent;

/**
 * Created by Celery on 16/4/11.
 *
 */
public interface IPresenterMainActivity {
    void setMainFragment();
    void exitApp();
    Fragment getCurrentFragment();
    void updateViewByFragmentInfo(Fragment fragment);
    void turnToMainFragment();
    void turnToBedTimeFragment();
    void turnToBedTimeFragmentQuickly();
    void turnToSettingsFragment();
    void sendFeedback();
    void startPersonalInformationActivity();
    String getNickname();
    String getSleepTime();
    boolean isNewToBedTime();
    void showWelcomeDialog();
    void showSocialSharingDialog();
    void openAboutBedTimeActivity();
    void handleActivityResult(int requestCode, int resultCode, Intent data);
    void performFabAnimation();
}
