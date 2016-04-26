package com.celerysoft.bedtime.fragment.bedtime.presenter;

import com.celerysoft.bedtime.fragment.bedtime.WakeupTimeListViewAdapter;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

/**
 * Created by Celery on 16/4/15.
 */
public interface IPresenterBedTime {
    WakeupTimeListViewAdapter fetchDataToCreateAdapter();
    void storeWakeupTime(WakeupTimeBean wakeupTime);
    void updateAdapter(WakeupTimeListViewAdapter adapter, int dayOfTheWeek);
    void showTimePickerDialog(TimePickerDialog.OnTimeSetListener listener, int dayOfTheWeek);
}
