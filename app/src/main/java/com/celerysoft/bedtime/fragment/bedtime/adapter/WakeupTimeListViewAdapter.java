package com.celerysoft.bedtime.fragment.bedtime.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.util.Const;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Celery on 16/4/15.
 */
public class WakeupTimeListViewAdapter extends BaseAdapter {
    private Context mContext;
    private Calendar mCalendar;

    private boolean mIs24HourTime = true;

    public void setIs24HourTime(boolean is24HourTime) {
        mIs24HourTime = is24HourTime;
        notifyDataSetChanged();
    }

    private ArrayList<WakeupTimeBean> mWakeupTimes;
    public ArrayList<WakeupTimeBean> getWakeupTimes() {
        return mWakeupTimes;
    }

    public void setWakeupTimes(ArrayList<WakeupTimeBean> wakeupTimes) {
        mWakeupTimes = wakeupTimes;
    }

    public WakeupTimeListViewAdapter(Context context) {
        mContext = context;

        mCalendar = Calendar.getInstance();
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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_wakeup_time_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.root = convertView.findViewById(R.id.list_view_wakeup_time_root);
            viewHolder.title = (AppCompatTextView) convertView.findViewById(R.id.list_view_wakeup_time_title);
            viewHolder.tag = (AppCompatTextView) convertView.findViewById(R.id.list_view_wakeup_time_tag);
            viewHolder.content = (AppCompatTextView) convertView.findViewById(R.id.list_view_wakeup_time_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WakeupTimeBean bean = mWakeupTimes.get(position);
        viewHolder.title.setText(getWakeupTimeTitleString(bean));
        viewHolder.content.setText(getWakeupTimeContentString(bean));


        if (isToday(bean.getDayOfTheWeek())) {
            viewHolder.root.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccentA100));
            viewHolder.title.setTextColor(mContext.getResources().getColor(R.color.colorText));
            viewHolder.tag.setText(mContext.getString(R.string.bedtime_today));
            viewHolder.content.setTextColor(mContext.getResources().getColor(R.color.colorText));
        } else if(isTomorrow(bean.getDayOfTheWeek())) {
            viewHolder.root.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            viewHolder.title.setTextColor(mContext.getResources().getColor(R.color.colorText));
            viewHolder.tag.setText(mContext.getString(R.string.bedtime_tomorrow));
            viewHolder.content.setTextColor(mContext.getResources().getColor(R.color.colorText));
        } else {
            viewHolder.root.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
            viewHolder.title.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryText));
            viewHolder.tag.setText(Const.EMPTY_STRING);
            viewHolder.content.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryText));
        }


        return convertView;
    }

    private class ViewHolder {
        public View root;
        public AppCompatTextView title;
        public AppCompatTextView tag;
        public AppCompatTextView content;
    }

    private boolean isToday(int dayOfWeek) {
        int today = mCalendar.get(Calendar.DAY_OF_WEEK);
        return today == dayOfWeek;
    }

    private boolean isTomorrow(int dayOfWeek) {
        int today = mCalendar.get(Calendar.DAY_OF_WEEK);
        int tomorrow = today == Calendar.SATURDAY ? Calendar.MONDAY : today + 1;
        return tomorrow == dayOfWeek;
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
        int minuteLength = String.valueOf(bean.getMinute()).length();
        String minute = minuteLength == 1 ? "0" + bean.getMinute() : String.valueOf(bean.getMinute());

        String hour;
        String content;

        if (mIs24HourTime) {
            hour = String.valueOf(bean.getHour());
            content = hour + ":" + minute;
        } else {
            boolean isAm = false;
            if (bean.getHour() < 12) {
                isAm = true;
            }

            if (isAm) {
                hour = String.valueOf(bean.getHour());
                content = hour + ":" + minute + " AM";
            } else {
                hour = String.valueOf(bean.getHour() - 12);
                content = hour + ":" + minute + " PM";
            }
        }

        return content;
    }
}
