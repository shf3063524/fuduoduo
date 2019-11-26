package com.fdw.fdd.activity.mine_fragment;

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
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.PayFailureActivity;
import com.fdw.fdd.activity.product.PaySuccessActivity;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.OrderDetailsAdapter;
import com.fdw.fdd.adapter.ShoppingFragmentAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.dialog.CancelOrderDialog;
import com.fdw.fdd.dialog.ConfirmPaymentDialog;
import com.fdw.fdd.dialog.PayPasswordDialog;
import com.fdw.fdd.entity.bean.DefaultAddressBean;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.OrderBean;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.entity.bean.ShopBean;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.kefu.LoginKeFu02Activity;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.tool.TimeLeftUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.tool.kefutool.Constant;
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
 * 订单详情-待付款页面
 * Author：Created by shihongfei on 2019/10/9 10:48
 * Email：1511808259@qq.com
 */
public class OrderDetailsActivity extends BaseActivity {
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
    @BindView(R.id.m_tv_time)
    TextView mTvTime;
    @BindView(R.id.m_rl_bottom)
    RelativeLayout mRlBottom;
    @BindView(R.id.m_view_line)
    View mViewLine;
    @BindView(R.id.m_iv_head)
    ImageView mIvHead;
    @BindView(R.id.m_tv_head_name)
    TextView mTvHeadName;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<OrderDetailsBean> mOrderDetailsData;
    private OrderDetailsAdapter mOrderDetailsAdapter;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private ArrayList<DoQueryOrdersDetailsData> responseData;

    private int startPage = 1;
    // 一次请求多少数据
    private static final int REQUEST_COUNT = 20;
    private int index = Constant.INTENT_CODE_IMG_SELECTED_DEFAULT;
    public static void openActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initPageData() {
        String orderId = getIntent().getStringExtra("orderId");
        orderDetails(orderId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetailsActivity.this, cl_e51C23, 1);
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLove();
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

    private void initRecyclerView() {
        mOrderDetailsData = new ArrayList<>();
        mOrderDetailsAdapter = new OrderDetailsAdapter(R.layout.item_order_details, mOrderDetailsData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mOrderDetailsAdapter);

        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetailsActivity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(OrderDetailsActivity.this, mData.get(position).getCommodity().getId());
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

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_one, R.id.m_tv_two, R.id.m_tv_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_one: // 联系卖家
                String phoneNumber = UserManager.getPhoneNumber(OrderDetailsActivity.this);
                Intent intent = new Intent();
                intent.putExtra(Constant.INTENT_CODE_IMG_SELECTED_KEY, index);
                intent.putExtra(Constant.MESSAGE_TO_INTENT_EXTRA, Constant.MESSAGE_TO_AFTER_SALES);
                intent.putExtra("phone", phoneNumber);
                intent.setClass(OrderDetailsActivity.this, LoginKeFu02Activity.class);
                startActivity(intent);
                break;
            case R.id.m_tv_two:   // 取消订单
                new CancelOrderDialog(OrderDetailsActivity.this)
                        .setListener(new CancelOrderDialog.OnCancleOrderClickListener() {
                            @Override
                            public void onancleOrderClick(String cancleOrderType) {
                                onCancelOrder(responseData.get(0).getOrder().getId(), responseData.get(0).getOrder().getSupplierId(), cancleOrderType);
                            }
                        }).show();
                break;
            case R.id.m_tv_three: // 立即支付
                new ConfirmPaymentDialog(OrderDetailsActivity.this, DoubleUtil.double2Str(responseData.get(0).getOrder().getActualPrice()))
                        .setListener(new ConfirmPaymentDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                new PayPasswordDialog(OrderDetailsActivity.this)
                                        .setListener(new PayPasswordDialog.OnClickListener() {
                                            @Override
                                            public void onClick(String payPassword) {
                                                ordersDoPay(payPassword, responseData.get(0).getOrder().getPayNumber(),DoubleUtil.double2Str(responseData.get(0).getOrder().getActualPrice()));
                                            }
                                        }).show();
                            }
                        }).show();
                break;
        }
    }

    /**
     * 取消订单
     *
     * @param ordersId     订单id
     * @param supplierId   商户id
     * @param cancelReason 取消原因
     */
    private void onCancelOrder(String ordersId, String supplierId, String cancelReason) {
        String consumerId = UserManager.getUserId(OrderDetailsActivity.this);
        OkGo.<AppResponse>get(Api.ORDERS_DOCANCELORDER)//
                .params("ordersId", ordersId)
                .params("supplierId", supplierId)
                .params("consumerId", consumerId)
                .params("cancelReason", cancelReason)
                .params("type", "0")
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            orderDetails(ordersId);
                            Toasty.info(OrderDetailsActivity.this, "取消订单成功").show();
                        }
                    }
                });
    }

    /**
     * @param orderId
     */
    private void orderDetails(String orderId) {
        String userId = UserManager.getUserId(OrderDetailsActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("id", userId)
                .params("orderId", orderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            responseData = simpleResponseAppResponse.getData();
                            refreshUi(responseData);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    /**
     * 数据处理
     *
     * @param detailsData
     */
    private void refreshUi(ArrayList<DoQueryOrdersDetailsData> detailsData) {
        String orderState = detailsData.get(0).getOrder().getOrderState();
        if ("3".equals(orderState)) {
            mIvHead.setImageDrawable(getResources().getDrawable(R.mipmap.ic_success_transaction));
            mTvHeadName.setText("交易关闭");
            mRlBottom.setVisibility(View.GONE);
            mViewLine.setVisibility(View.GONE);
            mTvTime.setVisibility(View.GONE);
        } else {
            mIvHead.setImageDrawable(getResources().getDrawable(R.mipmap.ic_waite_pay));
            mTvHeadName.setText("等待买家付款");
            mRlBottom.setVisibility(View.VISIBLE);
            mViewLine.setVisibility(View.VISIBLE);
            mTvTime.setVisibility(View.VISIBLE);
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
        // 剩余时间
        String newTime = TimeLeftUtil.doCalculate(order.getCreateTime());
        mTvTime.setText("还剩0小时" + newTime + "分");
        // 订单号
        mTvPayNumber.setText(order.getPayNumber());

        ArrayList<OrderDetailsBean> orderDetails = detailsData.get(0).getOrderDetails();
        mOrderDetailsData.clear();
        mOrderDetailsData.addAll(orderDetails);
        mOrderDetailsAdapter.notifyDataSetChanged();
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

    /**
     * 多订单立即支付
     */
    private void ordersDoPay(String payPassword, String payNumbers,String totalPrice) {
        String id = UserManager.getUserId(OrderDetailsActivity.this);
        Double mul = DoubleUtil.mul(Double.parseDouble(totalPrice), 100.00);
        OkGo.<AppResponse>get(Api.ORDERS_DOPAY)//
                .params("id", id) //
                .params("payPassword", payPassword) //
                .params("actualPrice", DoubleUtil.doubleTransf(mul)) //
                .params("payNumbers", payNumbers) //
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.getState() == 0) {
                            Toasty.info(OrderDetailsActivity.this, simpleResponseAppResponse.getMessage()).show();
                            PayFailureActivity.openActivity(OrderDetailsActivity.this);
                        } else {
                            PaySuccessActivity.openActivity(OrderDetailsActivity.this);
                        }
                    }
                });

    }
}
