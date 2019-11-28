package com.fdw.fdd.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fdw.fdd.tool.Constants;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import es.dmoral.toasty.Toasty;

/**
 * Create by kayroc on 2018/12/26
 * Email：kayroc@126.com
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_ID, false);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            if (!api.handleIntent(getIntent(), this)) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Logger.t("微信回调").d("get_message_from_wx");
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Logger.t("微信回调").d("show_message_from_wx");
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Toasty.normal(this, "分享成功").show();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toasty.normal(this, "分享取消").show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toasty.normal(this, "分享被拒绝").show();
                break;
            // case BaseResp.ErrCode.ERR_UNSUPPORT:
            //     Toasty.normal(this, "不支持分享").show();
            //     break;
            default:
                Toasty.normal(this, "分享返回").show();
                break;
        }

        finish();
    }
}
