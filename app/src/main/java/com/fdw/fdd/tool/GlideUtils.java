package com.fdw.fdd.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * glide-transformations:一个Android转换库，为Glide提供各种图像转换
 * 作者：kayroc on 2018/7/9 19:08
 * 邮箱：kayroc@126.com
 */
public class GlideUtils {

    /**
     * 加载图片
     *
     * @param context     上下文
     * @param url         服务器得到的数据
     * @param alternative 替代图片  (服务/商品图片:img_default) (商家图片/客户图片/会员卡/优惠券/:card_preloading_b) (店铺logo:shop_logo)
     * @param imageView
     */
    public static void loadImage(Context context, String url, int alternative, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                // .centerCrop()
                .placeholder(alternative) //占位图
                .error(alternative)       //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       服务器得到的数据
     * @param roundImg  替代图片
     * @param imageView
     */
    public static void loadCircleHeadImage(Context context, String url, int roundImg, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(roundImg)
                .error(roundImg)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 为指定view设置背景
     *
     * @param context 上下文
     * @param url     服务器得到的数据
     * @param view
     */
    public static void loadBgForTheView(Context context, String url, View view) {
        Glide.with(context)
                .load(url)
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }
                });
    }

}
