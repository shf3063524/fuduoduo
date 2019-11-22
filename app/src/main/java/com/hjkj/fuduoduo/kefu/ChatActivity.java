package com.hjkj.fuduoduo.kefu;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.activity.product.ProductDetailsActivity;
import com.hjkj.fuduoduo.entity.bean.DoQueryCommodityDetailsData;
import com.hjkj.fuduoduo.entity.net.AppResponse;
import com.hjkj.fuduoduo.fragment.CustomChatFragment;
import com.hjkj.fuduoduo.okgo.Api;
import com.hjkj.fuduoduo.okgo.JsonCallBack;
import com.hjkj.fuduoduo.tool.DoubleUtil;
import com.hjkj.fuduoduo.tool.SharedPrefUtil;
import com.hjkj.fuduoduo.tool.UserManager;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.recorder.MediaManager;
import com.hyphenate.helpdesk.easeui.ui.BaseActivity;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.util.CommonUtils;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.VisitorInfo;
import com.hyphenate.helpdesk.model.VisitorTrack;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.lzy.okgo.OkGo;

public class ChatActivity extends BaseActivity {
    public static ChatActivity instance = null;

    private ChatFragment chatFragment;

    String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.im_activity_chat);
        instance = this;
        //IM服务号
        toChatUsername = getIntent().getExtras().getString(Config.EXTRA_SERVICE_IM_NUMBER);
        //可以直接new ChatFragment使用
        chatFragment = new CustomChatFragment();

        //传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        shopDetalis();
    }


//    /**
//     * 发送订单或轨迹消息
//     */
//    private void sendOrderOrTrack() {
//        Bundle bundle = getIntent().getBundleExtra(Config.EXTRA_BUNDLE);
//        if (bundle != null) {
//            //检查是否是从某个商品详情进来
//            int selectedIndex = bundle.getInt(Constant.INTENT_CODE_IMG_SELECTED_KEY, Constant.INTENT_CODE_IMG_SELECTED_DEFAULT);
//            switch (selectedIndex) {
//                case Constant.INTENT_CODE_IMG_SELECTED_1:
//                case Constant.INTENT_CODE_IMG_SELECTED_2:
//                    sendOrderMessage(selectedIndex);
//                    break;
//                case Constant.INTENT_CODE_IMG_SELECTED_3:
//                case Constant.INTENT_CODE_IMG_SELECTED_4:
//                    sendTrackMessage(selectedIndex);
//                    break;
//            }
//        }
//    }

//    /**
//     * 发送订单消息
//     * <p>
//     * 不发送则是saveMessage
//     *
//     * @param selectedIndex
//     */
//    private void sendOrderMessage(int selectedIndex) {
//        Message message = Message.createTxtSendMessage(getMessageContent(selectedIndex), toChatUsername);
//        message.addContent(MessageHelper.createOrderInfo(this, selectedIndex));
//        ChatClient.getInstance().chatManager().saveMessage(message);
//    }

//    private String getMessageContent(int selectedIndex) {
//        switch (selectedIndex) {
//            case 1:
//                return getResources().getString(R.string.em_example1_text);
//            case 2:
//                return getResources().getString(R.string.em_example2_text);
//            case 3:
//                return getResources().getString(R.string.em_example3_text);
//            case 4:
//                return getResources().getString(R.string.em_example4_text);
//        }
//        // 内容自己随意定义。
//        return "";
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
        instance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(Config.EXTRA_SERVICE_IM_NUMBER);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (CommonUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 商品详情接口
     */
    private void shopDetalis() {
        String shopId = SharedPrefUtil.getString(ChatActivity.this, "shopId", "");
        String userId = UserManager.getUserId(ChatActivity.this);
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

    private void refreshUi(DoQueryCommodityDetailsData detailsData) {
        // 商品图片
        String productImage = PictureFileUtils.getImage(detailsData.getCommodity().getImages());
        // 商品名称
        String productName = detailsData.getCommodity().getName();
        // 商品价格
        String productPrice = DoubleUtil.double2Str(detailsData.getCommodity().getPrice());
        String nickName = UserManager.getNickName(ChatActivity.this);
        String username = UserManager.getUsername(ChatActivity.this);
        String phoneNumber = UserManager.getPhoneNumber(ChatActivity.this);
        String companyName = UserManager.getUserCompany(ChatActivity.this);
        String jobNumber = UserManager.getJobNumber(ChatActivity.this);
        sendTrackMessage(productImage, productName, productPrice,nickName,username,phoneNumber,companyName,jobNumber);
    }
    /**
     * 发送轨迹消息
     *
     */
    private void sendTrackMessage(String productImage, String productName, String productPrice,String nickName,String username,String phoneNumber,String companyName,String jobNumber) {
        Message message = Message.createTxtSendMessage("",toChatUsername);
        message.addContent(createVisitorTrack(productImage, productName, productPrice));
        message.addContent(createVisitorInfo(nickName, username, phoneNumber,companyName,jobNumber));
        ChatClient.getInstance().chatManager().sendMessage(message);
    }
    public static VisitorTrack createVisitorTrack(String productImage, String productName, String productPrice) {
        VisitorTrack track = ContentFactory.createVisitorTrack(null);
        track.title("我正在看")
                .price("￥" + productPrice)
                .desc(productName)
                .imageUrl(productImage);
        return track;
    }

    /**
     * 发送带访客属性的消息
     * @param nickName
     * @param username
     * @param phoneNumber
     * @param companyName
     * @param jobNumber
     */
    public static VisitorInfo createVisitorInfo(String nickName, String username, String phoneNumber, String companyName, String jobNumber) {
        VisitorInfo info = ContentFactory.createVisitorInfo(null);
        info.nickName(nickName)
                .name(username)
                .phone(phoneNumber)
                .companyName(companyName) // 公司名称
                .description(jobNumber);// 工号
        return info;
    }
}
