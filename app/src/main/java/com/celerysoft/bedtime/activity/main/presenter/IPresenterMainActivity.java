package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Fragment;

/**
 * Created by Celery on 16/4/11.
 */
public interface IPresenterMainActivity {
    void setMainFragment();
    void preExitApp();
    void exitApp();
    boolean readyToExitApp();
    void turnToMainFragment();
    void turnToBedTimeFragment();
    void turnToSettingsFragment();
}
