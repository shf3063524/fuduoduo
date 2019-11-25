package com.fdw.fdd.fragment;

import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.activity.shop_fragment.ConfirmOrder02Activtiy;
import com.fdw.fdd.adapter.ShopcartExpandableListViewAdapter;
import com.fdw.fdd.adapter.ShoppingFragmentAdapter;
import com.fdw.fdd.base.BaseFragment;
import com.fdw.fdd.dialog.ShoppingSortDialog;
import com.fdw.fdd.entity.bean.CartBean;
import com.fdw.fdd.entity.bean.CartDoQueryData;
import com.fdw.fdd.entity.bean.DoFindMaybeYouLikeData;
import com.fdw.fdd.entity.bean.GroupInfo;
import com.fdw.fdd.entity.bean.OrdersDoConfirmOrdersData;
import com.fdw.fdd.entity.bean.ProductInfo;
import com.fdw.fdd.entity.bean.ShopBean;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.SuperExpandableListView;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 购物车Fragment
 * Author：Created by shihongfei on 2019/9/24 14:35
 * Email：1511808259@qq.com
 */
public class ShoppingFragment extends BaseFragment implements ShopcartExpandableListViewAdapter.CheckInterface, ShopcartExpandableListViewAdapter.ModifyCountInterface {
    @BindView(R.id.exListView)
    SuperExpandableListView exListView;
    @BindView(R.id.all_chekbox)
    CheckBox cb_check_all;
    @BindView(R.id.m_tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.m_tv_delete)
    TextView mTvDelete;
    @BindView(R.id.m_tv_edit)
    TextView mTvEdit;
    @BindView(R.id.m_tv_unit)
    TextView mTvUnit;
    @BindView(R.id.m_tv_name)
    TextView mTvName;
    @BindView(R.id.m_tv_go_to_pay)
    TextView mTvGoToPay;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private ShopcartExpandableListViewAdapter selva;
    private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
    private Map<String, List<ProductInfo>> children = new HashMap<>();// 子元素数据列表

    private List<CartDoQueryData> mDatas = new ArrayList<>();
    private ArrayList<DoFindMaybeYouLikeData> mData;
    private ShoppingFragmentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FrameLayout mLayoutEmpty;
    private ArrayList<String> deleteLists = new ArrayList<>();
    //删除下标
    private int deletesIndex = 0;

    public static ShoppingFragment newInstance() {
        ShoppingFragment fragment = new ShoppingFragment();
        fragment.setArguments(null);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_shopping;
    }

    @Override
    protected void initViews() {
        initEvents();
        initRecyclerView();
        mTvEdit.setText("编辑");
        onLove();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
        mDatas.clear();
        selva.notifyDataSetChanged();
    }

