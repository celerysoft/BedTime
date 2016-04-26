package com.celerysoft.bedtime.fragment.main.presenter;

/**
 * Created by Celery on 16/4/14.
 */
public interface IPresenterMain {
    void turnOnNotification();
    void turnOffNotification();
    void startCountDownThread();
    void stopCountDownThread();
}
