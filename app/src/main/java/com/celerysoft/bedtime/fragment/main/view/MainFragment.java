package com.celerysoft.bedtime.fragment.main.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.main.presenter.IPresenterMain;
import com.celerysoft.bedtime.fragment.main.presenter.PresenterMain;

/**
 * Created by Celery on 16/4/12.
 */
public class MainFragment extends Fragment implements IViewMain {

    IPresenterMain mPresenter;

    SwitchCompat mSwitch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mPresenter = new PresenterMain(this);

        mSwitch = (SwitchCompat) v.findViewById(R.id.main_fragment_switch_notification);
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

        return v;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
