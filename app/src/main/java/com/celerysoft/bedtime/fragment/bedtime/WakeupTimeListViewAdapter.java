package com.celerysoft.bedtime.fragment.bedtime;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Celery on 16/4/15.
 */
public class WakeupTimeListViewAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<WakeupTimeBean> mWakeupTimes;
    public ArrayList<WakeupTimeBean> getWakeupTimes() {
        return mWakeupTimes;
    }

    public void setWakeupTimes(ArrayList<WakeupTimeBean> wakeupTimes) {
        mWakeupTimes = wakeupTimes;
    }

    public WakeupTimeListViewAdapter(Context context) {
        mContext = context;

        //createDefaultItems();
    }

    private void createDefaultItems() {
        mWakeupTimes = new ArrayList<>();

        WakeupTimeBean item0 = new WakeupTimeBean();
        item0.setDayOfTheWeek(0);
        item0.setHour(1);
        item0.setMinute(30);
        mWakeupTimes.add(item0);

        WakeupTimeBean item1 = new WakeupTimeBean();
        item1.setDayOfTheWeek(1);
        item1.setHour(2);
        item1.setMinute(30);
        mWakeupTimes.add(item1);

        WakeupTimeBean item2 = new WakeupTimeBean();
        item2.setDayOfTheWeek(2);
        item2.setHour(3);
        item2.setMinute(30);
        mWakeupTimes.add(item2);

        WakeupTimeBean item3 = new WakeupTimeBean();
        item3.setDayOfTheWeek(3);
        item3.setHour(4);
        item3.setMinute(30);
        mWakeupTimes.add(item3);

        WakeupTimeBean item4 = new WakeupTimeBean();
        item4.setDayOfTheWeek(4);
        item4.setHour(5);
        item4.setMinute(30);
        mWakeupTimes.add(item4);

        WakeupTimeBean item5 = new WakeupTimeBean();
        item5.setDayOfTheWeek(5);
        item5.setHour(6);
        item5.setMinute(30);
        mWakeupTimes.add(item5);

        WakeupTimeBean item6 = new WakeupTimeBean();
        item6.setDayOfTheWeek(6);
        item6.setHour(7);
        item6.setMinute(30);
        mWakeupTimes.add(item6);

    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_wakeup_time_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (AppCompatTextView) convertView.findViewById(R.id.list_view_wakeup_time_title);
            viewHolder.content = (AppCompatTextView) convertView.findViewById(R.id.list_view_wakeup_time_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WakeupTimeBean bean = mWakeupTimes.get(position);
        viewHolder.title.setText(getWakeupTimeTitleString(bean));
        viewHolder.content.setText(getWakeupTimeContentString(bean));

        return convertView;
    }

    private class ViewHolder {
        public AppCompatTextView title;
        public AppCompatTextView content;
    }

    private String getWakeupTimeTitleString(WakeupTimeBean bean) {
        String dayOfTheWeek = "";
        switch (bean.getDayOfTheWeek()) {
            case Calendar.MONDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_monday);
                break;
            case Calendar.TUESDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_wednesday);
                break;
            case Calendar.THURSDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_thursday);
                break;
            case Calendar.FRIDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_friday);
                break;
            case Calendar.SATURDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_saturday);
                break;
            case Calendar.SUNDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_sunday);
                break;
            default:
                break;
        }
        return dayOfTheWeek;
    }

    private String getWakeupTimeContentString(WakeupTimeBean bean) {
        // TODO 注意12小时制和24小时制
        int hourLength = String.valueOf(bean.getHour()).length();
        String hour = hourLength == 1 ? "0" + bean.getHour() : String.valueOf(bean.getHour());

        int minuteLength = String.valueOf(bean.getMinute()).length();
        String minute = minuteLength == 1 ? "0" + bean.getMinute() : String.valueOf(bean.getMinute());

        String content = hour + " : " + minute;
        return content;
    }
}
