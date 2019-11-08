package com.hjkj.fuduoduo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.entity.bean.GroupInfo;
import com.hjkj.fuduoduo.entity.bean.ProductInfo;
import com.mylhyl.circledialog.CircleDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * 测试Activity
 * Author：Created by shihongfei on 2019/9/28 17:30
 * Email：1511808259@qq.com
 */
public class TestActivity extends BaseActivity {
    @BindView(R.id.m_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.m_btn1)
    Button mBtn1;
    @BindView(R.id.m_btn2)
    Button mBtn2;
    @BindView(R.id.m_btn3)
    Button mBtn3;
    @BindView(R.id.m_btn4)
    Button mBtn4;
    @BindView(R.id.m_btn5)
    Button mBtn5;
    @BindView(R.id.m_btn6)
    Button mBtn6;
    @BindView(R.id.m_btn7)
    Button mBtn7;
    @BindView(R.id.m_btn8)
    Button mBtn8;
    @BindView(R.id.m_btn9)
    Button mBtn9;
    @BindView(R.id.m_btn10)
    Button mBtn10;
    @BindView(R.id.m_btn11)
    Button mBtn11;
    @BindView(R.id.m_card_view)
    CardView mCardView;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test;
    }

    @Override
    protected void initViews() {
    }
    @OnClick({R.id.m_btn1, R.id.m_btn2, R.id.m_btn3, R.id.m_btn4, R.id.m_btn5, R.id.m_btn6, R.id.m_btn7,
            R.id.m_btn8, R.id.m_btn9, R.id.m_btn10, R.id.m_btn11})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_btn1:       // 基本请求方式
                // commonRequest();
                break;
            case R.id.m_btn2:       // 显示/隐藏加载对话框
                // showLoadingDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // hideLoadingDialog();
                    }
                }, 2000);
                break;
            case R.id.m_btn3:       // 确定提示框
                //region 确定提示框
                new CircleDialog.Builder()
                        .setTitle("标题")
                        .setText("提示框")
                        .setPositive("确定", null)
                        .setOnShowListener(dialog ->
                                Toasty.info(getApplicationContext(), "显示了").show())
                        .setOnCancelListener(dialog ->
                                Toasty.info(getApplicationContext(), "取消了").show())
                        .show(getSupportFragmentManager());
                //endregion
                break;
            case R.id.m_btn4:       // 确定取消提示框
                //region 确定取消提示框
                new CircleDialog.Builder()
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .setTitle("标题")
                        .setText("冷却风扇口无异物，风机扇叶无损伤，无过热痕迹")
                        .configText(params -> {
                            // params.gravity = Gravity.LEFT | Gravity.TOP;
                            params.padding = new int[]{100, 0, 100, 50};
                        })
                        .setNegative("取消", null)
                        .setPositive("确定", v ->
                                Toasty.info(getApplicationContext(), "确定").show())
                        .show(getSupportFragmentManager());
                //endregion
                break;
            case R.id.m_btn5:       // 换头像
                //region 换头像
                final String[] items = {"拍照", "从相册选择", "小视频"};
                new CircleDialog.Builder()
                        .configDialog(params -> {
                            params.backgroundColorPress = Color.CYAN;
                            //增加弹出动画
                            // params.animStyle = R.style.dialogWindowAnim;
                        })
                        .setTitle("标题")
                        .setTitleColor(Color.BLUE)
                        .configTitle(params -> {
                            // params.backgroundColor = Color.RED;
                        })
                        .setSubTitle("副标题：请从以下中选择照片的方式进行提交")
                        .configSubTitle(params -> {
                            // params.backgroundColor = Color.YELLOW;
                        })
                        .setItems(items, (parent, view1, position1, id) ->
                                Toast.makeText(TestActivity.this, "点击了：" + items[position1]
                                        , Toast.LENGTH_SHORT).show())
                        .setNegative("取消", null)
                        // .setNeutral("中间", null)
                        // .setPositive("确定", null)
                        // .configNegative(new ConfigButton() {
                        //     @Override
                        //     public void onConfig(ButtonParams params) {
                        //         //取消按钮字体颜色
                        //         params.textColor = Color.RED;
                        //         params.backgroundColorPress = Color.BLUE;
                        //     }
                        // })
                        .show(getSupportFragmentManager());
                //endregion
                break;
            case R.id.m_btn6:       // 倒计时
                CircleDialog.Builder countDownbuilder = new CircleDialog.Builder()
                        .setTitle("标题")
                        .setText("提示框")
                        .configPositive(params -> params.disable = true)
                        .setPositive("确定(3s)", null)
                        .setNegative("取消", null);
                countDownbuilder.show(getSupportFragmentManager());
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long value = millisUntilFinished / 1000;
                        // Logger.w("millisUntilFinished = " + millisUntilFinished + "; value = " + value);
                        // mTvValue.setText(value);
                        countDownbuilder.configPositive(params -> params.text = "确定(" + (value + 1) + "s)").create();
                    }

                    @Override
                    public void onFinish() {
                        countDownbuilder.configPositive(params -> {
                            params.disable = false;
                            params.text = "确定";
                        }).create();
                    }
                }.start();
                break;
            case R.id.m_btn7:       // 输入框
                CircleDialog.Builder inputBuilder = new CircleDialog.Builder();
                inputBuilder.setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setInputManualClose(true)
                        .setTitle("输入框")
                        .setSubTitle("提示人物是什么？")
                        .setInputHint("请输入条件")
                        .setInputText("默认文本")
                        .autoInputShowKeyboard()
                        .setInputCounter(20)
                        // .setInputCounter(20, (maxLen, currentLen) -> maxLen - currentLen + "/" + maxLen)
                        .configInput(params -> {
                            // params.padding = new int[]{30, 30, 30, 30};
                            // params.inputBackgroundResourceId = R.drawable.bg_input;
                            // params.gravity = Gravity.CENTER;
                            //密码
                            // params.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_MULTI_LINE;
                            //文字加粗
                            params.styleText = Typeface.BOLD;
                        })
                        .setNegative("取消", null)
                        .setPositiveInput("确定", (text, v) -> {
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(TestActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TestActivity.this, text, Toast.LENGTH_SHORT).show();
                                inputBuilder.create().dismiss();
                            }
                        })
                        .show(getSupportFragmentManager());
                break;
            case R.id.m_btn8:       // RecyclerView测试
                // RvTestActivity.openActivity(this);
                break;
            case R.id.m_btn9:       // Camera权限
                // requestCameraPermissions();
                break;
            case R.id.m_btn10:      // 进入动画
                TranslateAnimation enterAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                        -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
                enterAnimation.setDuration(500);//动画时间500毫秒
                enterAnimation.setFillAfter(true);//动画出来控件可以点击
                mCardView.startAnimation(enterAnimation);//开始动画
                mCardView.setVisibility(View.VISIBLE);//设置可见
                break;
            case R.id.m_btn11:      // 退出动画
                TranslateAnimation exitAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                        0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                        -1.0f);
                exitAnimation.setDuration(500);
                exitAnimation.setFillAfter(false);//设置动画结束后控件不可点击
                mCardView.startAnimation(exitAnimation);
                mCardView.setVisibility(View.GONE);//隐藏不占位置
                break;
        }
    }
}
