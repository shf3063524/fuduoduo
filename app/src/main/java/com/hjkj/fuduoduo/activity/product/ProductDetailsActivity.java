package com.hjkj.fuduoduo.activity.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hjkj.fuduoduo.LoginActivity;
import com.hjkj.fuduoduo.MainActivity;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.mine_fragment.MyCollectionActivity;
import com.hjkj.fuduoduo.adapter.ProductPagerAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.dialog.BasicServicesDialog;
import com.hjkj.fuduoduo.dialog.ParameterDialog;
import com.hjkj.fuduoduo.dialog.ShopCartDialog;
import com.hjkj.fuduoduo.entity.bean.AttributesBean;
import com.hjkj.fuduoduo.entity.bean.CommodityBean;
import com.hjkj.fuduoduo.entity.bean.ConsumerBean;
import com.hjkj.fuduoduo.entity.bean.DoQueryCategoryDetailsData;
import com.hjkj.fuduoduo.entity.bean.DoQueryCommodityDetailsData;
import com.hjkj.fuduoduo.entity.bean.Evaluation;
import com.hjkj.fuduoduo.entity.bean.EvaluationBeans;
import com.hjkj.fuduoduo.entity.bean.EvaluationsBean;
import com.hjkj.fuduoduo.entity.bean.FreightTemplateBean;
import com.hjkj.fuduoduo.entity.bean.PasswordLoginData;
import com.hjkj.fuduoduo.entity.bean.SpecificationBeans;
import com.hjkj.fuduoduo.entity.bean.SpecificationsBean;
import com.hjkj.fuduoduo.entity.bean.SupplierBean;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.GetJsonDataUtil;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.tool.SharedPrefUtil;
import com.hjkj.fuduoduo.tool.StatusBarUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.ObservableScrollView;
import com.lzy.okgo.OkGo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 商品详情-页面
 * Author：Created by shihongfei on 2019/9/25 13:52
 * Email：1511808259@qq.com
 */
