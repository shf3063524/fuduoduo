package com.hjkj.fuduoduo.activity.mine_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;
import com.hjkj.fuduoduo.base.BaseActivity;
import com.hjkj.fuduoduo.tool.AnimoationUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 常见问题页面
 * Author：Created by shihongfei on 2019/10/8 17:07
 * Email：1511808259@qq.com
 */
public class CommonProblemActivity extends BaseActivity {
    @BindView(R.id.m_cv_one)
    CardView mCvOne;
    @BindView(R.id.m_iv_one)
    ImageView mIvOne;
    @BindView(R.id.m_tv_one)
    TextView mTvOne;
    @BindView(R.id.m_cv_two)
    CardView mCvTwo;
    @BindView(R.id.m_iv_two)
    ImageView mIvTwo;
    @BindView(R.id.m_tv_two)
    TextView mTvTwo;
    @BindView(R.id.m_cv_three)
    CardView mCvThree;
    @BindView(R.id.m_cv_four)
    CardView mCvFour;
    @BindView(R.id.m_cv_five)
    CardView mCvFive;
    @BindView(R.id.m_cv_six)
    CardView mCvSix;
    @BindView(R.id.m_cv_seven)
    CardView mCvSeven;
    @BindView(R.id.m_iv_three)
    ImageView mIvThree;
    @BindView(R.id.m_iv_four)
    ImageView mIvFour;
    @BindView(R.id.m_iv_five)
    ImageView mIvFive;
    @BindView(R.id.m_iv_six)
    ImageView mIvSix;
    @BindView(R.id.m_iv_seven)
    ImageView mIvSeven;
    @BindView(R.id.m_tv_three)
    TextView mTvThree;
    @BindView(R.id.m_tv_four)
    TextView mTvFour;
    @BindView(R.id.m_tv_five)
    TextView mTvFive;
    @BindView(R.id.m_tv_six)
    TextView mTvSix;
    @BindView(R.id.m_tv_seven)
    TextView mTvSeven;
    @BindView(R.id.m_layout_set_return)
    RelativeLayout mLayoutSetReturn;
    private boolean isOpen = false;

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, CommonProblemActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_common_problem;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.m_layout_set_return, R.id.m_cv_one, R.id.m_cv_two, R.id.m_cv_three, R.id.m_cv_four, R.id.m_cv_five, R.id.m_cv_six, R.id.m_cv_seven})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_layout_set_return:// 返回
                if (!clickBack()) {
                    finish();
                }
                break;
            case R.id.m_cv_one:   // 1
                if (isOpen) {
                    mCvOneGone(1);
                } else {
                    mCvOneVisible(1);
                    mCvOneGone(2);
                    mCvOneGone(3);
                    mCvOneGone(4);
                    mCvOneGone(5);
                    mCvOneGone(6);
                    mCvOneGone(7);
                }
                break;
            case R.id.m_cv_two:   // 2
                if (isOpen) {
                    mCvOneGone(2);
                } else {
                    mCvOneVisible(2);
                    mCvOneGone(1);
                    mCvOneGone(3);
                    mCvOneGone(4);
                    mCvOneGone(5);
                    mCvOneGone(6);
                    mCvOneGone(7);
                }
                break;
            case R.id.m_cv_three:   // 3
                if (isOpen) {
                    mCvOneGone(3);
                } else {
                    mCvOneVisible(3);
                    mCvOneGone(1);
                    mCvOneGone(2);
                    mCvOneGone(4);
                    mCvOneGone(5);
                    mCvOneGone(6);
                    mCvOneGone(7);
                }
                break;

            case R.id.m_cv_four:   // 4
                if (isOpen) {
                    mCvOneGone(4);
                } else {
                    mCvOneVisible(4);
                    mCvOneGone(1);
                    mCvOneGone(2);
                    mCvOneGone(3);
                    mCvOneGone(5);
                    mCvOneGone(6);
                    mCvOneGone(7);
                }
                break;
            case R.id.m_cv_five:   // 5
                if (isOpen) {
                    mCvOneGone(5);
                } else {
                    mCvOneVisible(5);
                    mCvOneGone(1);
                    mCvOneGone(2);
                    mCvOneGone(3);
                    mCvOneGone(4);
                    mCvOneGone(6);
                    mCvOneGone(7);
                }
                break;
            case R.id.m_cv_six:   // 6
                if (isOpen) {
                    mCvOneGone(6);
                } else {
                    mCvOneVisible(6);
                    mCvOneGone(1);
                    mCvOneGone(2);
                    mCvOneGone(3);
                    mCvOneGone(4);
                    mCvOneGone(5);
                    mCvOneGone(7);
                }
                break;
            case R.id.m_cv_seven:   // 7
                if (isOpen) {
                    mCvOneGone(7);
                } else {
                    mCvOneVisible(7);
                    mCvOneGone(1);
                    mCvOneGone(2);
                    mCvOneGone(3);
                    mCvOneGone(4);
                    mCvOneGone(5);
                    mCvOneGone(6);
                }
                break;
        }
    }

    private void mCvOneGone(int position) {
        switch (position) {
            case 1:
                isOpen = false;
                mTvOne.setVisibility(View.GONE);
                AnimoationUtils.rotateArrowDownAnimation(mIvOne, 200, 0, Animation.REVERSE);
                break;
            case 2:
                isOpen = false;
                mTvTwo.setVisibility(View.GONE);
                AnimoationUtils.rotateArrowDownAnimation(mIvTwo, 200, 0, Animation.REVERSE);
                break;
            case 3:
                isOpen = false;
                mTvThree.setVisibility(View.GONE);
                AnimoationUtils.rotateArrowDownAnimation(mIvThree, 200, 0, Animation.REVERSE);
                break;
            case 4:
                isOpen = false;
                mTvFour.setVisibility(View.GONE);
                AnimoationUtils.rotateArrowDownAnimation(mIvFour, 200, 0, Animation.REVERSE);
                break;
            case 5:
                isOpen = false;
                mTvFive.setVisibility(View.GONE);
                AnimoationUtils.rotateArrowDownAnimation(mIvFive, 200, 0, Animation.REVERSE);
                break;
            case 6:
                isOpen = false;
                mTvSix.setVisibility(View.GONE);
                AnimoationUtils.rotateArrowDownAnimation(mIvSix, 200, 0, Animation.REVERSE);
                break;
            case 7:
                isOpen = false;
                mTvSeven.setVisibility(View.GONE);
                AnimoationUtils.rotateArrowDownAnimation(mIvSeven, 200, 0, Animation.REVERSE);
                break;
        }
    }

    private void mCvOneVisible(int position) {
        switch (position) {
            case 1:
                isOpen = true;
                mTvOne.setVisibility(View.VISIBLE);
                AnimoationUtils.rotateArrowUpAnimation(mIvOne, 200, 0, Animation.REVERSE);
                break;
            case 2:
                isOpen = true;
                mTvTwo.setVisibility(View.VISIBLE);
                AnimoationUtils.rotateArrowUpAnimation(mIvTwo, 200, 0, Animation.REVERSE);
                break;
            case 3:
                isOpen = true;
                mTvThree.setVisibility(View.VISIBLE);
                AnimoationUtils.rotateArrowUpAnimation(mIvThree, 200, 0, Animation.REVERSE);
                break;
            case 4:
                isOpen = true;
                mTvFour.setVisibility(View.VISIBLE);
                AnimoationUtils.rotateArrowUpAnimation(mIvFour, 200, 0, Animation.REVERSE);
                break;
            case 5:
                isOpen = true;
                mTvFive.setVisibility(View.VISIBLE);
                AnimoationUtils.rotateArrowUpAnimation(mIvFive, 200, 0, Animation.REVERSE);
                break;
            case 6:
                isOpen = true;
                mTvSix.setVisibility(View.VISIBLE);
                AnimoationUtils.rotateArrowUpAnimation(mIvSix, 200, 0, Animation.REVERSE);
                break;
            case 7:
                isOpen = true;
                mTvSeven.setVisibility(View.VISIBLE);
                AnimoationUtils.rotateArrowUpAnimation(mIvSeven, 200, 0, Animation.REVERSE);
                break;
        }
    }
}
