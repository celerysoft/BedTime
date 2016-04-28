package com.celerysoft.bedtime.activity.main.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.view.View;

/**
 * Created by Celery on 16/4/11.
 */
public interface IViewMainActivity {
    FloatingActionButton getFloatActionButton();
    NavigationView getNavigationView();
    Context getContext();
    FragmentManager getFragmentManager();
    Fragment getCurrentFragment();
    Fragment getMainFragment();
    Fragment getSettingsFragment();
    Fragment getBedTimeFragment();

    void finish();
    ActionBar getSupportActionBar();
}