public class ProductDetailsActivity extends BaseActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    @BindView(R.id.m_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.m_current_page)
    TextView mCurrentPage;
    @BindView(R.id.m_count_page)
    TextView mCountPage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.m_iv_collect)
    ImageView mIvCollect;
    @BindView(R.id.iv_shopping_cart)
    ImageView mIvShoppingCart;
    @BindView(R.id.m_iv_head)
    ImageView mIvHead;
    @BindView(R.id.m_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.m_tv_time)
    TextView mTvTime;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.divide_line)
    View dividerView;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.m_layout_service)
    RelativeLayout mLayoutService;
    @BindView(R.id.m_tv_ship)
    TextView mTvShip;
    @BindView(R.id.tv_shop_cart)
    TextView mTvShopCart;
    @BindView(R.id.m_tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.m_tv_buy)
    TextView mTvBuy;
    @BindView(R.id.m_tv_name)
    TextView mTvName;
    @BindView(R.id.m_tv_shipping)
    TextView mTvShipping;
    @BindView(R.id.m_tv_monthly_sales)
    TextView mTvMonthlySales;
    @BindView(R.id.m_tv_address)
    TextView mTvAddress;
    @BindView(R.id.m_tv_parameter_content)
    TextView mTvParameterContent;
    @BindView(R.id.m_tv_select_content)
    TextView mTvSelectContent;
    @BindView(R.id.m_tv_product_review)
    TextView mTvProductReview;
    @BindView(R.id.m_layout_parameter)
    RelativeLayout mLayoutParameter;
    @BindView(R.id.m_layout_select)
    RelativeLayout mLayoutSelect;
    @BindView(R.id.m_layout_store)
    RelativeLayout mLayoutStore;
    @BindView(R.id.m_layout_review)
    RelativeLayout mLayoutReview;
    @BindView(R.id.m_layout_collect)
    RelativeLayout mLayoutCollect;
    @BindView(R.id.m_layout_review_one)
    LinearLayout mLayoutReviewOne;
    @BindView(R.id.m_web_view)
    WebView mWebView;
    private int imageHeight; //图片高度

    /**
     * 对收藏设定标签
     */
    private boolean check = false;
    private ProductPagerAdapter mPagerAdapter;
    private ArrayList<String> mPhotosData;
    private String shopId;
    private String collection;
    private String supplierId;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String shopId) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("shopId", shopId);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, headLayout);
    }

    @Override
    protected void initViews() {
        initViewPager();
        initListener();
        initWebView();
    }

    /**
     * 加载Html5需要设置的参数
     */
    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    protected void initPageData() {
        shopId = getIntent().getStringExtra("shopId");
        SharedPrefUtil.putString(ProductDetailsActivity.this, "shopId", shopId);
        shopDetalis(shopId);
    }

    private void initViewPager() {
        mPhotosData = new ArrayList<>();
        mPagerAdapter = new ProductPagerAdapter(this, mPhotosData);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initListener() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver treeObserver = mViewPager.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = mViewPager.getHeight();
                scrollView.setScrollViewListener(ProductDetailsActivity.this);

            }
        });
    }

    @Override
    protected void actionView() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage.setText(String.valueOf(position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                mIvShoppingCart.setImageResource(R.mipmap.ic_shopping_white);
                mIvShare.setImageResource(R.mipmap.ic_share_white);
            } else if (oldy > y) {
                // 手指向下滑动，屏幕内容上滑
                mIvBack.setImageResource(R.mipmap.ic_return_black);
                mIvShoppingCart.setImageResource(R.mipmap.ic_shopping_black);
                mIvShare.setImageResource(R.mipmap.ic_share_black);
            }

        } else {
            //滑动到banner下面设置普通颜色
            headLayout.setBackgroundColor(Color.WHITE);
            dividerView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_back, R.id.m_layout_service, R.id.m_layout_select, R.id.m_layout_parameter, R.id.m_layout_review, R.id.tv_shop_cart,
            R.id.m_tv_buy, R.id.m_layout_store, R.id.m_layout_collect, R.id.iv_shopping_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_service: // 基础服务
                new BasicServicesDialog(ProductDetailsActivity.this)
                        .setListener(new BasicServicesDialog.OnClickListener() {
                            @Override
                            public void onClick(int type) {
                            }
                        }).show();
                break;
            case R.id.m_layout_select: // 已选
                new ShopCartDialog(ProductDetailsActivity.this)
                        .setListener(new ShopCartDialog.OnClickListener() {
                            @Override
                            public void onClick(String commoditySpecificationId, String commoditySpecification, String number) {
                                mTvSelectContent.setText(commoditySpecification + " " + number + "件");
                                newAddshop(commoditySpecificationId, number);
                            }
                        }).show();
                break;
            case R.id.m_layout_parameter: // 规格
                new ParameterDialog(ProductDetailsActivity.this)
                        .setListener(new ParameterDialog.OnClickListener() {
                            @Override
                            public void onClick(int type) {

                            }
                        }).show();
                break;
            case R.id.m_layout_review: //全部评价
                AllReviewActivity.openActivity(ProductDetailsActivity.this);
                break;
            case R.id.tv_shop_cart: //加入购物车
                new ShopCartDialog(ProductDetailsActivity.this)
                        .setListener(new ShopCartDialog.OnClickListener() {
                            @Override
                            public void onClick(String commoditySpecificationId, String commoditySpecification, String number) {
                                mTvSelectContent.setText(commoditySpecification + " " + number + "件");
                                newAddshop(commoditySpecificationId, number);
                            }
                        }).show();
                break;
            case R.id.m_tv_buy: //立即购买
                new ShopCartDialog(ProductDetailsActivity.this)
                        .setListener(new ShopCartDialog.OnClickListener() {
                            @Override
                            public void onClick(String commoditySpecificationId, String commoditySpecification, String number) {
                                // 确认订单
                                ConfirmOrderActivtiy.openActivity(ProductDetailsActivity.this, commoditySpecificationId, number);
                            }
                        }).show();
                break;

            case R.id.m_layout_store: // 店铺详情
                // 到时这里需要对店铺详情做有无背景判断进行页面跳转
                StoreDetailsNoBackgroundActivity.openActivity(ProductDetailsActivity.this, supplierId);
//                StoreDetailsActivity.openActivity(ProductDetailsActivity.this, supplierId);
                break;

            case R.id.m_layout_collect: // 收藏
                if (check) {
                    check = false;
                    mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect));
                    delete();
                } else {
                    check = true;
                    mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect_yes));
                    addCollection();
                }
                break;
            case R.id.iv_shopping_cart: // 跳转购物车Frgment
