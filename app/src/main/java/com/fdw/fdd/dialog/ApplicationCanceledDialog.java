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
 * 撤销申请Dialog
 */
public class ApplicationCanceledDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private OnClickListener listener;
    private TextView mTvClose;
    private TextView mTvDetermine;
    private TextView mTvMessage;

    public ApplicationCanceledDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;


    }

    public ApplicationCanceledDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_canceled_dialog);

        mTvDetermine = findViewById(R.id.m_tv_determine);
        mTvClose = findViewById(R.id.m_tv_close);
        mTvMessage = findViewById(R.id.m_tv_message);


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

