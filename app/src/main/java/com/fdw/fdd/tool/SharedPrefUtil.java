package com.fdw.fdd.tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 工具类
 * Created by Grayson on 2015/11/8.
 */
public class SharedPrefUtil {
    private static SharedPreferences mSp;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSp;
    }

    public void clearData() {
        mSp.edit().clear().commit();
    }

    /**
     * 存储boolean类型数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean类型数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    /**
     * 存储String类型数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putString(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    /**
     * 获取String类型数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    /**
     * 删除SharedPreferences中的数据
     *
     * @param context 上下文
     * @param key     键
     */
    public static void remove(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).commit();
    }

    /**
     * 存储Int类型数据
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putInt(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).commit();
    }

    /**
     * 获取Int类型数据
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        return getSharedPreferences(context).getInt(key, defValue);
    }
}
