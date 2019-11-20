package com.hjkj.fuduoduo.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hjkj.fuduoduo.LoginActivity;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.DocheckactiveData;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.OneMinutesDownUtil;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 用户激活页面
 */
public class UserActivationActivity extends BaseActivity {
    @BindView(R.id.m_tv_login)
    TextView mTvLogin;
    @BindView(R.id.m_tv_get_vcode)
    TextView mTvGetVcode;
    @BindView(R.id.m_tv_service_agreement)
    TextView mTvServiceAgreement;
    @BindView(R.id.m_tv_actication)
    TextView mTvActication;
    @BindView(R.id.m_et_phone)
    ClearEditText mEtPhone;
    @BindView(R.id.m_et_vcode)
    ClearEditText mEtVcode;
    @BindView(R.id.m_et_login_password)
    ClearEditText mEtLoginPassword;
    @BindView(R.id.m_et_pay_password)
    ClearEditText mEtPayPassword;
    @BindView(R.id.m_cb)
    CheckBox mCb;
    @BindView(R.id.m_scroll_view)
    ScrollView mScrollView;
    private boolean check = true;
    /**
     * 验证码
     */
    private String vcode;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, UserActivationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_user_activation;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(UserActivationActivity.this, 0);
    }

    @Override
    protected void initViews() {
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
                    mTvActication.setBackground(getResources().getDrawable(R.drawable.shape_frame_cl_e5ef_all));
                } else {
                    mTvActication.setBackground(getResources().getDrawable(R.drawable.shape_frame_cl_f5a5a7));
                }
                mScrollView.smoothScrollTo(0, mScrollView.getHeight());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.m_tv_login, R.id.m_tv_get_vcode, R.id.m_cb, R.id.m_tv_actication, R.id.m_tv_service_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_tv_login:   // 登录
                LoginActivity.openActivity(UserActivationActivity.this);
                break;
            case R.id.m_tv_get_vcode: // 获取验证码
                if (textIsEmpty(mEtPhone)) {
                    Toasty.info(UserActivationActivity.this, "请先输入手机号").show();
                    return;
                }
                if (mEtPhone.length() != 11) {
                    Toasty.info(UserActivationActivity.this, "您输入的手机号不正确").show();
                    return;
                }
                try {
                    getVcode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.m_cb: //福多多平台服务协议
                if (check) {
                    check = false;
                    mCb.setChecked(true);
                } else {
                    check = true;
                    mCb.setChecked(false);
                }
                break;
            case R.id.m_tv_actication: // 激活
                if (textIsEmpty(mEtPhone)) {
                    Toasty.info(UserActivationActivity.this, "请先输入手机号").show();
                    return;
                }
                if (textIsEmpty(mEtPhone)) {
                    Toasty.info(UserActivationActivity.this, "请先输入验证码").show();
                    return;
                }

                if (textIsEmpty(mEtLoginPassword)) {
                    Toasty.info(UserActivationActivity.this, "请设置登录密码").show();
                    return;
                }
                if (textIsEmpty(mEtPayPassword)) {
                    Toasty.info(UserActivationActivity.this, "请设置支付密码").show();
                    return;
                }
                if (!mCb.isChecked()) {
                    Toasty.info(UserActivationActivity.this, "请阅读福多多平台服务协议").show();
                    return;
                }
                if (mEtPayPassword.length() != 6) {
                    Toasty.info(UserActivationActivity.this, "请设置6位数字支付密码").show();
                    return;
                }
                if (getTextString(mEtVcode).equals(vcode) && !vcode.isEmpty() && vcode != null) {
                    onActivation();
                } else {
                    Toasty.info(UserActivationActivity.this, "您输入的验证码有误").show();
                    return;
                }
                break;
            case R.id.m_tv_service_agreement: //福多多平台服务协议
                ServiceAgreementActivity.openActivity(UserActivationActivity.this);
                break;
        }
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
                .params("code", 1)//
                .execute(new DialogCallBack<AppResponse<VcodeLoginData>>(this, "正在登陆...") {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            vcode = simpleResponseAppResponse.getData().getVcode();
                        }
                    }
                });
    }

    /**
     * 激活接口
     */
    private void doActivate() {
        String phoneNumber = getTextString(mEtPhone);
        String password = getTextString(mEtLoginPassword);
        String payPassword = getTextString(mEtPayPassword);
        OkGo.<AppResponse>get(Api.USER_DOACTIVATE)//
                .params("phoneNumber", phoneNumber)//
                .params("password", password)//
                .params("payPassword", payPassword)//
                .params("state", 0)//
                .execute(new DialogCallBack<AppResponse>(this, "正在登陆...") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.normal(UserActivationActivity.this, "激活成功").show();
                            finish();
                        }
                    }
                });

    }

    /**
     * 激活验证接口
     */
    private void onActivation() {
        String phoneNumber = getTextString(mEtPhone);
        OkGo.<AppResponse<DocheckactiveData>>get(Api.USER_DOCHECKACTIVE)//
                .params("phoneNumber", phoneNumber)//
                .execute(new DialogCallBack<AppResponse<DocheckactiveData>>(this, "正在激活...") {
                    @Override
                    public void onSuccess(AppResponse<DocheckactiveData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DocheckactiveData appResponseData = simpleResponseAppResponse.getData();
                           if ("".equals(appResponseData.getConsumer().getUsername())){
                               Toasty.info(UserActivationActivity.this, "您手机号已经激活过了").show();
                               return;
                           }else {
                               doActivate();
                           }
                        }
                    }
                });
    }
}
