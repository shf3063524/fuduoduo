package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.activity.product.StoreDetailsNoBackgroundActivity;
import com.fdw.fdd.adapter.MyCollectionShoppingAdapter;
import com.fdw.fdd.adapter.MyCollectionStoreAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryCollectionsShopData;
import com.fdw.fdd.entity.bean.DoQueryCollectionsStoreData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.ClearEditText;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;
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
 * 我的收藏页面
 * Author：Created by shihongfei on 2019/10/5 19:20
 * Email：1511808259@qq.com
 */
public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_all_check)
    ImageView mIvAllCheck;
    //    @BindView(R.id.m_layout_empty)
//    FrameLayout mLayoutEmpty;
    @BindView(R.id.m_layout_all_check)
    LinearLayout mLayoutAllCheck;
    @BindView(R.id.m_layout_one)
    LinearLayout mLayoutOne;
    @BindView(R.id.m_layout_two)
    LinearLayout mLayoutTwo;
    @BindView(R.id.m_tv_return)
    TextView mTvReturn;
    @BindView(R.id.m_iv_inquire)
    ImageView mIvInquire;
    @BindView(R.id.m_tv_shopping)
    TextView mTvShopping;
    @BindView(R.id.m_tv_store)
    TextView mTvStore;
    @BindView(R.id.m_tv_finish)
    TextView mTvFinish;
    @BindView(R.id.m_tv_delete)
    TextView mTvDelete;
    @BindView(R.id.m_rl_bottom)
    RelativeLayout mRlBottom;
    @BindView(R.id.m_view_line)
    View mViewLine;
    @BindView(R.id.m_et_shop)
    ClearEditText mEtShop;
    @BindView(R.id.m_recycler_view_shop)
    RecyclerView mRecuclerViewShop;
    @BindView(R.id.m_recycler_view_store)
    RecyclerView mRecuclerViewStore;
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.m_refresh_layout02)
    SmartRefreshLayout mRefreshLayout02;
    @BindView(R.id.m_loading_layout02)
    LoadingLayout mLoadingLayout02;
    @BindColor(R.color.cl_e51C23)
    int cl_e51C23;
    @BindColor(R.color.cl_333)
    int cl_333;
    /**
     * 对管理-完成状态变化设定标签
     */
    private boolean checkFinish = false;
    /**
     * 对商品-店铺状态标签设定
     */
    private boolean check = true;
    /**
     * 全选设定标签状态
     */
    private boolean allCheck = true;
    private ArrayList<DoQueryCollectionsShopData> mShopData;
    private MyCollectionShoppingAdapter mShopAdapter;

    private ArrayList<DoQueryCollectionsStoreData> mStoreData;
    private MyCollectionStoreAdapter mStoreAdapter;
    /**
     * 0商品，1商户
     */
    private String type = "0";
    /**
     * 是否全选 默认未全选
     */
    private boolean isAllCheck = false;
    private String backtitle = "取消", searchtitle = "搜索";
    private String positionState = "1";

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initViews() {
        initRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout02.setEnableRefresh(true);
        mRefreshLayout02.setEnableLoadMore(false);
    }

    private void initLoadingLayout() {
        mLoadingLayout.showEmpty();
//        mLoadingLayout.setEmptyImage(R.drawable.ic_no_address);
        mLoadingLayout.setEmptyText("暂无数据");

        mLoadingLayout02.showEmpty();
//        mLoadingLayout.setEmptyImage(R.drawable.ic_no_address);
        mLoadingLayout02.setEmptyText("暂无数据");
    }

    @Override
    protected void onResume() {
        super.onResume();
        collectionShop("0");
    }

    private void initRecyclerView() {
        mShopData = new ArrayList<>();
        mShopAdapter = new MyCollectionShoppingAdapter(R.layout.item_my_collection_shopping, mShopData);
        LinearLayoutManager layoutManagerShop = new LinearLayoutManager(MyCollectionActivity.this);
        mRecuclerViewShop.setLayoutManager(layoutManagerShop);
        mRecuclerViewShop.setAdapter(mShopAdapter);

        mStoreData = new ArrayList<>();
        mStoreAdapter = new MyCollectionStoreAdapter(R.layout.item_my_collection_store, mStoreData);
        LinearLayoutManager layoutManagerStore = new LinearLayoutManager(MyCollectionActivity.this);
        mRecuclerViewStore.setLayoutManager(layoutManagerStore);
        mRecuclerViewStore.setAdapter(mStoreAdapter);
    }

    @Override
    protected void actionView() {

        mShopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_cv_shop:
                        ProductDetailsActivity.openActivity(MyCollectionActivity.this, mShopAdapter.getData().get(position).getCollections().getId());
                        break;
                }
            }
        });
        mStoreAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_cv_store:
                        StoreDetailsNoBackgroundActivity.openActivity(MyCollectionActivity.this, mStoreData.get(position).getSupplierId());
