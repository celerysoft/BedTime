package com.celerysoft.bedtime.fragment.bedtime;

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

        createDefaultItems();
    }

    private void createDefaultItems() {
        mWakeupTimes = new ArrayList<>();

        WakeupTimeBean item0 = new WakeupTimeBean();
        item0.setDayOfTheWeek(0);
        item0.setWakeupHour(1);
        item0.setWakeupMinute(30);
        mWakeupTimes.add(item0);

        WakeupTimeBean item1 = new WakeupTimeBean();
        item1.setDayOfTheWeek(1);
        item1.setWakeupHour(2);
        item1.setWakeupMinute(30);
        mWakeupTimes.add(item1);

        WakeupTimeBean item2 = new WakeupTimeBean();
        item2.setDayOfTheWeek(2);
        item2.setWakeupHour(3);
        item2.setWakeupMinute(30);
        mWakeupTimes.add(item2);

        WakeupTimeBean item3 = new WakeupTimeBean();
        item3.setDayOfTheWeek(3);
        item3.setWakeupHour(4);
        item3.setWakeupMinute(30);
        mWakeupTimes.add(item3);

        WakeupTimeBean item4 = new WakeupTimeBean();
        item4.setDayOfTheWeek(4);
        item4.setWakeupHour(5);
        item4.setWakeupMinute(30);
        mWakeupTimes.add(item4);

        WakeupTimeBean item5 = new WakeupTimeBean();
        item5.setDayOfTheWeek(5);
        item5.setWakeupHour(6);
        item5.setWakeupMinute(30);
        mWakeupTimes.add(item5);

        WakeupTimeBean item6 = new WakeupTimeBean();
        item6.setDayOfTheWeek(6);
        item6.setWakeupHour(7);
        item6.setWakeupMinute(30);
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
            case Const.MONDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_monday);
                break;
            case Const.TUESDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_tuesday);
                break;
            case Const.WEDNESDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_wednesday);
                break;
            case Const.THURSDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_thursday);
                break;
            case Const.FRIDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_friday);
                break;
            case Const.SATURDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_saturday);
                break;
            case Const.SUNDAY:
                dayOfTheWeek = mContext.getString(R.string.bedtime_sunday);
                break;
            default:
                break;
        }
        return dayOfTheWeek;
    }

    private String getWakeupTimeContentString(WakeupTimeBean bean) {
        // TODO 注意12小时制和24小时制
        String content = bean.getWakeupHour() + " : " + bean.getWakeupMinute();
        return content;
    }
}
