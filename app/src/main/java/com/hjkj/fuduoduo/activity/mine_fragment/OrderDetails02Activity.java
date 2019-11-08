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
import com.hjkj.fuduoduo.adapter.OrderDetails02Adapter;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
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
 * 订单详情-待发货页面
 * Author：Created by shihongfei on 2019/10/9 16:08
 * Email：1511808259@qq.com
 */
public class OrderDetails02Activity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_tv_one)
    TextView mIvOne;
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
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<OrderDetailsBean> mOrderDetailsData;
    private OrderDetails02Adapter mOrderDetails02Adapter;

    private ArrayList<TestBean> mData;
    private ShoppingFragmentAdapter mAdapter;

    public static void openActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetails02Activity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details02;
    }

    @Override
    protected void initPageData() {
        String orderId = getIntent().getStringExtra("orderId");
        orderDetails(orderId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetails02Activity.this, cl_e51C23, 1);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mOrderDetailsData = new ArrayList<>();
        mOrderDetails02Adapter = new OrderDetails02Adapter(R.layout.item_order_details02, mOrderDetailsData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetails02Activity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mOrderDetails02Adapter);

        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetails02Activity.this, 2));
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

        mOrderDetails02Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_tv_refund: // 退款 申请售后
                        ApplyForAfterSaleActivity.openActivity(OrderDetails02Activity.this);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_one, R.id.m_tv_two})
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
        }
    }

    private void orderDetails(String orderId) {
        String userId = UserManager.getUserId(OrderDetails02Activity.this);
        OkGo.<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>get(Api.ORDERS_DOQUERYORDERSDETAILS)//
                .params("id", userId)
                .params("saleState", "2")
                .params("orderId", orderId)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryOrdersDetailsData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryOrdersDetailsData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoQueryOrdersDetailsData> tempList = simpleResponseAppResponse.getData();
                            refreshUi(tempList);
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
//        String freightPrice = order.getFreightPrice();
//        mTvFreight.setText(DoubleUtil.double2Str(freightPrice) + "积分");
        // 合计价格
        String actualPrice = order.getActualPrice();
        mTvTotalPrice.setText(DoubleUtil.double2Str(actualPrice) + "积分");
        // 下单时间
        mTvOrderTime.setText(order.getCreateTime());
        // 订单号
        mTvPayNumber.setText(order.getPayNumber());
        // 支付时间
        mTvPayTime.setText(order.getPayTime());

        ArrayList<OrderDetailsBean> orderDetails = detailsData.get(0).getOrderDetails();
        mOrderDetailsData.clear();
        mOrderDetailsData.addAll(orderDetails);
        mOrderDetails02Adapter.notifyDataSetChanged();
    }
}
