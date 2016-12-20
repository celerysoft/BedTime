package com.celerysoft.bedtime.fragment.settings.bean;

/**
 * Created by admin on 16/12/20.
 * 
 */

public class Sound {
    private String mId;
    private String mTitle;
    private String mUri;
    private int mIndex;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }
}
