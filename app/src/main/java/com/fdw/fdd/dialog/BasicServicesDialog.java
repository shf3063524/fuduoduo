package com.fdw.fdd.dialog;

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

import com.fdw.fdd.R;

public class BasicServicesDialog extends Dialog implements View.OnClickListener {
    public static final int M_LAYOUT_ONE = 1;
    public static final int M_LAYOUT_TWO = 2;
    public static final int M_LAYOUT_THREE = 3;
    public static final int M_LAYOUT_FOUR = 4;
    public static final int M_LAYOUT_FIVE = 5;
    public static final int M_LAYOUT_SIX = 6;
    public static final int M_LAYOUT_SEVEN = 7;
    private Context context;

    private int type = 1;//默认第一种
    private OnClickListener listener;
    private RelativeLayout mLayoutOne;
    private RelativeLayout mLayoutTwo;
    private RelativeLayout mLayoutThree;
    private CheckBox mCbOne;
    private CheckBox mCbTwo;
    private CheckBox mCbThree;
    private TextView mTvClose;

    public BasicServicesDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public BasicServicesDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_services_dialog);
        mLayoutOne = findViewById(R.id.m_layout_one);
        mLayoutTwo = findViewById(R.id.m_layout_two);
        mLayoutThree = findViewById(R.id.m_layout_three);
        mCbOne = findViewById(R.id.m_cb_one);
        mCbTwo = findViewById(R.id.m_cb_two);
        mCbThree = findViewById(R.id.m_cb_three);
        mTvClose = findViewById(R.id.m_tv_close);

        mLayoutOne.setOnClickListener(this);
        mLayoutTwo.setOnClickListener(this);
        mLayoutThree.setOnClickListener(this);
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
//            case R.id.m_layout_one:
//                mCbOne.setChecked(true);
//                type = M_LAYOUT_ONE;
//                break;
//            case R.id.m_layout_two:
//                mCbTwo.setChecked(true);
//                type = M_LAYOUT_TWO;
//                break;
//            case R.id.m_layout_three:
//                mCbThree.setChecked(true);
//                type = M_LAYOUT_THREE;
//                break;
            case R.id.m_tv_close:
//                if (mCbOne.isChecked() && mCbTwo.isChecked() && mCbThree.isChecked()) {
//                    type = M_LAYOUT_FOUR;
//                } else if (mCbOne.isChecked() && mCbTwo.isChecked()) {
//                    type = M_LAYOUT_FIVE;
//                } else if (mCbOne.isChecked() && mCbThree.isChecked()) {
//                    type = M_LAYOUT_SIX;
//                }else {
//                    type = M_LAYOUT_SEVEN;
//                }
//                listener.onClick(type);
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(int type);
    }
}
