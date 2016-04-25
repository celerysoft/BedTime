package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Fragment;

/**
 * Created by Celery on 16/4/11.
 */
public interface IPresenterMainActivity {
    void preExitApp();
    void exitApp();
    boolean readyToExitApp();
    Fragment getCurrentFragment();
    void turnToMainFragment();
    void turnToBedTimeFragment();
    void turnToSettingsFragment();
}
