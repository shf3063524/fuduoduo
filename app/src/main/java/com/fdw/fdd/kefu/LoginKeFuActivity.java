package com.fdw.fdd.kefu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fdw.fdd.R;
import com.fdw.fdd.base.DemoBaseActivity;
import com.fdw.fdd.tool.UserManager;
import com.fdw.fdd.tool.kefutool.Constant;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.VisitorInfo;

/**
 * 客服-页面
 */
public class LoginKeFuActivity extends DemoBaseActivity {

    private boolean progressShow;
    private ProgressDialog progressDialog;
    private int selectedIndex = Constant.INTENT_CODE_IMG_SELECTED_DEFAULT;
    private int messageToIndex = Constant.MESSAGE_TO_DEFAULT;
    String toChatUsername;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        Intent intent = getIntent();
        selectedIndex = intent.getIntExtra(Constant.INTENT_CODE_IMG_SELECTED_KEY,
                Constant.INTENT_CODE_IMG_SELECTED_DEFAULT);
        messageToIndex = intent.getIntExtra(Constant.MESSAGE_TO_INTENT_EXTRA, Constant.MESSAGE_TO_DEFAULT);
        //IM服务号
        toChatUsername = getIntent().getExtras().getString(Config.EXTRA_SERVICE_IM_NUMBER);
//        ChatClient.getInstance().isLoggedInBefore();//可以检测是否已经登录过环信，如果登录过则环信SDK会自动登录，不需要再次调用登录操作
//        if (ChatClient.getInstance().isLoggedInBefore()) {
//            progressDialog = getProgressDialog();
//            progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
//            progressDialog.show();
//            toChatActivity();
//        } else {
        // 随机创建一个用户并登录环信服务器
        String phone = getIntent().getStringExtra("phone");
        final String account = "fuduo" + phone;
        final String userPwd = "fuduo" + phone + "fuduo";
        login(account, userPwd);
//        }
    }

    private ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginKeFuActivity.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressShow = false;
                }
            });
        }
        return progressDialog;
    }

    private void login(final String uname, final String upwd) {
        progressShow = true;
        progressDialog = getProgressDialog();
        progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        // login huanxin server
        ChatClient.getInstance().login(uname, upwd, new Callback() {
            @Override
            public void onSuccess() {
//                Log.d(TAG, "demo login success!");
                if (!progressShow) {
                    return;
                }
                toChatActivity();
            }

            @Override
            public void onError(int code, String error) {
//                Log.e(TAG, "login fail,code:" + code + ",error:" + error);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(LoginKeFuActivity.this,
                                getResources().getString(R.string.is_contact_customer_failure_seconed),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    private void toChatActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!LoginKeFuActivity.this.isFinishing())
                    progressDialog.dismiss();

                //此处演示设置技能组,如果后台设置的技能组名称为[shouqian|shouhou],这样指定即分配到技能组中.
                //为null则不按照技能组分配,同理可以设置直接指定客服scheduleAgent
                String queueName = null;
                switch (messageToIndex) {
                    case Constant.MESSAGE_TO_AFTER_SALES:
                        queueName = "shouhou";
                        break;
                    case Constant.MESSAGE_TO_PRE_SALES:
                        queueName = "shouqian";
                        break;
                }
                if (messageToIndex == Constant.MESSAGE_TO_CHATROOM_LIST) {
//                    startActivity(new Intent(LoginKeFuActivity.this,ChatRoomListActivity.class));
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_CODE_IMG_SELECTED_KEY, selectedIndex);

                    // 进入主页面
                    Intent intent = new IntentBuilder(LoginKeFuActivity.this)
                            .setTargetClass(ChatActivity.class)
//                            .setVisitorInfo(createVisitorInfo())
                            .setServiceIMNumber("kefuchannelimid_432631")
//                            .setScheduleQueue(MessageHelper.createQueueIdentity(queueName))
                            .setTitleName("高健小弟弟")
                            .setShowUserNick(true)
                            .setBundle(bundle)
                            .build();
                    startActivity(intent);
                    finish();
                }


            }
        });
    }

    /**
     * 发送带访客属性的消息
     */
    private VisitorInfo createVisitorInfo() {
        String nickName = UserManager.getNickName(LoginKeFuActivity.this);
        String username = UserManager.getUsername(LoginKeFuActivity.this);
        String phoneNumber = UserManager.getPhoneNumber(LoginKeFuActivity.this);
        String companyName = UserManager.getUserCompany(LoginKeFuActivity.this);
        String jobNumber = UserManager.getJobNumber(LoginKeFuActivity.this);
        VisitorInfo info = ContentFactory.createVisitorInfo(null);
        info.nickName(nickName)
                .name(username)
                .phone(phoneNumber)
                .companyName(companyName) // 公司名称
                .description(jobNumber);// 工号
        Message message = Message.createTxtSendMessage("", toChatUsername);
        message.addContent(info);
        ChatClient.getInstance().chatManager().sendMessage(message);
        return info;
    }


}
