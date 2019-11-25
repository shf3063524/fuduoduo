package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.LoginActivity;
import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.ConsumerBean;
import com.fdw.fdd.entity.bean.UserDoQueryData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.GlobalClickItem;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置-页面
 * Author：Created by shihongfei on 2019/9/27 09:54
 * Email：1511808259@qq.com
 */
public class SetActivity extends BaseActivity {
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayiytSetReturn;
    @BindView(R.id.m_layout_personal_center)
    RelativeLayout mLayoutPersonalCenter;
    @BindView(R.id.m_login_password)
    GlobalClickItem mLoginPassword;
    @BindView(R.id.m_payment_password)
    GlobalClickItem mPaymentPassword;
    @BindView(R.id.m_my_shipping_address)
    GlobalClickItem mMyShippingAddress;
    @BindView(R.id.m_about_us)
    GlobalClickItem mAboutUs;
    @BindView(R.id.m_tv_sign_out)
    TextView mTvSignOut;
    @BindView(R.id.m_tv_username)
    TextView mTvUsername;
    @BindView(R.id.m_iv_set_logo)
    ImageView mIvSetLogo;
    @BindView(R.id.m_tv_job_number)
    TextView mTvJobNumber;
    @BindColor(R.color.cl_333)
    int cl_47;
    private ConsumerBean consumerBean;
    private UserDoQueryData responseData;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_set;
    }

    @Override
    protected void initViews() {
        requestData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @OnClick({R.id.m_layout_set_return, R.id.m_login_password, R.id.m_payment_password, R.id.m_my_shipping_address, R.id.m_about_us,
            R.id.m_tv_sign_out, R.id.m_layout_personal_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_layout_set_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_login_password:   // 登录密码
                LoginPasswordActivity.openActivity(SetActivity.this);
                break;
            case R.id.m_payment_password:   // 支付密码
                PaymentPasswordActivity.openActivity(SetActivity.this);
                break;
            case R.id.m_my_shipping_address:   // 我的收货地址
                MyShippingAddressActivity.openActivity(SetActivity.this, "SetActivity");
                break;
            case R.id.m_about_us:   // 关于我们
                AboutUsActivity.openActivity(SetActivity.this);
                break;
            case R.id.m_tv_sign_out:   // 退出登录
                new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setText("是否确定退出登录？")
                        .setTextColor(cl_47)
                        .configText(params -> {
                            // params.gravity = Gravity.LEFT | Gravity.TOP;
                            params.padding = new int[]{100, 130, 100, 130};
                        })
                        .setNegative("取消", null)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserManager.setDataIsNull(SetActivity.this);
                                LoginActivity.openActivity(SetActivity.this, "SetActivity");

                                finish();
                            }
                        })
                        .show(getSupportFragmentManager());

                break;
            case R.id.m_layout_personal_center:   // 个人中心
                PersonalCenterActivity.openActivity(SetActivity.this, responseData.getConsumer(), "SetActivity");
                break;
        }
    }

    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(SetActivity.this);
        OkGo.<AppResponse<UserDoQueryData>>get(Api.USER_DOQUERY)//
                .params("id", userId)
                .execute(new JsonCallBack<AppResponse<UserDoQueryData>>() {
                    @Override
                    public void onSuccess(AppResponse<UserDoQueryData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            responseData = simpleResponseAppResponse.getData();
                            initDealUser(responseData);
                        }
                    }
                });
    }

    private void initDealUser(UserDoQueryData responseData) {
        // 头像
        GlideUtils.loadCircleHeadImage(SetActivity.this, responseData.getConsumer().getLogo(), R.drawable.ic_all_background, mIvSetLogo);
        // 用户姓名
        mTvUsername.setText(responseData.getConsumer().getUsername());
        //用户工号
        mTvJobNumber.setText("工号：" + responseData.getConsumer().getJobNumber());
    }
}
