package com.celerysoft.bedtime.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 16/5/16.
 * Assets util
 */
public class AssetsUtil {
    private static volatile AssetsUtil sInstance = null;

    public static AssetsUtil getInstance() {
        AssetsUtil instance = sInstance;
        if (instance == null) {
            synchronized (AlarmUtil.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new AssetsUtil();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private AssetsUtil() {}


    /**
     * Copy all the files from assets to external storage
     * @param context Context instance
     */
    public void copyFilesFromAssetsToExternalStorage(Context context) {
        File file = context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS);
        if (!file.exists()) {
            file.mkdirs();
        }

        String[] paths = new String[] {
                Const.NOTIFICATION_FILE_NAME
        };

        for (String path : paths) {
            try {
                InputStream is = context.getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(new File(file.getPath() + File.separator + path));
                byte[] buffer = new byte[1024];

                int byteCount;
                while((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
