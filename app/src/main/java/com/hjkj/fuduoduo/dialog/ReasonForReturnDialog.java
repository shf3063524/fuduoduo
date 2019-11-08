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
 * 未收到货原因Dialog
 */
public class ReasonForReturnDialog extends Dialog implements View.OnClickListener {
    public static final int M_LAYOUT_ONE = 1;
    public static final int M_LAYOUT_TWO = 2;
    public static final int M_LAYOUT_THREE = 3;
    public static final int M_LAYOUT_FOUR = 4;
    public static final int M_LAYOUT_FIVE = 5;
    public static final int M_LAYOUT_SIX = 6;
    private Context context;
    private RelativeLayout mLayoutOne;
    private RelativeLayout mLayoutTwo;
    private RelativeLayout mLayoutThree;
    private RelativeLayout mLayoutFour;
    private RelativeLayout mLayoutFive;
    private RelativeLayout mLayoutSix;
    private CheckBox mCbOne;
    private CheckBox mCbTwo;
    private CheckBox mCbThree;
    private CheckBox mCbFour;
    private CheckBox mCbFive;
    private CheckBox mCbSix;
    private TextView mTvClose;

    private int type = 1;//默认第一种
    private OnClickListener listener;

    public ReasonForReturnDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public ReasonForReturnDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reason_for_return_dialog);
        mLayoutOne = findViewById(R.id.m_layout_one);
        mLayoutTwo = findViewById(R.id.m_layout_two);
        mLayoutThree = findViewById(R.id.m_layout_three);
        mLayoutFour = findViewById(R.id.m_layout_four);
        mLayoutFive = findViewById(R.id.m_layout_five);
        mLayoutSix = findViewById(R.id.m_layout_six);

        mCbOne = findViewById(R.id.m_cb_one);
        mCbTwo = findViewById(R.id.m_cb_two);
        mCbThree = findViewById(R.id.m_cb_three);
        mCbFour = findViewById(R.id.m_cb_four);
        mCbFive = findViewById(R.id.m_cb_five);
        mCbSix = findViewById(R.id.m_cb_six);

        mTvClose = findViewById(R.id.m_tv_close);

        mLayoutOne.setOnClickListener(this);
        mLayoutTwo.setOnClickListener(this);
        mLayoutThree.setOnClickListener(this);
        mLayoutFour.setOnClickListener(this);
        mLayoutFive.setOnClickListener(this);
        mLayoutSix.setOnClickListener(this);
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
                mCbThree.setChecked(false);
                mCbFour.setChecked(false);
                mCbFive.setChecked(false);
                mCbSix.setChecked(false);
                type = M_LAYOUT_ONE;
                break;
            case R.id.m_layout_two:
                mCbOne.setChecked(false);
                mCbTwo.setChecked(true);
                mCbThree.setChecked(false);
                mCbFour.setChecked(false);
                mCbFive.setChecked(false);
                mCbSix.setChecked(false);
                type = M_LAYOUT_TWO;
                break;
            case R.id.m_layout_three:
                mCbOne.setChecked(false);
                mCbTwo.setChecked(false);
                mCbThree.setChecked(true);
                mCbFour.setChecked(false);
                mCbFive.setChecked(false);
                mCbSix.setChecked(false);
                type = M_LAYOUT_THREE;
                break;
            case R.id.m_layout_four:
                mCbOne.setChecked(false);
                mCbTwo.setChecked(false);
                mCbThree.setChecked(false);
                mCbFour.setChecked(true);
                mCbFive.setChecked(false);
                mCbSix.setChecked(false);
                type = M_LAYOUT_FOUR;
                break;
            case R.id.m_layout_five:
                mCbOne.setChecked(false);
                mCbTwo.setChecked(false);
                mCbThree.setChecked(false);
                mCbFour.setChecked(false);
                mCbFive.setChecked(true);
                mCbSix.setChecked(false);
                type = M_LAYOUT_FIVE;
                break;
            case R.id.m_layout_six:
                mCbOne.setChecked(false);
                mCbTwo.setChecked(false);
                mCbThree.setChecked(false);
                mCbFour.setChecked(false);
                mCbFive.setChecked(false);
                mCbSix.setChecked(true);
                type = M_LAYOUT_SIX;
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
