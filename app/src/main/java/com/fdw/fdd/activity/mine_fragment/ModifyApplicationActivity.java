package com.fdw.fdd.activity.mine_fragment;

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

import com.fdw.fdd.R;
import com.fdw.fdd.adapter.ChooseImageAdapter;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.dialog.ReasonForReturn02Dialog;
import com.fdw.fdd.entity.bean.DoQueryOrdersDetailsData;
import com.fdw.fdd.entity.bean.DoqueryreturnorderdetailsData;
import com.fdw.fdd.entity.bean.OrderDetailsBean;
import com.fdw.fdd.entity.bean.VcodeLoginData;
import com.fdw.fdd.entity.net.AppResponse;
import com.fdw.fdd.okgo.Api;
import com.fdw.fdd.okgo.DialogCallBack;
import com.fdw.fdd.tool.DoubleUtil;
import com.fdw.fdd.tool.GlideUtils;
import com.fdw.fdd.view.ClearEditText;
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
 * 修改申请-页面
 */
public class ModifyApplicationActivity extends BaseActivity {
    @BindView(R.id.m_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_shopping)
    ImageView mIvShopping;
    @BindView(R.id.m_tv_content)
    TextView mTvContent;
    @BindView(R.id.m_tv_specification)
    TextView mTvSpecification;
    @BindView(R.id.m_tv_refund_amount)
    TextView mTvRefundAmount;
    @BindView(R.id.m_tv_prompt)
    TextView mTvPrompt;
    @BindView(R.id.m_tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.m_et_content)
    ClearEditText mEtContent;
    @BindView(R.id.m_tv_reason_for_return)
    TextView mTvReasonForReturn;
    @BindView(R.id.m_layout_symbol)
    RelativeLayout mLayoutSymbol;
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
    private ArrayList<DoQueryOrdersDetailsData> detailsData;
    private OrderDetailsBean orderDetailsBean;
    private String uploadImages;
    private DoqueryreturnorderdetailsData doqueryreturnorderdetailsData;

    /**
     * @param context
     * @param detailsData      整个订单数据
     * @param orderDetailsBean 单个商品数据
     * @param freightPrice     计算的运费
     * @param doqueryreturnorderdetailsData  查询退货订单详情
     */
    public static void openActivity(Context context, ArrayList<DoQueryOrdersDetailsData> detailsData, OrderDetailsBean orderDetailsBean, String freightPrice, DoqueryreturnorderdetailsData doqueryreturnorderdetailsData) {
        Intent intent = new Intent(context, ModifyApplicationActivity.class);
        intent.putExtra("detailsData", detailsData);
        intent.putExtra("OrderDetailsBean", orderDetailsBean);
        intent.putExtra("freightPrice", freightPrice);
        intent.putExtra("DoqueryreturnorderdetailsData", doqueryreturnorderdetailsData);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_modify_application;
    }
    @Override
    protected void initPageData() {
        doqueryreturnorderdetailsData = (DoqueryreturnorderdetailsData) getIntent().getSerializableExtra("DoqueryreturnorderdetailsData");
        String freightPrice = getIntent().getStringExtra("freightPrice");
        detailsData = (ArrayList<DoQueryOrdersDetailsData>) getIntent().getSerializableExtra("detailsData");
        orderDetailsBean = (OrderDetailsBean) getIntent().getSerializableExtra("OrderDetailsBean");
        onProcessingData(freightPrice, orderDetailsBean);
    }

    private void onProcessingData(String freightPrice, OrderDetailsBean orderDetailsBean) {
        // 规格图片
        GlideUtils.loadImage(ModifyApplicationActivity.this, orderDetailsBean.getSpecification().getSpecificationImage(), R.drawable.ic_all_background, mIvShopping);
        // 商品名称
        mTvContent.setText(orderDetailsBean.getCommodity().getName());
        // 规格
        mTvSpecification.setText(orderDetailsBean.getSpecification().getCommoditySpecification());
        // 退款金额
        Double mul = DoubleUtil.mul(Double.parseDouble(orderDetailsBean.getOrderDetail().getPrice()), Double.parseDouble(orderDetailsBean.getOrderDetail().getNumber()));
        Double addTotalPrice = DoubleUtil.add(mul, Double.parseDouble(freightPrice));
        mTvRefundAmount.setText(DoubleUtil.double2Str(String.valueOf(mul)));
        // 提示
        mTvPrompt.setText("最多" + DoubleUtil.double2Str(String.valueOf(addTotalPrice)) + "积分，含发货邮费" + DoubleUtil.double2Str(freightPrice) + "积分");

    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {

        mAdapter = new ChooseImageAdapter(ModifyApplicationActivity.this, onAddPicClickListener);
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
                                    PictureSelector.create(ModifyApplicationActivity.this)
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
        PictureSelector.create(ModifyApplicationActivity.this)
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
                            PictureSelector.create(ModifyApplicationActivity.this).themeStyle(R.style.picture_QQ_style).openExternalPreview(position, selectMediaCustomer);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(ModifyApplicationActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(ModifyApplicationActivity.this).externalPictureAudio(media.getPath());
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
            case R.id.m_layout_symbol: // 退款原因
                new ReasonForReturn02Dialog(ModifyApplicationActivity.this)
                        .setListener(new ReasonForReturn02Dialog.OnClickListener() {
                            @Override
                            public void onClick(int type) {
                                switch (type) {
                                    case ReasonForReturn02Dialog.M_LAYOUT_ONE:
                                        mTvReasonForReturn.setText("多拍/拍错/不想要");
                                        break;
                                    case ReasonForReturn02Dialog.M_LAYOUT_TWO:
                                        mTvReasonForReturn.setText("协商一致退款");
                                        break;
                                    case ReasonForReturn02Dialog.M_LAYOUT_THREE:
                                        mTvReasonForReturn.setText("缺货");
                                        break;
                                    case ReasonForReturn02Dialog.M_LAYOUT_FOUR:
                                        mTvReasonForReturn.setText("未按约定时间发货");
                                        break;
                                    case ReasonForReturn02Dialog.M_LAYOUT_FIVE:
                                        mTvReasonForReturn.setText("空包裹/少货");
                                        break;
                                    case ReasonForReturn02Dialog.M_LAYOUT_SIX:
                                        mTvReasonForReturn.setText("其他");
                                        break;
                                }
                            }
                        }).show();
                break;
            case R.id.m_tv_submit: // 提交
                if ("请选择".equals(getTextString(mTvReasonForReturn))) {
                    Toasty.info(ModifyApplicationActivity.this, "请选择退款原因").show();
                    return;
                }
                if (selectClientPhotoPathList.size() > 0 && selectClientPhotoPathList != null) {
                    for (String pictures : selectClientPhotoPathList) {
                        UploadAvatar(new File(pictures));
                    }
                    onImages();
                }
                onSubmitInformation();
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
     * 申请售后提交信息
     */
    private void onSubmitInformation() {
        String id = doqueryreturnorderdetailsData.getReturnOrderDetails().getId();
        String description = getTextString(mEtContent);
        String returnReason = getTextString(mTvReasonForReturn);
        OkGo.<AppResponse>get(Api.ORDERS_DOMODIFYRETURNORDER)//
                .params("id", id) //	订单详情id
                .params("returnReason", returnReason) //退货原因
                .params("description", description) //申请描述
                .params("images", uploadImages) //	图片
                .execute(new DialogCallBack<AppResponse>(this, "正在提交...") {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
//                            OrderDetails02RefundDetailsActivity.openActivity(ModifyApplicationActivity.this,orderDetailsBean,detailsData,"","");
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
