package com.celerysoft.bedtime.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.util.ActivityManagerUtil;

/**
 * Created by admin on 16/4/25.
 *
 */
public class BaseActivity extends AppCompatActivity {
    private ActivityManagerUtil mActivityManagerUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivityManagerUtil = ActivityManagerUtil.getInstance();

        mActivityManagerUtil.registerActivity(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        mActivityManagerUtil.unregisterActivity(this);

        super.finish();
    }
}
