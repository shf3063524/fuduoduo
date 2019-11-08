package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.PayPsdInputView;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 修改支付密码-输入密码页面
 * Author：Created by shihongfei on 2019/10/2 13:11
 * Email：1511808259@qq.com
 */
public class PaymentPassword02Activity extends BaseActivity {
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayourSetReturn;
    @BindView(R.id.m_layout_background)
    FrameLayout mLayourbackground;
    @BindView(R.id.m_tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.m_password)
    PayPsdInputView mPassword;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, PaymentPassword02Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_payment_password02;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void actionView() {

        mPassword.setComparePassword(new PayPsdInputView.onPasswordListener() {

            @Override
            public void onDifference(String oldPsd, String newPsd) {
                // TODO: 2018/1/22  和上次输入的密码不一致  做相应的业务逻辑处理
                Toast.makeText(PaymentPassword02Activity.this, "两次密码输入不同" + oldPsd + "!=" + newPsd, Toast.LENGTH_SHORT).show();
                mPassword.cleanPsd();
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
                Toast.makeText(PaymentPassword02Activity.this, "密码相同" + psd, Toast.LENGTH_SHORT).show();
                mPassword.setComparePassword("");
                mPassword.cleanPsd();
            }

            @Override
            public void inputFinished(String inputPsd) {
                // TODO: 2018/1/3 输完逻辑
                Toast.makeText(PaymentPassword02Activity.this, "输入完毕：" + inputPsd, Toast.LENGTH_SHORT).show();
                mPassword.setComparePassword(inputPsd);
            }
        });

    }

    @OnClick({R.id.m_layout_set_return, R.id.m_tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_layout_set_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_confirm:   // 确认
                if (mPassword.getPasswordString().isEmpty()){
                    Toasty.info(PaymentPassword02Activity.this,"请输入支付密码").show();
                    return;
                }
                doActivate();
                break;
        }
    }

    private void doActivate() {
        String phoneNumber = UserManager.getPhoneNumber(PaymentPassword02Activity.this);
        String passwordString = mPassword.getPasswordString();
        OkGo.<AppResponse>get(Api.USER_DOACTIVATE)//
                .params("phoneNumber", phoneNumber)//
                .params("payPassword", passwordString)//
                .params("state", 2)//
                .execute(new DialogCallBack<AppResponse>(this, "正在修改...") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mLayourbackground.setVisibility(View.VISIBLE);
                            SetActivity.openActivity(PaymentPassword02Activity.this);
                            finish();
                        }
                    }
                });
    }
}
