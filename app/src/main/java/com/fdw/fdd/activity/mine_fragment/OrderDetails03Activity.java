package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.OrderDetails03Adapter;
import com.fdw.fdd.adapter.ShoppingFragmentAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DefaultAddressBean;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.DoqueryreturnorderdetailsData;
import com.fdw.fdd.entity.bean.ExpressBean;
import com.fdw.fdd.entity.bean.OrderBean;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.entity.bean.ShopBean;
import com.fdw.fdd.entity.bean.VcodeLoginData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.StatusBarUtil;
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
 * 订单详情-待收货页面
 * Author：Created by shihongfei on 2019/10/9 16:50
 * Email：1511808259@qq.com
 */
public class OrderDetails03Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_tv_one)
    TextView mTvOne;
    @BindView(R.id.m_tv_two)
    TextView mTvTwo;
    @BindView(R.id.m_tv_three)
    TextView mTvThree;
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
    @BindView(R.id.m_tv_delivery_time)
    TextView mTvDeliveryTime;
    @BindView(R.id.m_tv_express_name)
    TextView mTvExpressName;
    @BindView(R.id.m_tv_track_number)
    TextView mTvTrackNumber;
    @BindView(R.id.m_tv_logistics_content)
    TextView mTvLogisticeContent;
    @BindView(R.id.m_tv_logistics_time)
    TextView mTvLogisticeTime;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindView(R.id.m_layout_logistics)
    LinearLayout mLayoutLogistics;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<OrderDetailsBean> mOrderDetailsData;
    private OrderDetails03Adapter mOrderDetailsAdapter;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private ArrayList<ExpressBean> express;
    private ArrayList<DoQueryOrdersDetailsData> detailsData;
    private String orderId;
    private int startPage = 1;
    // 一次请求多少数据
    private static final int REQUEST_COUNT = 20;

    public static void openActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetails03Activity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details03;
    }

    @Override
    protected void initPageData() {
        orderId = getIntent().getStringExtra("orderId");
        orderDetail(orderId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetails03Activity.this, cl_e51C23, 1);
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
        orderDetail(orderId);
        onLove();
    }

    private void initRecyclerView() {
        mOrderDetailsData = new ArrayList<>();
        mOrderDetailsAdapter = new OrderDetails03Adapter(R.layout.item_order_details03, mOrderDetailsData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetails03Activity.this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mOrderDetailsAdapter);

        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetails03Activity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(OrderDetails03Activity.this, mData.get(position).getCommodity().getId());
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
        mOrderDetailsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_tv_refund: // 选择服务类型
                        switch (mOrderDetailsData.get(position).getRefunding()) {
                            case "卖家申请换货":
                                ExchangeDetailsActivity.openActivity(OrderDetails03Activity.this, mOrderDetailsData.get(position).getOrderDetail().getId());
                                break;
                            case "等待商家处理换货申请":
                                ExchangeDetailsActivity.openActivity(OrderDetails03Activity.this, mOrderDetailsData.get(position).getOrderDetail().getId());
                                break;
                            case "换货中":
                                onRefundDetails(mOrderDetailsData.get(position).getOrderDetail().getId(), "换货中");
                                break;
                            case "商家拒绝换货请求":

                                break;
                            case "换货完成":
                                ReplacementCompletedActivity.openActivity(OrderDetails03Activity.this, mOrderDetailsData.get(position).getOrderDetail().getId());
                                break;
                            case "商家发起仅退款":
                                onFreightCalculation(mOrderDetailsData.get(position).getCommodity().getSupplierId(), mOrderDetailsData.get(position).getCommodity().getFreightTemplateName(), mOrderDetailsData.get(position).getOrderDetail().getNumber(), position);
                                break;
                            case "仅退款处理中":
                                onFreightCalculation(mOrderDetailsData.get(position).getCommodity().getSupplierId(), mOrderDetailsData.get(position).getCommodity().getFreightTemplateName(), mOrderDetailsData.get(position).getOrderDetail().getNumber(), position);
                                break;
                            case "商家发起退货退款":
                                onFreightCalculation(mOrderDetailsData.get(position).getCommodity().getSupplierId(), mOrderDetailsData.get(position).getCommodity().getFreightTemplateName(), mOrderDetailsData.get(position).getOrderDetail().getNumber(), position);
                                break;
                            case "退货退款处理中":
                                onFreightCalculation(mOrderDetailsData.get(position).getCommodity().getSupplierId(), mOrderDetailsData.get(position).getCommodity().getFreightTemplateName(), mOrderDetailsData.get(position).getOrderDetail().getNumber(), position);
                                break;
                            case "退款中":
                                onRefundDetails(mOrderDetailsData.get(position).getOrderDetail().getId(), "退款中");
                                break;
                            case "商家拒绝退款":
                                break;
                            case "退款成功":
                                RefundDetailsActivity.openActivity(OrderDetails03Activity.this, mOrderDetailsData.get(position).getOrderDetail().getId());
                                break;
                            case "买家取消":
                                OrderDetails02RefundDetails02Activity.openActivity(OrderDetails03Activity.this, mOrderDetailsData.get(position));
                                break;
                            case "":
                                SelectServiceTypeActivity.openActivity(OrderDetails03Activity.this, mOrderDetailsData.get(position), detailsData);
                                break;
                        }
                        break;
                }
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_one, R.id.m_tv_two, R.id.m_tv_three, R.id.m_layout_logistics})
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
            case R.id.m_tv_two:// 查看物流
                ViewLogisticsActivity.openActivity(OrderDetails03Activity.this, detailsData.get(0).getOrder().getOrderNumber());
                break;
            case R.id.m_tv_three: // 确认收货
                onConfirm(detailsData.get(0).getOrder().getId());
                break;
            case R.id.m_layout_logistics: // 物流信息
                LogisticsInfoActivity.openActivity(OrderDetails03Activity.this, express);
                break;
        }
    }

    private void orderDetail(String orderId) {
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("orderId", orderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            detailsData = simpleResponseAppResponse.getData();
                            refreshUi(detailsData);
                        }
                    }
                });
    }

    /**
     * @param detailsData
     */
    private void refreshUi(ArrayList<DoQueryOrdersDetailsData> detailsData) {
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
        // 订单相关
        OrderBean order = detailsData.get(0).getOrder();
        // 运费
        String freightPrice = order.getFreightPrice();
        mTvFreight.setText(DoubleUtil.double2Str(freightPrice) + "积分");
        // 合计价格
        String actualPrice = order.getActualPrice();
        mTvTotalPrice.setText(DoubleUtil.double2Str(actualPrice) + "积分");
        // 下单时间
        mTvOrderTime.setText(order.getCreateTime());
        // 订单号
        mTvPayNumber.setText(order.getOrderNumber());
        // 物流编号
        mTvTrackNumber.setText(detailsData.get(0).getFreight().getFreightNumber());
        // 支付时间
        mTvPayTime.setText(order.getPayTime());
        // 快递名称
        mTvExpressName.setText(detailsData.get(0).getFreight().getFreightCompany());
        // 发货时间
        mTvDeliveryTime.setText(detailsData.get(0).getFreight().getCreateTime());

        ArrayList<OrderDetailsBean> orderDetails = detailsData.get(0).getOrderDetails();
        mOrderDetailsData.clear();
        mOrderDetailsData.addAll(orderDetails);
        mOrderDetailsAdapter.notifyDataSetChanged();
        // 物流信息
        express = detailsData.get(0).getExpress();
        // 物流内容
        mTvLogisticeContent.setText(express.get(0).getContext());
        // 物流时间
        mTvLogisticeTime.setText(express.get(0).getTime());
    }

    /**
     * 单个商品订单详情运费计算
     *
     * @param supplierId 商户id
     * @param name       运费模板名称
     * @param number     商品数量
     * @param position
     */
    private void onFreightCalculation(String supplierId, String name, String number, int position) {
        OkGo.<AppResponse<VcodeLoginData>>get(Api.ORDERS_DOCOUNTFREIGHTPRICE)//
                .params("supplierId", supplierId)
                .params("name", name)
                .params("number", number)
                .execute(new JsonCallBack<AppResponse<VcodeLoginData>>() {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            String freightPrice = simpleResponseAppResponse.getData().getVcode();
                            OrderDetails02RefundDetailsActivity.openActivity(OrderDetails03Activity.this, mOrderDetailsData.get(position), detailsData, freightPrice, "OrderDetails03Activity");
                        }
                    }
                });
    }


    /**
     * 查询退货订单详情
     */
    private void onRefundDetails(String orderDetailsId, String jumpKey) {
        OkGo.<AppResponse<DoqueryreturnorderdetailsData>>get(Api.ORDERS_DOQUERYRETURNORDERDETAILS)//
                .params("orderDetailsId", orderDetailsId) //订单详情
                .execute(new DialogCallBack<AppResponse<DoqueryreturnorderdetailsData>>(this, "") {
                    @Override
                    public void onSuccess(AppResponse<DoqueryreturnorderdetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DoqueryreturnorderdetailsData appResponseData = simpleResponseAppResponse.getData();
                            if ("1".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails02Activity.openActivity(OrderDetails03Activity.this, orderDetailsId, jumpKey);
                            } else if ("2".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails03Activity.openActivity(OrderDetails03Activity.this, orderDetailsId, jumpKey);
                            } else if ("3".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails04Activity.openActivity(OrderDetails03Activity.this, orderDetailsId);
                            } else if ("4".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails05Activity.openActivity(OrderDetails03Activity.this, orderDetailsId);
                            }
                        }
                    }
                });
    }

    /**
     * 订单确认收货
     */
    private void onConfirm(String id) {
        OkGo.<AppResponse>get(Api.ORDERS_DORECEIVE)//
                .params("id", id) // 订单id
                .params("saleState", "4") //交易状态
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.info(OrderDetails03Activity.this, "确认收货成功").show();
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


