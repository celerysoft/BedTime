package com.celerysoft.bedtime.fragment.bedtime;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.celerysoft.bedtime.R;

import java.util.ArrayList;

/**
 * Created by Celery on 16/4/15.
 */
public class WakeupTimeListViewAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<Item> mItems;

    public WakeupTimeListViewAdapter(Context context) {
        mContext = context;

        createDefaultItems();
    }

    private void createDefaultItems() {
        mItems = new ArrayList<Item>();

        Item item0 = new Item();
        item0.title = "周一";
        item0.content = "08:30";
        mItems.add(item0);

        Item item1 = new Item();
        item1.title = "周二";
        item1.content = "08:30";
        mItems.add(item1);

        Item item2 = new Item();
        item2.title = "周三";
        item2.content = "08:30";
        mItems.add(item2);

        Item item3 = new Item();
        item3.title = "周四";
        item3.content = "08:30";
        mItems.add(item3);

        Item item4 = new Item();
        item4.title = "周五";
        item4.content = "08:30";
        mItems.add(item4);

        Item item5 = new Item();
        item5.title = "周六";
        item5.content = "09:30";
        mItems.add(item5);

        Item item6 = new Item();
        item6.title = "周天";
        item6.content = "09:30";
        mItems.add(item6);

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

        Item item = mItems.get(position);
        viewHolder.title.setText(item.title);
        viewHolder.content.setText(item.content);

        return convertView;
    }

    private class ViewHolder {
        public AppCompatTextView title;
        public AppCompatTextView content;
    }

    private class Item {
        public String title;
        public String content;
    }
}
