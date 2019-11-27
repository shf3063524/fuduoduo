package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.tool.GlideUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择服务类型-评价页面
 */
public class SelectServiceType02Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_iv_pop_ups)
    ImageView mIvPopUps;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_layout_rechange_china_pic)
    RelativeLayout mLayoutRechangeChinaPic;
    private OrderDetailsBean orderDetailsBean;
    private ArrayList<DoQueryOrdersDetailsData> detailsData;

    public static void openActivity(Context context, OrderDetailsBean orderDetailsBean, ArrayList<DoQueryOrdersDetailsData> detailsData) {
        Intent intent = new Intent(context, SelectServiceType02Activity.class);
        intent.putExtra("OrderDetailsBean", orderDetailsBean);
        intent.putExtra("detailsData", detailsData);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_service_type02;
    }

    @Override
    protected void initPageData() {
        detailsData = (ArrayList<DoQueryOrdersDetailsData>) getIntent().getSerializableExtra("detailsData");
        orderDetailsBean = (OrderDetailsBean) getIntent().getSerializableExtra("OrderDetailsBean");
        onProcessData(orderDetailsBean);
    }

    /**
     * 数据处理
     *
     * @param orderDetailsBean 商品数据
     */
    private void onProcessData(OrderDetailsBean orderDetailsBean) {
        // 规格图片
        GlideUtils.loadImage(SelectServiceType02Activity.this, orderDetailsBean.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品内容
        mTvContent.setText(orderDetailsBean.getCommodity().getName());
        // 规格
        mTvSpecification.setText(orderDetailsBean.getSpecification().getCommoditySpecification());
    }

    @Override
    protected void initViews() {
        initQQPop();
    }

    @Override
    protected void actionView() {
        mIvPopUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int height = mLayoutSetReturn.getHeight();
                showQQPop(view, height);
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_layout_rechange_china_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_rechange_china_pic:   // 我要换货 申请换货
                ApplyForAReplacementActivity.openActivity(SelectServiceType02Activity.this, orderDetailsBean, detailsData);
                break;
        }
    }
}
