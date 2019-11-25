package com.fdw.fdd.activity.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fdw.fdd.R;
import com.fdw.fdd.activity.product.ProductDetailsActivity;
import com.fdw.fdd.adapter.NationalDayAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoqueryhotsaledetailsData;
import com.fdw.fdd.entity.bean.HomePageSortBean;
import com.fdw.fdd.entity.bean.MultipleListBean;
import com.fdw.fdd.entity.bean.SingleListBean;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.StatusBarUtil;
import com.fdw.fdd.view.ObservableScrollView;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 欢度国庆页面
 * Author：Created by shihongfei on 2019/9/26 09:24
 * Email：1511808259@qq.com
 */
public class NationalDayActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_search_black)
    ImageView mIvSearchBlack;
    @BindView(R.id.iv_shopping_cart)
    ImageView mIvShoppingCart;
    @BindView(R.id.divide_line)
    View dividerView;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    @BindView(R.id.iv_header)
    ImageView headerIv;
    @BindView(R.id.m_cv_one)
    CardView mCvOne;
    @BindView(R.id.m_cv_two)
    CardView mCvTwo;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_content02)
    TextView mTvContent02;
    @BindView(R.id.m_tv_price)
    TextView mTvPrice;
    @BindView(R.id.m_tv_price02)
    TextView mTvPrice02;
    @BindView(R.id.m_iv_shopping01)
    ImageView mIvShopping01;
    @BindView(R.id.m_iv_shopping02)
    ImageView mIvShopping02;

    private int imageHeight; //图片高度
    private DoqueryhotsaledetailsData responseData;

    public static void openActivity(Context context, String sortId) {
        Intent intent = new Intent(context, NationalDayActivity.class);
        intent.putExtra("sortId", sortId);
        context.startActivity(intent);
    }

    private ArrayList<MultipleListBean> mData;
    private NationalDayAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_national_day;
    }

    @Override
    protected void initPageData() {
        String sortId = getIntent().getStringExtra("sortId");
        onProcessData(sortId);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, headLayout);
    }

    @Override
    protected void initViews() {
        initListener();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        mAdapter = new NationalDayAdapter(R.layout.item_national_day, mData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(NationalDayActivity.this, 2, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver treeObserver = headerIv.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                headerIv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = headerIv.getHeight();
                scrollView.setScrollViewListener(NationalDayActivity.this);

            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //设置渐变的头部的背景颜色
            headLayout.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            dividerView.setVisibility(View.GONE);
        } else if (y > 0 && y <= imageHeight) {
            //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / imageHeight;
            int alpha = (int) (scale * 255);
            headLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            // mIvBack.getBackground().setAlpha(255 - alpha);
            // mIvShoppingCart.getBackground().setAlpha(255 - alpha);
            // mIvShare.getBackground().setAlpha(255 - alpha);
            if (oldy < y) {
                // 手指向上滑动，屏幕内容下滑
                mIvBack.setImageResource(R.mipmap.ic_return_white);
                mIvSearchBlack.setImageResource(R.mipmap.ic_search_white);
                mIvShoppingCart.setImageResource(R.mipmap.ic_shopping_white);
            } else if (oldy > y) {
                // 手指向下滑动，屏幕内容上滑
                mIvBack.setImageResource(R.mipmap.ic_return_black);
                mIvSearchBlack.setImageResource(R.mipmap.ic_search_black);
                mIvShoppingCart.setImageResource(R.mipmap.ic_shopping_black);
            }

        } else {
            //滑动到banner下面设置普通颜色
            headLayout.setBackgroundColor(Color.WHITE);
            dividerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void actionView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductDetailsActivity.openActivity(NationalDayActivity.this, mData.get(position).getId());
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.m_cv_one, R.id.m_cv_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_cv_one: // 条目一
                ProductDetailsActivity.openActivity(NationalDayActivity.this, responseData.getSingleList().get(0).getId());
                break;
            case R.id.m_cv_two:// 条目二
                ProductDetailsActivity.openActivity(NationalDayActivity.this, responseData.getSingleList().get(1).getId());
                break;
        }
    }

    private void onProcessData(String sortId) {
        OkGo.<AppResponse<DoqueryhotsaledetailsData>>get(Api.HOMEPAGESORT_DOQUERYHOTSALEDETAILS)//
                .params("id", sortId)
                .execute(new JsonCallBack<AppResponse<DoqueryhotsaledetailsData>>() {
                    @Override
                    public void onSuccess(AppResponse<DoqueryhotsaledetailsData> simpleResponseAppResponse) {
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
     * @param responseData
     */
    private void refreshUi(DoqueryhotsaledetailsData responseData) {
        // 头部图片
        HomePageSortBean homePageSort = responseData.getHotSale();
        GlideUtils.loadImage(NationalDayActivity.this, homePageSort.getBackImage(), R.drawable.ic_all_background, headerIv);
        // 下面两个商品
        ArrayList<SingleListBean> singleList = responseData.getSingleList();
        // 第一件商品名称
        mTvContent.setText(singleList.get(0).getName());
        // 第一件商品价格
        mTvPrice.setText(DoubleUtil.double2Str(singleList.get(0).getPrice()));
        // 第一件商品图片
        GlideUtils.loadImage(NationalDayActivity.this, PictureFileUtils.getImage(singleList.get(0).getImages()), R.drawable.ic_all_background, mIvShopping01);

        // 第二件商品名称
        mTvContent02.setText(singleList.get(1).getName());
        // 第二件商品价格
        mTvPrice02.setText(DoubleUtil.double2Str(singleList.get(1).getPrice()));
        // 第二件商品图片
        GlideUtils.loadImage(NationalDayActivity.this, PictureFileUtils.getImage(singleList.get(1).getImages()), R.drawable.ic_all_background, mIvShopping02);
        mData.clear();
        ArrayList<MultipleListBean> multipleList = responseData.getMultipleList();
        mData.addAll(multipleList);
        mAdapter.notifyDataSetChanged();
    }
}