//                        StoreDetailsActivity.openActivity(MyCollectionActivity.this);
                        break;
                    case R.id.m_iv_delete:
                        new CircleDialog.Builder()
                                .setCanceledOnTouchOutside(false)
                                .setCancelable(false)
                                .setText("确定删除该条店铺信息吗？")
                                .configText(params -> {
                                    // params.gravity = Gravity.LEFT | Gravity.TOP;
                                    params.padding = new int[]{100, 130, 100, 130};
                                })
                                .setNegative("取消", null)
                                .setPositive("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        delete(mStoreData.get(position).getId());
                                    }
                                })
                                .show(getSupportFragmentManager());
                        break;
                }
            }
        });

        mEtShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //如果编辑框中文本的长度大于0就显示删除按钮否则不显示
                if (charSequence.length() > 0) {
                    mTvReturn.setText(searchtitle);
                    String newText = charSequence.toString().trim();
                    if ("1".equals(positionState)) {
                        mShopAdapter.getFilter().filter(newText); // 当数据改变时，调用过滤器；
                    } else if ("2".equals(positionState)) {
                        mStoreAdapter.getFilter().filter(newText); // 当数据改变时，调用过滤器；
                    }

                } else {
                    mTvReturn.setText(backtitle);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                collectionShop("0");
            }
        });
        mRefreshLayout02.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                collectionStore("1");
            }
        });
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_shopping, R.id.m_tv_store, R.id.m_tv_finish, R.id.m_layout_all_check, R.id.m_tv_delete, R.id.m_iv_inquire, R.id.m_tv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_shopping:// 商品
                positionState = "1";
                collectionShop("0");
                check = true;
                mTvShopping.setTextColor(cl_e51C23);
                mTvStore.setTextColor(cl_333);
                mTvShopping.setBackground(getResources().getDrawable(R.drawable.shape_bottom_cl_e51c23));
                mTvStore.setBackgroundColor(getResources().getColor(R.color.cl_fff));
//                mRecuclerViewShop.setVisibility(View.VISIBLE);
//                mRecuclerViewStore.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout02.setVisibility(View.GONE);
                break;
            case R.id.m_tv_store://店铺
                positionState = "2";
                collectionStore("1");
                check = false;
                mTvShopping.setTextColor(cl_333);
                mTvStore.setTextColor(cl_e51C23);
                mTvFinish.setText("管理");
                mRlBottom.setVisibility(View.GONE);
                mViewLine.setVisibility(View.GONE);
                mTvStore.setBackground(getResources().getDrawable(R.drawable.shape_bottom_cl_e51c23));
                mTvShopping.setBackgroundColor(getResources().getColor(R.color.cl_fff));