//                MainActivity.openActivity(ProductDetailsActivity.this, "", "ProductDetailsActivity");
                break;
        }
    }

    /**
     * 商品详情接口
     *
     * @param shopId 商品id
     */
    private void shopDetalis(String shopId) {
        String userId = UserManager.getUserId(ProductDetailsActivity.this);
        OkGo.<AppResponse<DoQueryCommodityDetailsData>>get(Api.COMMODITY_DOQUERYCOMMODITYDETAILS)//
                .params("id", shopId)
                .params("userId", userId)
                .execute(new JsonCallBack<AppResponse<DoQueryCommodityDetailsData>>() {
                    @Override
                    public void onSuccess(AppResponse<DoQueryCommodityDetailsData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            DoQueryCommodityDetailsData data = simpleResponseAppResponse.getData();
                            refreshUi(data);
                        }

                    }
                });
    }

    private void refreshUi(DoQueryCommodityDetailsData data) {
        // 产品数据
        CommodityBean commodity = data.getCommodity();
        //商品id
        String id = commodity.getId();
        SharedPrefUtil.putString(ProductDetailsActivity.this, "commodityId", id);
        // 商户id
        supplierId = commodity.getSupplierId();

        // 产品图片
        ArrayList<String> picture = new ArrayList<>();
        String images = commodity.getImages();
        if (images != null && !images.equals("") && !images.isEmpty()) {
            String[] imageUrl = images.split(",");
            picture.addAll(Arrays.asList(imageUrl));
        }

        if (picture != null && !picture.isEmpty()) {
            for (int i = 0; i < picture.size(); i++)
                mPhotosData.add(picture.get(i));
            mPagerAdapter.notifyDataSetChanged();
            if (!mPhotosData.isEmpty()) {
                mCountPage.setText(String.valueOf(mPhotosData.size()));
                mCurrentPage.setText("1");
            }
        } else {
            mViewPager.setVisibility(View.VISIBLE);
        }

        // 产品积分
        mTvIntegral.setText(DoubleUtil.double2Str(commodity.getPrice()));
        // 产品介绍
        mTvName.setText(commodity.getName());
        // 销量
        mTvMonthlySales.setText("销量：" + commodity.getFictitiousVolume());
        // 商品详情
        String description = commodity.getDescription();

        mWebView.loadDataWithBaseURL(null,GetJsonDataUtil.jsoup(ProductDetailsActivity.this,description),"text/html","utf-8",null);

        // 包邮的地址涵盖在这里
        FreightTemplateBean freightTemplate = data.getFreightTemplate();
        // 包邮
        if ("1".equals(freightTemplate.getFreeShipping())) {
            mTvShipping.setText("包邮");
        } else {
            mTvShipping.setText("不包邮");
        }
        // 店铺地址
        SupplierBean supplier = data.getSupplier();
        mTvAddress.setText(supplier.getProvince() + supplier.getCity());
        // 商品评价条数
        EvaluationsBean evaluations = data.getEvaluations();
        mTvProductReview.setText("商品评价（" + evaluations.getNumber() + ")");
        if (!"0".equals(evaluations.getNumber())) {
            // 所有评价
            ArrayList<EvaluationBeans> evaluation = evaluations.getEvaluation();
            // 第一个用户
            ConsumerBean consumer = evaluation.get(0).getConsumer();
            //用户头像
            GlideUtils.loadCircleHeadImage(ProductDetailsActivity.this, consumer.getLogo(), R.drawable.ic_all_background, mIvHead);
            //用户电话
            String phoneNumber = consumer.getPhoneNumber();
            String phonenum = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11);
            mTvPhone.setText(phonenum);
            // 评级时间
            Evaluation evaluation1 = evaluation.get(0).getEvaluation();
            mTvTime.setText(evaluation1.getCreateTime());
            // 评价规格
            SpecificationBeans specification = evaluation.get(0).getSpecification();
            mTvSpecification.setText(specification.getCommoditySpecification());
            // 评价内容
            mTvContent.setText(evaluation1.getEvaluationContent());
        } else {
            mLayoutReviewOne.setVisibility(View.GONE);
        }
        //规格
        ArrayList<SpecificationsBean> specifications = data.getSpecifications();
        // 参数
        ArrayList<AttributesBean> attributes = data.getAttributes();

        // 服务
        mTvShip.setText(commodity.getServiceType());
        //已选默认第一个
        mTvSelectContent.setText(specifications.get(0).getCommoditySpecification() + " 1件");
        // 参数内容
        if (attributes.size() > 0 && attributes != null) {
            mTvParameterContent.setText(attributes.get(0).getAttribute() + "：" + attributes.get(0).getValue());
        } else {
            mTvParameterContent.setText("暂无规格参数");
        }


        collection = data.getCollection();
        if ("0".equals(collection)) {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect));
            check = false;
        } else {
            mIvCollect.setImageDrawable(getResources().getDrawable(R.drawable.ic_collect_yes));
            check = true;
        }
    }

    /**
     * 添加收藏夹接口
     */
    private void addCollection() {
        String userId = UserManager.getUserId(ProductDetailsActivity.this);
        OkGo.<AppResponse<VcodeLoginData>>get(Api.USER_DOCOLLECT)//
                .params("userId", userId)
                .params("collectionsId", shopId)
                .params("type", "0")
                .execute(new JsonCallBack<AppResponse<VcodeLoginData>>() {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            String vcode = simpleResponseAppResponse.getData().getVcode();
                            collection = vcode;
                            Toasty.info(ProductDetailsActivity.this, "收藏成功").show();
                        }

                    }
                });
    }

    /**
     * 删除需要传的id
     */
    private void delete() {
        OkGo.<AppResponse>get(Api.USER_DODELETECOLLECTIONS)//
                .params("ids", collection)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        Toasty.normal(ProductDetailsActivity.this, "删除成功").show();
                    }
                });
    }


    /**
     * 购物车新增接口
     *
     * @param commoditySpecificationId 商品规格id
     * @param number                   数量
     */
    private void newAddshop(String commoditySpecificationId, String number) {
        String userId = UserManager.getUserId(ProductDetailsActivity.this);
        String commodityId = SharedPrefUtil.getString(ProductDetailsActivity.this, "commodityId", "");
        OkGo.<AppResponse>get(Api.CART_DOSAVE)//
                .params("consumerId", userId)
                .params("shopId", supplierId)
                .params("commodityId", commodityId)
                .params("commoditySpecificationId", commoditySpecificationId)
                .params("number", number)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        Toasty.normal(ProductDetailsActivity.this, "新增成功").show();
                    }
                });
    }

}
