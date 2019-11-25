package com.fdw.fdd.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.fdw.fdd.R;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Fragment基类
 * Author：Created by shihongfei on 2019/9/24 13:43
 * Email：1511808259@qq.com
 */
public abstract class BaseStatusFragment extends Fragment {
    protected Context mContext;
    private KProgressHUD mLoaddingView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /**
     * 显示加载中对话框
     */
    public void showLoadingDialog() {
        if (mLoaddingView == null) {
            mLoaddingView = KProgressHUD.create(mContext)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                    .setDimAmount(0.3f);
        }
        mLoaddingView.show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mLoaddingView != null) {
            mLoaddingView = null;
        }
    }
}
