package com.celerysoft.bedtime.activity.browser.view;

import android.support.design.widget.FloatingActionButton;

import com.celerysoft.ripple.Wrapper;

/**
 * Created by admin on 16/5/30.
 *
 */
public interface IViewBrowserActivity {
    FloatingActionButton getFloatingActionButton();
    BrowserActivity getActivity();
    Wrapper getAnimationWrapper();
}
