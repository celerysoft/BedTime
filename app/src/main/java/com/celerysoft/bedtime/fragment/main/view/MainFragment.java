package com.celerysoft.bedtime.fragment.main.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.main.presenter.IPresenterMain;
import com.celerysoft.bedtime.fragment.main.presenter.PresenterMain;
import com.celerysoft.bedtime.base.BaseFragment;

/**
 * Created by Celery on 16/4/12.
 */
public class MainFragment extends BaseFragment implements IViewMain {
    private final String TAG = "MainFragment";

    private IPresenterMain mPresenter;

    private SwitchCompat mSwitch;
    private AppCompatTextView mTvLeftHour;
    private AppCompatTextView mTvLeftHourLabel;
    private AppCompatTextView mTvLeftMinute;
    private AppCompatTextView mTvLeftMinuteLabel;
    private AppCompatTextView mTvAction;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mPresenter = new PresenterMain(this);

        initView(v);

        mPresenter.startCountDownThread();

        return v;
    }

    private void initView(View v) {
        mSwitch = (SwitchCompat) v.findViewById(R.id.main_fragment_switch_notification);
        mSwitch.setChecked(mPresenter.getNotificationStatus());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPresenter.turnOnNotification();
                } else {
                    mPresenter.turnOffNotification();
                }
            }
        });

        mTvLeftHour = (AppCompatTextView) v.findViewById(R.id.main_fragment_tv_left_hour);
        mTvLeftHourLabel = (AppCompatTextView) v.findViewById(R.id.main_fragment_tv_left_hour_label);
        mTvLeftMinute = (AppCompatTextView) v.findViewById(R.id.main_fragment_tv_left_minute);
        mTvLeftMinuteLabel = (AppCompatTextView) v.findViewById(R.id.main_fragment_tv_left_minute_label);
        mTvAction = (AppCompatTextView) v.findViewById(R.id.main_fragment_tv_action);

    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        mPresenter.stopCountDownThread();
        super.onDestroyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden) {
            mPresenter.stopCountDownThread();
        } else {
            mPresenter.startCountDownThread();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public AppCompatTextView getTvLeftHour() {
        return mTvLeftHour;
    }

    @Override
    public AppCompatTextView getTvAction() {
        return mTvAction;
    }

    @Override
    public AppCompatTextView getTvLeftMinuteLabel() {
        return mTvLeftMinuteLabel;
    }

    @Override
    public AppCompatTextView getTvLeftMinute() {
        return mTvLeftMinute;
    }

    @Override
    public AppCompatTextView getTvLeftHourLabel() {
        return mTvLeftHourLabel;
    }
}
