package com.celerysoft.bedtime.fragment.settings.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.settings.presenter.IPresenterSettings;
import com.celerysoft.bedtime.fragment.settings.presenter.PresenterSettings;
import com.celerysoft.bedtime.view.BaseFragment;

/**
 * Created by admin on 16/4/29.
 */
public class SettingsFragment extends BaseFragment implements IViewSettings {
    IPresenterSettings mPresenter;
    public IPresenterSettings getPresenter() {
        return mPresenter;
    }

    private View mViewTimeFormat;
    private AppCompatTextView mTvTimeFormat;
    private SwitchCompat mSwitchTimeFormat;
    private View mViewLanguage;
    private AppCompatTextView mTvLanguage;
    private View mViewPersonalInformation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new PresenterSettings(this);

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mViewTimeFormat = v.findViewById(R.id.settings_fragment_time_format);
        mViewTimeFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitchTimeFormat.setChecked(!mSwitchTimeFormat.isChecked());
            }
        });

        mTvTimeFormat = (AppCompatTextView) v.findViewById(R.id.settings_fragment_tv_time_format_desc);

        mSwitchTimeFormat = (SwitchCompat) v.findViewById(R.id.settings_fragment_switch_time_format);
        mSwitchTimeFormat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPresenter.apply24HourTime(true);
                    mTvTimeFormat.setText(getString(R.string.settings_fragment_tv_time_format_desc_text_24));
                } else {
                    mPresenter.apply24HourTime(false);
                    mTvTimeFormat.setText(getString(R.string.settings_fragment_tv_time_format_desc_text_12));
                }
            }
        });
        boolean is24HourTime = mPresenter.is24HourTime();
        mSwitchTimeFormat.setChecked(is24HourTime);

        mViewLanguage = v.findViewById(R.id.settings_fragment_language);
        mViewLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showChooseLanguageDialog();
            }
        });

        mTvLanguage = (AppCompatTextView) v.findViewById(R.id.settings_fragment_tv_language_desc);
        mTvLanguage.setText(mPresenter.getLanguageString());

        mViewPersonalInformation = v.findViewById(R.id.settings_fragment_personal_information);
        mViewPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showPersonalInformation();
            }
        });

        return v;
    }
}
