package com.celerysoft.bedtime.activity.launch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;
import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.bedtime.util.AlarmUtil;
import com.celerysoft.ripple.Wrapper;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by admin on 16/4/29.
 *
 */
public class LaunchActivity extends BaseActivity {
    public static final String KEY_LOADING_DELAY = "KEY_LOADING_DELAY";

    private ProgressBar mProgressBar;
    private Wrapper mAnimationWrapper;

    private long mLoadingDelay;
    private boolean mIsInitializationFinished = false;
    private boolean mIsOnResume = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);

        initActivity();
    }

    private void initActivity() {
        bindView();
        bindListener();

        mLoadingDelay = getIntent().getLongExtra(KEY_LOADING_DELAY, 0);
        mIsInitializationFinished = false;
        mIsOnResume = false;

        initView();

        initUmeng();

        initSocialSharing();

        setAppLanguage();

        AlarmUtil.getInstance().setUpNextAlarm(this);

        mIsInitializationFinished = true;
        startMainActivityWhenReady();
    }

    private void bindView() {
        mProgressBar = (ProgressBar) findViewById(R.id.launcher_progress_bar);
        mAnimationWrapper = (Wrapper) findViewById(R.id.launcher_animation);
    }

    private void bindListener() {
        mAnimationWrapper.addAnimatorListenerAdapter(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startMainActivity();
                finish();
            }
        });
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initUmeng() {
        MobclickAgent.setCheckDevice(false);
        MobclickAgent.setCatchUncaughtExceptions(false);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(model.getLocale());
        } else {
            //noinspection deprecation
            config.locale = model.getLocale();
        }
        resources.updateConfiguration(config, displayMetrics);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mIsOnResume = true;
        startMainActivityWhenReady();
    }

    private void startMainActivityWhenReady() {
        if (mIsOnResume && mIsInitializationFinished) {
            mAnimationWrapper.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayTransitionAnimation();
                }
            }, mLoadingDelay);
        }
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
