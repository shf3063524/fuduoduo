package com.fdw.fdd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.fdw.fdd.LoginActivity;
import com.fdw.fdd.R;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.tool.StatusBarUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.model.Response;
import com.mylhyl.circledialog.CircleDialog;
import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushInterface;
import es.dmoral.toasty.Toasty;

/**
 * Activity基类——处理状态栏、加载中dialog
 * Author：Created by shihongfei on 2019/9/24 08:53
 * Email：1511808259@qq.com
 */
public abstract class BaseStatusActivity extends AppCompatActivity {
    private DialogFragment mLoginErrorDialog;
    private KProgressHUD mLoaddingView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusBar();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setStatusBar();
    }

    /**
     * 设置状态栏，默认为主题色
     * 子类可通过重写此方法，通过StatusBarUtil工具类实现对状态栏的修改
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        // StatusBarUtil.setTranslucent(this);
    }
    /**
     * 显示加载中对话框
     */
    public void showLoadingDialog() {
        if (mLoaddingView == null) {
            mLoaddingView = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setBackgroundColor(ContextCompat.getColor(this, R.color.cl_333))
                    .setDimAmount(0.3f);
        }
        mLoaddingView.show();
    }

    /**
     * 显示加载中对话框（可设置提示信息）
     *
     * @param promptTitle 加载中的提示文本
     */
    public void showLoadingDialog(String promptTitle) {
        if (mLoaddingView == null) {
            mLoaddingView = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setBackgroundColor(ContextCompat.getColor(this, R.color.cl_333))
                    .setLabel(promptTitle)
                    .setDimAmount(0.3f);
        }
        mLoaddingView.show();
    }
    /**
     * 响应错误码（后台规定的状态码）处理
     *
     * @param response   响应结果
     * @param <T>        Json解析的实体
     * @param isUseToast 是否使用Toast对用户进行提示
     */
    public <T> String handleError(Response<AppResponse<T>> response, boolean isUseToast) {
        if (response == null || response.getException() == null)
            return null;
        response.getException().printStackTrace();
        String errorInfo = response.getException().getMessage();
        String state = getExceptionByName(errorInfo, "state");
        if (!TextUtils.isEmpty(state) && errorInfo.contains("&")) {
            switch (state) {
                case "-1":    // 未传入 token
                case "-2":    // 用户不存在
                case "-3":    // 用户没有 token
                case "-4":    // token 错误
                case "-5":    // token 已过期
                    showLoginErrorDialog();
                    break;
                case "1001":    // 暂无数据
                    break;
                default:
                    if (isUseToast) {
                        Toasty.info(this, getExceptionByName(errorInfo, "msg")).show();
                    } else {
                        new CircleDialog.Builder()
                                .setCanceledOnTouchOutside(false)
                                .setText(getExceptionByName(errorInfo, "msg"))
                                .setPositive("我知道了", null)
                                .show(getSupportFragmentManager());
                    }
                    break;
            }
            return state;
        } else {
            Toasty.info(this, "无法连接网络，请稍后重试").show();
            return null;
        }
    }

    /***
     * 获取异常中的信息
     * @param exception 异常信息
     * @param name code or msg
     * @return name对应的value
     */
    public static String getExceptionByName(String exception, String name) {
        String result = "";
        String[] keyValue = exception.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    /**
     * 显示登陆异常对话框
     */
    public void showLoginErrorDialog() {
        if (mLoginErrorDialog == null) {
            mLoginErrorDialog = new CircleDialog.Builder()
                    .setCanceledOnTouchOutside(false)
                    .setTitle("登陆状态异常")
                    .setText("您的账号在异地登陆，如不是您本人操作，可能您的密码已经泄漏，请立即修改密码！")
                    .setPositive("我知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mLoginErrorDialog.dismiss();
                            mLoginErrorDialog = null;
//                            UserManager.setDataIsNull(BaseStatusActivity.this);
                            LoginActivity.openActivity(BaseStatusActivity.this, "token_error");
                        }
                    })
                    .show(getSupportFragmentManager());
        }
    }

    /**
     * 隐藏加载中对话框
     */
    public void hideLoadingDialog() {
        if (mLoaddingView != null && mLoaddingView.isShowing()) {
            mLoaddingView.dismiss();
            mLoaddingView = null;
        }
    }
}
