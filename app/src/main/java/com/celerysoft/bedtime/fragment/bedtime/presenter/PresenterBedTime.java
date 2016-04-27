package com.celerysoft.bedtime.fragment.bedtime.presenter;

import android.content.Context;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.presenter.IPresenterMainActivity;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.fragment.bedtime.WakeupTimeListViewAdapter;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeModel;
import com.celerysoft.bedtime.fragment.bedtime.view.IViewBedTime;
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


    public PresenterBedTime(IViewBedTime view) {
        mView = view;

        mContext = mView.getActivity();

        mPresenterMainActivity = ((MainActivity) mView.getActivity()).getPresenter();

        mModel = new WakeupTimeModel(mContext);
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

        adapter.setWakeupTimes(wakeupTimes);

        return adapter;
    }

    @Override
    public void storeWakeupTime(WakeupTimeBean wakeupTime) {
        mModel.storeWakeupTime(wakeupTime);
    }

    @Override
    public void showTimePickerDialog(TimePickerDialog.OnTimeSetListener listener, int dayOfTheWeek) {
        WakeupTimeBean wakeupTime = mModel.findWakeUpTimeByDayOfTheWeek(dayOfTheWeek);

        // TODO 处理12小时制和24小时制
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(listener, wakeupTime.getWakeupHour(), wakeupTime.getWakeupMinute(), true);
        timePickerDialog.setThemeDark(false);
        timePickerDialog.vibrate(false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.enableSeconds(false);
        try {
            timePickerDialog.setAccentColor(mContext.getResources().getColor(R.color.colorPrimary, null));
        } catch (NoSuchMethodError e) {
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
}
