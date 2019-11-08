package com.hjkj.fuduoduo.tool;

import android.content.Context;

public class UserManager {
    // 用户手机号
    private final static String PHONE_NUMBER = "phoneNumber";
    // 用户ID
    private final static String USER_ID = "id";
    // 用户名
    private final static String USERNAME = "username";
    // 用户公司
    private final static String USER_COMPANY = "user_company";


    public static void setPhoneNumber(Context context, String value) {
        SharedPrefUtil.putString(context, PHONE_NUMBER, value);
    }

    public static String getPhoneNumber(Context context) {
        return SharedPrefUtil.getString(context, PHONE_NUMBER, "");
    }

    public static void setUserId(Context context, String value) {
        SharedPrefUtil.putString(context, USER_ID, value);
    }

    public static String getUserId(Context context) {
        return SharedPrefUtil.getString(context, USER_ID, "");
    }

    public static void setUsername(Context context, String value) {
        SharedPrefUtil.putString(context, USERNAME, value);
    }

    public static String getUsername(Context context) {
        return SharedPrefUtil.getString(context, USERNAME, "");
    }

    public static void setUserCompany(Context context, String value) {
        SharedPrefUtil.putString(context, USER_COMPANY, value);
    }

    public static String getUserCompany(Context context) {
        return SharedPrefUtil.getString(context, USER_COMPANY, "");
    }

    public static void setDataIsNull(Context context) {
        setPhoneNumber(context, "");
        setUserId(context, "");
        setUsername(context, "");
        setUserCompany(context, "");
    }
}
