package com.celerysoft.bedtime.activity.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.celerysoft.bedtime.R;

/**
 * Created by admin on 16/5/5.
 */
public class SocialSharingListViewAdapter extends BaseAdapter {
    public static final int WECHAT = 0;
    public static final int WECHAT_TIMELINE = 1;
    public static final int QQ = 2;
    public static final int QZONE = 3;

    private Context mContext;

    private Item[] mItems;

    public SocialSharingListViewAdapter(Context context) {
        mContext = context;

        mItems = new Item[getCount()];

        mItems[WECHAT] = new Item();
        mItems[WECHAT].iconResId = R.drawable.umeng_socialize_wechat;
        mItems[WECHAT].textResId = R.string.main_dialog_share_wechat;

        mItems[WECHAT_TIMELINE] = new Item();
        mItems[WECHAT_TIMELINE].iconResId = R.drawable.umeng_socialize_wxcircle;
        mItems[WECHAT_TIMELINE].textResId = R.string.main_dialog_share_wechat_timeline;

        mItems[QQ] = new Item();
        mItems[QQ].iconResId = R.drawable.umeng_socialize_qq_on;
        mItems[QQ].textResId = R.string.main_dialog_share_qq;

        mItems[QZONE] = new Item();
        mItems[QZONE].iconResId = R.drawable.umeng_socialize_qzone_on;
        mItems[QZONE].textResId = R.string.main_dialog_share_qzone;
    }

    @Override
    public int getCount() {
        return 4;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_list_item_signle_line_icon_with_text, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.dialog_list_item_single_line_iv);
            viewHolder.text = (TextView) convertView.findViewById(R.id.dialog_list_item_single_line_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = mItems[position];
        viewHolder.icon.setImageResource(item.iconResId);
        viewHolder.text.setText(item.textResId);

        return convertView;
    }

    private class ViewHolder {
        ImageView icon;
        TextView text;
    }

    private class Item {
        int iconResId;
        int textResId;
    }
}
