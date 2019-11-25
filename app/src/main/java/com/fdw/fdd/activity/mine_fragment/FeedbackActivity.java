package com.fdw.fdd.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;
import com.fdw.fdd.base.BaseActivity;
import com.fdw.fdd.view.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 意见反馈页面
 * Author：Created by shihongfei on 2019/10/8 11:23
 * Email：1511808259@qq.com
 */
public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.m_et_content)
    ClearEditText mEtContent;
    @BindView(R.id.m_tv_Input_count)
    TextView mTvInputCount;
    @BindView(R.id.m_layout_determine)
    RelativeLayout mLayoutDetermine;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    @BindView(R.id.m_tv_determine)
    TextView mTvDetermine;
    private int num = 200;// 限制的最大字数
    private String inputText;
    private int selectionStart;
    private int selectionEnd;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void actionView() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                inputText = charSequence.toString().trim();

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                if (number > 0) {
                    mLayoutDetermine.setBackgroundResource(R.drawable.shape_frame_cl_e51c23_all);
                }else{
                    mLayoutDetermine.setBackgroundResource(R.drawable.shape_frame_cl_ccc_all);
                }
                mTvInputCount.setText(number + "/200");
                selectionStart = mEtContent.getSelectionStart();
                selectionEnd = mEtContent.getSelectionEnd();
                if (inputText.length() > num) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEtContent.setText(s);
                    mEtContent.setSelection(tempSelection);
                }
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
