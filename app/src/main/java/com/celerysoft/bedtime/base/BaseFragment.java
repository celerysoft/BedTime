package com.celerysoft.bedtime.base;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by admin on 16/5/6.
 */
public class BaseFragment extends Fragment {
    private OnFragmentStatusChangedListener mOnFragmentStatusChangedListener;
    public void setOnFragmentStatusChangedListener(OnFragmentStatusChangedListener onFragmentStatusChangedListener) {
        mOnFragmentStatusChangedListener = onFragmentStatusChangedListener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mOnFragmentStatusChangedListener != null) {
            mOnFragmentStatusChangedListener.onFragmentStart(this);
        }
    }

    public interface OnFragmentStatusChangedListener {
        void onFragmentStart(BaseFragment fragment);
    }
}
