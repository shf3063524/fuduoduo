package com.fdw.fdd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.activity.mine_fragment.AfterSaleActivity;
import com.fdw.fdd.activity.mine_fragment.CommonProblemActivity;
import com.fdw.fdd.activity.mine_fragment.FeedbackActivity;
import com.fdw.fdd.activity.mine_fragment.FootPrintActivity;
import com.fdw.fdd.activity.mine_fragment.FudouTransferActivity;
import com.fdw.fdd.activity.mine_fragment.MemberCenterActivity;
import com.fdw.fdd.activity.mine_fragment.MyCollectionActivity;
import com.fdw.fdd.activity.mine_fragment.MyOrderActivity;
import com.fdw.fdd.activity.mine_fragment.SetActivity;
import com.fdw.fdd.activity.mine_fragment.FudouBanlanceActivity;
import com.fdw.fdd.activity.mine_fragment.FudouRechargeActivity;
import com.fdw.fdd.base.BaseFragment;
import com.fdw.fdd.entity.bean.ConsumerBean;
import com.fdw.fdd.entity.bean.EnteerpriseBean;
import com.fdw.fdd.entity.bean.UserDoQueryData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.kefu.LoginKeFu02Activity;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.tool.kefutool.Constant;
import com.lzy.okgo.OkGo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author：Created by shihongfei on 2019/9/24 14:35
 * Email：1511808259@qq.com
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.m_iv_set)
    ImageView mIvSet;

    @BindView(R.id.m_iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.m_tv_name)
    TextView mTvName;
    @BindView(R.id.m_tv_username)
    TextView mTvUsername;
    @BindView(R.id.m_tv_job_number)
    TextView mTvJobNumber;
    @BindView(R.id.m_tv_blessing)
    TextView mTvBlessing;
    @BindView(R.id.m_tv_company)
    TextView mTvCompany;
    @BindView(R.id.m_tv_balance)
    TextView mTvBalance;

    @BindView(R.id.m_layout_fudou_banlance)
    LinearLayout mLayoutFudouBanlance;
    @BindView(R.id.m_layout_fudou_recharge)
    LinearLayout mLayoutFudouRecharge;
    @BindView(R.id.m_layout_transfer_fudou)
    LinearLayout mLayoutTransferFudou;
    @BindView(R.id.m_layout_pending_payment)
    LinearLayout mLayoutPendingPayment;
    @BindView(R.id.m_layout_delivered)
    LinearLayout mLayoutDelivered;
    @BindView(R.id.m_layout_pending_receipt)
    LinearLayout mLayoutPendingReceipt;
    @BindView(R.id.m_layout_comment)
    LinearLayout mLayoutComment;
    @BindView(R.id.m_layout_after_sale)
    LinearLayout mLayoutAfterSale;
    @BindView(R.id.m_layout_my_collection)
    LinearLayout mLayoutMyCollection;
    @BindView(R.id.m_layout_footprint)
    LinearLayout mLayoutfootPrint;
    @BindView(R.id.m_layout_feedback)
    LinearLayout mLayoutfeedback;
    @BindView(R.id.m_layout_my_order)
    RelativeLayout mLayoutMyOrder;
    @BindView(R.id.m_layout_member_center)
    RelativeLayout mLayoutMemberCenter;
    @BindView(R.id.m_layout_common_problem)
    LinearLayout mLayoutCommonProblem;
    @BindView(R.id.m_layout_customer_service_secret)
    LinearLayout mLayoutCustomerServiceSecret;
    private String fudouBalance;
    private int index = Constant.INTENT_CODE_IMG_SELECTED_DEFAULT;
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        fragment.setArguments(null);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.m_iv_set, R.id.m_layout_fudou_banlance, R.id.m_layout_fudou_recharge, R.id.m_layout_transfer_fudou, R.id.m_layout_my_order, R.id.m_layout_pending_payment,
            R.id.m_layout_delivered, R.id.m_layout_pending_receipt, R.id.m_layout_comment, R.id.m_layout_after_sale,
            R.id.m_layout_my_collection, R.id.m_layout_footprint, R.id.m_layout_feedback, R.id.m_layout_common_problem, R.id.m_layout_customer_service_secret, R.id.m_layout_member_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_set:   // 设置
                SetActivity.openActivity(mContext);
                break;
            case R.id.m_layout_fudou_banlance:   // 福豆余额
                FudouBanlanceActivity.openActivity(mContext, fudouBalance);
                break;
            case R.id.m_layout_fudou_recharge:   // 福豆充值
                FudouRechargeActivity.openActivity(mContext);
                break;
            case R.id.m_layout_transfer_fudou:   // 转赠福豆
                FudouTransferActivity.openActivity(mContext);
                break;
            case R.id.m_layout_my_order:   // 查看全部订单
                MyOrderActivity.openActivity(mContext, "0");
                break;
            case R.id.m_layout_pending_payment:   // 待付款
                MyOrderActivity.openActivity(mContext, "1");
                break;
            case R.id.m_layout_delivered:   // 待发货
                MyOrderActivity.openActivity(mContext, "2");
                break;
            case R.id.m_layout_pending_receipt:   // 待收货
                MyOrderActivity.openActivity(mContext, "3");
                break;
            case R.id.m_layout_comment:   // 待评价
                MyOrderActivity.openActivity(mContext, "4");
                break;
            case R.id.m_layout_after_sale:   // 售后
                AfterSaleActivity.openActivity(mContext);
                break;
            case R.id.m_layout_my_collection:   // 我的收藏
                MyCollectionActivity.openActivity(mContext);
                break;
            case R.id.m_layout_footprint:   // 足迹
                FootPrintActivity.openActivity(mContext);
                break;
            case R.id.m_layout_feedback:   // 意见反馈
                FeedbackActivity.openActivity(mContext);
                break;
            case R.id.m_layout_common_problem:   // 常见问题
                CommonProblemActivity.openActivity(mContext);
                break;
            case R.id.m_layout_customer_service_secret:   // 客服小秘
                String phoneNumber = UserManager.getPhoneNumber(mContext);
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_CODE_IMG_SELECTED_KEY, index);
                intent.putExtra(Constant.MESSAGE_TO_INTENT_EXTRA, Constant.MESSAGE_TO_AFTER_SALES);
                intent.putExtra("phone", phoneNumber);
                intent.setClass(mContext, LoginKeFu02Activity.class);
                startActivity(intent);
                break;
            case R.id.m_layout_member_center:   // 会员中心
                MemberCenterActivity.openActivity(mContext);
                break;
        }
    }

    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(mContext);
        OkGo.<AppResponse<UserDoQueryData>>get(Api.USER_DOQUERY)//
                .params("id", userId)
                .execute(new JsonCallBack<AppResponse<UserDoQueryData>>() {
                    @Override
                    public void onSuccess(AppResponse<UserDoQueryData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            UserDoQueryData tempidList = simpleResponseAppResponse.getData();
                            initDealUser(tempidList);
                        }
                    }
                });
    }

    /**
     * 处理个人信息数据
     *
     * @param data
     */
    private void initDealUser(UserDoQueryData data) {
        //企业数据
        EnteerpriseBean enteerprise = data.getEnterprise();
        // 公司名称
        mTvCompany.setText(enteerprise.getName());
        UserManager.setUserCompany(mContext, enteerprise.getName());
        // 用户数据
        ConsumerBean consumer = data.getConsumer();
        // 头像
        GlideUtils.loadCircleHeadImage(mContext, consumer.getLogo(), R.drawable.ic_all_background, mIvLogo);
        //用户昵称
        mTvName.setText(consumer.getName());
        UserManager.setNickName(mContext, consumer.getName());
        // 用户姓名
        mTvUsername.setText(consumer.getUsername());
        UserManager.setUsername(mContext, consumer.getUsername());
        //用户工号
        mTvJobNumber.setText("工号：" + consumer.getJobNumber());
        UserManager.setJobNumber(mContext, consumer.getJobNumber());
        // 福气值
        mTvBlessing.setText("福气值" + consumer.getBlessing());
        // 福豆余额
        fudouBalance = consumer.getBalance();
        if (!"".equals(consumer.getBalance()) || null != consumer.getBalance()) {
            mTvBalance.setText(DoubleUtil.double2Str(consumer.getBalance()));
        }

    }
}
