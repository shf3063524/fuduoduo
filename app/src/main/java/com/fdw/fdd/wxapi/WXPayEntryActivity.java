package com.fdw.fdd.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fdw.fdd.R;
import com.fdw.fdd.tool.Constants;
import com.fdw.fdd.tool.SharedPrefUtil;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import es.dmoral.toasty.Toasty;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

		String weChartId = SharedPrefUtil.getString(this, getString(R.string.weChartId), "");
		api = WXAPIFactory.createWXAPI(this, weChartId);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		// 微信支付回调
		Logger.d("onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode) {
				case 0:
					Toasty.normal(this, "支付成功").show();
					SharedPrefUtil.putBoolean(this,Constants.WX_PAY_SUCCESS_FLAG, true);
					break;
				case -1:
					Toasty.normal(this, "支付失败").show();
					SharedPrefUtil.putBoolean(this, Constants.WX_PAY_SUCCESS_FLAG, false);
					break;
				case -2:
					Toasty.normal(this, "已取消支付").show();
					SharedPrefUtil.putBoolean(this, Constants.WX_PAY_SUCCESS_FLAG, false);
					break;
			}
			finish();
		}
	}
}