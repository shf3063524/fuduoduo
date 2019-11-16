package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.tool.GlideUtils;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择服务类型-待收货页面
 * Author：Created by shihongfei on 2019/10/10 22:44
 * Email：1511808259@qq.com
 */
public class SelectServiceTypeActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_layout_refund)
    RelativeLayout mLayoutRefund;
    @BindView(R.id.m_layout_refund_china_pic)
    RelativeLayout mLayoutRefundChinaPic;
    @BindView(R.id.m_layout_rechange_china_pic)
    RelativeLayout mLayoutRechangeChinaPic;
    private ArrayList<DoQueryOrdersDetailsData> detailsData;
    private OrderDetailsBean orderDetailsBean;

    public static void openActivity(Context context, OrderDetailsBean orderDetailsBean, ArrayList<DoQueryOrdersDetailsData> detailsData) {
        Intent intent = new Intent(context, SelectServiceTypeActivity.class);
        intent.putExtra("OrderDetailsBean", orderDetailsBean);
        intent.putExtra("detailsData", detailsData);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_service_type;
    }

    @Override
    protected void initPageData() {

        detailsData = (ArrayList<DoQueryOrdersDetailsData>) getIntent().getSerializableExtra("detailsData");
        orderDetailsBean = (OrderDetailsBean) getIntent().getSerializableExtra("OrderDetailsBean");
        onProcessData(orderDetailsBean);
    }

    /**
     * 处理数据
     *
     * @param orderDetailsBean
     */
    private void onProcessData(OrderDetailsBean orderDetailsBean) {
        // 商品图片
        GlideUtils.loadImage(SelectServiceTypeActivity.this,orderDetailsBean.getSpecification().getSpecificationImage(),R.drawable.ic_all_background,mIvShopping);
        // 商品名称
        mTvContent.setText(orderDetailsBean.getCommodity().getName());
        // 商品规格
        mTvSpecification.setText(orderDetailsBean.getSpecification().getCommoditySpecification());
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.m_iv_arrow, R.id.m_layout_refund, R.id.m_layout_refund_china_pic, R.id.m_layout_rechange_china_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_refund:   // 我要退款 申请退款
                RequestARefundActivity.openActivity(SelectServiceTypeActivity.this);
                break;
            case R.id.m_layout_refund_china_pic:   // 我要退货退款
                RequestARefund02Activity.openActivity(SelectServiceTypeActivity.this);
                break;
            case R.id.m_layout_rechange_china_pic:   // 我要换货 申请换货
                ApplyForAReplacementActivity.openActivity(SelectServiceTypeActivity.this,orderDetailsBean,detailsData);
                break;
        }
    }
}
