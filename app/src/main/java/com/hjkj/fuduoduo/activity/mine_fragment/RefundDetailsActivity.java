package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.RefundDetailsAdapter;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnorderdetailsData;
import com.hjkj.fuduoduo.entity.bean.FreightMapBean;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
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
 * 退款详情页面
 * Author：Created by shihongfei on 2019/10/10 11:16
 * Email：1511808259@qq.com
 */
public class RefundDetailsActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_exchange_reason)
    TextView mTvExchangeReason;
    @BindView(R.id.m_tv_exchange_number)
    TextView mTvExchangeNumber;
    @BindView(R.id.m_tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.m_tv_exchange_numbering)
    TextView mTvExchangenumbering;
    @BindView(R.id.m_tv_time)
    TextView mTvTime;
    @BindView(R.id.m_tv_price)
    TextView mTvPrice;
    @BindView(R.id.m_tv_price02)
    TextView mTvPrice02;
    @BindView(R.id.m_layout_negotiation_history)
    RelativeLayout mLayoutNegotiationHistory;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private DoqueryreturnorderdetailsData appResponseData;

    public static void openActivity(Context context, String orderDetailsId) {
        Intent intent = new Intent(context, RefundDetailsActivity.class);
        intent.putExtra("orderDetailsId", orderDetailsId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_refund_details;
    }

    @Override
    protected void initPageData() {
        String orderDetailsId = getIntent().getStringExtra("orderDetailsId");
        onRefundDetails(orderDetailsId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(RefundDetailsActivity.this, cl_e51C23, 1);
        initRecyclerView();
        onLove();
    }
    @Override
    protected void onResume() {
        super.onResume();
        onLove();
    }
    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(RefundDetailsActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(RefundDetailsActivity.this, mData.get(position).getCommodity().getId());
            }
        });
    }

    @OnClick({R.id.m_iv_arrow,R.id.m_layout_negotiation_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_negotiation_history: // 协商历史
                NegotiationHistoryActivity.openActivity(RefundDetailsActivity.this, appResponseData.getReturnOrderDetails().getId(), appResponseData.getCommodity().getSupplierId());

                break;
        }
    }

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
                            appResponseData = simpleResponseAppResponse.getData();
                            refreshUi(appResponseData);
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
        GlideUtils.loadImage(RefundDetailsActivity.this, appResponseData.getCommoditySpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(appResponseData.getCommodity().getName());
        // 原有
        mTvSpecification.setText(appResponseData.getCommoditySpecification().getCommoditySpecification());
        // 退款原因
        mTvExchangeReason.setText("退款原因：" + appResponseData.getReturnOrderDetails().getReturnReason());
        // 退款金额
        mTvExchangeNumber.setText("退款金额：" + DoubleUtil.double2Str(appResponseData.getReturnOrder().getPrice()) + "积分");
        mTvPrice.setText(DoubleUtil.double2Str(appResponseData.getReturnOrder().getPrice()));
        mTvPrice02.setText(DoubleUtil.double2Str(appResponseData.getReturnOrder().getPrice()));

        // 申请时间：
        mTvApplicationTime.setText("申请时间：" + appResponseData.getReturnOrderDetails().getCreateTime());
        // 换货时间倒计时
        mTvTime.setText(TimeLeftUtil2.doCalculate(appResponseData.getReturnOrderDetails().getCreateTime()));
        // 退款编号:
        mTvExchangenumbering.setText("退款编号：" + appResponseData.getReturnOrder().getReturnNumber());
    }

    /**
     * 猜你喜欢接口
     */
    private void onLove() {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOFINDMAYBEYOULIKE)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindMaybeYouLikeData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoFindMaybeYouLikeData> data = simpleResponseAppResponse.getData();
                            mData.addAll(data);
                            myScrollView.smoothScrollTo(0, 20);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
