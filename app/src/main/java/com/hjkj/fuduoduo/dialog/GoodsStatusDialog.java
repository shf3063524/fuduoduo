package com.hjkj.fuduoduo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;

/**
 * 货物状态Dialog
 */
public class GoodsStatusDialog extends Dialog implements View.OnClickListener {
    public static final int M_LAYOUT_ONE = 1;
    public static final int M_LAYOUT_TWO = 2;
    private Context context;

    private int type = 1;//默认第一种
    private OnClickListener listener;
    private RelativeLayout mLayoutOne;
    private RelativeLayout mLayoutTwo;
    private CheckBox mCbOne;
    private CheckBox mCbTwo;
    private TextView mTvClose;

    public GoodsStatusDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public GoodsStatusDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_status_dialog);
        mLayoutOne = findViewById(R.id.m_layout_one);
        mLayoutTwo = findViewById(R.id.m_layout_two);
        mCbOne = findViewById(R.id.m_cb_one);
        mCbTwo = findViewById(R.id.m_cb_two);
        mTvClose = findViewById(R.id.m_tv_close);

        mLayoutOne.setOnClickListener(this);
        mLayoutTwo.setOnClickListener(this);
        mTvClose.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        if (getWindow() != null) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            getWindow().setAttributes(params);
            getWindow().setGravity(Gravity.BOTTOM);
            setCanceledOnTouchOutside(true);
        }
        // if (!haveWXPay&&!haveAliPay&&!haveBalance){
        //     dismiss();
        // }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_layout_one:
                mCbOne.setChecked(true);
                mCbTwo.setChecked(false);
                type = M_LAYOUT_ONE;
                break;
            case R.id.m_layout_two:
                mCbOne.setChecked(false);
                mCbTwo.setChecked(true);
                type = M_LAYOUT_TWO;
                break;
            case R.id.m_tv_close:
                listener.onClick(type);
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(int type);
    }
}
