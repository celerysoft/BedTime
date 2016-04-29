package com.celerysoft.bedtime.activity.main.view;

import android.app.Fragment;
import android.content.Context;
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

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.presenter.IPresenterMainActivity;
import com.celerysoft.bedtime.activity.main.presenter.PresenterMainActivity;
import com.celerysoft.bedtime.fragment.bedtime.view.BedTimeFragment;
import com.celerysoft.bedtime.fragment.main.view.MainFragment;
import com.celerysoft.bedtime.fragment.settings.view.SettingsFragment;
import com.celerysoft.bedtime.view.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IViewMainActivity {

    private IPresenterMainActivity mPresenter;
    public IPresenterMainActivity getPresenter() {
        return mPresenter;
    }

    private FloatingActionButton mFloatingActionButton;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private MainFragment mMainFragment;
    private BedTimeFragment mBedTimeFragment;
    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new PresenterMainActivity(this);

        init();

        restoreInstanceState(getIntent().getExtras());
    }

    private void init() {

        mMainFragment = new MainFragment();
        mBedTimeFragment = new BedTimeFragment();
        mSettingsFragment = new SettingsFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.turnToBedTimeFragment();
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
    }

    protected void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String launchFragment = savedInstanceState.getString("launch_fragment");
            if (launchFragment != null) {
                if (launchFragment.equals("settings")) {
                    mPresenter.turnToSettingsFragment();
                    return;
                }
            }
        }

        mPresenter.turnToMainFragment();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (mPresenter.getCurrentFragment().getClass().equals(MainFragment.class)) {
                if (mPresenter.readyToExitApp()) {
                    mPresenter.exitApp();
                } else {
                    mPresenter.preExitApp();
                }
            } else {
                mPresenter.turnToMainFragment();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mPresenter.turnToSettingsFragment();
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

        } else if (id == R.id.nav_feedback) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer != null) {
//            drawer.closeDrawer(GravityCompat.START);
//        }

        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter = null;
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
    public Fragment getCurrentFragment() {
        return null;
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
