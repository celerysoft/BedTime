package com.celerysoft.bedtime.view;

import android.content.Context;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.celerysoft.bedtime.R;

/**
 * Created by Celery on 16/4/14.
 * SwitchPreference follow Material Design.
 */
public class MaterialDesignSwitchPreference extends SwitchPreference {

    public MaterialDesignSwitchPreference(Context context) {
        super(context, null);
    }

    public MaterialDesignSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MaterialDesignSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onBindView(View view) {
        // Clean listener before invoke SwitchPreference.onBindView
        //ViewGroup viewGroup = (ViewGroup)view;
        //clearListenerInViewGroup(viewGroup);

        super.onBindView(view);

        final SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.pref_item_switch);
        switchCompat.setOnCheckedChangeListener(null);

        Boolean initVal = this.getPersistedBoolean(false);
        switchCompat.setChecked(initVal);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (!callChangeListener(isChecked)) {
                    // Listener didn't like it, change it back.
                    // CompoundButton will make sure we don't recurse.
                    buttonView.setChecked(!isChecked);
                    return;
                }

                // don't block the animation of switch.
                buttonView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MaterialDesignSwitchPreference.this.setChecked(isChecked);
                    }
                }, 200);

            }
        });

        this.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean oldValue = MaterialDesignSwitchPreference.this.getPersistedBoolean(false);
                boolean value = (boolean) newValue;

                if (oldValue == value) {
                    return false;
                } else {
                    switchCompat.setChecked(value);
                    return true;
                }
            }
        });
    }

    private void clearListenerInViewGroup(ViewGroup viewGroup) {
        if (null == viewGroup) {
            return;
        }

        int count = viewGroup.getChildCount();
        for(int n = 0; n < count; ++n) {
            View childView = viewGroup.getChildAt(n);
            if(childView instanceof SwitchCompat) {
                final SwitchCompat switchView = (SwitchCompat) childView;
                switchView.setOnCheckedChangeListener(null);
                return;
            } else if (childView instanceof ViewGroup){
                ViewGroup childGroup = (ViewGroup)childView;
                clearListenerInViewGroup(childGroup);
            }
        }
    }
}
