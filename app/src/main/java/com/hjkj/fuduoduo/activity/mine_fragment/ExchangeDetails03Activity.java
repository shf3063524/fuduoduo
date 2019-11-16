package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.dialog.ApplicationCanceledDialog;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnorderdetailsData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.tool.TimeLeftUtil2;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 换货详情-已发货页面
 */
public class ExchangeDetails03Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_change_to)
    TextView mTvChangeto;
    @BindView(R.id.m_tv_time)
    TextView mTvTime;
    @BindView(R.id.m_tv_exchange_reason)
    TextView mTvExchangeReason;
    @BindView(R.id.m_tv_exchange_number)
    TextView mTvExchangeNumber;
    @BindView(R.id.m_tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.m_tv_exchange_numbering)
    TextView mTvExchangenumbering;
    @BindView(R.id.m_tv_address)
    TextView mTvAddress;
    @BindView(R.id.m_cv_negotiation_history)
    CardView mCvNegotiationHistory;
    @BindView(R.id.m_layout_logistics)
    LinearLayout mLayoutLogistics;
    @BindView(R.id.m_tv_application_canceled)
    TextView mTvApplicationCancled;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<TestBean> mData;
    private ShoppingFragmentAdapter mAdapter;

    public static void openActivity(Context context, String orderDetailsId) {
        Intent intent = new Intent(context, ExchangeDetails03Activity.class);
        intent.putExtra("orderDetailsId", orderDetailsId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_exchange_details03;
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(ExchangeDetails03Activity.this, cl_e51C23, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(ExchangeDetails03Activity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        myScrollView.smoothScrollTo(0, 20);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_cv_negotiation_history, R.id.m_tv_application_canceled, R.id.m_layout_logistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_cv_negotiation_history: // 协商历史
//                NegotiationHistoryActivity.openActivity(ExchangeDetails02Activity.this, appResponseData.getReturnOrderDetails().getId(), appResponseData.getCommodity().getSupplierId());
                break;
            case R.id.m_tv_application_canceled: // 撤销申请
//                new ApplicationCanceledDialog(ExchangeDetails02Activity.this)
//                        .setListener(new ApplicationCanceledDialog.OnClickListener() {
//                            @Override
//                            public void onClick() {
//                                onApplicationCanceled();
//                            }
//                        }).show();
                break;
            case R.id.m_layout_logistics: // 物流信息
                ViewLogistics02Activity.openActivity(ExchangeDetails03Activity.this);
                break;
        }
    }
//    /**
//     * 撤销申请接口
//     */
//    private void onApplicationCanceled() {
//        OkGo.<AppResponse>get(Api.ORDERS_DOCANCELRETURNORDER)//
//                .params("id", appResponseData.getReturnOrderDetails().getId()) //退货订单详情id
//                .params("agree", "5") //	撤销申请
//                .execute(new DialogCallBack<AppResponse>(this, "") {
//                    @Override
//                    public void onSuccess(AppResponse simpleResponseAppResponse) {
//                        if (simpleResponseAppResponse.isSucess()) {
//                            finish();
//                        }
//                    }
//                });
//    }

    /**
     * 查询退货订单详情
     */
    private void onRefundDetails(String orderDetailsId) {
        OkGo.<AppResponse<DoqueryreturnorderdetailsData>>get(Api.ORDERS_DOQUERYRETURNORDERDETAILS)//
                .params("orderDetailsId", orderDetailsId) //订单详情
                .execute(new DialogCallBack<AppResponse<DoqueryreturnorderdetailsData>>(this, "") {
                    @Override
                    public void onSuccess(AppResponse<DoqueryreturnorderdetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
//                            appResponseData = simpleResponseAppResponse.getData();
//                            refreshUi(appResponseData);
                        }
                    }
                });
    }

    /**
     * 处理数据
     *
     * @param appResponseData
     */
    private void refreshUi(DoqueryreturnorderdetailsData appResponseData) {
        // 商品图片
        GlideUtils.loadImage(ExchangeDetails03Activity.this, appResponseData.getExchangeSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(appResponseData.getCommodity().getName());
        //换成
        mTvChangeto.setText("换成：" + appResponseData.getExchangeSpecification().getCommoditySpecification());
        // 原有
        mTvSpecification.setText("原有：" + appResponseData.getCommoditySpecification().getCommoditySpecification());
        // 换货原因
        mTvExchangeReason.setText("换货原因：" + appResponseData.getReturnOrderDetails().getReturnReason());
        // 换货数量
        mTvExchangeNumber.setText("换货数量：" + appResponseData.getReturnOrderDetails().getNumber());
        // 申请时间：
        mTvApplicationTime.setText("申请时间：" + appResponseData.getReturnOrderDetails().getCreateTime());
        // 换货时间倒计时
        mTvTime.setText(TimeLeftUtil2.doCalculate(appResponseData.getReturnOrderDetails().getCreateTime()));
        // 换货编号:
        mTvExchangenumbering.setText("换货编号：" + appResponseData.getReturnOrderDetails().getReturnOrderNumber());

        DefaultAddressBean freightAddress = appResponseData.getFreightAddress();
        // 地址
        mTvAddress.setText(freightAddress.getName() + "  " + freightAddress.getMobilephoneNumber() + "  " + freightAddress.getProvince() + freightAddress.getCity() + freightAddress.getArea() + freightAddress.getStreet());
    }
}
