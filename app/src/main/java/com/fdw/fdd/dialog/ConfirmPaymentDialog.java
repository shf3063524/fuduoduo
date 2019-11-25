package com.fdw.fdd.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fdw.fdd.R;

/**
 * 确认付款Dialog
 */
public class ConfirmPaymentDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String actualPrice;
    private OnClickListener listener;
    private TextView mTvClose;
    private TextView mTvDetermine;
    private TextView mTvPrice;

    public ConfirmPaymentDialog(@NonNull Context context,String actualPrice) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.actualPrice = actualPrice;

    }

    public ConfirmPaymentDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_payment_dialog);

        mTvDetermine = findViewById(R.id.m_tv_determine);
        mTvClose = findViewById(R.id.m_tv_close);
        mTvPrice = findViewById(R.id.m_tv_price);

        mTvPrice.setText(actualPrice);
        mTvDetermine.setOnClickListener(this);
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
            getWindow().setGravity(Gravity.BOTTOM); // Dialog在屏幕中间显示
            setCanceledOnTouchOutside(true);
        }
        // if (!haveWXPay&&!haveAliPay&&!haveBalance){
        //     dismiss();
        // }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_tv_determine: // 确认
                listener.onClick();
                dismiss();
                break;
            case R.id.m_tv_close:
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick();
    }
}
