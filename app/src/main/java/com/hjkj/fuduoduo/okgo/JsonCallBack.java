package com.hjkj.fuduoduo.okgo;

import android.util.Log;

import com.google.gson.stream.JsonReader;
import com.hjkj.fuduoduo.MyApp;
import com.hjkj.fuduoduo.tool.GsonHelper;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import es.dmoral.toasty.Toasty;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * Created by kayroc on 2018/8/2
 * Email：kayroc@126.com
 */
public abstract class JsonCallBack<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;

    public JsonCallBack() {
    }

    public JsonCallBack(Type type) {
        this.type = type;
    }

    public JsonCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        //网络异常使用
        Throwable exception = response.getException();
        //直接报异常
        if (exception != null) {
            exception.printStackTrace();
        }

        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            Toasty.error(MyApp.getmContext(), "网络连接失败，请连接网络").show();
        } else if (exception instanceof SocketTimeoutException) {
           Toasty.error(MyApp.getmContext(), "网络请求超时").show();
        } else if (exception instanceof HttpException) {
           Toasty.error(MyApp.getmContext(), "服务端响应码404和500了，知道该什么办吗？").show();
        } else if (exception instanceof StorageException) {
           Toasty.error(MyApp.getmContext(), "SD卡不存在或者没有权限").show();
        } else if (exception instanceof IllegalStateException) {
            Log.e("http", exception.getMessage());
        }
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        ResponseBody responseBody = response.body();
        //如果为空则
        if (responseBody == null) {
            return null;
        }

        T data = null;

        //打印http


        JsonReader jsonReader = new JsonReader(responseBody.charStream());

        if (type != null) {
            data = GsonHelper.fromJson(jsonReader, type);
        } else if (clazz != null) {
            data =  GsonHelper.fromJson(jsonReader, clazz);
        } else {
            //获取父类型
            Type genType = getClass().getGenericSuperclass();
            //获取bean类型
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            //解析
            data =  GsonHelper.fromJson(jsonReader, type);
        }


        return data;
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        onSuccess(response.body());
    }

    public abstract void onSuccess(T t);
}

