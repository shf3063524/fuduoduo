package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.adapter.ChooseImageAdapter;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.DoqueryexpresscompanyData;
import com.hjkj.fuduoduo.entity.bean.DoqueryreturnorderdetailsData;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.tool.GlideUtils;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hjkj.fuduoduo.view.ClearEditText;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.mylhyl.circledialog.CircleDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 申请换货填写物流信息
 */
public class ApplyForAReplacement02Activity extends BaseActivity {
    private static final int REQUEST_LOGISTICS_COMPANY = 1016;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_phone)
    TextView mTvPhone;
    @BindView(R.id.m_tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.m_tv_title)
    TextView mTvTtile;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_etshipment_number)
    ClearEditText mEtShipmentNumber;
    @BindView(R.id.m_et_content)
    ClearEditText mEtContent;
    @BindView(R.id.m_tv_reason_for_return)
    TextView mTvReasonForReturn;
    @BindView(R.id.m_layout_symbol)
    RelativeLayout mLayoutSymbol;
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

    private ChooseImageAdapter mAdapter;
    private List<LocalMedia> selectMediaCustomer = new ArrayList<>();

    /**
     * 客户上传图片集合
     */
    private List<String> selectClientPhotoPathList = new ArrayList<>();

    private ArrayList<String> imagesList = new ArrayList<>();
    private String uploadImages;
    private DoqueryreturnorderdetailsData doqueryreturnorderdetailsData;
    private String isReturn;
    private String jumpKey;

    public static void openActivity(Context context, DoqueryreturnorderdetailsData doqueryreturnorderdetailsData, String jumpKey) {
        Intent intent = new Intent(context, ApplyForAReplacement02Activity.class);
        intent.putExtra("DoqueryreturnorderdetailsData", doqueryreturnorderdetailsData);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_apply_for_a_replacement02;
    }

    @Override
    protected void initPageData() {
        doqueryreturnorderdetailsData = (DoqueryreturnorderdetailsData) getIntent().getSerializableExtra("DoqueryreturnorderdetailsData");
        jumpKey = getIntent().getStringExtra("jumpKey");
        onProcessData(jumpKey, doqueryreturnorderdetailsData);
    }

    /**
     * 处理从上一个页面传过来的数据
     *
     * @param jumpKey
     * @param requestData
     */
    private void onProcessData(String jumpKey, DoqueryreturnorderdetailsData requestData) {
        if ("换货中".equals(jumpKey)) {
            mTvTtile.setText("申请换货");
            // 图片
            GlideUtils.loadImage(ApplyForAReplacement02Activity.this, requestData.getExchangeSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
            // 商品名称
            mTvContent.setText(requestData.getCommodity().getName());
            // 商品规格
            mTvSpecification.setText(requestData.getExchangeSpecification().getCommoditySpecification());
            isReturn = "1";
        } else if ("退款中".equals(jumpKey)) {
            mTvTtile.setText("申请退货");
            // 图片
            GlideUtils.loadImage(ApplyForAReplacement02Activity.this, requestData.getCommoditySpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
            // 商品名称
            mTvContent.setText(requestData.getCommodity().getName());
            // 商品规格
            mTvSpecification.setText(requestData.getCommoditySpecification().getCommoditySpecification());

            isReturn = "2";
        }

    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {

        mAdapter = new ChooseImageAdapter(ApplyForAReplacement02Activity.this, onAddPicClickListener);
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
                                    PictureSelector.create(ApplyForAReplacement02Activity.this)
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
        PictureSelector.create(ApplyForAReplacement02Activity.this)
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
                            PictureSelector.create(ApplyForAReplacement02Activity.this).themeStyle(R.style.picture_QQ_style).openExternalPreview(position, selectMediaCustomer);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(ApplyForAReplacement02Activity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(ApplyForAReplacement02Activity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }

            @Override
            public void onRemoveItemClick(int index) {
                selectClientPhotoPathList.remove(index);
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
                case REQUEST_LOGISTICS_COMPANY: // 选择物流公司响应码
                    DoqueryexpresscompanyData doqueryexpresscompanyData = (DoqueryexpresscompanyData) data.getSerializableExtra("company");
                    // 物流公司名称
                    mTvReasonForReturn.setText(doqueryexpresscompanyData.getName());
                    break;
            }
        }
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_layout_symbol, R.id.m_tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_layout_symbol: // 选择物流公司
                ChooseCompanyActivity.openActivityForResult(ApplyForAReplacement02Activity.this, REQUEST_LOGISTICS_COMPANY);
                break;
            case R.id.m_tv_submit: // 提交
                if ("请选择物流公司".equals(getTextString(mTvReasonForReturn))) {
                    Toasty.info(ApplyForAReplacement02Activity.this, "请选择物流公司").show();
                    return;
                }
                if (textIsEmpty(mEtShipmentNumber)) {
                    Toasty.info(ApplyForAReplacement02Activity.this, "请填写物流单号").show();
                    return;
                }

                if (selectClientPhotoPathList.size() > 0 && selectClientPhotoPathList != null) {
                    for (String pictures : selectClientPhotoPathList) {
                        UploadAvatar(new File(pictures));
                    }
                }
                onDoSaveReturnFreight();
                break;
        }
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
     * 添加退换货物流接口
     */
    private void onDoSaveReturnFreight() {
        onImages();
        String consumerId = UserManager.getUserId(ApplyForAReplacement02Activity.this);
        String supplierId = doqueryreturnorderdetailsData.getCommodity().getSupplierId();
        String orderNumber = doqueryreturnorderdetailsData.getReturnOrder().getOrderNumber();
        String freightCode = getTextString(mEtShipmentNumber);
        String freightCompany = getTextString(mTvReasonForReturn);
        String reMarks = getTextString(mEtContent);
        String returnOrderId = doqueryreturnorderdetailsData.getReturnOrder().getId();
        OkGo.<AppResponse>post(Api.ORDERS_DOSAVERETURNFREIGHT)//
                .params("consumerId", consumerId) //买家id
                .params("supplierId", supplierId) // 供货商id
                .params("orderNumber", orderNumber) // 订单编号
                .params("freightCode", freightCode) //	运单号码
                .params("freightCompany", freightCompany) //物流公司
                .params("isReturn", isReturn) //是否是原订单 1:换货 2：退货
                .params("reMarks", reMarks) //说明
                .params("returnOrderId", returnOrderId) //	退货订单id
                .params("images", uploadImages) //	图片
                .execute(new DialogCallBack<AppResponse>(this, "正在提交...") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            ExchangeDetails03Activity.openActivity(ApplyForAReplacement02Activity.this, doqueryreturnorderdetailsData.getOrderDetails().getId(),jumpKey);
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
