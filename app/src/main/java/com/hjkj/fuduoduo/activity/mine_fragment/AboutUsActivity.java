package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.demon.js_pdf.WebViewUtil;
import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.m_web_view)
    WebView mWebView;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initViews() {
        WebViewUtil.WebViewSetting(mWebView);
        //框架内部对WebView的默认设置
        WebViewUtil.WebViewSetting(mWebView);
        //框架内部对url进行了编码，防止因为中文无法加载的情况
        //yourpdf可以是链接，也可以是本地pdf文件路径
        String path = "file:///android_asset/关于我们.pdf";
        WebViewUtil.WebViewLoadPDF(mWebView, path);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoadingDialog();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoadingDialog();
            }
        });
    }
    @OnClick({R.id.m_layout_set_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_layout_set_return:   // 返回
                if (!clickBack()) {
                    finish();
                }
                break;
        }
    }
}
