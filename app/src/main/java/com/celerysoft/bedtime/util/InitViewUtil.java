package com.celerysoft.bedtime.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.ListView;

import com.celerysoft.bedtime.R;

/**
 * Created by Celery on 16/7/14.
 * Make the views with same type looks the same.
 */
public class InitViewUtil {
    private static volatile InitViewUtil sInstance = null;

    public static InitViewUtil getInstance() {
        InitViewUtil instance = sInstance;
        if (instance == null) {
            synchronized (InitViewUtil.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new InitViewUtil();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private InitViewUtil() {}

    /**
     * Init the AlertDialog style, call this method after {@link AlertDialog#show()}
     * @param dialog Dialog
     */
    public void initAlertDialog(AlertDialog dialog) {
        Context context = dialog.getContext();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.copyFrom(dialog.getWindow().getAttributes());

        int screenWidthInDp = Util.px2dp(context, Util.getScreentWidth(context));

        if (screenWidthInDp < 40 + 280 + 40) {
            lp.width = Util.dp2px(context, 280);
        } else {
            int maxWidth = Util.getScreentWidth(context) - Util.dp2px(context, 40) * 2;
            lp.width = lp.width > maxWidth ? maxWidth : lp.width;

            int minWidth = Util.dp2px(context, 280);
            lp.width = lp.width < minWidth ? minWidth : lp.width;

            lp.width = maxWidth;
        }

        int maxHeight = Util.getScreentWidth(context) - Util.dp2px(context, 24) * 2;
        lp.height = lp.height > maxHeight ? maxHeight : lp.height;

        dialog.getWindow().setAttributes(lp);
    }

    public void initListView(ListView listView) {
        listView.setSelector(R.drawable.selector_list_item);
    }
}
