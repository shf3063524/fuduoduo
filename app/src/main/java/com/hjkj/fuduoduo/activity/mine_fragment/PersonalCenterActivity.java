package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hjkj.fuduoduo.LoginActivity;
import com.hjkj.fuduoduo.MainActivity;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.ConsumerBean;
import com.hjkj.fuduoduo.entity.bean.VcodeLoginData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.DialogCallBack;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.AppDateMgr;
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
import java.util.Date;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 个人资料-页面
 */
public class PersonalCenterActivity extends BaseActivity {
    @BindView(R.id.m_iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.m_iv_head)
    ImageView mIvHead;
    @BindView(R.id.m_tv_finish)
    TextView mTvFinsh;
    @BindView(R.id.m_tv_write_gender)
    TextView mTvWriteGender;
    @BindView(R.id.m_tv_write_birth)
    TextView mTvWriteBirth;
    @BindView(R.id.m_layout_head)
    RelativeLayout mLayoutHead;
    @BindView(R.id.m_layout_gender)
    RelativeLayout mLayoutGender;
    @BindView(R.id.m_layout_birth)
    RelativeLayout mLayoutBirth;
    @BindView(R.id.m_et_username)
    ClearEditText mEtUsername;
    @BindView(R.id.m_et_nickname)
    ClearEditText mEtNickname;
    @BindView(R.id.m_et_phone)
    ClearEditText mEtPhone;
    @BindColor(R.color.cl_ff0481df)
    int cl_ff0481df;
    @BindColor(R.color.cl_e8f2ff)
    int cl_e8f2ff;

    private static final int maxSelectNum = 1;
    private List<LocalMedia> selectMedia = new ArrayList<>();
    private TimePickerView pvTime;
    private String gender;
    private String logoHead = null;
    private String jumpKey;

