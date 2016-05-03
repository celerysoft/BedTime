package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.Snackbar;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.IViewMainActivity;
import com.celerysoft.bedtime.fragment.bedtime.view.BedTimeFragment;
import com.celerysoft.bedtime.fragment.main.view.MainFragment;
import com.celerysoft.bedtime.fragment.settings.view.SettingsFragment;
import com.celerysoft.bedtime.util.ActivityManagerUtil;
import com.celerysoft.bedtime.view.BaseActivity;

/**
 * Created by Celery on 16/4/11.
 * Presenter for main activity.
 */
public class PresenterMainActivity implements IPresenterMainActivity {
    private IViewMainActivity mView;

    private Fragment mCurrentFragment;

    private long mLastPressBackTime;

    public PresenterMainActivity(IViewMainActivity view) {
        super();

        mView = view;
    }

    @Override
    public void preExitApp() {
        showExitAppSnackBar();
        mLastPressBackTime = System.currentTimeMillis();
    }

    @Override
    public void exitApp() {
        ActivityManagerUtil.getInstance().exitApp((BaseActivity) mView);
    }

    @Override
    public boolean readyToExitApp() {
        if (System.currentTimeMillis() - mLastPressBackTime <= 2000) {
            return true;
        } else {
            return false;
        }
    }

    public void showExitAppSnackBar() {
        String text = mView.getContext().getString(R.string.main_snack_bar_text);
//        String actionText = mView.getContext().getString(R.string.main_snack_bar_action_text);
        Snackbar.make(mView.getFloatActionButton(), text, Snackbar.LENGTH_LONG)
//                .setAction(actionText, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mView.finish();
//                    }
//                })
                .show();
    }

    @Override
    public void setMainFragment() {
        mView.getFragmentManager().beginTransaction()
                .add(R.id.main_fragment_container, mView.getMainFragment())
                .commit();

        mCurrentFragment = mView.getMainFragment();
        mView.getSupportActionBar().setTitle(R.string.main_actionbar_title);
        mView.getNavigationView().setCheckedItem(R.id.nav_main);
        showFloatActionButton();
    }

    private void turnToFragment(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != null && fromFragment.equals(toFragment)) {
            return;
        }

        FragmentTransaction ft = mView.getFragmentManager().beginTransaction();

        if (fromFragment != null) {
            ft.setCustomAnimations(R.animator.move_in, R.animator.move_out, R.animator.move_in, R.animator.move_out);
            ft.replace(R.id.main_fragment_container, toFragment);
        } else {
            ft.add(R.id.main_fragment_container, toFragment);
            if (toFragment.getClass().equals(MainFragment.class)) {
                ft.commit();
                return;
            }
        }

        ft.addToBackStack(null);
        ft.commit();
    }

//    private void turnToFragment(Fragment fromFragment, Fragment toFragment) {
//        if (fromFragment != null && fromFragment.equals(toFragment)) {
//            return;
//        }
//
//        FragmentTransaction ft = mView.getFragmentManager().beginTransaction();
//
//        if (fromFragment != null) {
//            ft.setCustomAnimations(R.animator.move_in, R.animator.move_out, android.R.animator.fade_in, android.R.animator.fade_out);
//            ft.hide(fromFragment);
//        }
//
//        if (toFragment != null) {
//            if (toFragment.isAdded()) {
//                ft.show(toFragment);
//            } else {
//                ft.add(R.id.main_fragment_container, toFragment);
//            }
//        }
//
//        ft.commit();
//    }

    @Override
    public void turnToMainFragment() {
        turnToFragment(mCurrentFragment, mView.getMainFragment());
    }

    @Override
    public void turnToBedTimeFragment() {
        turnToFragment(mCurrentFragment, mView.getBedTimeFragment());
    }

    @Override
    public void turnToSettingsFragment() {
        turnToFragment(mCurrentFragment, mView.getSettingsFragment());
    }

    @Override
    public void updateViewByFragmentInfo(Fragment fragment) {
        if (fragment instanceof MainFragment) {
            mCurrentFragment = mView.getMainFragment();
            mView.getSupportActionBar().setTitle(R.string.main_actionbar_title);
            mView.getNavigationView().setCheckedItem(R.id.nav_main);
            showFloatActionButton();
        } else if (fragment instanceof BedTimeFragment) {
            mCurrentFragment = mView.getBedTimeFragment();
            mView.getSupportActionBar().setTitle(R.string.bedtime_actionbar_title);
            mView.getNavigationView().setCheckedItem(R.id.nav_bedtime);
            hideFloatActionButton();
        } else if (fragment instanceof SettingsFragment) {
            mCurrentFragment = mView.getSettingsFragment();
            mView.getSupportActionBar().setTitle(R.string.settings_actionbar_title);
            mView.getNavigationView().setCheckedItem(R.id.nav_settings);
            hideFloatActionButton();
        }
    }

    private void hideFloatActionButton() {
        mView.getFloatActionButton().hide();
    }

    private void showFloatActionButton() {
        mView.getFloatActionButton().show();
    }
}
