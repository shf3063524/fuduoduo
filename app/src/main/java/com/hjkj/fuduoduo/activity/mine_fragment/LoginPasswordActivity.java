package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.OneMinutesDownUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 修改登录密码页面
 * Author：Created by shihongfei on 2019/10/2 10:02
 * Email：1511808259@qq.com
 */
public class LoginPasswordActivity extends BaseActivity {
    @BindView(R.id.m_cet_sms_code)
    ClearEditText mCetSmsCode;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayourSetReturn;
    @BindView(R.id.m_cet_enter_new_password)
    ClearEditText mCeEnterNewPassword;
    @BindView(R.id.m_cet_enter_password_again)
    ClearEditText mCetEnterPasswordAgain;
    @BindView(R.id.m_tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.m_tv_login_phone)
    TextView mTvLoginPhone;
    @BindView(R.id.m_tv_confirm)
    TextView mTvConfirm;
    private String vcode;
    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LoginPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login_password;
    }

    @Override
    protected void initViews() {
        mTvLoginPhone.setText("登录手机号  " + UserManager.getPhoneNumber(LoginPasswordActivity.this));
    }

    @Override
    protected void actionView() {
        mCetEnterPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
                    Toasty.info(LoginPasswordActivity.this, "请输入验证码").show();
                    return;
                }
                if (textIsEmpty(mCeEnterNewPassword)) {
                    Toasty.info(LoginPasswordActivity.this, "请输入新密码").show();
                    return;
                }
                if (textIsEmpty(mCetEnterPasswordAgain)) {
                    Toasty.info(LoginPasswordActivity.this, "请再次输入密码").show();
                    return;
                }
                if (!getTextString(mCeEnterNewPassword).equals(getTextString(mCetEnterPasswordAgain))) {
                    Toasty.info(LoginPasswordActivity.this, "您输入的密码不一致，请重新输入").show();
                    return;
                }
                if (getTextString(mCetSmsCode).equals(vcode) && !vcode.isEmpty() && vcode != null) {
                    doActivate();
                } else {
                    Toasty.info(LoginPasswordActivity.this, "您输入的验证码有误").show();
                    return;
                }
                break;
        }
    }
    /**
     * 修改登录接口
     */
    private void doActivate() {
        String phoneNumber = UserManager.getPhoneNumber(LoginPasswordActivity.this);
        String password = getTextString(mCeEnterNewPassword);
        OkGo.<AppResponse>get(Api.USER_DOACTIVATE)//
                .params("phoneNumber", phoneNumber)//
                .params("password", password)//
                .params("payPassword", 1)//
                .params("state", 1)//
                .execute(new DialogCallBack<AppResponse>(this, "正在登陆...") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            finish();
                        }
                    }
                });
    }
    /**
     * 获取验证码
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getVcode() {
        final OneMinutesDownUtil timeCountDown = new OneMinutesDownUtil(mTvGetCode);

        timeCountDown.start();
        String phoneNumber = UserManager.getPhoneNumber(LoginPasswordActivity.this);
        OkGo.<AppResponse<VcodeLoginData>>get(Api.USER_DOSEND)//
                .params("phoneNumber", phoneNumber)//
                .params("code", 2)//
                .execute(new DialogCallBack<AppResponse<VcodeLoginData>>(this, "正在登陆...") {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            vcode = simpleResponseAppResponse.getData().getVcode();
                        }
                    }
                });
    }
}