//                mRecuclerViewShop.setVisibility(View.GONE);
//                mRecuclerViewStore.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.GONE);
                mRefreshLayout02.setVisibility(View.VISIBLE);
                break;
            case R.id.m_tv_finish:// 管理
                if (check) {
                    if (checkFinish) {
                        checkFinish = false;
                        mTvFinish.setText("管理");
                        mRlBottom.setVisibility(View.GONE);
                        mViewLine.setVisibility(View.GONE);
                        for (DoQueryCollectionsShopData data : mShopData) {
                            data.setClickcheck(false);
                        }
                        mShopAdapter.notifyDataSetChanged();
                    } else {
                        checkFinish = true;
                        mTvFinish.setText("完成");
                        mRlBottom.setVisibility(View.VISIBLE);
                        mViewLine.setVisibility(View.VISIBLE);
                        for (DoQueryCollectionsShopData data : mShopData) {
                            data.setClickcheck(true);
                        }
                        mShopAdapter.notifyDataSetChanged();
                    }
                } else {
                    return;
                }
                break;

            case R.id.m_layout_all_check: // 全选
                isAllCheck = !isAllCheck;
                // 修改数据源
                for (DoQueryCollectionsShopData data : mShopData) {
                    data.setCheck(isAllCheck);
                }
                mShopAdapter.notifyDataSetChanged();

                // 根据全选状态修改文本
                if (isAllCheck) {
                    mIvAllCheck.setBackgroundResource(R.drawable.ic_yes_check);
                } else {
                    mIvAllCheck.setBackgroundResource(R.drawable.ic_no_check);
                }
                break;
            case R.id.m_tv_delete: //商品删除
                ArrayList<DoQueryCollectionsShopData> tempList = mShopAdapter.getCheckIdLists();
                StringBuilder ids = new StringBuilder();
                for (int i = 0; i < tempList.size(); i++) {
                    DoQueryCollectionsShopData doQueryCollectionsShopData = tempList.get(i);
                    if (i != tempList.size() - 1) {
                        ids.append(doQueryCollectionsShopData.getCollection().getId()).append(",");
                    } else {
                        ids.append(doQueryCollectionsShopData.getCollection().getId());
                    }
                }
                String memberIds = ids.toString().trim();
                delete(memberIds);
                break;
            case R.id.m_iv_inquire: // 搜索图标
                mLayoutOne.setVisibility(View.GONE);
                mIvInquire.setVisibility(View.GONE);
                mTvFinish.setVisibility(View.GONE);
                mLayoutTwo.setVisibility(View.VISIBLE);
                mTvReturn.setVisibility(View.VISIBLE);
                break;
            case R.id.m_tv_return: // 取消
                mLayoutOne.setVisibility(View.VISIBLE);
                mIvInquire.setVisibility(View.VISIBLE);
                mTvFinish.setVisibility(View.VISIBLE);
                mLayoutTwo.setVisibility(View.GONE);
                mTvReturn.setVisibility(View.GONE);
                mRefreshLayout.autoRefresh();
                break;
        }
    }

    /**
     *
     */
    private void collectionShop(String type) {
        String userId = UserManager.getUserId(MyCollectionActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryCollectionsShopData>>>get(Api.USER_DOQUERYCOLLECTIONS)//
                .params("id", userId)
                .params("type", type)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryCollectionsShopData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryCollectionsShopData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            mShopData.clear();
                            ArrayList<DoQueryCollectionsShopData> tempidList = simpleResponseAppResponse.getData();
                            mShopData.addAll(tempidList);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mShopAdapter.notifyDataSetChanged();
//                        if (mShopData.size() > 0) {
//                            mLayoutEmpty.setVisibility(View.GONE);
//                        } else {
//                            mLayoutEmpty.setVisibility(View.VISIBLE);
//                        }

                        if (mShopData.size() > 0) {
                            mLoadingLayout.showContent();
                        }
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                });
    }

    /**
     * @param type
     */
    private void collectionStore(String type) {
        String userId = UserManager.getUserId(MyCollectionActivity.this);
        OkGo.<AppResponse<ArrayList<DoQueryCollectionsStoreData>>>get(Api.USER_DOQUERYCOLLECTIONS)//
                .params("id", userId)
                .params("type", type)
                .execute(new JsonCallBack<AppResponse<ArrayList<DoQueryCollectionsStoreData>>>() {
                    @Override
                    public void onSuccess(AppResponse<ArrayList<DoQueryCollectionsStoreData>> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ArrayList<DoQueryCollectionsStoreData> tempidList = simpleResponseAppResponse.getData();
                            mStoreData.clear();
                            mStoreData.addAll(tempidList);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mStoreAdapter.notifyDataSetChanged();
//                        if (mStoreData.size() > 0) {
//                            mLayoutEmpty.setVisibility(View.GONE);
//                        } else {
//                            mLayoutEmpty.setVisibility(View.VISIBLE);
//                        }

                        if (mStoreData.size() > 0) {
                            mLoadingLayout02.showContent();
                        }
                        mRefreshLayout02.finishRefresh();
                        mRefreshLayout02.finishLoadMore();
                    }
                });
    }

    /**
     * 删除需要传的id
     *
     * @param id
     */
    private void delete(String id) {
        OkGo.<AppResponse>get(Api.USER_DODELETECOLLECTIONS)//
                .params("ids", id)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        Toasty.normal(MyCollectionActivity.this, "删除成功").show();
                        if ("0".equals(type)) {
                            collectionShop("0");
                        } else {
                            collectionStore("1");
                        }
                    }
                });
    }
}