    private void initRecyclerView() {

        //把RecyclerView添加进去尾部
        LayoutInflater infla = LayoutInflater.from(mContext);
        View footView = infla.inflate(R.layout.add_exlistview_button, null);
        mRecyclerView = (RecyclerView) footView.findViewById(R.id.m_recycler_view);
        mLayoutEmpty = (FrameLayout) footView.findViewById(R.id.m_layout_empty);
        exListView.addFooterView(footView);
        mData = new ArrayList<>();
        mAdapter = new ShoppingFragmentAdapter(R.layout.item_shopping_fragment, mData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initEvents() {
//        selva = new ShopcartExpandableListViewAdapter(groups, children, mContext);
        selva = new ShopcartExpandableListViewAdapter(getActivity(), mDatas);
        selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
        selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        exListView.setAdapter(selva);

        for (int i = 0; i < selva.getGroupCount(); i++) {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }
        //全选的选中事件；
        cb_check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                doCheckAll(b);
            }
        });

    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(mContext, mData.get(position).getCommodity().getId());
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
                            if (unbinder != null) {
                                mData.addAll(data);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {

        CartDoQueryData cartDoQueryData = mDatas.get(groupPosition);
        ShopBean shop = cartDoQueryData.getShop();
        ArrayList<CartBean> cartBeans = new ArrayList<>();
        ShopBean shopBean = new ShopBean();
        CartDoQueryData cartDoQueryDatas = new CartDoQueryData();
        for (CartBean cartBean : cartDoQueryData.getCart()) {
            CartBean carts = new CartBean();
            carts = cartBean;
            //如果被选中，那么所有的子view都被选中
            carts.setChoosed(isChecked);
            cartBeans.add(carts);
        }
        //父VIew也被选中
        shopBean = shop;
        shopBean.setChoosed(isChecked);
        cartDoQueryDatas = cartDoQueryData;
        cartDoQueryDatas.setShop(shopBean);
        cartDoQueryDatas.setCart(cartBeans);
        mDatas.set(groupPosition, cartDoQueryDatas);

        //当父View有一个未被选中的时候，全选一定不被选中
        int parentNum = 0;
        for (CartDoQueryData data : mDatas) {
            if (data.getShop().isChoosed()) {
                parentNum++;
            }
        }
        if (parentNum > 0) {

        } else {
            doCheckAll(false);
        }

        selva.notifyDataSetChanged();
        calculate();

    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        //如果有一个子View被选中，那么父View也要被选中

        //获取父数据
        ShopBean shop = mDatas.get(groupPosition).getShop();

        CartDoQueryData cartDoQueryDatas = mDatas.get(groupPosition);

        CartDoQueryData cartDoQueryData = new CartDoQueryData();

        int checkNum = 0;
        for (CartBean cartBean : cartDoQueryDatas.getCart()) {
            if (cartBean.isChoosed()) {
                checkNum++;
            }
        }
        //如果子View存在選中，那麽父VIew一定被选中
        if (checkNum > 0) {
            shop.setChoosed(true);
            cartDoQueryData = cartDoQueryDatas;
            cartDoQueryData.setShop(shop);
        } else {
            shop.setChoosed(false);
            cartDoQueryData = cartDoQueryDatas;
            cartDoQueryData.setShop(shop);
            //当父View有一个未被选中的时候，全选一定不被选中
            doCheckAll(false);
        }
        mDatas.set(groupPosition, cartDoQueryData);
        selva.notifyDataSetChanged();
        calculate();

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CartBean product = (CartBean) selva.getChild(groupPosition, childPosition);
        //产品库存
        int productCount = Integer.valueOf(TextUtils.isEmpty(product.getCommoditySpecification().getNumber()) ? "0" : product.getCommoditySpecification().getNumber());
        //用户当前产品数量
        int currentNum = Integer.valueOf(TextUtils.isEmpty(product.getCart().getNumber()) ? "0" : product.getCart().getNumber());
        //如果没超过库存，那么就增加
        if (currentNum <= productCount) {
            currentNum++;
        } else {
            Toasty.info(mContext, "已经是最大产品数量了", 1500).show();
        }

        product.getCart().setNumber(currentNum + "");
        ((TextView) showCountView).setText(currentNum + "");

        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        CartBean product = (CartBean) selva.getChild(groupPosition, childPosition);
        //产品库存
        int productCount = Integer.valueOf(TextUtils.isEmpty(product.getCommoditySpecification().getNumber()) ? "0" : product.getCommoditySpecification().getNumber());
        //用户当前产品数量
        int currentNum = Integer.valueOf(TextUtils.isEmpty(product.getCart().getNumber()) ? "0" : product.getCart().getNumber());
        //如果没超过库存，那么就增加
        if (currentNum > 1) {
            currentNum--;
        } else {
            Toasty.info(mContext, "已经是最小产品数量了", 1500).show();
        }

        product.getCart().setNumber(currentNum + "");
        ((TextView) showCountView).setText(currentNum + "");

        selva.notifyDataSetChanged();
        calculate();
    }

    @OnClick({R.id.m_tv_go_to_pay, R.id.m_tv_delete, R.id.m_tv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.m_tv_go_to_pay:
                if (totalCount == 0) {
                    Toast.makeText(mContext, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
//                new CircleDialog.Builder()
//                        .setCanceledOnTouchOutside(false)
//                        .setCancelable(false)
//                        .setTitle("")
//                        .setText("总计:\n" + totalCount + "种商品\n" + totalPrice + "积分")
//                        .configText(params -> {
//                            // params.gravity = Gravity.LEFT | Gravity.TOP;
//                            params.padding = new int[]{100, 0, 100, 50};
//                        })
//                        .setNegative("取消", null)
//                        .setPositive("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                onOrders();
//                            }
//                        })
//                        .show(getChildFragmentManager());
                new ShoppingSortDialog(mContext, mDatas)
                        .setListener(new ShoppingSortDialog.OnClickListener() {
                            @Override
                            public void onClick(String type) {
                                if (type == null || type.isEmpty()) {
                                    Toasty.info(mContext, "您没有可以进行结算的商品").show();
                                    return;
                                }
                                onOrders(type);
                            }
                        }).show();
                break;
            case R.id.m_tv_delete:
                if (totalCount == 0) {
                    Toast.makeText(mContext, "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setTitle("")
                        .setText("您确定要将这些商品从购物车中移除吗?")
                        .configText(params -> {
                            // params.gravity = Gravity.LEFT | Gravity.TOP;
                            params.padding = new int[]{100, 0, 100, 50};
                        })
                        .setNegative("取消", null)
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                doDelete();
                            }
                        })
                        .show(getChildFragmentManager());
                break;
            case R.id.m_tv_edit: //编辑
                if ("编辑".equals(getTextString(mTvEdit))) {
                    mTvEdit.setText("完成");
                    mTvName.setVisibility(View.GONE);
                    mTvUnit.setVisibility(View.GONE);
                    mTvTotalPrice.setVisibility(View.GONE);
                    mTvGoToPay.setVisibility(View.GONE);
                    mTvDelete.setVisibility(View.VISIBLE);

                } else {
                    mTvEdit.setText("编辑");
                    mTvName.setVisibility(View.VISIBLE);
                    mTvUnit.setVisibility(View.VISIBLE);
                    mTvTotalPrice.setVisibility(View.VISIBLE);
                    mTvGoToPay.setVisibility(View.VISIBLE);
                    mTvDelete.setVisibility(View.GONE);
                }
                break;
        }
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    private void doDelete() {
        //遍历整个列表，找出已选中的数据
        //存放选中的id集合
        for (CartDoQueryData data : mDatas) {
            for (int i = 0; i < data.getCart().size(); i++) {
                CartBean cartBean = data.getCart().get(i);
                if (cartBean.isChoosed()) {
                    //如果被选中;那么存ID
                    String id = cartBean.getCart().getId();
                    deleteLists.add(id);
                }
            }
        }

        deletesIndex = 0;
        //依次删除
        for (String deleteList : deleteLists) {
            delete(deleteList, deleteLists.size());
        }
        calculate();
    }

    /**
     * 全选与反选
     *
     * @param checked
     */
    private void doCheckAll(boolean checked) {
        if (mDatas != null && mDatas.size() > 0) {
            for (CartDoQueryData data : mDatas) {
                //父VIew选中
                ShopBean shop = data.getShop();
                if (shop != null) {
                    shop.setChoosed(checked);
                }
                //子View选中
                ArrayList<CartBean> dataCart = data.getCart();
                if (dataCart != null) {
                    for (CartBean cartBean : dataCart) {
                        cartBean.setChoosed(checked);
                    }
                }
            }
        }
        selva.notifyDataSetChanged();
        calculate();
    }


    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (CartDoQueryData data : mDatas) {
            for (CartBean cartBean : data.getCart()) {
                if (cartBean.isChoosed()) {
                    totalCount++;
//                    totalPrice += Double.valueOf(TextUtils.isEmpty(DoubleUtil.double2Str(cartBean.getCommodity().getPrice())) ? "0.00" : cartBean.getCommodity().getPrice()) * Double.valueOf(TextUtils.isEmpty(cartBean.getCart().getNumber()) ? "0" : cartBean.getCart().getNumber());
                    totalPrice += Double.parseDouble(TextUtils.isEmpty(cartBean.getCommoditySpecification().getSalePrice()) ? "0.00" : cartBean.getCommoditySpecification().getSalePrice()) * Double.parseDouble(TextUtils.isEmpty(cartBean.getCart().getNumber()) ? "0" : cartBean.getCart().getNumber());
                }
            }
        }

        mTvTotalPrice.setText("" + DoubleUtil.double2Str(String.valueOf(totalPrice)));
        mTvGoToPay.setText("去支付(" + totalCount + ")");
    }

    @Override
    protected void requestData() {
        String userId = UserManager.getUserId(mContext);
        OkGo.<AppResponse<ArrayList<CartDoQueryData>>>get(Api.CART_DOQUERY)//
                .params("consumerId", userId)
                .execute(new JsonCallBack<AppResponse<ArrayList<CartDoQueryData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<CartDoQueryData>> simpleResponseAppResponse) {
                        mDatas.clear();
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<CartDoQueryData> tempList = simpleResponseAppResponse.getData();
                            if (tempList != null) {
                                Log.e("出数据了", tempList.toString());
                                mDatas.addAll(tempList);
                            }
                        }
                        selva.notifyDataSetChanged();

                        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < mDatas.size(); i++) {
                                    exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
                                }
                            }
                        });


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (mDatas.size() > 0) {
                            mLayoutEmpty.setVisibility(View.GONE);
                        } else {
                            mLayoutEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 删除购物车商品接口
     *
     * @param id
     * @param nums
     */
    private void delete(String id, int nums) {

        OkGo.<AppResponse>get(Api.CART_DODELETE)//
                .params("ids", id)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    deletesIndex++;
                                    if (deletesIndex == nums) {
                                        //重新刷新购物车列表
                                        requestData();
                                    }
                                }
                            });
                        }
                    }
                });
    }

    /**
     * 购物车多规格确认订单接口上传参数
     *
     * @param type
     */
    private void onOrders(String type) {
        String userId = UserManager.getUserId(mContext);
        OkGo.<AppResponse<OrdersDoConfirmOrdersData>>post(Api.ORDERS_DOCONFIRMORDERS)//
                .params("consumerId", userId)
                .params("orderDetails", type)
                .execute(new DialogCallBack<AppResponse<OrdersDoConfirmOrdersData>>(mContext, "") {

                    @Override
                    public void onSuccess(AppResponse<OrdersDoConfirmOrdersData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            OrdersDoConfirmOrdersData responseData = simpleResponseAppResponse.getData();
                            ConfirmOrder02Activtiy.openActivity(mContext, responseData);
                        }
                    }

                });
    }

//    private String getOrderUploadParameter() {
//        ArrayList<UserBillingBean> userBillingBeans = new ArrayList<>();
//        for (CartDoQueryData data : mDatas) {
//            for (CartBean cartBean : data.getCart()) {
//                if (cartBean.isChoosed()) {
//                    String number = cartBean.getCart().getNumber();
//                    String id = cartBean.getCommoditySpecification().getId();
//                    userBillingBeans.add(new UserBillingBean(id, number));
//                }
//            }
//        }
//        return new Gson().toJson(userBillingBeans);
//    }
}


