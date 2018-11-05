package com.celerysoft.bedtime.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.Log;

import com.celerysoft.bedtime.R;
import com.celerysoft.bedtime.activity.main.view.MainActivity;
import com.celerysoft.bedtime.util.Const;
import com.celerysoft.bedtime.util.FileUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 16/7/13.
 * Base application
 */
public class BaseApplication extends Application {
    CrashHandler mCrashHandler;

//    @Override
//    protected void attachBaseContext(Context base) {
////        super.attachBaseContext(BaseContextWrapper.wrap(base));
//        super.attachBaseContext(base);
//    }

    @Override
    public void onCreate() {
        Log.i("sososo", "onAppCreate");
        super.onCreate();

        initAppConst();

        initCrashHandler();

        initAppShortCuts();
    }

    private void initAppConst() {
        Const.DEFAULT_SHARED_PREFERENCES_FILE_NAME = getString(R.string.shared_preferences_key_default);
    }

    private void initAppShortCuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = this.getSystemService(ShortcutManager.class);

            Intent intent = new Intent(this, MainActivity.class);
            intent.setAction(Const.APP_SHORTCUT_ACTION_SET_WAKEUP_TIME);
            ShortcutInfo shortcut = new ShortcutInfo.Builder(this, Const.APP_SHORTCUT_ACTION_SET_WAKEUP_TIME)
                    .setShortLabel(getString(R.string.shortcut_set_wakeup_time_short_label))
                    .setLongLabel(getString(R.string.shortcut_set_wakeup_time_long_label))
                    .setDisabledMessage(getString(R.string.shortcut_set_wakeup_time_disabled_message))
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_alarm_black))
                    .setIntent(intent)
                    .build();

            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
        }
    }

    private void initCrashHandler() {
        mCrashHandler = new CrashHandler(this);
    }

    private class CrashHandler implements Thread.UncaughtExceptionHandler {
        private Context mContext;
        private Thread.UncaughtExceptionHandler mHandler;
        private Map<String, String> mDeviceInformation = new LinkedHashMap<>();
        private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public CrashHandler(Context context) {
            super();

            mContext = context;
            mHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
//        if (!handleException(ex) && mHandler != null) {
//            mHandler.uncaughtException(thread, ex);
//        } else {
//            System.exit(1);
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
            handleException(ex);
            if (mHandler != null) {
                mHandler.uncaughtException(thread, ex);
            }
        }

        private boolean handleException(Throwable ex) {
            if (ex == null)
                return false;

            if(!FileUtil.getInstance().isExternalStorageMounted()) {
                return false;
            }

            collectDeviceInfo(mContext);

            saveCrashInfo2File(ex);

            return false;
        }

        private void collectDeviceInfo(Context context) {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (pi != null) {
                    String versionName = pi.versionName == null ? "null" : pi.versionName;
                    String versionCode = pi.versionCode + "";
                    mDeviceInformation.put("versionName", versionName);
                    mDeviceInformation.put("versionCode", versionCode);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    mDeviceInformation.put(field.getName(), field.get("").toString());
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        private String saveCrashInfo2File(Throwable ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("==========Device Information==========" + "\r\n");
            for (Map.Entry<String, String> entry : mDeviceInformation.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                sb.append(key);
                sb.append("=");
                sb.append(value);
                sb.append("\r\n");
            }
            sb.append("\r\n" + "==========Crash  Information==========" + "\r\n");
            Writer writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            ex.printStackTrace(pw);
            Throwable cause = ex.getCause();

            while (cause != null) {
                cause.printStackTrace(pw);
                cause = cause.getCause();
            }
            pw.close();
            String result = writer.toString();
            sb.append(result);

            long timestamp = System.currentTimeMillis();
            String time = mSimpleDateFormat.format(new Date());
            String fileName = time + " - " + timestamp + ".log";


            if(FileUtil.getInstance().writeToExternalCache(mContext, "Crash", fileName, sb.toString())) {
                return fileName;
            } else {
                return null;
            }
        }
    }
}
