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
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.lzy.okgo.OkGo;

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
    /**
     * 跳转二级页面标识
     */
    private String positionSate ;

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
        GlideUtils.loadImage(SelectServiceTypeActivity.this, orderDetailsBean.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
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
                positionSate = "0";
                onFreightCalculation(orderDetailsBean.getCommodity().getSupplierId(), orderDetailsBean.getCommodity().getFreightTemplateName(), orderDetailsBean.getOrderDetail().getNumber());
                break;
            case R.id.m_layout_refund_china_pic:   // 我要退货退款
                positionSate = "1";
                onFreightCalculation(orderDetailsBean.getCommodity().getSupplierId(), orderDetailsBean.getCommodity().getFreightTemplateName(), orderDetailsBean.getOrderDetail().getNumber());
                break;
            case R.id.m_layout_rechange_china_pic:   // 我要换货 申请换货
                ApplyForAReplacementActivity.openActivity(SelectServiceTypeActivity.this, orderDetailsBean, detailsData);
                break;
        }
    }

    /**
     * 单个商品订单详情运费计算
     *
     * @param supplierId 商户id
     * @param name       运费模板名称
     * @param number     商品数量
     */
    private void onFreightCalculation(String supplierId, String name, String number) {
        OkGo.<AppResponse<VcodeLoginData>>get(Api.ORDERS_DOCOUNTFREIGHTPRICE)//
                .params("supplierId", supplierId)
                .params("name", name)
                .params("number", number)
                .execute(new JsonCallBack<AppResponse<VcodeLoginData>>() {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            String freightPrice = simpleResponseAppResponse.getData().getVcode();
                            if ("0".equals(positionSate)) {
                                RequestARefundActivity.openActivity(SelectServiceTypeActivity.this, orderDetailsBean, detailsData, freightPrice);
                            } else {
                                ApplyForAfterSaleActivity.openActivity(SelectServiceTypeActivity.this, orderDetailsBean, detailsData, freightPrice,"SelectServiceTypeActivity");
                            }
                        }
                    }
                });
    }
}
