package com.celerysoft.bedtime.fragment.bedtime.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.bedtime.adapter.WakeupTimeListViewAdapter;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.bedtime.presenter.IPresenterBedTime;
import com.celerysoft.bedtime.fragment.bedtime.presenter.PresenterBedTime;
import com.celerysoft.bedtime.base.BaseFragment;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by Celery on 16/4/12.
 *
 */
public class BedTimeFragment extends BaseFragment implements IViewBedTime {

    IPresenterBedTime mPresenter;
    public IPresenterBedTime getPresenter() {
        return mPresenter;
    }

    ImageView mIvHelp;

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

        mIvHelp = (ImageView) v.findViewById(R.id.bedtime_iv_help);
        mIvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showHelpDialog();
            }
        });

        mListViewWakeupTime = (ListViewCompat) v.findViewById(R.id.bedtime_lv_wakeup_time);
        mAdapter = mPresenter.fetchDataToCreateAdapter();
        mListViewWakeupTime.setAdapter(mAdapter);
        mListViewWakeupTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final int dayOfTheWeek = positionToDayOfTheWeek(position);

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        WakeupTimeBean wakeupTime = new WakeupTimeBean();
                        wakeupTime.setDayOfTheWeek(dayOfTheWeek);
                        wakeupTime.setHour(hourOfDay);
                        wakeupTime.setMinute(minute);
                        mPresenter.storeWakeupTime(wakeupTime);
                        mPresenter.updateAdapter(mAdapter, position, dayOfTheWeek);
                    }
                };

                mPresenter.showTimePickerDialog(onTimeSetListener, dayOfTheWeek);
            }
        });
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        mAdapter = mPresenter.fetchDataToCreateAdapter();
//        mListViewWakeupTime.setAdapter(mAdapter);
//    }

    private int positionToDayOfTheWeek(int position) {
        int dayOfTheWeek = Calendar.SUNDAY;
        switch (position) {
            case 0:
                dayOfTheWeek = Calendar.MONDAY;
                break;
            case 1:
                dayOfTheWeek = Calendar.TUESDAY;
                break;
            case 2:
                dayOfTheWeek = Calendar.WEDNESDAY;
                break;
            case 3:
                dayOfTheWeek = Calendar.THURSDAY;
                break;
            case 4:
                dayOfTheWeek = Calendar.FRIDAY;
                break;
            case 5:
                dayOfTheWeek = Calendar.SATURDAY;
                break;
            case 6:
                dayOfTheWeek = Calendar.SUNDAY;
                break;
            default:
                break;
        }
        return dayOfTheWeek;
    }
}
