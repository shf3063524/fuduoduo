package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.ShoppingFragmentAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DefaultAddressBean;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.bean.DoqueryreturnorderdetailsData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.okgo.JsonCallBack;
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
import ezy.ui.layout.LoadingLayout;

/**
 * 换货详情-换货完成
 */
public class ReplacementCompletedActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_change_to)
    TextView mTvChangeto;
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
    @BindView(R.id.m_tv_remaining_time)
    TextView mTvRemainingTime;
    @BindView(R.id.m_rl_negotiation_history)
    RelativeLayout mRlNegotiationHistory;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_iv_pop_ups)
    ImageView mIvPopUps;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private DoqueryreturnorderdetailsData appResponseData;
    private String orderDetailsId;
    private int startPage = 1;
    // 一次请求多少数据
    private static final int REQUEST_COUNT = 20;

    public static void openActivity(Context context, String orderDetailsId) {
        Intent intent = new Intent(context, ReplacementCompletedActivity.class);
        intent.putExtra("orderDetailsId", orderDetailsId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_replacement_completed;
    }

    @Override
    protected void initPageData() {
        orderDetailsId = getIntent().getStringExtra("orderDetailsId");
        onRefundDetails(orderDetailsId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(ReplacementCompletedActivity.this, cl_e51C23, 1);
        initRecyclerView();
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
        initQQPop();
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
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(ReplacementCompletedActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
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

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(ReplacementCompletedActivity.this, mData.get(position).getCommodity().getId());
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

    @OnClick({R.id.m_iv_arrow, R.id.m_rl_negotiation_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_rl_negotiation_history: // 协商历史
                NegotiationHistoryActivity.openActivity(ReplacementCompletedActivity.this, appResponseData.getReturnOrderDetails().getId(), appResponseData.getCommodity().getSupplierId());

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
        GlideUtils.loadImage(ReplacementCompletedActivity.this, appResponseData.getExchangeSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        //换成
        mTvChangeto.setText("换成：" + appResponseData.getExchangeSpecification().getCommoditySpecification());
        // 商品名称
        mTvContent.setText(appResponseData.getCommodity().getName());
        // 原有
        mTvSpecification.setText("原有：" + appResponseData.getCommoditySpecification().getCommoditySpecification());
        // 换货原因
        mTvExchangeReason.setText("换货原因：" + appResponseData.getReturnOrderDetails().getReturnReason());
        // 换货数量
        mTvExchangeNumber.setText("换货数量：" + appResponseData.getReturnOrderDetails().getNumber());
        // 申请时间：
        mTvApplicationTime.setText("申请时间：" + appResponseData.getReturnOrderDetails().getCreateTime());
        // 换货时间倒计时
        mTvRemainingTime.setText(TimeLeftUtil2.doCalculate(appResponseData.getReturnOrderDetails().getCreateTime()));
        // 换货编号:
        mTvExchangenumbering.setText("换货编号：" + appResponseData.getReturnOrderDetails().getReturnOrderNumber());
        DefaultAddressBean freightAddress = appResponseData.getFreightAddress();
        // 地址
        mTvAddress.setText(freightAddress.getName() + "  " + freightAddress.getMobilephoneNumber() + "  " + freightAddress.getProvince() + freightAddress.getCity() + freightAddress.getArea() + freightAddress.getStreet());

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
