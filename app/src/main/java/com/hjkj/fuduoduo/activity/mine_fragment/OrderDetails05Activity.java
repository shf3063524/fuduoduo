package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.OrderDetails02Adapter;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnordersData;
import com.hjkj.fuduoduo.entity.bean.OrderBean;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.entity.bean.ReturnDetailsListBean;
import com.hjkj.fuduoduo.entity.bean.ReturnOrderBean;
import com.hjkj.fuduoduo.entity.bean.ShopBean;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;
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
 * 售后-跳转订单详情
 */
public class OrderDetails05Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_head)
    ImageView mIvHead;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_head)
    TextView mTvHead;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_price_num)
    TextView mTvPriceNum;
    @BindView(R.id.m_tv_refund)
    TextView mTvReFund;
    @BindView(R.id.m_tv_one)
    TextView mTvOne;
    @BindView(R.id.m_tv_two)
    TextView mIvTwo;
    @BindView(R.id.m_tv_username)
    TextView mTvUserName;
    @BindView(R.id.m_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.m_tv_address)
    TextView mTvAddress;
    @BindView(R.id.m_tv_store)
    TextView mTvStore;
    @BindView(R.id.m_tv_freight)
    TextView mTvFreight;
    @BindView(R.id.m_tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.m_tv_order_time)
    TextView mTvOrderTime;
    @BindView(R.id.m_tv_pay_number)
    TextView mTvPayNumber;
    @BindView(R.id.m_tv_pay_time)
    TextView mTvPayTime;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    private int startPage = 1;
    // 一次请求多少数据
    private static final int REQUEST_COUNT = 20;
    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private ArrayList<DoqueryreturnordersData> responseData;

    public static void openActivity(Context context, String returnOrderId) {
        Intent intent = new Intent(context, OrderDetails05Activity.class);
        intent.putExtra("returnOrderId", returnOrderId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details05;
    }

    @Override
    protected void initPageData() {
        String returnOrderId = getIntent().getStringExtra("returnOrderId");
        orderDetails(returnOrderId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetails05Activity.this, cl_e51C23, 1);
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
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetails05Activity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(OrderDetails05Activity.this, mData.get(position).getCommodity().getId());
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

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_one, R.id.m_tv_two, R.id.m_tv_refund})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_one: // 联系卖家
                Toasty.info(this, "联系卖家").show();
                break;
            case R.id.m_tv_two: // 提醒发货
                Toasty.info(this, "提醒发货").show();
                break;
            case R.id.m_tv_refund: // 退款中
//                if ("买家取消".equals(responseData.get(0).getRefunding())) {
//                    ApplyForAfterSaleActivity.openActivity(OrderDetails05Activity.this, mOrderDetailsData.get(position), doQueryOrdersDetailsData, freightPrice);
//                } else if ("退款成功".equals(responseData.get(0).getRefunding())) {
//                    RefundDetailsActivity.openActivity(OrderDetails05Activity.this);
//                } else {
//                    OrderDetails05RefundDetailsActivity.openActivity(OrderDetails05Activity.this, mOrderDetailsData.get(position), doQueryOrdersDetailsData, freightPrice);
//                }
                break;
        }
    }

    /**
     * @param returnOrderId 售后订单id
     */
    private void orderDetails(String returnOrderId) {
        String id = UserManager.getUserId(OrderDetails05Activity.this);
        OkGo.<AppResponse<ArrayList<DoqueryreturnordersData>>>get(Api.ORDERS_DOQUERYRETURNORDERS)//
                .params("id", id)
                .params("returnOrderId", returnOrderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoqueryreturnordersData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoqueryreturnordersData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            responseData = simpleResponseAppResponse.getData();
                            refreshUi(responseData);
                        }
                    }
                });
    }

    /**
     * 数据处理
     *
     * @param detailsData
     */
    private void refreshUi(ArrayList<DoqueryreturnordersData> detailsData) {
        if ("仅退款处理中".equals(detailsData.get(0).getRefunding())) {
            mIvHead.setImageDrawable(getResources().getDrawable(R.mipmap.ic_waite_delivered));
            mTvHead.setText("买家已付款");
        } else {
            mIvHead.setImageDrawable(getResources().getDrawable(R.mipmap.ic_success_transaction));
            mTvHead.setText("交易关闭");
        }
        // 地址
        DefaultAddressBean freightAddress = detailsData.get(0).getFreightAddress();
        // 邮寄人姓名
        mTvUserName.setText(freightAddress.getName());
        // 邮寄人电话
        mTvPhone.setText(freightAddress.getMobilephoneNumber());
        // 邮寄人地址
        mTvAddress.setText(freightAddress.getProvince() + freightAddress.getCity() + freightAddress.getArea() + freightAddress.getStreet());
        // 商店名称
        ShopBean shop = detailsData.get(0).getShop();
        mTvStore.setText("  " + shop.getName() + " ");
        ReturnDetailsListBean returnDetailsList = detailsData.get(0).getReturnDetailsList();
        // 商品图片
        GlideUtils.loadImage(OrderDetails05Activity.this, returnDetailsList.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(returnDetailsList.getCommodity().getName());
        // 规格
        mTvSpecification.setText(returnDetailsList.getSpecification().getCommoditySpecification());
        // 积分+数量
        mTvPriceNum.setText(DoubleUtil.double2Str(returnDetailsList.getReturnOrderDetails().getPrice()) + "积分 x" + returnDetailsList.getReturnOrderDetails().getNumber());

        // 订单相关
        ReturnOrderBean returnOrder = detailsData.get(0).getReturnOrder();
        // 运费
        String freightPrice = detailsData.get(0).getFreightPrice();
        if (null == freightPrice || freightPrice.isEmpty()) {
            mTvFreight.setText("0 积分");
        } else {
            mTvFreight.setText(DoubleUtil.double2Str(freightPrice) + "积分");
        }
        // 合计价格
        String actualPrice = returnOrder.getPrice();
        mTvTotalPrice.setText(DoubleUtil.double2Str(actualPrice) + "积分");
        OrderBean orderBean = detailsData.get(0).getOrder();
        // 下单时间
        mTvOrderTime.setText(orderBean.getCreateTime());
        // 订单号
        mTvPayNumber.setText(orderBean.getOrderNumber());
//        // 支付时间
        mTvPayTime.setText(orderBean.getPayTime());
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
