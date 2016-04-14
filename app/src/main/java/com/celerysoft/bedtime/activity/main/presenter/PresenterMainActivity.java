package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.IViewMainActivity;

/**
 * Created by Celery on 16/4/11.
 * Presenter for main activity.
 */
public class PresenterMainActivity implements IPresenterMainActivity {
    private IViewMainActivity mView;

    private Fragment mCurrentFragment;

    public PresenterMainActivity(IViewMainActivity view) {
        super();

        mView = view;
    }

    @Override
    public void showExitAppSnackBar() {
        String text = mView.getContext().getString(R.string.main_snack_bar_text);
        String actionText = mView.getContext().getString(R.string.main_snack_bar_action_text);
        Snackbar.make(mView.getFloatActionButton(), text, Snackbar.LENGTH_LONG)
                .setAction(actionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mView.finish();
                    }
                })
                .show();
    }

    private void turnToFragment(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != null && fromFragment.equals(toFragment)) {
            return;
        }

        FragmentTransaction ft = mView.getFragmentManager().beginTransaction();

        if (fromFragment != null) {
            //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            ft.hide(fromFragment);
        }

        if (toFragment != null) {
            if (toFragment.isAdded()) {
                ft.show(toFragment);
            } else {
                ft.add(R.id.main_fragment_container, toFragment);
            }
        }

        ft.commit();
    }

    @Override
    public void turnToMainFragment() {
        turnToFragment(mCurrentFragment, mView.getMainFragment());
        mCurrentFragment = mView.getMainFragment();
        mView.getSupportActionBar().setTitle(R.string.main_actionbar_title);
        mView.getFloatActionButton().setVisibility(View.VISIBLE);
    }

    @Override
    public void turnToBedTimeFragment() {
        turnToFragment(mCurrentFragment, mView.getBedTimeFragment());
        mCurrentFragment = mView.getBedTimeFragment();
        mView.getSupportActionBar().setTitle(R.string.bedtime_actionbar_title);
        mView.getFloatActionButton().setVisibility(View.VISIBLE);
    }

    @Override
    public void turnToSettingsFragment() {
        turnToFragment(mCurrentFragment, mView.getSettingsFragment());
        mCurrentFragment = mView.getSettingsFragment();
        mView.getSupportActionBar().setTitle(R.string.settings_actionbar_title);
        mView.getFloatActionButton().setVisibility(View.GONE);
    }


}
