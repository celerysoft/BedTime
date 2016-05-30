package com.celerysoft.bedtime.activity.browser.presenter;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.browser.view.IViewBrowserActivity;

/**
 * Created by admin on 16/5/30.
 *
 */
public class PresenterBrowserActivity implements IPresenterBrowserActivity {
    IViewBrowserActivity mView;

    public PresenterBrowserActivity(IViewBrowserActivity view) {
        mView = view;
    }

    @Override
    public void displayAnimationOfFinishingActivity() {
        mView.getFloatingActionButton().hide();
        mView.getFloatingActionButton().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.getAnimationView().performAnimation();
            }
        }, 150);
    }

    @Override
    public void finishActivityWithAnimation() {
        mView.getActivity().finish();
        mView.getActivity().overridePendingTransition(R.anim.ripple_alpha_in, R.anim.ripple_alpha_out);
    }
}
