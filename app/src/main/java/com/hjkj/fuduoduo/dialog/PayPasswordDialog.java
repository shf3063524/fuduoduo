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
import android.widget.Toast;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.view.widget.Keyboard;
import com.hjkj.fuduoduo.view.widget.PayEditText;

/**
 * 支付密码Dialog
 */
public class PayPasswordDialog extends Dialog implements View.OnClickListener {
    private Context context;

    private String payPassword ;//默认第一种
    private OnClickListener listener;
    private static final String[] KEY = new String[]{
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "<<", "0", "完成"
    };

    private PayEditText payEditText;
    private Keyboard keyboard;

    public PayPasswordDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
    }

    public PayPasswordDialog setListener(OnClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_password_dialog);
        payEditText = (PayEditText) findViewById(R.id.PayEditText_pay);
        keyboard = (Keyboard) findViewById(R.id.KeyboardView_pay);
        setSubView();
        initEvent();
    }

    private void setSubView() {
        //设置键盘
        keyboard.setKeyboardKeys(KEY);
    }

    private void initEvent() {
        keyboard.setOnClickKeyboardListener(new Keyboard.OnClickKeyboardListener() {
            @Override
            public void onKeyClick(int position, String value) {
                if (position < 11 && position != 9) {
                    payEditText.add(value);
                } else if (position == 9) {
                    payEditText.remove();
                } else if (position == 11) {
                    //当点击完成的时候，也可以通过payEditText.getText()获取密码，此时不应该注册OnInputFinishedListener接口
//                    Toast.makeText(context, "您的密码是：" + payEditText.getText(), Toast.LENGTH_SHORT).show();
                    payPassword = payEditText.getText();
                    listener.onClick(payPassword);
                    dismiss();
                }
            }
        });

        /**
         * 当密码输入完成时的回调
         */
        payEditText.setOnInputFinishedListener(new PayEditText.OnInputFinishedListener() {
            @Override
            public void onInputFinished(String password) {
//                Toast.makeText(context, "您的密码是：" + password, Toast.LENGTH_SHORT).show();
            }
        });
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

    }

    public interface OnClickListener {
        void onClick(String payPassword);
    }
}
