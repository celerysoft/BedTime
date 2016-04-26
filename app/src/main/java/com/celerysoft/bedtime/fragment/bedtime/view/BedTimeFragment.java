package com.celerysoft.bedtime.fragment.bedtime.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.bedtime.WakeupTimeListViewAdapter;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.presenter.PresenterBedTime;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

/**
 * Created by Celery on 16/4/12.
 */
public class BedTimeFragment extends Fragment implements IViewBedTime {

    PresenterBedTime mPresenter;

    ListViewCompat mListViewWakeupTime;
    WakeupTimeListViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bedtime, container, false);

        initView(v);

        return v;
    }

    private void initView(View v) {
        mPresenter = new PresenterBedTime(this);

        mListViewWakeupTime = (ListViewCompat) v.findViewById(R.id.bedtime_lv_wakeup_time);
        mAdapter = mPresenter.fetchDataToCreateAdapter();
        mListViewWakeupTime.setAdapter(mAdapter);
        mListViewWakeupTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        WakeupTimeBean wakeupTime = new WakeupTimeBean();
                        wakeupTime.setDayOfTheWeek(position);
                        wakeupTime.setWakeupHour(hourOfDay);
                        wakeupTime.setWakeupMinute(minute);
                        mPresenter.storeWakeupTime(wakeupTime);
                        mPresenter.updateAdapter(mAdapter, position);
                    }
                };

                mPresenter.showTimePickerDialog(onTimeSetListener, position);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        mAdapter = mPresenter.fetchDataToCreateAdapter();
        mListViewWakeupTime.setAdapter(mAdapter);
    }
}
