package com.celerysoft.bedtime.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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
            synchronized (FileUtil.class) {
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

    public File getAvatarTempFile(Context context) {
        if (isExternalStorageMounted()) {
            File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (file != null) {
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdirs();
                }

                return new File(file.getPath() + File.separator + "temp" + Const.USER_AVATAR_FILE_NAME);
            }
        }

        return null;
    }

    public String getAvatarTempPath(Context context) {
        if (isExternalStorageMounted()) {
            File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (file != null) {
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdirs();
                }

                return file.getPath() + File.separator + "temp" + Const.USER_AVATAR_FILE_NAME;
            }
        }

        return null;
    }

    public String getAvatarPath(Context context) {
        if (isExternalStorageMounted()) {
            File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (file != null) {
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdirs();
                }

                return file.getPath() + File.separator + Const.USER_AVATAR_FILE_NAME;
            }
        }

        return null;
    }

    public boolean saveAvatarToExternalStorage(Context context, Bitmap bitmap) {
        if (isExternalStorageMounted()) {
            File file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (file != null) {
                if (!file.exists()) {
                    //noinspection ResultOfMethodCallIgnored
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
                    while ((byteCount = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }
        }

        return false;
    }

    /**
     * 获取通知声音文件的Uri
     * @param context Context
     * @return 通知声音文件的Uri
     */
    @Deprecated
    public Uri getNotificationSoundUri(Context context) {
        if (!isExternalStorageMounted()) {
            return null;
        } else {
            return Uri.fromFile(getNotificationSoundFile(context));
        }
    }

    /**
     * 获取通知声音文件
     * @param context Context
     * @return 通知声音文件
     */
    @SuppressWarnings("ConstantConditions")
    @Deprecated
    public File getNotificationSoundFile(Context context) {
        if (isNotificationSoundFileExist(context)) {
            return new File(getNotificationSoundFilePath(context));
        } else {
            if (copyFilesFromAssetsToExternalStorage(context)) {
                return new File(getNotificationSoundFilePath(context));
            } else {
                return null;
            }
        }
    }

    /**
     * 判断通知声音文件是否存在
     * @param context Context
     * @return 通知声音文件是否存在
     */
    @Deprecated
    private boolean isNotificationSoundFileExist(Context context) {
        String notificationSoundFilePath = getNotificationSoundFilePath(context);
        if (notificationSoundFilePath != null) {
            File notificationSoundFile = new File(notificationSoundFilePath);
            return notificationSoundFile.exists();
        } else {
            return false;
        }
    }

    /**
     * 获取通知声音文件路径
     * @param context Context
     * @return 通知声音文件路径
     */
    @Deprecated
    private String getNotificationSoundFilePath(Context context) {
        File notificationsDirectory = context.getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS);
        if (notificationsDirectory != null) {
            return notificationsDirectory.getPath() + File.separator + Const.NOTIFICATION_FILE_NAME;
        } else {
            return null;
        }
    }

    /**
     * Copy all the files from assets to external storage
     * @param context Context instance
     */
    public boolean copyFilesFromAssetsToExternalStorage(Context context) {
        if (isExternalStorageMounted()) {
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
    public boolean isExternalStorageMounted() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
