package com.celerysoft.bedtime.fragment.bedtime;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.celerysoft.bedtime.R;

/**
 * Created by Celery on 16/4/12.
 */
public class BedTimeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bedtime, container, false);
    }
}
