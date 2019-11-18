package com.hjkj.fuduoduo.activity.mine_fragment;

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
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.adapter.OrderDetails04Adapter;
import com.hjkj.fuduoduo.adapter.ShoppingFragmentAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DefaultAddressBean;
import com.hjkj.fuduoduo.entity.bean.DoFindMaybeYouLikeData;
import com.hjkj.fuduoduo.entity.bean.DoQueryOrdersDetailsData;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnorderdetailsData;
import com.hjkj.fuduoduo.entity.bean.ExpressBean;
import com.hjkj.fuduoduo.entity.bean.OrderBean;
import com.hjkj.fuduoduo.entity.bean.OrderDetailsBean;
import com.hjkj.fuduoduo.entity.bean.ShopBean;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.SpaceItemDecoration;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 订单详情-待评价页面
 * Author：Created by shihongfei on 2019/10/10 09:48
 * Email：1511808259@qq.com
 */
public class OrderDetails04Activity extends BaseActivity {
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
    @BindView(R.id.m_tv_logistics_content)
    TextView mTvLogisticeContent;
    @BindView(R.id.m_tv_logistics_time)
    TextView mTvLogisticeTime;
    @BindView(R.id.m_layout_logistics)
    LinearLayout mLayoutLogistics;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_scrollview)
    ScrollView myScrollView;
    @BindView(R.id.m_love_recycler_view)
    RecyclerView mLoveRecyclerView;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    private ArrayList<OrderDetailsBean> mOrderDetailsData;
    private OrderDetails04Adapter mOrderDetailsAdapter;

    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private ArrayList<DoQueryOrdersDetailsData> responseData;
    private ArrayList<ExpressBean> express;

    public static void openActivity(Context context, String orderId) {
        Intent intent = new Intent(context, OrderDetails04Activity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details04;
    }

    @Override
    protected void initPageData() {
        String orderId = getIntent().getStringExtra("orderId");
        orderDetails(orderId);
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setColor(OrderDetails04Activity.this, cl_e51C23, 1);
        initRecyclerView();
        onLove();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLove();
    }

    private void initRecyclerView() {
        mOrderDetailsData = new ArrayList<>();
        mOrderDetailsAdapter = new OrderDetails04Adapter(R.layout.item_order_details04, mOrderDetailsData);
        // mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        // mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetails04Activity.this);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mOrderDetailsAdapter);

        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        mLoveRecyclerView.setNestedScrollingEnabled(false);
        mLoveRecyclerView.setLayoutManager(new GridLayoutManager(OrderDetails04Activity.this, 2));
        mLoveRecyclerView.addItemDecoration(new SpaceItemDecoration(2, 12, false));

        mLoveRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(OrderDetails04Activity.this, mData.get(position).getCommodity().getId());
            }
        });

        mOrderDetailsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_tv_refund: // 选择服务类型
                        switch (mOrderDetailsData.get(position).getRefunding()) {
                            case "卖家申请换货":
                                ExchangeDetailsActivity.openActivity(OrderDetails04Activity.this, mOrderDetailsData.get(position).getOrderDetail().getId());
                                break;
                            case "等待商家处理换货申请":
                                ExchangeDetailsActivity.openActivity(OrderDetails04Activity.this, mOrderDetailsData.get(position).getOrderDetail().getId());
                                break;
                            case "换货中":
                                onRefundDetails(mOrderDetailsData.get(position).getOrderDetail().getId(), "换货中");
                                break;
                            case "商家拒绝换货请求":
                                break;
                            case "换货完成":
                                ReplacementCompletedActivity.openActivity(OrderDetails04Activity.this, mOrderDetailsData.get(position).getOrderDetail().getId());
                                break;
                            case "买家取消":
                                break;
                            case "":
                                SelectServiceType02Activity.openActivity(OrderDetails04Activity.this, mOrderDetailsData.get(position), responseData);
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
            case R.id.m_tv_two: // 查看物流
                ViewLogisticsActivity.openActivity(OrderDetails04Activity.this, responseData.get(0).getOrder().getOrderNumber());
                break;
            case R.id.m_tv_three: // 评价-发表评价
                PostEvaluationActivity.openActivity(OrderDetails04Activity.this, responseData);
                break;
            case R.id.m_layout_logistics: // 物流信息
                LogisticsInfoActivity.openActivity(OrderDetails04Activity.this, express);
                break;
        }
    }

    private void orderDetails(String orderId) {
        String userId = UserManager.getUserId(OrderDetails04Activity.this);
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
        String freightPrice = order.getFreightPrice();
        mTvFreight.setText(DoubleUtil.double2Str(freightPrice) + "积分");
        // 合计价格
        String actualPrice = order.getActualPrice();
        mTvTotalPrice.setText(DoubleUtil.double2Str(actualPrice) + "积分");
        // 下单时间
        mTvOrderTime.setText(order.getCreateTime());
        // 订单号
        mTvPayNumber.setText(order.getPayNumber());
        // 支付时间
        mTvPayTime.setText(order.getPayTime());
        // 快递名称
        mTvExpressName.setText(detailsData.get(0).getFreight().getFreightCompany());
        // 发货时间
        mTvDeliveryTime.setText(detailsData.get(0).getFreight().getCreateTime());
        // 物流信息
        express = detailsData.get(0).getExpress();
        // 物流内容
        mTvLogisticeContent.setText(express.get(0).getContext());
        // 物流时间
        mTvLogisticeTime.setText(express.get(0).getTime());


        ArrayList<OrderDetailsBean> orderDetails = detailsData.get(0).getOrderDetails();
        mOrderDetailsData.clear();
        mOrderDetailsData.addAll(orderDetails);
        mOrderDetailsAdapter.notifyDataSetChanged();
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
                                ExchangeDetails02Activity.openActivity(OrderDetails04Activity.this, orderDetailsId, jumpKey);
                            } else if ("2".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails03Activity.openActivity(OrderDetails04Activity.this, orderDetailsId, jumpKey);
                            } else if ("3".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails04Activity.openActivity(OrderDetails04Activity.this, orderDetailsId);
                            } else if ("4".equals(appResponseData.getReturnOrderDetails().getReturnFreightType())) {
                                ExchangeDetails05Activity.openActivity(OrderDetails04Activity.this, orderDetailsId);
                            }
                        }
                    }
                });
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
