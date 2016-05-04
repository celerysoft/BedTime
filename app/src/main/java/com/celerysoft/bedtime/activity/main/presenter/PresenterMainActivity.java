package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

import com.celerysoft.bedtime.BuildConfig;
import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.information.model.PersonalInformationModel;
import com.celerysoft.bedtime.activity.information.view.PersonalInformationActivity;
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

    private Context mContext;

    private Fragment mCurrentFragment;

    private long mLastPressBackTime;

    private PersonalInformationModel mModel;

    public PresenterMainActivity(IViewMainActivity view) {
        super();

        mView = view;

        mContext = mView.getContext();

        mModel = new PersonalInformationModel(mContext);
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
        return System.currentTimeMillis() - mLastPressBackTime <= 2000;
    }

    public void showExitAppSnackBar() {
        String text = mContext.getString(R.string.main_snack_bar_text);
        Snackbar.make(mView.getFloatActionButton(), text, Snackbar.LENGTH_LONG).show();
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

    @Override
    public void sendFeedback() {
        String content = "Version name: " + BuildConfig.VERSION_NAME
                + "Version code: " + BuildConfig.VERSION_CODE
                + "\n\n----------------\n\n";


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "celerysoft@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "BedTime Feedback");
        intent.putExtra(Intent.EXTRA_TEXT, content);

        if(mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            mContext.startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
            builder.setMessage(mContext.getString(R.string.main_could_not_send_feedback_dialog_message))
                    .setPositiveButton(mContext.getString(R.string.main_could_not_send_feedback_dialog_btn_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ClipData clipData = ClipData.newPlainText("Email address", "celerysoft@gmail.com");

                            ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                            cmb.setPrimaryClip(clipData);
                        }
                    })
                    .show();
        }


    }

    @Override
    public void startPersonalInformationActivity() {
        Intent intent = new Intent(mContext, PersonalInformationActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public String getNickname() {
        return mModel.getNickname();
    }

    @Override
    public String getSleepTime() {
        return mContext.getString(R.string.main_tv_sleep_time_text) + mModel.getSleepTime();
    }

    @Override
    public boolean isNewToBedTime() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_preferences_key_default), Context.MODE_PRIVATE);
        boolean isNew = sharedPreferences.getBoolean(mContext.getString(R.string.shared_preferences_key_is_new_user), true);

        if (isNew) {
            sharedPreferences.edit()
                    .putBoolean(mContext.getString(R.string.shared_preferences_key_is_new_user), false)
                    .apply();
        }

        return isNew;
    }

    @Override
    public void showWelcomeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
        builder.setTitle(mContext.getString(R.string.main_dialog_welcome_title))
                .setMessage(mContext.getString(R.string.main_dialog_welcome_message))
                .setPositiveButton(mContext.getString(R.string.main_dialog_welcome_positive_btn_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent intent = new Intent(mContext, PersonalInformationActivity.class);
                        mContext.startActivity(intent);
                    }
                })
                .show();
    }

    // TODO add social sharing.
    @Override
    public void showSocialSharingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
        builder.setMessage(mContext.getString(R.string.main_dialog_share_message))
                .setPositiveButton(mContext.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void hideFloatActionButton() {
        mView.getFloatActionButton().hide();
    }

    private void showFloatActionButton() {
        mView.getFloatActionButton().show();
    }
}
