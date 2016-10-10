package com.celerysoft.bedtime.test;

import android.content.Context;
import android.util.Log;

import com.celerysoft.bedtime.fragment.bedtime.model.WakeupTimeBean;
import com.celerysoft.bedtime.fragment.main.model.BedTimeBean;
import com.celerysoft.bedtime.fragment.main.model.BedTimeModel;

/**
 * Created by admin on 16/10/9.
 *
 */

public class UnitTest {
    private static volatile UnitTest sInstance = null;

    public static UnitTest getInstance() {
        UnitTest instance = sInstance;
        if (instance == null) {
            synchronized (UnitTest.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new UnitTest();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private UnitTest() {}

    public void startTest(Context context) {
        String result = "\n";

        result += testConnectionBetweenWakeupTimeAndBedTime(context);

        result += "\n";

        Log.i("UnitTest", result);
    }

    private String testConnectionBetweenWakeupTimeAndBedTime(Context context) {
        final String TEST_NAME = "ConnectionBetweenWakeupTimeAndBedTime";

        BedTimeBean[] bedTimeBeans = TestUtil.deriveAllBedTime(context);
        WakeupTimeBean[] wakeupTimeBeans = TestUtil.deriveAllWakeupTime(context);

        if (bedTimeBeans.length != wakeupTimeBeans.length) {
            return deriveResultString(TEST_NAME, false);
        }

        BedTimeModel bedTimeModel = new BedTimeModel(context);
        int sleepHour = bedTimeModel.getSleepHour();
        int sleepMinute = bedTimeModel.getSleepMinute();

        int count = bedTimeBeans.length;
        for (int i = 0; i < count; ++i) {
            WakeupTimeBean wakeupTimeBean = wakeupTimeBeans[i];
            BedTimeBean bedTimeBean = bedTimeBeans[i];

            int differentMinute;
            if (wakeupTimeBean.getDayOfTheWeek() != bedTimeBean.getActualDayOfWeek()) {
                differentMinute = (24 + wakeupTimeBean.getHour() - bedTimeBean.getHour()) * 60 + (wakeupTimeBean.getMinute() - bedTimeBean.getMinute());
            } else {
                differentMinute = (wakeupTimeBean.getHour() - bedTimeBean.getHour()) * 60 + (wakeupTimeBean.getMinute() - bedTimeBean.getMinute());
            }

            if (differentMinute != sleepHour * 60 + sleepMinute) {
                return deriveResultString(TEST_NAME, false);
            }
        }
        return deriveResultString(TEST_NAME, true);
    }

    private String deriveResultString(String testName, boolean result) {
        return String.format("%s, %b", testName, result);
    }
}
