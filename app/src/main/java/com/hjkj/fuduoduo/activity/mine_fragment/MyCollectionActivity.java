package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.activity.product.StoreDetailsActivity;
import com.hjkj.fuduoduo.adapter.MyCollectionShoppingAdapter;
import com.hjkj.fuduoduo.adapter.MyCollectionStoreAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.TestBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsShopData;
import com.hjkj.fuduoduo.entity.bean.DoQueryCollectionsStoreData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.UserManager;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

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
    @BindView(R.id.m_layout_empty)
    FrameLayout mLayoutEmpty;
    @BindView(R.id.m_layout_all_check)
    LinearLayout mLayoutAllCheck;
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
    @BindView(R.id.m_recycler_view_shop)
    RecyclerView mRecuclerViewShop;
    @BindView(R.id.m_recycler_view_store)
    RecyclerView mRecuclerViewStore;
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
        initRecyclerView();
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
                        ProductDetailsActivity.openActivity(MyCollectionActivity.this,mShopData.get(position).getCollections().getId());
                        break;
                }
            }
        });
        mStoreAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.m_cv_store:
                        StoreDetailsActivity.openActivity(MyCollectionActivity.this);
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
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_shopping, R.id.m_tv_store, R.id.m_tv_finish, R.id.m_layout_all_check, R.id.m_tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_shopping:// 商品
                collectionShop("0");
                check = true;
                mTvShopping.setTextColor(cl_e51C23);
                mTvStore.setTextColor(cl_333);
                mTvShopping.setBackground(getResources().getDrawable(R.drawable.shape_bottom_cl_e51c23));
                mTvStore.setBackgroundColor(getResources().getColor(R.color.cl_fff));
                mRecuclerViewShop.setVisibility(View.VISIBLE);
                mRecuclerViewStore.setVisibility(View.GONE);
                break;
            case R.id.m_tv_store://店铺
                collectionStore("1");
                check = false;
                mTvShopping.setTextColor(cl_333);
                mTvStore.setTextColor(cl_e51C23);
                mTvFinish.setText("管理");
                mRlBottom.setVisibility(View.GONE);
                mViewLine.setVisibility(View.GONE);
                mTvStore.setBackground(getResources().getDrawable(R.drawable.shape_bottom_cl_e51c23));
                mTvShopping.setBackgroundColor(getResources().getColor(R.color.cl_fff));
                mRecuclerViewShop.setVisibility(View.GONE);
                mRecuclerViewStore.setVisibility(View.VISIBLE);
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
                            ArrayList<DoQueryCollectionsShopData> tempidList = simpleResponseAppResponse.getData();
                            mShopData.clear();
                            mShopData.addAll(tempidList);
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mShopAdapter.notifyDataSetChanged();
                        if (mShopData.size() > 0){
                            mLayoutEmpty.setVisibility(View.GONE);
                        }else {
                            mLayoutEmpty.setVisibility(View.VISIBLE);
                        }
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
                        if (mStoreData.size() > 0){
                            mLayoutEmpty.setVisibility(View.GONE);
                        }else {
                            mLayoutEmpty.setVisibility(View.VISIBLE);
                        }
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
                        }else {
                            collectionStore("1");
                        }
                    }
                });
    }
}
