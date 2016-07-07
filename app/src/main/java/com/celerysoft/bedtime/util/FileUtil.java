package com.celerysoft.bedtime.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 16/5/16.
 * Assets util
 */
public class FileUtil {
    private static volatile FileUtil sInstance = null;

    public static FileUtil getInstance() {
        FileUtil instance = sInstance;
        if (instance == null) {
            synchronized (AlarmUtil.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new FileUtil();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private FileUtil() {}

    public String getAvatarTempPath(Context context) {
        if (isExternalStorageWritable()) {
            File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                file.mkdirs();
            }

            return file.getPath() + File.separator + "temp" + Const.USER_AVATAR_FILE_NAME;
        } else {
            return null;
        }
    }

    public String getAvatarPath(Context context) {
        if (isExternalStorageWritable()) {
            File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                file.mkdirs();
            }

            return file.getPath() + File.separator + Const.USER_AVATAR_FILE_NAME;
        } else {
            return null;
        }
    }

    public boolean saveAvatarToExternalStorage(Context context, Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                file.mkdirs();
            }

            String path = Const.USER_AVATAR_FILE_NAME;
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                InputStream is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

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

            return true;
        } else {
            return false;
        }
    }

    /**
     * Copy all the files from assets to external storage
     * @param context Context instance
     */
    public boolean copyFilesFromAssetsToExternalStorage(Context context) {
        if (isExternalStorageWritable()) {
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

            return true;
        } else {
            return false;
        }
    }

    /**
     *  Checks if external storage is available for read and write
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
