package com.fdw.fdd.tool;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 1分钟倒计时工具类
 * Created by kayroc on 2016/12/7.
 */
public class OneMinutesDownUtil extends CountDownTimer {

    public static final int TIME_COUNT = 60000;
    private TextView mBtn;
    public OneMinutesDownUtil(TextView btn) {
        super(TIME_COUNT, 1000);
        this.mBtn = btn;
    }

    // 计时完毕时触发
    @Override
    public void onFinish() {
        mBtn.setText("发送验证码");
        mBtn.setEnabled(true);
    }

    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        mBtn.setEnabled(false);
        mBtn.setText(millisUntilFinished / 1000 + "s后重新发送");
    }
}
