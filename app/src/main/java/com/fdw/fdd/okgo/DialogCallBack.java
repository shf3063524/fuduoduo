package com.fdw.fdd.okgo;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.fdw.fdd.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.request.base.Request;


/**
 * Created by kayroc on 2018/8/13
 * Email：kayroc@126.com
 */
public abstract class DialogCallBack<T> extends JsonCallBack<T> {
    private KProgressHUD mLoaddingView;

    public DialogCallBack(Context context, String promptTitle) {
        super();
        initLoadingDialog(context, promptTitle);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (mLoaddingView != null && !mLoaddingView.isShowing()) {
            mLoaddingView.show();
        }
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        hideLoadingDialog();
    }

    /**
     * 显示加载中提示对话框
     *
     * @param promptTitle 加载中的提示文本
     */
    public void initLoadingDialog(Context context, String promptTitle) {
        if (mLoaddingView == null) {
            mLoaddingView = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setBackgroundColor(ContextCompat.getColor(context, R.color.cl_333))
                    .setLabel(promptTitle)
                    .setDimAmount(0.3f);
        }
    }

    /**
     * 隐藏加载中提示对话框
     */
    public void hideLoadingDialog() {
        if (mLoaddingView != null && mLoaddingView.isShowing()) {
            mLoaddingView.dismiss();
            mLoaddingView = null;
        }
    }
}
