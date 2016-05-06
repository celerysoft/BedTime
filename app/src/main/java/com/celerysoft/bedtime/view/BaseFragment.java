package com.celerysoft.bedtime.view;

import android.app.Fragment;

/**
 * Created by admin on 16/5/6.
 */
public class BaseFragment extends Fragment {
    private OnFragmentStatusChangedListener mOnFragmentStatusChangedListener;
    public void setOnFragmentStatusChangedListener(OnFragmentStatusChangedListener onFragmentStatusChangedListener) {
        mOnFragmentStatusChangedListener = onFragmentStatusChangedListener;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mOnFragmentStatusChangedListener != null) {
            mOnFragmentStatusChangedListener.onFragmentStart(this);
        }
    }

    public interface OnFragmentStatusChangedListener {
        void onFragmentStart(BaseFragment fragment);
    }
}
