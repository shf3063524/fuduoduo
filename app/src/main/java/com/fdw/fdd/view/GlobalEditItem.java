package com.fdw.fdd.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fdw.fdd.R;


/**
 * 作者：kayroc on 2017/12/8 10:54
 * 邮箱：kayroc@126.com
 */
public class GlobalEditItem extends RelativeLayout {
    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private TextView mTvLeft;
    private ClearEditText mEtRight;
    private int defaultLeftColor = Color.parseColor("#333333");
    private int defaultRightColor = Color.parseColor("#333333");
    private int defaultRightHintColor = Color.parseColor("#999999");
    private ImageView mIvMust;
    private TextChangeListener textChangeListener;

    public GlobalEditItem(Context context) {
        this(context, null);
    }

    public GlobalEditItem(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public GlobalEditItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(mContext);
        setAttrValue(attrs);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.item_global_edit, this);
        mTvLeft = (TextView) mRelativeLayout.findViewById(R.id.m_tv_left);
        mEtRight = (ClearEditText) mRelativeLayout.findViewById(R.id.m_et_right);
        mIvMust = (ImageView) mRelativeLayout.findViewById(R.id.m_iv_must);
    }

    /**
     * 设置自定义属性值
     *
     * @param attrs res-->values-->attrs.xml
     */
    private void setAttrValue(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.GlobalEditItem);
        // 获取属性值
        String leftText = typedArray.getString(R.styleable.GlobalEditItem_leftText);
        String rightText = typedArray.getString(R.styleable.GlobalEditItem_rightText);
        String rightHint = typedArray.getString(R.styleable.GlobalEditItem_rightHint);
        int leftTextColor = typedArray.getColor(R.styleable.GlobalEditItem_leftTextColor, defaultLeftColor);
        float leftTextSize = typedArray.getDimension(R.styleable.GlobalEditItem_leftTextSize, 0);
        int rightTextColor = typedArray.getColor(R.styleable.GlobalEditItem_rightTextColor, defaultRightColor);
        float rightTextSize = typedArray.getDimension(R.styleable.GlobalEditItem_rightTextSize, 0);

        int rightTextColorHint = typedArray.getColor(R.styleable.GlobalEditItem_rightTextColorHint, defaultRightHintColor);
        int isShowMustIcon = typedArray.getInt(R.styleable.GlobalEditItem_showMustIcon, 1);
        int infoInputType = typedArray.getInt(R.styleable.GlobalEditItem_rightInputType, 0);

        typedArray.recycle();

        // 设置属性
        if (!TextUtils.isEmpty(leftText)) {
            mTvLeft.setText(leftText);
        }
        if (!TextUtils.isEmpty(rightText)) {
            mEtRight.setText(rightText);
        }
        if (!TextUtils.isEmpty(rightHint)) {
            mEtRight.setHint(rightHint);
        }
        mTvLeft.setTextColor(leftTextColor);
        if (leftTextSize != 0) {
            mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        }
        mEtRight.setTextColor(rightTextColor);
        if (rightTextSize != 0) {
            mEtRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        }
        mEtRight.setHintTextColor(rightTextColorHint);

        switch (infoInputType) {
            case 0: // textMultiLine
                mEtRight.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                //要使多行文本属性生效要加上下面两句才行
                mEtRight.setSingleLine(false);
                mEtRight.setHorizontallyScrolling(false);
                break;
            case 1: // phone
                mEtRight.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 2: // number
                mEtRight.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
        }

        // 是否显示必填的图标
        switch (isShowMustIcon) {
            case 0:
                mIvMust.setVisibility(View.VISIBLE);
                break;
            case 1:
                mIvMust.setVisibility(View.GONE);
                break;
            case 2:
                mIvMust.setVisibility(View.INVISIBLE);
                break;
        }

        mEtRight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (textChangeListener != null)
                    textChangeListener.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textChangeListener != null)
                    textChangeListener.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textChangeListener != null)
                    textChangeListener.afterTextChanged(s);
            }
        });
    }

    public abstract static class TextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable charSequence) {

        }
    }

    public void addTextChangeListener(TextChangeListener textChangeListener) {
        this.textChangeListener = textChangeListener;
    }

    /**
     * 设置右边 ClearEditText 的文本
     *
     * @param rightText
     */
    public void setRightText(CharSequence rightText) {
        mEtRight.setText(rightText);
    }

    /**
     * 获取右边 ClearEditText 的文本
     *
     * @return
     */
    public String getRightText() {
        return mEtRight.getText().toString().trim();
    }

    /**
     * 设置左边 TextView 的文本
     *
     * @param leftText
     */
    public void setLeftText(CharSequence leftText) {
        mTvLeft.setText(leftText);
    }

    /**
     * 获取左边 TextView 的文本
     *
     * @return
     */
    public String getLeftText() {
        return mTvLeft.getText().toString().trim();
    }

    /**
     * 设置左边 TextView 的颜色
     *
     * @param color
     */
    public void setLeftTextColor(int color) {
        mTvLeft.setTextColor(color);
    }

    /**
     * 设置右边 ClearEditText 的颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        mEtRight.setTextColor(color);
    }

    /**
     * 设置右边 ClearEditText 的颜色
     *
     * @param color
     */
    public void setRightTextColorHint(int color) {
        mEtRight.setTextColor(color);
    }

    public ClearEditText getmEtRight() {
        return mEtRight;
    }
}
