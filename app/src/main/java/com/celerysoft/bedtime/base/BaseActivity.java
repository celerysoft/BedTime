package com.celerysoft.bedtime.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.celerysoft.bedtime.util.ActivityManagerUtil;

/**
 * Created by admin on 16/4/25.
 *
 */
public class BaseActivity extends AppCompatActivity {
    ActivityManagerUtil mActivityManagerUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivityManagerUtil = ActivityManagerUtil.getInstance();

        mActivityManagerUtil.registerActivity(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        mActivityManagerUtil.unregisterActivity(this);

        super.finish();
    }
}
