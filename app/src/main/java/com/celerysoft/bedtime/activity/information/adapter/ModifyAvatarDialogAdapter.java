package com.celerysoft.bedtime.activity.information.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.celerysoft.bedtime.R;

/**
 * Created by admin on 16/6/29.
 *
 */
public class ModifyAvatarDialogAdapter extends BaseAdapter {
    public static final int CAMERA = 0;
    public static final int GALLERY = 1;
    public static final int CANCEL = 2;

    private Context mContext;

    private ListItem[] mListItems;

    public ModifyAvatarDialogAdapter(Context context) {
        mContext = context;

        mListItems = new ListItem[getCount()];

        mListItems[CAMERA] = new ListItem();
        mListItems[CAMERA].textStringResId = R.string.personal_information_avatar_dialog_camera;
        mListItems[CAMERA].textColorResId = R.color.colorPrimary;

        mListItems[GALLERY] = new ListItem();
        mListItems[GALLERY].textStringResId = R.string.personal_information_avatar_dialog_gallery;
        mListItems[GALLERY].textColorResId = R.color.colorPrimary;

        mListItems[CANCEL] = new ListItem();
        mListItems[CANCEL].textStringResId = android.R.string.cancel;
        mListItems[CANCEL].textColorResId = R.color.colorSecondaryText;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dialog_list_item_single_line_text_in_center, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvText = (TextView) convertView.findViewById(R.id.dialog_list_item_single_line_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListItem item = mListItems[position];
        viewHolder.tvText.setText(item.textStringResId);
        viewHolder.tvText.setTextColor(mContext.getResources().getColor(item.textColorResId));

        return convertView;
    }

    private class ViewHolder {
        public TextView tvText;
    }

    private class ListItem {
        public int textStringResId;
        public int textColorResId;
    }
}
