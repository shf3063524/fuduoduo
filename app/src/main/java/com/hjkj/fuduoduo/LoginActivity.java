package com.hjkj.fuduoduo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hjkj.fuduoduo.activity.PersonalCenter02Activity;
import com.hjkj.fuduoduo.activity.login.ForgetPasswordActivity;
import com.hjkj.fuduoduo.activity.login.UserActivationActivity;
import com.hjkj.fuduoduo.activity.mine_fragment.PersonalCenterActivity;
import com.hjkj.fuduoduo.activity.mine_fragment.SetActivity;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.ConsumerBean;
import com.hjkj.fuduoduo.entity.bean.PasswordLoginData;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.OneMinutesDownUtil;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.lzy.okgo.OkGo;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.m_layout_password)
    RelativeLayout mLayoutPassword;
    @BindView(R.id.m_layout_login)
    LinearLayout mLayoutLogin;
    @BindView(R.id.m_layout_vcode)
    RelativeLayout mLayoutVode;
    @BindView(R.id.m_tv_login_vcode)
    TextView mTvLoginVcode;
    @BindView(R.id.m_tv_login)
    TextView mTvLogin;
    @BindView(R.id.m_tv_get_vcode)
    TextView mTvGetVcode;
    @BindView(R.id.m_tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.m_tv_user_activation)
    TextView mTvUserActivation;
    @BindView(R.id.m_et_phone)
    ClearEditText mEtPhone;
    @BindView(R.id.m_et_password)
    ClearEditText mEtPassword;
    @BindView(R.id.m_et_vcode)
    ClearEditText mEtVcode;
    @BindView(R.id.togglePwd)
    ToggleButton togglePwd;
    @BindView(R.id.m_scroll_view)
    ScrollView mScrollView;
    private boolean checkVcode = false;
    private String vcode;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String jumpKey) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(LoginActivity.this, 0);
    }

    @Override
    protected void initViews() {
        String accessToken = UserManager.getUserId(LoginActivity.this);
        if (!TextUtils.isEmpty(accessToken)) {
            MainActivity.openActivity(this);
            finish();
        }

        mTvLoginVcode.setText("验证码登录");

//        //测试，预先注册一个账号
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ChatClient.getInstance().register("xiaoming", "cs12345678", new Callback(){
//                    @Override
//                    public void onSuccess() {
//                        Log.d("IM注册成功", "onSuccess: ");
//                    }
//
//                    @Override
//                    public void onError(int code, String error) {
//                        Log.d("IM注册失败", "onSuccess: "+error);
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//                });
//            }
//        }).start();

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
        togglePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        /**
         *
         */
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if (bottom != 0 && oldBottom != 0 && bottom - rect.bottom <= 0) {
                    // 隐藏
                    mScrollView.smoothScrollTo(0, 0);
                } else {
                    mScrollView.smoothScrollTo(0, mScrollView.getHeight());
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.m_tv_login_vcode, R.id.m_tv_login, R.id.m_tv_user_activation, R.id.m_tv_get_vcode, R.id.m_tv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_tv_login_vcode:   // 验证码登录
                if (checkVcode) {
                    checkVcode = false;
                    mTvLoginVcode.setText("验证码登录");
                    mLayoutVode.setVisibility(View.GONE);
                    mLayoutPassword.setVisibility(View.VISIBLE);
                } else {
                    checkVcode = true;
                    mTvLoginVcode.setText("密码登录");
                    mLayoutVode.setVisibility(View.VISIBLE);
                    mLayoutPassword.setVisibility(View.GONE);
                }
                break;
            case R.id.m_tv_login: // 登录
                if (textIsEmpty(mEtPhone)) {
                    Toasty.info(LoginActivity.this, "请先输入手机号").show();
                    return;
                }
                if ("验证码登录".equals(getTextString(mTvLoginVcode))) {
                    if (textIsEmpty(mEtPassword)) {
                        Toasty.info(LoginActivity.this, "请先输入密码").show();
                        return;
                    }
                    passwordLogin();
                } else if ("密码登录".equals(getTextString(mTvLoginVcode))) {
                    if (textIsEmpty(mEtVcode)) {
                        Toasty.info(LoginActivity.this, "请先输入验证码").show();
                        return;
                    }
                    if (getTextString(mEtVcode).equals(vcode) && !vcode.isEmpty() && vcode != null) {
                        vcodeLogin();
                    } else {
                        Toasty.info(LoginActivity.this, "您输入的验证码有误").show();
                        return;
                    }

                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ChatClient.getInstance().login("xiaoming", "cs12345678", new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("IM登录成功", "onSuccess: ");
                            }

                            @Override
                            public void onError(int code, String error) {
                                Log.d("IM登录失败", "onSuccess: " + error);
                            }

                            @Override
                            public void onProgress(int progress, String status) {

                            }
                        });
                    }
                }).start();
                break;
            case R.id.m_tv_user_activation: // 用户激活
                UserActivationActivity.openActivity(LoginActivity.this);
                break;
            case R.id.m_tv_get_vcode: // 获取验证码
                if (textIsEmpty(mEtPhone)) {
                    Toasty.info(LoginActivity.this, "请先输入手机号").show();
                    return;
                }
                try {
                    getVcode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.m_tv_forget_password: // 忘记密码
                ForgetPasswordActivity.openActivity(LoginActivity.this);
                break;
        }
    }

    /**
     * 密码登录
     */
    private void passwordLogin() {
        String phoneNumber = getTextString(mEtPhone);
        String password = getTextString(mEtPassword);
        OkGo.<AppResponse<PasswordLoginData>>get(Api.USER_DOLOGIN)//
                .params("phoneNumber", phoneNumber)//
                .params("password", password)//
                .params("state", 0)//
                .execute(new DialogCallBack<AppResponse<PasswordLoginData>>(this, "正在登陆...") {
                    @Override
                    public void onSuccess(AppResponse<PasswordLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            String message = simpleResponseAppResponse.getMessage(); //判断是否是0：新用户还是1：老用户
                            PasswordLoginData data = simpleResponseAppResponse.getData();


                            //消费者信息
                            ConsumerBean consumer = data.getConsumer();
                            UserManager.setPhoneNumber(LoginActivity.this, consumer.getPhoneNumber());
                            UserManager.setUserId(LoginActivity.this, consumer.getId());
                            if ("0".equals(message)) {
                                PersonalCenter02Activity.openActivity(LoginActivity.this, message, "LoginActivity");
                            } else {
                                MainActivity.openActivity(LoginActivity.this, message, "LoginActivity");
                            }
                        }
                    }
                });

    }

    /**
     * 验证码登录
     */
    private void vcodeLogin() {
        String phoneNumber = getTextString(mEtPhone);
        OkGo.<AppResponse<PasswordLoginData>>get(Api.USER_DOLOGIN)//
                .params("phoneNumber", phoneNumber)//
                .params("password", null)//
                .params("state", 1)//
                .execute(new DialogCallBack<AppResponse<PasswordLoginData>>(this, "正在登陆...") {
                    @Override
                    public void onSuccess(AppResponse<PasswordLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            String message = simpleResponseAppResponse.getMessage();
                            PasswordLoginData data = simpleResponseAppResponse.getData();


                            //消费者信息
                            ConsumerBean consumer = data.getConsumer();
                            UserManager.setPhoneNumber(LoginActivity.this, consumer.getPhoneNumber());
                            UserManager.setUserId(LoginActivity.this, consumer.getId());

                            MainActivity.openActivity(LoginActivity.this, message, "LoginActivity");
                        }
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
                .params("code", 1)//
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
