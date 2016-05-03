package com.celerysoft.bedtime.activity.launch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;
import com.celerysoft.bedtime.view.BaseActivity;

/**
 * Created by admin on 16/4/29.
 */
public class LaunchActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);

        setAppLanguage();
    }

    private void setAppLanguage() {
        SettingsModel model = new SettingsModel(this);

        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        Configuration config = resources.getConfiguration();
        config.locale = model.getLocale();
        resources.updateConfiguration(config, displayMetrics);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
