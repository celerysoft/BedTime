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
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

/**
 * Created by Celery on 16/4/12.
 */
public class BedTimeFragment extends Fragment implements IViewBedTime {

    ListViewCompat mListViewWakeupTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bedtime, container, false);

        initView(v);

        return v;
    }

    private void initView(View v) {
        mListViewWakeupTime = (ListViewCompat) v.findViewById(R.id.bedtime_lv_wakeup_time);
        mListViewWakeupTime.setAdapter(new WakeupTimeListViewAdapter(this.getActivity()));
        mListViewWakeupTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

                    }
                };

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener, 2, 30, true);
                timePickerDialog.setThemeDark(false);
                timePickerDialog.vibrate(false);
                timePickerDialog.dismissOnPause(true);
                timePickerDialog.enableSeconds(false);
                try {
                    timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary, null));
                } catch (NoSuchMethodError e) {
                    timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                }
                timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
            }
        });
    }
}
