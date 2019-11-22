package com.hjkj.fuduoduo.tool;

import android.content.Context;
import android.util.Log;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

public class UserManager {
    // 用户手机号
    private final static String PHONE_NUMBER = "phoneNumber";
    // 用户ID
    private final static String USER_ID = "id";
    // 用户名
    private final static String USERNAME = "username";
    // 用户公司
    private final static String USER_COMPANY = "user_company";
    // 用户昵称
    private final static String NICK_NAME = "nick_name";
    // 用户工号
    private final static String JOB_NUMBER = "job_number";

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
    public static void setNickName(Context context, String value) {
        SharedPrefUtil.putString(context, NICK_NAME, value);
    }

    public static String getNickName(Context context) {
        return SharedPrefUtil.getString(context, NICK_NAME, "");
    }
    public static void setJobNumber(Context context, String value) {
        SharedPrefUtil.putString(context, JOB_NUMBER, value);
    }

    public static String getJobNumber(Context context) {
        return SharedPrefUtil.getString(context, JOB_NUMBER, "");
    }
    public static void setDataIsNull(Context context) {
        setPhoneNumber(context, "");
        setUserId(context, "");
        setUsername(context, "");
        setUserCompany(context, "");
        setNickName(context, "");
        setJobNumber(context, "");
        //第一个参数为是否解绑推送的devicetoken
        ChatClient.getInstance().logout(true, new Callback(){
            @Override
            public void onSuccess() {
                Log.d("IM", "onSuccess:退出成功！ ");
            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });


    }
}
