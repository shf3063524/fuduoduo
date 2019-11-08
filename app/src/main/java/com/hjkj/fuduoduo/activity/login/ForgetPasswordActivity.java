package com.hjkj.fuduoduo.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.OneMinutesDownUtil;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 忘记密码页面
 */
public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_tv_get_vcode)
    TextView mTvGetVcode;
    @BindView(R.id.m_tv_login)
    TextView mTvLogin;
    @BindView(R.id.m_et_phone)
    ClearEditText mEtPhone;
    @BindView(R.id.m_et_vcode)
    ClearEditText mEtVcode;
    @BindView(R.id.m_et_new_password)
    ClearEditText mEtNewPassword;
    @BindView(R.id.m_et_confirm_password)
    ClearEditText mEtconfirmPassword;
    private String vcode;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initViews() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.m_iv_arrow, R.id.m_tv_get_vcode, R.id.m_tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_get_vcode: // 获取验证码
                if (textIsEmpty(mEtPhone)) {
                    Toasty.info(ForgetPasswordActivity.this, "请先输入手机号").show();
                    return;
                }
                try {
                    getVcode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.m_tv_login: // 确定
                if (textIsEmpty(mEtPhone)) {
                    Toasty.info(ForgetPasswordActivity.this, "请先输入手机号").show();
                    return;
                }
                if (textIsEmpty(mEtVcode)) {
                    Toasty.info(ForgetPasswordActivity.this, "请输入验证码").show();
                    return;
                }
                if (textIsEmpty(mEtNewPassword)) {
                    Toasty.info(ForgetPasswordActivity.this, "请输入新密码").show();
                    return;
                }
                if (textIsEmpty(mEtconfirmPassword)) {
                    Toasty.info(ForgetPasswordActivity.this, "请再次输入密码").show();
                    return;
                }
                if (!getTextString(mEtNewPassword).equals(getTextString(mEtconfirmPassword))) {
                    Toasty.info(ForgetPasswordActivity.this, "您输入的密码不一致，请重新输入").show();
                    return;
                }
                if (getTextString(mEtVcode).equals(vcode) && !vcode.isEmpty() && vcode != null) {
                    doActivate();
                } else {
                    Toasty.info(ForgetPasswordActivity.this, "您输入的验证码有误").show();
                    return;
                }
                break;
        }
    }

    @Override
    protected void actionView() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String newText = charSequence.toString().trim();
                if (!newText.isEmpty()) {
                    mTvLogin.setBackground(getResources().getDrawable(R.drawable.shape_frame_cl_e5ef_all));
                } else {
                    mTvLogin.setBackground(getResources().getDrawable(R.drawable.shape_frame_cl_f5a5a7));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 获取验证码
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getVcode() {
        final OneMinutesDownUtil timeCountDown = new OneMinutesDownUtil(mTvGetVcode);
        timeCountDown.start();

        String phoneNumber = getTextString(mEtPhone);
        OkGo.<AppResponse<VcodeLoginData>>get(Api.USER_DOSEND)//
                .params("phoneNumber", phoneNumber)//
                .params("code", 4)//
                .execute(new DialogCallBack<AppResponse<VcodeLoginData>>(this, "正在获取...") {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            vcode = simpleResponseAppResponse.getData().getVcode();
                        }
                    }
                });
    }

    /**
     * 忘记密码接口
     */
    private void doActivate() {
        String phoneNumber = getTextString(mEtPhone);
        String password = getTextString(mEtNewPassword);
        OkGo.<AppResponse>get(Api.USER_DOACTIVATE)//
                .params("phoneNumber", phoneNumber)//
                .params("password", password)//
                .params("state", 1)//
                .execute(new DialogCallBack<AppResponse>(this, "正在修改...") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.normal(ForgetPasswordActivity.this,"修改成功").show();
                            finish();
                        }
                    }
                });
    }
}
