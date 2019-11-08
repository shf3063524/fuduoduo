package com.hjkj.fuduoduo.tool;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * 作者：kayroc on 2018/1/3 17:50
 * 邮箱：kayroc@126.com
 */
public class AnimoationUtils {

    /**
     * 旋转箭头向上
     * @param view 需要旋转的控件
     */
    public static void rotateArrowUpAnimation(View view, long durationMillis, int repeatCount, int repeatMode) {
        if (view == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(durationMillis);
        animation.setRepeatCount(repeatCount);
        animation.setRepeatMode(repeatMode);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    /**
     * 旋转箭头向下
     * @param view 需要旋转的图片控件
     */
    public static void rotateArrowDownAnimation(View view, long durationMillis, int repeatCount, int repeatMode) {
        if (view == null) return;
        RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(durationMillis);
        animation.setRepeatCount(repeatCount);
        animation.setRepeatMode(repeatMode);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    /**
     * 旋转箭头 右至下
     * @param view 需要旋转的控件
     */
    public static void rotateArrowRightToDownAnimation(View view, long durationMillis, int repeatCount, int repeatMode) {
        if (view == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(durationMillis);
        animation.setRepeatCount(repeatCount);
        animation.setRepeatMode(repeatMode);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    /**
     * 旋转箭头 下至右
     * @param view
     * @param durationMillis
     * @param repeatCount
     * @param repeatMode
     */
    public static void rotateArrowDownToRightAnimation(View view, long durationMillis, int repeatCount, int repeatMode) {
        if (view == null) return;
        RotateAnimation animation = new RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(durationMillis);
        animation.setRepeatCount(repeatCount);
        animation.setRepeatMode(repeatMode);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

}
