package com.fdw.fdd.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fdw.fdd.R;
import com.fdw.fdd.tool.GlideUtils;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.ArrayList;

public class ProductPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> photos;

    public ProductPagerAdapter(Context context, ArrayList<String> photos) {
        this.context = context;
        this.photos = photos;
    }

    /**
     * 用于判断Viewpager是否可以复用
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object; // 固定写法
    }

    /**
     * 返回有多少页
     */
    @Override
    public int getCount() {
        return photos.size();
    }

    /**
     * 跟ListView中的Adpater中的getView方法类似，用于创建一个Item
     *
     * @param container ViewPager容器
     * @param position  要生成item的位置
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        // 产品图片
        String photo = photos.get(position);
        if (!TextUtils.isEmpty(photo) && photo.contains("http")) {
            GlideUtils.loadImage(context, photo, R.drawable.ic_all_background, imageView);
        } else {
            imageView.setBackgroundResource(R.drawable.ic_all_background);
        }
        // 将ImageView添加到ViewPager中
        container.addView(imageView);
        return imageView;

    }

    /**
     * 销毁一个Item
     *
     * @param container ViewPager容器
     * @param position  要销毁item的位置
     * @param object    instantiateItem方法的返回值
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
        if (object instanceof PhotoView) {
            PhotoView s = (PhotoView) object;
            BitmapDrawable bitmapDrawable = (BitmapDrawable) s.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bm = bitmapDrawable.getBitmap();
                if (bm != null && !bm.isRecycled()) {
                    s.setImageResource(0);
                    bm.recycle();
                }
            }
        }
    }
}
