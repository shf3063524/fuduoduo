package com.hjkj.fuduoduo.kefu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.DemoBaseActivity;
import com.hjkj.fuduoduo.tool.kefutool.Constant;
import com.hjkj.fuduoduo.tool.kefutool.MessageHelper;
import com.hjkj.fuduoduo.tool.kefutool.Preferences;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;

/**
 * 客服-页面
 */
public class LoginKeFuActivity extends DemoBaseActivity {

    private boolean progressShow;
    private ProgressDialog progressDialog;
    private int selectedIndex = Constant.INTENT_CODE_IMG_SELECTED_DEFAULT;
    private int messageToIndex = Constant.MESSAGE_TO_DEFAULT;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        Intent intent = getIntent();
        selectedIndex = intent.getIntExtra(Constant.INTENT_CODE_IMG_SELECTED_KEY,
                Constant.INTENT_CODE_IMG_SELECTED_DEFAULT);
        messageToIndex = intent.getIntExtra(Constant.MESSAGE_TO_INTENT_EXTRA, Constant.MESSAGE_TO_DEFAULT);

        //ChatClient.getInstance().isLoggedInBefore() 可以检测是否已经登录过环信，如果登录过则环信SDK会自动登录，不需要再次调用登录操作
        if (ChatClient.getInstance().isLoggedInBefore()) {
            progressDialog = getProgressDialog();
            progressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
            progressDialog.show();
            toChatActivity();
        } else {
            //随机创建一个用户并登录环信服务器
            createRandomAccountThenLoginChatServer();
        }

    }
    private void createRandomAccountThenLoginChatServer() {
        // 自动生成账号,此处每次都随机生成一个账号,为了演示.正式应从自己服务器获取账号
//        final String account = Preferences.getInstance().getUserName();
        final String account = "six_hao@163.com";
//        final String userPwd = Constant.DEFAULT_ACCOUNT_PWD;
        final String userPwd = "Lh20150930";
        progressDialog = getProgressDialog();
        progressDialog.setMessage(getString(R.string.system_is_regist));
        progressDialog.show();
        // createAccount to huanxin server
        // if you have a account, this step will ignore
        ChatClient.getInstance().createAccount(account, userPwd, new Callback() {
            @Override
            public void onSuccess() {
//                Log.d(TAG, "demo register success");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //登录环信服务器
                        login(account, userPwd);
                    }
                });
            }

            @Override
            public void onError(final int errorCode, String error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        if (errorCode == Error.NETWORK_ERROR){
                            Toast.makeText(getApplicationContext(), R.string.network_unavailable, Toast.LENGTH_SHORT).show();
                        }else if (errorCode == Error.USER_ALREADY_EXIST){
                            Toast.makeText(getApplicationContext(), R.string.user_already_exists, Toast.LENGTH_SHORT).show();
                        }else if(errorCode == Error.USER_AUTHENTICATION_FAILED){
                            Toast.makeText(getApplicationContext(), R.string.no_register_authority, Toast.LENGTH_SHORT).show();
                        } else if (errorCode == Error.USER_ILLEGAL_ARGUMENT){
                            Toast.makeText(getApplicationContext(), R.string.illegal_user_name, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), R.string.register_user_fail, Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
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
                switch (messageToIndex){
                    case Constant.MESSAGE_TO_AFTER_SALES:
                        queueName = "shouhou";
                        break;
                    case Constant.MESSAGE_TO_PRE_SALES:
                        queueName = "shouqian";
                        break;
                }
                if (messageToIndex == Constant.MESSAGE_TO_CHATROOM_LIST){
//                    startActivity(new Intent(LoginKeFuActivity.this,ChatRoomListActivity.class));
                    finish();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_CODE_IMG_SELECTED_KEY, selectedIndex);

                    // 进入主页面
                    Intent intent = new IntentBuilder(LoginKeFuActivity.this)
                            .setTargetClass(ChatActivity.class)
                            .setVisitorInfo(MessageHelper.createVisitorInfo())
                            .setServiceIMNumber(Preferences.getInstance().getCustomerAccount())
                            .setScheduleQueue(MessageHelper.createQueueIdentity(queueName))
//						.setScheduleAgent(MessageHelper.createAgentIdentity("ceshiok1@qq.com"))
                            .setShowUserNick(true)
                            .setBundle(bundle)
                            .build();
                    startActivity(intent);
                    finish();
                }



            }
        });
    }

}
