package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.ChooseImageAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.entity.bean.VcodeLoginData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.okgo.JsonCallBack;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.view.ClearEditText;
import com.fdw.fdd.view.MyRatingBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 发表评价页面
 */
public class PostEvaluationActivity extends BaseActivity implements MyRatingBar.OnRatingChangeListener {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_release)
    TextView mTvRelease;
    @BindView(R.id.m_tv_evaluation)
    TextView mTvEvalution;
    @BindView(R.id.m_et_content)
    ClearEditText mEtContent;
    @BindView(R.id.m_tv_evaluation02)
    TextView mTvEvalution02;
    @BindView(R.id.m_tv_evaluation03)
    TextView mTvEvalution03;
    @BindView(R.id.m_mrb_evaluation01)
    MyRatingBar mMrbEvalution01;
    @BindView(R.id.m_mrb_evaluation02)
    MyRatingBar mMrbEvalution02;
    @BindView(R.id.m_mrb_evaluation03)
    MyRatingBar mMrbEvalution03;
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindColor(R.color.cl_ff0481df)
    int cl_ff0481df;
    @BindColor(R.color.cl_e8f2ff)
    int cl_e8f2ff;
    /**
     * 上传照片最多一次3张
     */
    private static final int maxSelectNum = 3;

    /**
     * 上传腾讯云标识
     */
    private ChooseImageAdapter mAdapter;
    private List<LocalMedia> selectMediaCustomer = new ArrayList<>();
    private ArrayList<DoQueryOrdersDetailsData> detailsData;
    private String stars = "5";
    private String freightStars = "5";
    private String serviceStars = "5";

    /**
     * 客户上传图片集合
     */
    private List<String> selectClientPhotoPathList = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();
    private String uploadImages;
    private String commodityIds;

    public static void openActivity(Context context, ArrayList<DoQueryOrdersDetailsData> detailsData) {
        Intent intent = new Intent(context, PostEvaluationActivity.class);
        intent.putExtra("detailsData", detailsData);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_post_evaluation;
    }

    @Override
    protected void initPageData() {
        detailsData = (ArrayList<DoQueryOrdersDetailsData>) getIntent().getSerializableExtra("detailsData");
        ProgressData(detailsData);
    }

    /**
     * 数据处理
     *
     * @param detailsData
     */
    private void ProgressData(ArrayList<DoQueryOrdersDetailsData> detailsData) {
        // 商品图片
        GlideUtils.loadImage(PostEvaluationActivity.this, detailsData.get(0).getOrderDetails().get(0).getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(detailsData.get(0).getOrderDetails().get(0).getCommodity().getName());

        ArrayList<OrderDetailsBean> orderDetails = detailsData.get(0).getOrderDetails();
        ArrayList<String> commodityIdss = new ArrayList<>();
        for (OrderDetailsBean orderDetail : orderDetails) {
            String id = orderDetail.getSpecification().getId();
            commodityIdss.add(id);
        }
        StringBuilder sbClient = new StringBuilder();
        if (commodityIdss.size() != 0) {
            for (int i = 0; i < commodityIdss.size(); i++) {
                if (i == 0) {
                    sbClient.append(commodityIdss.get(i));
                } else {
                    sbClient.append(",").append(commodityIdss.get(i));
                }
            }
        }
        if (!sbClient.toString().isEmpty()) {
            commodityIds = sbClient.toString();
        } else {
            commodityIds = "";
        }
    }

    @Override
    protected void initViews() {
        mMrbEvalution01.setOnRatingChangeListener(this);
        mMrbEvalution02.setOnRatingChangeListener(this);
        mMrbEvalution03.setOnRatingChangeListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {

        mAdapter = new ChooseImageAdapter(PostEvaluationActivity.this, onAddPicClickListener);
        mAdapter.setList(selectMediaCustomer);
        mAdapter.setSelectMax(maxSelectNum);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        //设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //设置adapter
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 客户备注图片上传监听
     */
    private ChooseImageAdapter.onAddPicClickListener onAddPicClickListener = new ChooseImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            final String[] items = {"相册", "拍照"};
            new CircleDialog.Builder()
                    .configDialog(params -> {
                        params.backgroundColorPress = cl_e8f2ff;
                        //增加弹出动画
                        params.animStyle = R.style.dialogWindowAnim;
                    })
                    .setTitle("请选择照片来源")
                    .setTitleColor(cl_ff0481df)
                    .configTitle(params -> {
                        // params.backgroundColor = Color.RED;
                    })
                    .setItems(items, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position1, long l) {
                            switch (items[position1]) {
                                case "相册":
                                    selectPhoto();
                                    break;
                                case "拍照":
                                    PictureSelector.create(PostEvaluationActivity.this)
                                            .openCamera(PictureMimeType.ofImage())
                                            .enableCrop(true)// 是否裁剪
                                            .compress(true)// 是否压缩
                                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
                                            .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                            .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                                            .circleDimmedLayer(false)// 是否圆形裁剪
                                            .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                                            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                                            .rotateEnabled(false) // 裁剪是否可旋转图片
                                            .scaleEnabled(true)// 裁剪是否可放大缩小图片
                                            .forResult(PictureConfig.CHOOSE_REQUEST);
                                    break;
                            }
                        }
                    })
                    .setNegative("取消", null)
                    // .setNeutral("中间", null)
                    // .setPositive("确定", null)
                    // .configNegative(new ConfigButton() {
                    //     @Override
                    //     public void onConfig(ButtonParams params) {
                    //         //取消按钮字体颜色
                    //         params.textColor = cl_47;
                    //         params.textSize = DensityUtil.dp2px(16);
                    //     }
                    // })
                    .show(getSupportFragmentManager());
        }
    };

    private void selectPhoto() {
        // currentPosition = 0;
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(PostEvaluationActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选(PictureConfig.MULTIPLE) or 单选(PictureConfig.SINGLE)
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectMediaCustomer)// 是否传入已选图片
                // .isDragFrame(false)// 是否可拖动裁剪框(固定)
                //                        .videoMaxSecond(15)
                //                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(false) // 裁剪是否可旋转图片
                .scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void actionView() {
        /**
         * 客户上传图片回调监听
         */
        mAdapter.setOnItemClickListener(new ChooseImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectMediaCustomer.size() > 0) {
                    LocalMedia media = selectMediaCustomer.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(NewVisitActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(PostEvaluationActivity.this).themeStyle(R.style.picture_QQ_style).openExternalPreview(position, selectMediaCustomer);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(PostEvaluationActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(PostEvaluationActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }

            @Override
            public void onRemoveItemClick(int index) {
//                 selectClientPhotoPathList.remove(index);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                //选择图片
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> resultList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    selectMediaCustomer.addAll(resultList);
                    for (LocalMedia media : resultList) {
                        String picture = "";
                        if (media.isCut() && !media.isCompressed()) {
                            // 裁剪过
                            picture = media.getCutPath();
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            picture = media.getCompressPath();
                        } else {
                            // 原图地址
                            picture = media.getPath();
                        }
                        if (!TextUtils.isEmpty(picture)) {
                            selectClientPhotoPathList.add(picture);
                            mAdapter.setList(selectMediaCustomer);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_release: //发布
                if (selectClientPhotoPathList.size() > 0 && selectClientPhotoPathList != null) {
                    for (String pictures : selectClientPhotoPathList) {
                        UploadAvatar(new File(pictures));
                    }
                    onImages();
                }
                onPostEvaluation();
                break;
        }
    }

    @Override
    public void onRatingChange(MyRatingBar bar, float RatingCount) {
        switch (bar.getId()) {
            case R.id.m_mrb_evaluation01: //整体评价
                changeByStartTest(bar, RatingCount, mTvEvalution);
                switch (getTextString(mTvEvalution)) {
                    case "非常差":
                        stars = "1";
                        break;
                    case "差":
                        stars = "2";
                        break;
                    case "一般":
                        stars = "3";
                        break;
                    case "满意":
                        stars = "4";
                        break;
                    case "非常满意":
                        stars = "5";
                        break;
                }
                break;
            case R.id.m_mrb_evaluation02: // 物流服务
                changeByStartTest(bar, RatingCount, mTvEvalution02);
                switch (getTextString(mTvEvalution02)) {
                    case "非常差":
                        freightStars = "1";
                        break;
                    case "差":
                        freightStars = "2";
                        break;
                    case "一般":
                        freightStars = "3";
                        break;
                    case "满意":
                        freightStars = "4";
                        break;
                    case "非常满意":
                        freightStars = "5";
                        break;
                }
                break;
            case R.id.m_mrb_evaluation03: // 服务态度
                changeByStartTest(bar, RatingCount, mTvEvalution03);
                switch (getTextString(mTvEvalution03)) {
                    case "非常差":
                        serviceStars = "1";
                        break;
                    case "差":
                        serviceStars = "2";
                        break;
                    case "一般":
                        serviceStars = "3";
                        break;
                    case "满意":
                        serviceStars = "4";
                        break;
                    case "非常满意":
                        serviceStars = "5";
                        break;
                }
                break;
        }
    }

    private void changeByStartTest(MyRatingBar bar, float star, TextView textView) {
        Drawable amimia = ContextCompat.getDrawable(this, R.mipmap.fat_star_empty);
        Drawable smiling = ContextCompat.getDrawable(this, R.mipmap.fat_star_full);
        if (star == 1.0f) {
            chage(bar, textView, amimia, smiling, getResources().getString(R.string.Worst));
        } else if (star == 2.0f) {
            chage(bar, textView, amimia, smiling, getResources().getString(R.string.Bad));
        } else if (star == 3.0f) {
            chage(bar, textView, amimia, smiling, getResources().getString(R.string.Good));
        } else if (star == 4.0f) {
            chage(bar, textView, amimia, smiling, getResources().getString(R.string.Better));
        } else if (star == 5.0f) {
            chage(bar, textView, amimia, smiling, getResources().getString(R.string.Best));

        }
    }

    private void chage(MyRatingBar bar, TextView textView, Drawable empty, Drawable full, String string) {
        textView.setText(string);
        bar.setStarEmptyDrawable(empty);
        bar.setStarFillDrawable(full);
    }

    private void UploadAvatar(File file) {
        OkGo.<AppResponse<VcodeLoginData>>post(Api.IMAGE_DOUPLOADPORTRAIT)//
                .params("image", file)
                .execute(new DialogCallBack<AppResponse<VcodeLoginData>>(this, "正在提交...") {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            // 图片返回地址
                            String vcode = simpleResponseAppResponse.getData().getVcode();
                            imagesList.add(vcode);
                        }
                    }
                });
    }

    /**
     * 发布评价接口
     */
    private void onPostEvaluation() {
        String orderId = detailsData.get(0).getOrder().getId();
        String supplierId = detailsData.get(0).getShop().getSupplierId();
        String shopId = detailsData.get(0).getShop().getId();
        String consumerId = UserManager.getUserId(PostEvaluationActivity.this);
        String evaluationContent = getTextString(mEtContent);
        OkGo.<AppResponse>get(Api.ORDERS_DOEVALUATE)//
                .params("orderId", orderId) //	订单id
                .params("supplierId", supplierId) //	供货商id
                .params("shopId", shopId) //	店铺id
                .params("consumerId", consumerId) //	买家id
                .params("commodityIds", commodityIds) //	规格id
                .params("stars", stars) //整体评价星级
                .params("freightStars", freightStars) //	物流评价星级
                .params("serviceStars", serviceStars) //	服务评价星级
                .params("evaluationContent", evaluationContent) //	评价内容
                .params("images", uploadImages) //	评价图片
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.info(PostEvaluationActivity.this, "评价成功").show();
                            AppraiseSuccessfulActivity.openActivity(PostEvaluationActivity.this);
                            finish();
                        }
                    }
                });
    }

    private void onImages() {
        StringBuilder sbClient = new StringBuilder();
        if (imagesList.size() != 0) {
            for (int i = 0; i < imagesList.size(); i++) {
                if (i == 0) {
                    sbClient.append(imagesList.get(i));
                } else {
                    sbClient.append(",").append(imagesList.get(i));
                }
            }
        }
        if (!sbClient.toString().isEmpty()) {
            uploadImages = sbClient.toString();
        } else {
            uploadImages = "";
        }
    }
}
