package com.celerysoft.bedtime.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Celery on 16/9/20.
 *
 */
public class BitmapUtil {
    private static volatile BitmapUtil sInstance = null;

    public static BitmapUtil getInstance() {
        BitmapUtil instance = sInstance;
        if (instance == null) {
            synchronized (BitmapUtil.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new BitmapUtil();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private BitmapUtil() {}


    public Bitmap deriveSquareBitmap(String path, int length) {
        Bitmap bitmap;

        bitmap = deriveBitmap(path, length, length);
        bitmap = autoCropSquareBitmap(bitmap);

        return bitmap;
    }

    public Bitmap deriveBitmap(String path, int targetWidth, int targetHeight) {
        Bitmap bitmap = null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            int photoWidth = options.outWidth;
            int photoHeight = options.outHeight;

            options.inSampleSize = calculateInSampleSize(photoWidth, photoHeight, targetWidth, targetHeight);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }

        return bitmap;
    }

    /**
     * calculate the scaling of bitmap
     * @param originalWidth bitmap original width
     * @param originalHeight bitmap original height
     * @param targetWidth target width
     * @param targetHeight target height
     * @return scaling of bitmap
     */
    private int calculateInSampleSize(int originalWidth, int originalHeight, int targetWidth, int targetHeight) {
        int inSampleSize = 1;

        if (originalHeight > targetHeight || originalWidth > targetWidth) {
            inSampleSize = inSampleSize << 1;
            while ((originalHeight / inSampleSize) > targetHeight && (originalWidth / inSampleSize) > targetWidth) {
                //设置inSampleSize为2的幂是因为解码器最终还是会对非2的幂的数进行向下处理，获取到最靠近2的幂的数。
                //inSampleSize *= 2;
                inSampleSize = inSampleSize << 1;
            }
        }

        return inSampleSize;
    }

    /**
     * crop rectangle bitmap to square bitmap
     * @param bitmap rectangle bitmap
     * @return square bitmap
     */
    public Bitmap autoCropSquareBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int length = Math.min(width, height);

        int x = (width - length) / 2;
        int y = (height - length) / 2;

        bitmap = Bitmap.createBitmap(bitmap, x, y, length, length);

        return bitmap;
    }
}
