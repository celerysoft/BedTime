package com.celerysoft.bedtime.fragment.bedtime.presenter;

import com.celerysoft.bedtime.activity.main.presenter.IPresenterMainActivity;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.bedtime.view.IViewBedTime;

/**
 * Created by Celery on 16/4/15.
 */
public class PresenterBedTime implements IPresenterBedTime {

    IViewBedTime mView;

    IPresenterMainActivity mPresenterMainActivity;


    public PresenterBedTime(IViewBedTime view) {
        mView = view;

        mPresenterMainActivity = ((MainActivity) mView.getActivity()).getPresenter();
    }

    @Override
    public void onBackPress() {
        mPresenterMainActivity.turnToMainFragment();
    }
}
