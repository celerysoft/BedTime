package com.celerysoft.bedtime.fragment.settings.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;
import com.celerysoft.bedtime.fragment.settings.view.IViewSettings;

/**
 * Created by admin on 16/4/29.
 */
public class PresenterSettings implements IPresenterSettings {
    private IViewSettings mView;
    private Context mContext;

    private SharedPreferences mSharedPreferences;
    private SettingsModel mModel;

    private int newLanguage;

    public PresenterSettings(IViewSettings view) {
        mView = view;
        mContext = mView.getActivity();

        mSharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.shared_preferences_key_default), Context.MODE_PRIVATE);

        mModel = new SettingsModel(mContext, mSharedPreferences);
    }

    @Override
    public void showChooseLanguageDialog() {
        final int oldLanguage = mModel.getAppLanguage();
        newLanguage = oldLanguage;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
        builder.setTitle(mContext.getString(R.string.settings_fragment_tv_language_text))
                .setSingleChoiceItems(mModel.getLanguageStrings(), oldLanguage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PresenterSettings.this.newLanguage = which;
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (PresenterSettings.this.newLanguage != oldLanguage) {
                            mModel.setAppLanguage(newLanguage);
                            PresenterSettings.this.changeAppLanguage();
                        }
                    }
                })
                .show();
    }

    @Override
    public void showPersonalInformation() {

    }

    private void changeAppLanguage() {
        Resources resources = mContext.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        Configuration config = resources.getConfiguration();
        config.locale = mModel.getLocale();
        resources.updateConfiguration(config, displayMetrics);

        restartActivity();
    }

    private void restartActivity() {
        mView.getActivity().finish();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("launch_fragment", "settings");
        mContext.startActivity(intent);
    }
}
