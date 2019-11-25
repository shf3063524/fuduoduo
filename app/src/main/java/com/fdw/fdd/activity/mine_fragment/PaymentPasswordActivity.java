package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.VcodeLoginData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.tool.OneMinutesDownUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.ClearEditText;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 修改支付密码页面
 * Author：Created by shihongfei on 2019/10/2 10:54
 * Email：1511808259@qq.com
 */
public class PaymentPasswordActivity extends BaseActivity {
    @BindView(R.id.m_cet_sms_code)
    ClearEditText mCetSmsCode;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayourSetReturn;
    @BindView(R.id.m_tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.m_tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.m_tv_login_phone)
    TextView mTvLoginPhone;
    private String vcode;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, PaymentPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_payment_password;
    }

    @Override
    protected void initViews() {
        mTvLoginPhone.setText("登录手机号  " + UserManager.getPhoneNumber(PaymentPasswordActivity.this));
    }

    @Override
    protected void actionView() {
        mCetSmsCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String newText = charSequence.toString().trim();
                if (!newText.isEmpty()) {
                    mTvConfirm.setBackground(getResources().getDrawable(R.drawable.shape_frame_cl_e5ef_all));
                } else {
                    mTvConfirm.setBackground(getResources().getDrawable(R.drawable.shape_frame_cl_f5a5a7));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.m_layout_set_return, R.id.m_tv_get_code, R.id.m_tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_layout_set_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_get_code:   // 获取验证码
                try {
                    getVcode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.m_tv_confirm:   // 确认
                if (textIsEmpty(mCetSmsCode)) {
                    Toasty.info(PaymentPasswordActivity.this, "请输入验证码").show();
                    return;
                }
                if (getTextString(mCetSmsCode).equals(vcode) && !vcode.isEmpty() && vcode != null) {
                    PaymentPassword02Activity.openActivity(PaymentPasswordActivity.this);
                    finish();
                } else {
                    Toasty.info(PaymentPasswordActivity.this, "您输入的验证码有误").show();
                    return;
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getVcode() {
        final OneMinutesDownUtil timeCountDown = new OneMinutesDownUtil(mTvGetCode);

        timeCountDown.start();
        String phoneNumber = UserManager.getPhoneNumber(PaymentPasswordActivity.this);
        OkGo.<AppResponse<VcodeLoginData>>get(Api.USER_DOSEND)//
                .params("phoneNumber", phoneNumber)//
                .params("code", 3)//
                .execute(new DialogCallBack<AppResponse<VcodeLoginData>>(this, "正在获取...") {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            vcode = simpleResponseAppResponse.getData().getVcode();
                        }
                    }
                });
    }
}
