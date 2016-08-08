package com.celerysoft.bedtime.activity.main.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.information.model.PersonalInformationModel;
import com.celerysoft.bedtime.activity.main.presenter.IPresenterMainActivity;
import com.celerysoft.bedtime.activity.main.presenter.PresenterMainActivity;
import com.celerysoft.bedtime.fragment.bedtime.view.BedTimeFragment;
import com.celerysoft.bedtime.fragment.main.view.MainFragment;
import com.celerysoft.bedtime.fragment.settings.view.SettingsFragment;
import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.bedtime.base.BaseFragment;
import com.celerysoft.bedtime.util.TestUtil;
import com.celerysoft.ripple.Wrapper;
import com.umeng.socialize.UMShareAPI;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener ,IViewMainActivity {

    private IPresenterMainActivity mPresenter;
    public IPresenterMainActivity getPresenter() {
        return mPresenter;
    }

    private FloatingActionButton mFloatingActionButton;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private ImageView mIvAvatar;
    private TextView mTvNickname;
    private TextView mTvSleepTime;

    private MainFragment mMainFragment;
    private BedTimeFragment mBedTimeFragment;
    private SettingsFragment mSettingsFragment;

    private Wrapper mAnimationWrapper;

    private PersonalInformationModel mPersonalInformationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new PresenterMainActivity(this);
        mPersonalInformationModel = new PersonalInformationModel(this);

        initActivity();

        initFragment();

        restoreInstanceState(getIntent().getExtras());

        if(mPresenter.isNewToBedTime()) {
            mPresenter.showWelcomeDialog();
        }
    }

    private void initActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TestUtil.createTestNotification(MainActivity.this);
                mPresenter.performFabAnimation();
            }
        });

        mAnimationWrapper = (Wrapper) findViewById(R.id.main_ripple_animation_view);
        mAnimationWrapper.addAnimatorListenerAdapter(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPresenter.turnToBedTimeFragmentQuickly();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        if (mNavigationView != null) {
            View header = mNavigationView.getHeaderView(0);
            mIvAvatar = (ImageView) header.findViewById(R.id.main_nav_iv_avatar);
            mIvAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.startPersonalInformationActivity();
                    mDrawer.closeDrawer(GravityCompat.START);
                }
            });

            mTvNickname = (TextView) header.findViewById(R.id.main_nav_tv_nickname);
            mTvSleepTime = (TextView) header.findViewById(R.id.main_nav_tv_sleep_time);
        }
    }

    private void initFragment() {
        mMainFragment = new MainFragment();
        mMainFragment.setOnFragmentStatusChangedListener(mOnFragmentStatusChangedListener);
        mBedTimeFragment = new BedTimeFragment();
        mBedTimeFragment.setOnFragmentStatusChangedListener(mOnFragmentStatusChangedListener);
        mSettingsFragment = new SettingsFragment();
        mSettingsFragment.setOnFragmentStatusChangedListener(mOnFragmentStatusChangedListener);

        mPresenter.setMainFragment();
    }

    private BaseFragment.OnFragmentStatusChangedListener mOnFragmentStatusChangedListener = new BaseFragment.OnFragmentStatusChangedListener() {
        @Override
        public void onFragmentStart(BaseFragment fragment) {
            mPresenter.updateViewByFragmentInfo(fragment);
        }
    };

    protected void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String launchFragment = savedInstanceState.getString("launch_fragment");
            if (launchFragment != null) {
                if (launchFragment.equals("settings")) {
                    mPresenter.turnToSettingsFragment();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mTvNickname.setText(mPresenter.getNickname());
        mTvSleepTime.setText(mPresenter.getSleepTime());

        Bitmap avatar = mPersonalInformationModel.getAvatar();
        if (avatar != null) {
            mIvAvatar.setImageBitmap(avatar);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (mPresenter.getCurrentFragment().getClass().equals(MainFragment.class)
                    || !getFragmentManager().popBackStackImmediate()) {
                mPresenter.exitApp();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_us) {
            mPresenter.openAboutBedTimeActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
            mPresenter.turnToMainFragment();
        } else if (id == R.id.nav_bedtime) {
            mPresenter.turnToBedTimeFragment();
        } else if (id == R.id.nav_settings) {
            mPresenter.turnToSettingsFragment();
        } else if (id == R.id.nav_share) {
            mPresenter.showSocialSharingDialog();
        } else if (id == R.id.nav_feedback) {
            mPresenter.sendFeedback();
        }

        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter = null;
    }

    @Override
    public Wrapper getAnimationWrapper() {
        return mAnimationWrapper;
    }

    @Override
    public FloatingActionButton getFloatActionButton() {
        return mFloatingActionButton;
    }

    @Override
    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Fragment getMainFragment() {
        return mMainFragment;
    }

    @Override
    public Fragment getSettingsFragment() {
        return mSettingsFragment;
    }

    @Override
    public Fragment getBedTimeFragment() {
        return mBedTimeFragment;
    }
}
