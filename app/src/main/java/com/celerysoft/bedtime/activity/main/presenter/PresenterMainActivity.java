package com.celerysoft.bedtime.activity.main.presenter;

import android.app.Activity;
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
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;

import com.celerysoft.bedtime.BuildConfig;
import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.browser.view.BrowserActivity;
import com.celerysoft.bedtime.activity.information.model.PersonalInformationModel;
import com.celerysoft.bedtime.activity.information.view.PersonalInformationActivity;
import com.celerysoft.bedtime.activity.main.adapter.SocialSharingListViewAdapter;
import com.celerysoft.bedtime.activity.main.view.IViewMainActivity;
import com.celerysoft.bedtime.fragment.bedtime.view.BedTimeFragment;
import com.celerysoft.bedtime.fragment.main.view.MainFragment;
import com.celerysoft.bedtime.fragment.settings.view.SettingsFragment;
import com.celerysoft.bedtime.util.ActivityManagerUtil;
import com.celerysoft.bedtime.util.FileUtil;
import com.celerysoft.bedtime.util.Const;
import com.celerysoft.bedtime.base.BaseActivity;
import com.celerysoft.bedtime.util.InitViewUtil;
import com.celerysoft.bedtime.util.Util;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by Celery on 16/4/11.
 * Presenter for activity_main_toolbar activity.
 */
public class PresenterMainActivity implements IPresenterMainActivity {
    private final int REQUEST_CODE_OPEN_ABOUT_US_ACTIVITY = 1001;

    private IViewMainActivity mView;

    private Context mContext;

    private Fragment mCurrentFragment;

    private long mLastPressBackTime;

    private PersonalInformationModel mModel;

    private AlertDialog mSocialSharingDialog;

    public PresenterMainActivity(IViewMainActivity view) {
        super();

        mView = view;

        mContext = mView.getContext();

        mModel = new PersonalInformationModel(mContext);
    }

    private boolean readyToExitApp() {
        return System.currentTimeMillis() - mLastPressBackTime <= 2000;
    }

    private void preExitApp() {
        showExitAppSnackBar();
        mLastPressBackTime = System.currentTimeMillis();
    }

    private void showExitAppSnackBar() {
        String text = mContext.getString(R.string.main_snack_bar_text);
        Snackbar.make(mView.getFloatActionButton(), text, Snackbar.LENGTH_LONG)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        mLastPressBackTime = 0;
                    }
                })
                .show();
    }

    @Override
    public void exitApp() {
        if (readyToExitApp()) {
            ActivityManagerUtil.getInstance().exitApp();
        } else {
            preExitApp();
        }
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
        turnToFragment(fromFragment, toFragment, R.animator.move_in, R.animator.move_out, R.animator.move_in_pop, R.animator.move_out_pop);
    }

    private void turnToFragment(Fragment fromFragment, Fragment toFragment, int enter, int exit, int popEnter, int popExit) {
        if (fromFragment != null && fromFragment.equals(toFragment)) {
            return;
        }

        FragmentTransaction ft = mView.getFragmentManager().beginTransaction();

        if (fromFragment != null) {
            ft.setCustomAnimations(enter, exit, popEnter, popExit);
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
    public void turnToBedTimeFragmentQuickly() {
        turnToFragment(mCurrentFragment, mView.getBedTimeFragment(), R.animator.fade_in_quickly, R.animator.fade_out_quickly, R.animator.move_in_pop, R.animator.move_out_pop);
    }

    @Override
    public void turnToSettingsFragment() {
        turnToFragment(mCurrentFragment, mView.getSettingsFragment());
    }

    @Override
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
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


        Intent intent = new Intent(Intent.ACTION_SEND);
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

    @Override
    public void showSocialSharingDialog() {
        ListViewCompat listView = new ListViewCompat(mContext);
        listView.setAdapter(new SocialSharingListViewAdapter(mContext));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShareAction action = new ShareAction((Activity) mContext);
                action.withTitle(mContext.getString(R.string.main_share_title))
                        .withText(mContext.getString(R.string.main_share_message))
                        .withMedia(new UMImage(mContext, Const.BED_TIME_LOGO_URL))
                        .withTargetUrl(Const.ABOUT_BED_TIME_URL)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Snackbar.make(mView.getFloatActionButton(), R.string.main_snack_bar_share_success, Snackbar.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Snackbar.make(mView.getFloatActionButton(), R.string.main_snack_bar_share_fail, Snackbar.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Snackbar.make(mView.getFloatActionButton(), R.string.main_snack_bar_share_cancel, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                switch (position) {
                    case SocialSharingListViewAdapter.WECHAT:
                        action.setPlatform(SHARE_MEDIA.WEIXIN);
                        break;
                    case SocialSharingListViewAdapter.WECHAT_TIMELINE:
                        action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case SocialSharingListViewAdapter.QQ:
                        action.setPlatform(SHARE_MEDIA.QQ);
                        break;
                    case SocialSharingListViewAdapter.QZONE:
                        action.setPlatform(SHARE_MEDIA.QZONE);
                        break;
                    default:
                        break;
                }
                if (mSocialSharingDialog != null && mSocialSharingDialog.isShowing()) {
                    mSocialSharingDialog.dismiss();
                }
                action.share();
            }
        });
        InitViewUtil.getInstance().initListView(listView);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
        mSocialSharingDialog = builder.setView(listView, 0, Util.dp2px(mContext, 20), 0, Util.dp2px(mContext, 20))
                .setTitle(R.string.main_dialog_share_title)
                .show();
        InitViewUtil.getInstance().initAlertDialog(mSocialSharingDialog);
    }

    @Override
    public void openAboutBedTimeActivity() {
        mView.getFloatActionButton().hide();
        mView.getFloatActionButton().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, BrowserActivity.class);
                intent.putExtra(BrowserActivity.INTENT_EXTRA_STRING_NAME_OF_URL, Const.ABOUT_BED_TIME_URL);
                intent.putExtra(BrowserActivity.INTENT_EXTRA_STRING_NAME_OF_TITLE, mContext.getString(R.string.action_about_us));
                ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE_OPEN_ABOUT_US_ACTIVITY);
            }
        }, 300);
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPEN_ABOUT_US_ACTIVITY:
                if (mCurrentFragment.equals(mView.getMainFragment())) {
                    mView.getAnimationWrapper().setVisibility(View.VISIBLE);
                    mView.getFloatActionButton().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mView.getFloatActionButton().show();
                        }
                    }, 300);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void performFabAnimation() {
        mView.getFloatActionButton().hide();
        mView.getFloatActionButton().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.getAnimationWrapper().performAnimation();
            }
        }, 150);
    }

    private void hideFloatActionButton() {
        mView.getAnimationWrapper().setVisibility(View.INVISIBLE);
        mView.getFloatActionButton().hide();
    }

    private void showFloatActionButton() {
        mView.getAnimationWrapper().setVisibility(View.VISIBLE);
        mView.getFloatActionButton().show();
    }
}
