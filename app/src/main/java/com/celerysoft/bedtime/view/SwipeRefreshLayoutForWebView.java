package com.celerysoft.bedtime.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by admin on 16/9/23.
 * Handle scroll conflict between SwipeRefreshLayout and WebView.
 */

public class SwipeRefreshLayoutForWebView extends SwipeRefreshLayout {
    private WebView mWebView;

    public SwipeRefreshLayoutForWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeRefreshLayoutForWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            if (getChildAt(i) instanceof WebView) {
                setWebView((WebView) getChildAt(i));
                break;
            }
        }
    }

    public void setWebView(WebView webView) {
        mWebView = webView;
    }

    @Override
    public boolean canChildScrollUp() {
        if (mWebView != null) {
            if (!mWebView.canScrollVertically(-1) && !mWebView.canScrollVertically(1)) {
                return true;
            }
        }

        return super.canChildScrollUp();
    }
}
