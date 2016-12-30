package com.celerysoft.bedtime.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Celery on 16/12/30.
 *
 */

public class SPUtil {
    /**
     * default SharedPreferences file name
     **/
    public static final String FILE_NAME = Const.DEFAULT_SHARED_PREFERENCES_FILE_NAME;

    /**
     * Store data by SharedPreferences
     *
     * @param context Context instance
     * @param key     key
     * @param value   value
     */
    public static void put(Context context, String key, Object value) {
        put(context, FILE_NAME, key, value);
    }

    /**
     * Store data to SharedPreferences
     *
     * @param context    Context instance
     * @param spFileName SharedPreferences file name
     * @param key        key
     * @param value      value
     */
    public static void put(Context context, String spFileName, String key, Object value) {

        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * get data from SharedPreferences
     *
     * @param context      Context instance
     * @param spFileName   SharedPreferences file name
     * @param key          key
     * @param defaultValue defaultValue
     * @return data
     */
    public static String get(Context context, String spFileName, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static String get(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static Long get(Context context, String spFileName, String key, Long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static Long get(Context context, String key, Long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static Boolean get(Context context, String spFileName, String key, Boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static Boolean get(Context context, String key, Boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static Float get(Context context, String spFileName, String key, Float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static Float get(Context context, String key, Float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static Integer get(Context context, String spFileName, String key, Integer defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static Integer get(Context context, String key, Integer defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * remove data by key
     *
     * @param context    Context instance
     * @param spFileName SharedPreferences file name
     * @param key        specific key
     */
    public static void remove(Context context, String spFileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * remove default SharedPreferences data by key
     *
     * @param context Context instance
     * @param key     specific key
     */
    public static void remove(Context context, String key) {
        remove(context, FILE_NAME, key);
    }

    /**
     * remove all data in default SharedPreferences
     *
     * @param context Context instance
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * ask if the specific key is exist
     *
     * @param context Context instance
     * @param key     key
     * @return exist
     */
    public static boolean contains(Context context, String spFileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * ask if the specific key is exist in default SharedPreferences
     *
     * @param context Context instance
     * @param key     key
     * @return exist
     */
    public static boolean contains(Context context, String key) {
        return contains(context, FILE_NAME, key);
    }

    /**
     * Get all key-value pairs.
     *
     * @param context    Context instance
     * @param spFileName SharedPreferences file name
     * @return all key-value pairs
     */
    public static Map<String, ?> getAll(Context context, String spFileName) {
        SharedPreferences sp = context.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * Get all key-value pairs in default SharedPreferences.
     *
     * @param context Context instance
     * @return all key-value pairs
     */
    public static Map<String, ?> getAll(Context context) {
        return getAll(context, FILE_NAME);
    }

    /**
     * fix apply()
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * find apply()
         *
         * @return apply function
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
