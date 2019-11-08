package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.ConsumerBean;
import com.hjkj.fuduoduo.entity.bean.UserDoQueryData;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.view.GlobalClickItem;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

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

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, SetActivity.class);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, ConsumerBean consumerBean) {
        Intent intent = new Intent(context, SetActivity.class);
        intent.putExtra("ConsumerBean", consumerBean);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_set;
    }

    @Override
    protected void initPageData() {
        ConsumerBean consumerBean = (ConsumerBean) getIntent().getSerializableExtra("ConsumerBean");
        // 头像
        GlideUtils.loadCircleHeadImage(SetActivity.this, consumerBean.getLogo(), R.drawable.ic_all_background, mIvSetLogo);
        // 用户姓名
        mTvUsername.setText(consumerBean.getUsername());
        //用户工号
        mTvJobNumber.setText("工号：" + consumerBean.getJobNumber());
    }

    @Override
    protected void initViews() {

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
                MyShippingAddressActivity.openActivity(SetActivity.this);
                break;
            case R.id.m_about_us:   // 关于我们
                Toasty.info(this, "关于我们").show();
                break;
            case R.id.m_tv_sign_out:   // 退出登录

                break;
            case R.id.m_layout_personal_center:   // 个人中心
                PersonalCenterActivity.openActivity(SetActivity.this);
                break;
        }
    }
}
