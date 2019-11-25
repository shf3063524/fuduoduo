package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.ShoppingFragmentAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.dialog.ApplicationCanceledDialog;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.bean.DoqueryreturnordersData;
import com.fdw.fdd.entity.bean.ReturnOrderDetailsBean;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.tool.TimeLeftUtil2;
import com.fdw.fdd.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ezy.ui.layout.LoadingLayout;

/**
 * 售后订单-跳转退款详情
 */
public class OrderDetails05RefundDetailsActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_application_canceled)
    TextView mTvApplicationCanceled;
    @BindView(R.id.m_tv_modify_application)
    TextView mTvModifyApplication;
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
    @BindView(R.id.m_cv_negotiation_history)
    CardView mCvNegotiationHistory;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private DoqueryreturnordersData doqueryreturnordersData;
    private int startPage = 1;
    // 一次请求多少数据
    private static final int REQUEST_COUNT = 20;

    /**
     * @param context
     * @param doqueryreturnordersData 单个商品数据
     */
    public static void openActivity(Context context, DoqueryreturnordersData doqueryreturnordersData) {
        Intent intent = new Intent(context, OrderDetails05RefundDetailsActivity.class);
        intent.putExtra("DoqueryreturnordersData", doqueryreturnordersData);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details02_refund_details;
    }

    @Override
    protected void initPageData() {
        doqueryreturnordersData = (DoqueryreturnordersData) getIntent().getSerializableExtra("DoqueryreturnordersData");
        onProcessingData(doqueryreturnordersData);
    }

    private void onProcessingData(DoqueryreturnordersData doqueryreturnordersData) {
        // 商品图片
        GlideUtils.loadImage(OrderDetails05RefundDetailsActivity.this, doqueryreturnordersData.getReturnDetailsList().getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(doqueryreturnordersData.getReturnDetailsList().getCommodity().getName());
        // 商品规格
        mTvSpecification.setText(doqueryreturnordersData.getReturnDetailsList().getSpecification().getCommoditySpecification());

        ReturnOrderDetailsBean orderDetails = doqueryreturnordersData.getReturnDetailsList().getReturnOrderDetails();
        // 退款原因
        mTvReasonForReturn.setText("退款原因：" + orderDetails.getReturnReason());
        // 退款金额
        mTvRefundAmount.setText("退款金额：" + DoubleUtil.double2Str(doqueryreturnordersData.getOrder().getActualPrice()) + "积分");
        // 申请时间
        mTvApplicationTime.setText("申请时间：" + doqueryreturnordersData.getReturnOrder().getCreateTime());
        // 退款编号
        mTvRefundNumber.setText("退款编号：" + doqueryreturnordersData.getReturnOrder().getReturnOrderNumber());
        // 还剩余多长时间
        mTvRemainingTime.setText(TimeLeftUtil2.doCalculate(doqueryreturnordersData.getReturnOrder().getCreateTime()));
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetails05RefundDetailsActivity.this, cl_e51C23, 1);
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(true);
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
//        mLoadingLayout.setEmptyImage(R.drawable.ic_no_address);
        mLoadingLayout.setEmptyText("暂无数据");
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
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetails05RefundDetailsActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(OrderDetails05RefundDetailsActivity.this, mData.get(position).getCommodity().getId());
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mAdapter.notifyDataSetChanged();
                startPage++;
                onLove();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mData.clear();
                startPage = 1;
                onLove();
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_application_canceled, R.id.m_tv_modify_application, R.id.m_cv_negotiation_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_application_canceled: // 撤销申请
                new ApplicationCanceledDialog(OrderDetails05RefundDetailsActivity.this)
                        .setListener(new ApplicationCanceledDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                onApplicationCanceled();
                            }
                        }).show();
                break;
            case R.id.m_tv_modify_application: // 修改申请
                if (doqueryreturnordersData.getReturnDetailsList().getReturnOrderDetails().getCreateTime().equals(doqueryreturnordersData.getReturnDetailsList().getReturnOrderDetails().getUpdateTime())) {
                    ModifyApplication02Activity.openActivity(OrderDetails05RefundDetailsActivity.this, doqueryreturnordersData);
                } else {
                    Toasty.info(OrderDetails05RefundDetailsActivity.this, "抱歉，您目前没有可发起的服务类型").show();
                    return;
                }
                break;
            case R.id.m_cv_negotiation_history: // 协商历史
                NegotiationHistoryActivity.openActivity(OrderDetails05RefundDetailsActivity.this, doqueryreturnordersData.getReturnDetailsList().getReturnOrderDetails().getOrderDetailsId(), doqueryreturnordersData.getReturnDetailsList().getCommodity().getSupplierId());
                break;
        }
    }

    /**
     * 撤销申请接口
     */
    private void onApplicationCanceled() {
        OkGo.<AppResponse>get(Api.ORDERS_DOCANCELRETURNORDER)//
                .params("id", doqueryreturnordersData.getReturnDetailsList().getReturnOrderDetails().getId()) //退货订单详情id
                .params("agree", "5") //	撤销申请
                .execute(new DialogCallBack<AppResponse>(this, "") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            finish();
                        }
                    }
                });
    }

    /**
     * 猜你喜欢接口
     */
    private void onLove() {
        OkGo.<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>get(Api.COMMODITY_DOFINDMAYBEYOULIKE)//
                .params("page", startPage)//
                .params("size", REQUEST_COUNT)//
                .execute(new JsonCallBack<AppResponse<ArrayList<DoFindMaybeYouLikeData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoFindMaybeYouLikeData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mData.clear();
                            ArrayList<DoFindMaybeYouLikeData> data = simpleResponseAppResponse.getData();
                            mData.addAll(data);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        myScrollView.smoothScrollTo(0, 20);
                        mAdapter.notifyDataSetChanged();
                        if (mData.size() > 0) {
                            mLoadingLayout.showContent();
                        }
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                });
    }
}
