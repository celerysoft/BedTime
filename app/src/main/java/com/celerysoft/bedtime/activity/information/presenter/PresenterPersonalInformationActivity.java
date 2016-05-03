package com.celerysoft.bedtime.activity.information.presenter;

import android.content.Context;
import android.view.View;

import com.celerysoft.bedtime.activity.information.model.PersonalInformationModel;
import com.celerysoft.bedtime.activity.information.view.IViewPersonalInformationActivity;

/**
 * Created by admin on 16/5/3.
 */
public class PresenterPersonalInformationActivity implements IPresenterPersonalInformationActivity {
    private IViewPersonalInformationActivity mView;
    private PersonalInformationModel mModel;

    private Context mContext;

    public PresenterPersonalInformationActivity(IViewPersonalInformationActivity view) {
        mView = view;

        mContext = mView.getContext();

        mModel = new PersonalInformationModel(mContext);
    }

    @Override
    public String getNickname() {
        return mModel.getNickname();
    }

    @Override
    public void editNickname() {
        mView.getFloatingActionButton().show();
        mView.getTvNickname().setVisibility(View.GONE);
        mView.getEtNickname().setVisibility(View.VISIBLE);
        mView.getEtNickname().setText(mView.getTvNickname().getText());
        mView.getEtNickname().requestFocus();
    }

    @Override
    public String getAge() {
        return mModel.getAge();
    }

    @Override
    public void editAge() {
        mView.getFloatingActionButton().show();
        mView.getTvAge().setVisibility(View.GONE);
        mView.getEtAge().setVisibility(View.VISIBLE);
        mView.getEtAge().setText(mView.getTvAge().getText());
        mView.getEtAge().requestFocus();
    }

    @Override
    public String getSleepTime() {
        return mModel.getSleepTime();
    }

    @Override
    public void saveInformation() {
        mView.getFloatingActionButton().hide();

        if (mView.getTvNickname().getVisibility() == View.GONE) {
            String nickname = mView.getEtNickname().getText().toString();

            mView.getTvNickname().setVisibility(View.VISIBLE);
            mView.getTvNickname().setText(nickname);
            mView.getEtNickname().setVisibility(View.GONE);
            mModel.setNickname(nickname);
        }

        if (mView.getTvAge().getVisibility() == View.GONE) {
            String age = mView.getEtAge().getText().toString();

            mView.getTvAge().setVisibility(View.VISIBLE);
            mView.getTvAge().setText(age);
            mView.getEtAge().setVisibility(View.GONE);
            mModel.setAge(age);
        }
    }

    @Override
    public void showSleepTimePickerDialog() {

    }


}
