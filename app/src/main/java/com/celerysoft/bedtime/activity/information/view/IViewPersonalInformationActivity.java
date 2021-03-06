package com.celerysoft.bedtime.activity.information.view;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin on 16/5/3.
 */
public interface IViewPersonalInformationActivity {
    Context getContext();
    FragmentManager getFragmentManager();
    View getBtnNickname();
    AppCompatTextView getTvNickname();
    AppCompatEditText getEtNickname();
    View getBtnAge();
    AppCompatTextView getTvAge();
    AppCompatEditText getEtAge();
    AppCompatTextView getTvSleepTime();
    FloatingActionButton getFloatingActionButton();
    ImageView getIvAvatar();
    void startActivityForResult(Intent intent, int requestCode);
}
