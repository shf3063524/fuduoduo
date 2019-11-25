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
 * 去充值吧Dialog
 */
public class GoRechargeDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String message;

    private int type = 1;//默认第一种
    private OnClickListener listener;
    private TextView mTvClose;
    private TextView mTvDetermine;
    private TextView mTvMessage;

    public GoRechargeDialog(@NonNull Context context, String message) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.message = message;

    }

    public GoRechargeDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_recharge_dialog);

        mTvDetermine = findViewById(R.id.m_tv_determine);
        mTvClose = findViewById(R.id.m_tv_close);
        mTvMessage = findViewById(R.id.m_tv_message);


        mTvMessage.setText(message);
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
            getWindow().setGravity(Gravity.CENTER);
            setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_tv_determine: // 确认
                listener.onClick(type);
                dismiss();
                break;
            case R.id.m_tv_close:
                dismiss();
                break;
        }
    }

    public interface OnClickListener {
        void onClick(int type);
    }
}

