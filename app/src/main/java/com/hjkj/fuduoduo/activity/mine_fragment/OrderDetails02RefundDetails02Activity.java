package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.dialog.ApplicationCanceledDialog;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnorderdetailsData;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
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

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetails02RefundDetails02Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_reason_for_return)
    TextView mTvReasonForReturn;
    @BindView(R.id.m_tv_refund_amount)
    TextView mTvRefundAmount;
    @BindView(R.id.m_tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.m_tv_refund_number)
    TextView mTvRefundNumber;
    @BindView(R.id.m_tv_remaining_time)
    TextView mTvRemainingTime;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;

    public static void openActivity(Context context, OrderDetailsBean orderDetailsBean) {
        Intent intent = new Intent(context, OrderDetails02RefundDetails02Activity.class);
        intent.putExtra("OrderDetailsBean", orderDetailsBean);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details02_refund_details02;
    }

    @Override
    protected void initPageData() {
        OrderDetailsBean orderDetailsBean = (OrderDetailsBean) getIntent().getSerializableExtra("OrderDetailsBean");
        onProcessingData(orderDetailsBean);
    }

    /**
     * 数据处理
     *
     * @param orderDetailsBean
     */
    private void onProcessingData(OrderDetailsBean orderDetailsBean) {
        // 商品图片
        GlideUtils.loadImage(OrderDetails02RefundDetails02Activity.this, orderDetailsBean.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(orderDetailsBean.getCommodity().getName());
        // 商品规格
        mTvSpecification.setText(orderDetailsBean.getSpecification().getCommoditySpecification());
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetails02RefundDetails02Activity.this, cl_e51C23, 1);
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
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetails02RefundDetails02Activity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(OrderDetails02RefundDetails02Activity.this, mData.get(position).getCommodity().getId());
            }
        });
    }


    @OnClick({R.id.m_iv_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
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
                            DoqueryreturnorderdetailsData appResponseData = simpleResponseAppResponse.getData();
                            refreshUi(appResponseData);
                        }
                    }
                });
    }

    /**
     * 接口数据处理
     *
     * @param responseData
     */
    private void refreshUi(DoqueryreturnorderdetailsData responseData) {
        // 退款原因
        mTvReasonForReturn.setText("退款原因：" + responseData.getReturnOrderDetails().getReturnReason());
        // 退款金额
        mTvRefundAmount.setText("退款金额：" + DoubleUtil.double2Str(responseData.getReturnOrder().getTotalPrice()));
        // 申请时间
        mTvApplicationTime.setText("申请时间：" + responseData.getReturnOrderDetails().getCreateTime());
        // 退款编号
        mTvRefundAmount.setText("退款编号：" + responseData.getReturnOrder().getReturnOrderNumber());
        // 还剩余多长时间
        mTvRemainingTime.setText(TimeLeftUtil2.doCalculate(responseData.getReturnOrder().getCreateTime()));
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
