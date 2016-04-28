package com.celerysoft.bedtime.animator;

import android.view.View;

/**
 * Created by admin on 16/4/28.
 */
public class AnimatorWrapper {
    private View mTarget;

    public AnimatorWrapper(View v) {
        mTarget = v;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }

    public int getHeight() {
        return mTarget.getLayoutParams().height;
    }

    public void setHeight(int height) {
        mTarget.getLayoutParams().height = height;
        mTarget.requestLayout();
    }

    public float getTranslationY() {
        return mTarget.getTranslationY();
    }

    public void setTranslationY(float translationY) {
        mTarget.setTranslationY(translationY);
        mTarget.requestLayout();
    }
}
