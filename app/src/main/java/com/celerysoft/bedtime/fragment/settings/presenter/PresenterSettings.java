package com.celerysoft.bedtime.fragment.settings.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.information.view.PersonalInformationActivity;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.settings.bean.Sound;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;
import com.celerysoft.bedtime.fragment.settings.view.IViewSettings;
import com.celerysoft.bedtime.util.InitViewUtil;

/**
 * Created by admin on 16/4/29.
 *
 */
public class PresenterSettings implements IPresenterSettings {
    private IViewSettings mView;
    private Context mContext;

    private SettingsModel mModel;
    public SettingsModel getModel() {
        return mModel;
    }

    private int mNewLanguage;
    private int mNewSound;

    public PresenterSettings(IViewSettings view) {
        mView = view;
        mContext = mView.getActivity();

        mModel = new SettingsModel(mContext);
    }

    @Override
    public void showChooseLanguageDialog() {
        final int oldLanguage = mModel.getAppLanguage();
        mNewLanguage = oldLanguage;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
        builder.setTitle(mContext.getString(R.string.settings_fragment_tv_language_text))
                .setSingleChoiceItems(mModel.getLanguageStrings(), oldLanguage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNewLanguage = which;
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mNewLanguage != oldLanguage) {
                            mModel.setAppLanguage(mNewLanguage);
                            changeAppLanguage();
                        }
                    }
                });

        AlertDialog dialog = builder.show();
        InitViewUtil.getInstance().initAlertDialog(dialog);
    }

    @Override
    public void showChooseSoundDialog() {
        final int oldSound = mModel.getAppSoundIndex();
        mNewSound = oldSound;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
        builder.setTitle(mContext.getString(R.string.settings_fragment_tv_sound_text))
                .setSingleChoiceItems(mModel.getSoundStrings(), oldSound, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNewSound = which;

                        Sound sound = mModel.findSoundByIndex(which);
                        if (sound == null) {
                            return;
                        }

                        Uri uri = Uri.parse(sound.getUri());
                        Ringtone r = RingtoneManager.getRingtone(mContext, uri);
                        r.play();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (PresenterSettings.this.mNewSound != oldSound) {
                            mModel.setAppSound(mNewSound);
                            mView.getSoundDescTv().setText(mModel.findSoundByIndex(mNewSound).getTitle());
                        }
                    }
                });

        AlertDialog dialog = builder.show();
        InitViewUtil.getInstance().initAlertDialog(dialog);
    }

    @Override
    public void showPersonalInformation() {
        Intent intent = new Intent(mContext, PersonalInformationActivity.class);
        mContext.startActivity(intent);
    }

    private void changeAppLanguage() {
//        Resources resources = mContext.getResources();
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//
//        Configuration config = resources.getConfiguration();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLocale(mModel.getLocale());
//            mContext.getApplicationContext().createConfigurationContext(config);
//            //noinspection deprecation
//            resources.updateConfiguration(config, displayMetrics);
//        } else {
//            //noinspection deprecation
//            config.locale = mModel.getLocale();
//            //noinspection deprecation
//            resources.updateConfiguration(config, displayMetrics);
//        }

        restartActivity();
    }

    private void restartActivity() {
        mView.getActivity().finish();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("launch_fragment", "settings");
        mContext.startActivity(intent);
    }

    @Override
    public String getLanguageString() {
        return mModel.getAppLanguageString();
    }

    @Override
    public String getSoundString() {
        return mModel.getAppSoundString();
    }

    @Override
    public void apply24HourTime(boolean applied) {
        mModel.apply24HourTime(applied);
    }

    @Override
    public boolean is24HourTime() {
        return mModel.is24HourTime();
    }
}
