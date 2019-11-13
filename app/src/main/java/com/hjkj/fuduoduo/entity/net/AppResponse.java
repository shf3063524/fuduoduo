package com.hjkj.fuduoduo.entity.net;

import com.hjkj.fuduoduo.MyApp;

import java.io.Serializable;

import es.dmoral.toasty.Toasty;

/**
 * 网络请求响应数据
 * Created by kayroc on 2018/8/2
 * Email：kayroc@126.com
 */
public class AppResponse<T> implements Serializable {

    private int state;
    private String message;//"0"（新用户）"1"（老用户）
    private T data;

    /**
     * 判断服务器是否请求成功~!
     *
     * @return
     */
    public boolean isSucess() {
        if (state == 1) {
            return true;
        } else {
            Toasty.normal(MyApp.getmContext(), message).show();
            return false;
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AppResponse{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
