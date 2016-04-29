package com.celerysoft.bedtime.fragment.settings.presenter;

/**
 * Created by admin on 16/4/29.
 */
public interface IPresenterSettings {
    void showChooseLanguageDialog();
    void showPersonalInformation();
    String getLanguageString();
    void apply24HourTime(boolean applied);
    boolean is24HourTime();
}