    public static void openActivity(Context context, String jumpKey) {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, ConsumerBean consumerBean, String jumpKey) {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        intent.putExtra("ConsumerBean", consumerBean);
        intent.putExtra("jumpKey", jumpKey);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initPageData() {
        ConsumerBean consumerBean = (ConsumerBean) getIntent().getSerializableExtra("ConsumerBean");
        jumpKey = getIntent().getStringExtra("jumpKey");
        if ("SetActivity".equals(jumpKey)) {
            uploadedProfileDisplay(consumerBean);
        }
    }

    @Override
    protected void initViews() {
        initPickerView();
    }

    @OnClick({R.id.m_iv_arrow, R.id.m_tv_finish, R.id.m_layout_head, R.id.m_layout_gender, R.id.m_layout_birth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_iv_arrow:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_tv_finish: //完成
                if (textIsEmpty(mEtUsername)) {
                    Toasty.info(PersonalCenterActivity.this, "请填写用户名").show();
                    return;
                }
                if (textIsEmpty(mEtNickname)) {
                    Toasty.info(PersonalCenterActivity.this, "请填写昵称").show();
                    return;
                }
                if (textIsEmpty(mTvWriteGender)) {
                    Toasty.info(PersonalCenterActivity.this, "请选择性别").show();
                    return;
                }
                if (textIsEmpty(mTvWriteBirth)) {
                    Toasty.info(PersonalCenterActivity.this, "请选择出生日期").show();
                    return;
                }
                //开始上传
                if (logoHead == null || logoHead.isEmpty()) {
                    Toasty.info(PersonalCenterActivity.this, "请选择头像").show();
                    return;
                }
                UploadAvatar(new File(logoHead));
                break;
            case R.id.m_layout_head: //换头像
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
                                        PictureSelector.create(PersonalCenterActivity.this)
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
                break;
            case R.id.m_layout_gender: //性别
                final String[] itemGenders = {"男", "女", "保密"};
                new CircleDialog.Builder()
                        .configDialog(params -> {
                            params.backgroundColorPress = cl_e8f2ff;
                            //增加弹出动画
                            params.animStyle = R.style.dialogWindowAnim;
                        })
                        .setTitleColor(cl_ff0481df)
                        .setTitle("请选择性别")
                        .configTitle(params -> {
                            // params.backgroundColor = Color.RED;
                        })
                        .setItems(itemGenders, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position1, long l) {
                                switch (itemGenders[position1]) {
                                    case "男":
                                        mTvWriteGender.setText("男");
                                        gender = "1";
                                        break;
                                    case "女":
                                        mTvWriteGender.setText("女");
                                        gender = "2";
                                        break;
                                    case "保密":
                                        mTvWriteGender.setText("保密");
                                        gender = "3";
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
                break;
            case R.id.m_layout_birth: //生日
                pvTime.show();
                break;
        }
    }

    private void selectPhoto() {
        // currentPosition = 0;
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(PersonalCenterActivity.this)
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
                .selectionMedia(selectMedia)// 是否传入已选图片
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

    /**
     * Dialog 模式下，在底部弹出
     */
    private void initPickerView() {
        pvTime = new TimePickerBuilder(PersonalCenterActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String parseTime = AppDateMgr.parseDate(AppDateMgr.YYYYMMDD_CHINESE, String.valueOf(date.getTime()));
                mTvWriteBirth.setText(parseTime);
            }
        }).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            Log.e(picture, "图片=" + picture);
                            //yyyyMMddHHmmssXXXXXX .txt
                            //随机数名称
//                            String randomFileName = RandomUtil.getRandomFileName();
                            // 文件名后缀
//                            String suffix = picture.substring(picture.lastIndexOf(".") + 1).toUpperCase();
//                            String cosPath = randomFileName + "." + suffix;
//                            uploadToTencent(cosPath, picture);
                            GlideUtils.loadCircleHeadImage(this, picture, R.mipmap.ic_head, mIvHead);
                            logoHead = picture;
                        }
                    }

                    break;
            }
        }
    }

    /**
     * 完成资料填写
     *
     * @param logo
     */
    private void finishWrite(String logo) {
        String userId = UserManager.getUserId(PersonalCenterActivity.this);
        String username = getTextString(mEtUsername);
        String name = getTextString(mEtNickname);
        String birthday = getTextString(mTvWriteBirth);
        OkGo.<AppResponse>get(Api.USER_DOUPDATE)//
                .params("id", userId)
                .params("username", username)
                .params("name", name)
                .params("logo", logo)
                .params("birthday", birthday)
                .params("gender", gender)
                .execute(new JsonCallBack<AppResponse>() {
                    @Override
                    public void onSuccess(AppResponse simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            Toasty.normal(PersonalCenterActivity.this, "保存成功").show();
//                            AddAddressActivity.openActivity(PersonalCenterActivity.this, jumpKey);
                            finish();
                        }
                    }
                });
    }

    /**
     * 上传头像
     */
    private void UploadAvatar(File file) {
        OkGo.<AppResponse<VcodeLoginData>>post(Api.IMAGE_DOUPLOADPORTRAIT)//
                .params("image", file)
                .execute(new DialogCallBack<AppResponse<VcodeLoginData>>(this, "正在获取...") {
                    @Override
                    public void onSuccess(AppResponse<VcodeLoginData> simpleResponseAppResponse) {
                        if (simpleResponseAppResponse.isSucess()) {
                            // 图片返回地址
                            String vcode = simpleResponseAppResponse.getData().getVcode();
                            finishWrite(vcode);
                        }
                    }
                });
    }

    /**
     * 已经上传的个人资料展示
     *
     * @param consumerBean
     */
    private void uploadedProfileDisplay(ConsumerBean consumerBean) {
        // 头像
        GlideUtils.loadCircleHeadImage(PersonalCenterActivity.this, consumerBean.getLogo(), R.drawable.ic_all_background, mIvHead);
        // 用户名
        mEtUsername.setText(consumerBean.getUsername());
        // 昵称
        mEtNickname.setText(consumerBean.getName());
        // 手机号
        mEtPhone.setText(consumerBean.getPhoneNumber());
        // 性别
        switch (consumerBean.getGender()) {
            case "1":
                mTvWriteGender.setText("男");
                break;
            case "2":
                mTvWriteGender.setText("女");
                break;
            case "3":
                mTvWriteGender.setText("保密");
                break;
        }
        // 生日
        mTvWriteBirth.setText(consumerBean.getBirthday());
    }

}
