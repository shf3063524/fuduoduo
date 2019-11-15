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

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.OrderDetailsAdapter;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.dialog.CancelOrderDialog;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.bean.OrderBean;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.entity.bean.ShopBean;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.tool.TimeLeftUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

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
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<OrderDetailsBean> mOrderDetailsData;
    private OrderDetailsAdapter mOrderDetailsAdapter;

    private ArrayList<TestBean> mData;
    private ShoppingFragmentAdapter mAdapter;
    private ArrayList<DoQueryOrdersDetailsData> responseData;

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
        initRecyclerView();
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
        mData.clear();
        for (int i = 0; i < 10; i++) {
            mData.add(new TestBean("item" + i));
        }
        myScrollView.smoothScrollTo(0, 20);
        mAdapter.notifyDataSetChanged();
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
                Toasty.info(this, "联系卖家").show();
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
                Toasty.info(this, "立即支付").show();
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
}
