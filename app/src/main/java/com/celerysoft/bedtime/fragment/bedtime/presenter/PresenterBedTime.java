package com.celerysoft.bedtime.fragment.bedtime.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.information.view.PersonalInformationActivity;
import com.celerysoft.bedtime.activity.main.presenter.IPresenterMainActivity;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.bedtime.adapter.WakeupTimeListViewAdapter;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.fragment.bedtime.view.IViewBedTime;
import com.celerysoft.bedtime.fragment.main.model.BedTimeModel;
import com.celerysoft.bedtime.fragment.settings.model.SettingsModel;
import com.celerysoft.bedtime.util.AlarmUtil;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Celery on 16/4/15.
 */
public class PresenterBedTime implements IPresenterBedTime {

    IViewBedTime mView;

    Context mContext;

    IPresenterMainActivity mPresenterMainActivity;

    WakeupTimeModel mModel;
    BedTimeModel mBedTimeModel;
    SettingsModel mSettingsModel;


    public PresenterBedTime(IViewBedTime view) {
        mView = view;

        mContext = mView.getActivity();

        mPresenterMainActivity = ((MainActivity) mView.getActivity()).getPresenter();

        mModel = new WakeupTimeModel(mContext);
        mBedTimeModel = new BedTimeModel(mContext);
        mSettingsModel = new SettingsModel(mContext);
    }

    @Override
    public WakeupTimeListViewAdapter fetchDataToCreateAdapter() {
        WakeupTimeListViewAdapter adapter = new WakeupTimeListViewAdapter(mView.getActivity());

        ArrayList<WakeupTimeBean> wakeupTimes = new ArrayList<>();

        WakeupTimeBean monday = mModel.findWakeUpTimeByDayOfTheWeek(Calendar.MONDAY);
        wakeupTimes.add(monday);
        WakeupTimeBean tuesday = mModel.findWakeUpTimeByDayOfTheWeek(Calendar.TUESDAY);
        wakeupTimes.add(tuesday);
        WakeupTimeBean wednesday = mModel.findWakeUpTimeByDayOfTheWeek(Calendar.WEDNESDAY);
        wakeupTimes.add(wednesday);
        WakeupTimeBean thursday = mModel.findWakeUpTimeByDayOfTheWeek(Calendar.THURSDAY);
        wakeupTimes.add(thursday);
        WakeupTimeBean friday = mModel.findWakeUpTimeByDayOfTheWeek(Calendar.FRIDAY);
        wakeupTimes.add(friday);
        WakeupTimeBean saturday = mModel.findWakeUpTimeByDayOfTheWeek(Calendar.SATURDAY);
        wakeupTimes.add(saturday);
        WakeupTimeBean sunday = mModel.findWakeUpTimeByDayOfTheWeek(Calendar.SUNDAY);
        wakeupTimes.add(sunday);

        adapter.setIs24HourTime(mSettingsModel.is24HourTime());
        adapter.setWakeupTimes(wakeupTimes);


        return adapter;
    }

    @Override
    public void storeWakeupTime(WakeupTimeBean wakeupTime) {
        mModel.storeWakeupTime(wakeupTime);
        mBedTimeModel.refreshBedTimeByDayOfTheWeek(wakeupTime.getDayOfTheWeek());

        updateAlarm();
    }

    @Override
    public void showTimePickerDialog(TimePickerDialog.OnTimeSetListener listener, int dayOfTheWeek) {
        WakeupTimeBean wakeupTime = mModel.findWakeUpTimeByDayOfTheWeek(dayOfTheWeek);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(listener, wakeupTime.getHour(), wakeupTime.getMinute(), mSettingsModel.is24HourTime());
        timePickerDialog.setThemeDark(false);
        timePickerDialog.vibrate(false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.enableSeconds(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePickerDialog.setAccentColor(mContext.getResources().getColor(R.color.colorPrimary, null));
        } else {
            timePickerDialog.setAccentColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        timePickerDialog.show(mView.getFragmentManager(), "TimePickerDialog");
    }

    @Override
    public void updateAdapter(WakeupTimeListViewAdapter adapter, int position, int dayOfTheWeek) {
        WakeupTimeBean wakeupTime = mModel.findWakeUpTimeByDayOfTheWeek(dayOfTheWeek);
        adapter.getWakeupTimes().set(position, wakeupTime);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AppTheme_Dialog_Light);
        builder.setTitle(mContext.getString(R.string.bedtime_help_dialog_title))
                .setMessage(mContext.getString(R.string.bedtime_help_dialog_message))
                .setPositiveButton(mContext.getString(R.string.got_it), null)
                .show();
    }

    private void updateAlarm() {
        AlarmUtil.getInstance().setUpNextAlarm(mContext);
    }
}
