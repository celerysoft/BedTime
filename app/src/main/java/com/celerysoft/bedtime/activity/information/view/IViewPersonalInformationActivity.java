package com.celerysoft.bedtime.activity.information.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;

/**
 * Created by admin on 16/5/3.
 */
public interface IViewPersonalInformationActivity {
    Context getContext();
    AppCompatTextView getTvNickname();
    AppCompatEditText getEtNickname();
    AppCompatTextView getTvAge();
    AppCompatEditText getEtAge();
    AppCompatTextView getTvSleepTime();
    FloatingActionButton getFloatingActionButton();
}
