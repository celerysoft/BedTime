package com.celerysoft.bedtime.activity.launch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;
import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.bedtime.util.AlarmUtil;
import com.celerysoft.ripple.Wrapper;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by admin on 16/4/29.
 *
 */
public class LaunchActivity extends BaseActivity {
    private ProgressBar mProgressBar;
    private Wrapper mAnimationWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);

        initActivity();
    }

    private void initActivity() {
        mProgressBar = (ProgressBar) findViewById(R.id.launcher_progress_bar);
        mAnimationWrapper = (Wrapper) findViewById(R.id.launcher_animation);
        mAnimationWrapper.addAnimatorListenerAdapter(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startMainActivity();
            }
        });

        initSocialSharing();

        setAppLanguage();

        AlarmUtil.getInstance().setUpNextAlarm(this);

        displayTransitionAnimation();
    }

    private void initSocialSharing() {
        PlatformConfig.setWeixin("wx31a99c803f850798", "655c7b76686dc0b3e76f01ebad3f350e");
        PlatformConfig.setQQZone("1105305383", "I3AhpsSYYwhzr9zI");
    }

    private void setAppLanguage() {
        SettingsModel model = new SettingsModel(this);

        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        Configuration config = resources.getConfiguration();
        config.locale = model.getLocale();
        resources.updateConfiguration(config, displayMetrics);
    }

    private void displayTransitionAnimation() {
        mProgressBar.setVisibility(View.GONE);
        mAnimationWrapper.performAnimation();
    }

    private void startMainActivity() {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.ripple_alpha_in, R.anim.ripple_alpha_out);
    }
}
