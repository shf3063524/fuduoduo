package com.hjkj.fuduoduo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjkj.fuduoduo.R;


/**
 * Created by 123 on 2018/8/23.
 */
public class GlobalClickItem extends RelativeLayout {
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private ImageView mIcon;
    private TextView mTvLeft;
    private ImageView mIconArrow;
    private TextView mTvRight;
    private int defaultLeftColor = Color.parseColor("#000000");
    private int defaultRightColor = Color.parseColor("#999999");
    private OnItemClickListener onItemClickListener;
    private TextView mTvLeft2;
    private RelativeLayout mLayoutLeft;
    private ImageView mIvMust;
    private ImageView mIvMust2;

    public GlobalClickItem(Context context) {
        this(context, null);
    }

    public GlobalClickItem(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GlobalClickItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(mContext);
        actionView();
        setAttrValue(attrs);
    }

    private void actionView() {
        mRelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mTvLeft, mTvRight);
                }
            }
        });
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.item_global_click, this);
        mLayoutLeft = (RelativeLayout) mRelativeLayout.findViewById(R.id.m_layout_left);
        mIcon = (ImageView) mRelativeLayout.findViewById(R.id.m_icon);
        mTvLeft = (TextView) mRelativeLayout.findViewById(R.id.m_tv_left);
        mTvLeft2 = (TextView) mRelativeLayout.findViewById(R.id.m_tv_left_2);
        mIconArrow = (ImageView) mRelativeLayout.findViewById(R.id.m_icon_arrow);
        mTvRight = (TextView) mRelativeLayout.findViewById(R.id.m_tv_right);
        mIvMust = (ImageView) mRelativeLayout.findViewById(R.id.m_iv_must);
        mIvMust2 = (ImageView) mRelativeLayout.findViewById(R.id.m_iv_must_2);
    }

    /**
     * 设置自定义属性值
     *
     * @param attrs res-->values-->attrs.xml
     */
    private void setAttrValue(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.GlobalClickItem);
        // 获取属性值
        String leftText = typedArray.getString(R.styleable.GlobalClickItem_left_text);
        String rightText = typedArray.getString(R.styleable.GlobalClickItem_right_text);
        Drawable icon = typedArray.getDrawable(R.styleable.GlobalClickItem_icon);
        Drawable arrow = typedArray.getDrawable(R.styleable.GlobalClickItem_arrow);
        int leftTextColor = typedArray.getColor(R.styleable.GlobalClickItem_left_text_color, defaultLeftColor);
        float leftTextSize = typedArray.getDimension(R.styleable.GlobalClickItem_left_text_size, 0);
        int rightTextColor = typedArray.getColor(R.styleable.GlobalClickItem_right_text_color, defaultRightColor);
        float rightTextSize = typedArray.getDimension(R.styleable.GlobalClickItem_right_text_size, 0);
        int isShowArrow = typedArray.getInt(R.styleable.GlobalClickItem_show_arrow, 0);
        int isShowLeftIcon = typedArray.getInt(R.styleable.GlobalClickItem_show_left_icon, 0);
        int isShowMustIcon = typedArray.getInt(R.styleable.GlobalClickItem_show_must_icon, 1);
        boolean clickable = typedArray.getBoolean(R.styleable.GlobalClickItem_clickable, true);

        // 设置属性
        mRelativeLayout.setClickable(clickable);
        if (icon != null) {
            mIcon.setImageDrawable(icon);
        }
        if (arrow != null) {
            mIconArrow.setImageDrawable(arrow);
        }
        if (!TextUtils.isEmpty(leftText)) {
            mTvLeft.setText(leftText);
            mTvLeft2.setText(leftText);
        }
        if (!TextUtils.isEmpty(rightText)) {
            mTvRight.setText(rightText);
        }
        mTvLeft.setTextColor(leftTextColor);
        mTvLeft2.setTextColor(leftTextColor);
        if (leftTextSize != 0) {
            mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
            mTvLeft2.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        }
        mTvRight.setTextColor(rightTextColor);
        if (rightTextSize != 0) {
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        }

        // 是否显示右侧的箭头
        switch (isShowArrow) {
            case 0:
                mIconArrow.setVisibility(View.VISIBLE);
                break;
            case 1:
                mIconArrow.setVisibility(View.GONE);
                break;
            case 2:
                mIconArrow.setVisibility(View.INVISIBLE);
                break;
        }
        // 是否显示左侧的图标
        switch (isShowLeftIcon) {
            case 0:
                mIcon.setVisibility(View.VISIBLE);
                mLayoutLeft.setVisibility(VISIBLE);
                mTvLeft2.setVisibility(GONE);
                break;
            case 1:
                mIcon.setVisibility(View.GONE);
                mLayoutLeft.setVisibility(GONE);
                mTvLeft2.setVisibility(VISIBLE);
                break;
            case 2:
                mIcon.setVisibility(View.INVISIBLE);
                mLayoutLeft.setVisibility(VISIBLE);
                mTvLeft2.setVisibility(GONE);
                break;
        }
        // 是否显示必填的图标
        switch (isShowMustIcon) {
            case 0:
                if (mLayoutLeft.getVisibility() == VISIBLE) {
                    mIvMust.setVisibility(View.VISIBLE);
                } else {
                    mIvMust2.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                mIvMust.setVisibility(View.GONE);
                mIvMust2.setVisibility(View.GONE);
                break;
            case 2:
                mIvMust.setVisibility(View.INVISIBLE);
                mIvMust2.setVisibility(View.INVISIBLE);
                break;
        }
        typedArray.recycle();
    }

    /**
     * 设置右边 textview 的文本
     *
     * @param rightText
     */
    public void setRightText(CharSequence rightText) {
        mTvRight.setText(rightText);
    }

    /**
     * 获取右边 textview 的文本
     *
     * @return
     */
    public String getRightText() {
        return mTvRight.getText().toString().trim();
    }

    /**
     * 设置左边 textview 的文本
     *
     * @param leftText
     */
    public void setLeftText(CharSequence leftText) {
        mTvLeft.setText(leftText);
        mTvLeft2.setText(leftText);
    }

    /**
     * 获取左边 textview 的文本
     *
     * @return
     */
    public String getLeftText() {
        return mTvLeft.getText().toString().trim();
    }

    /**
     * 获取arrow控件
     *
     * @return
     */
    public ImageView getArrow() {
        return mIconArrow;
    }

    public interface OnItemClickListener {
        /**
         * @param mTvLeft  左侧的textview
         * @param mTvRight 右边的textview，默认显示
         */
        void onItemClick(TextView mTvLeft, TextView mTvRight);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TextView getmTvLeft() {
        return mTvLeft;
    }

    public TextView getmTvRight() {
        return mTvRight;
    }
}
