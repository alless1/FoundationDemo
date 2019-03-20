package com.alless.commonlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alless.commonlib.R;


/**
 * Created by chengjie on 2019/2/25
 * Description:
 */
public class TabView extends LinearLayout {

    private ImageView mIv_icon;
    private TextView mTv_title;

    private String mText;
    private int mColor_Normal;
    private int mColor_Checked;
    private Drawable mDrawable_Normal;
    private Drawable mDrawable_Checked;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_tab, this);
        mIv_icon = inflate.findViewById(R.id.iv_icon);
        mTv_title = inflate.findViewById(R.id.tv_title);
        //初始化xml设置属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabViewAttrs);
        mText = ta.getString(R.styleable.TabViewAttrs_tab_text);
        mDrawable_Normal =ta.getDrawable(R.styleable.TabViewAttrs_tab_icon_normal);
        mDrawable_Checked = ta.getDrawable(R.styleable.TabViewAttrs_tab_icon_checked);
        mColor_Normal = ta.getInt(R.styleable.TabViewAttrs_tab_textcolor_normal,0);
        mColor_Checked = ta.getInt(R.styleable.TabViewAttrs_tab_textcolor_checked,0);
        boolean aBoolean = ta.getBoolean(R.styleable.TabViewAttrs_tab_checked, false);
        ta.recycle();
        mTv_title.setText(mText);
        mTv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        setChecked(aBoolean);
    }

    public void setText(String text) {
        mText = text;
    }

    public void setTextColorId(int color_Normal, int color_Checked) {
        mColor_Normal = color_Normal;
        mColor_Checked = color_Checked;
    }

    public void setDrawable(Drawable drawable_Normal, Drawable drawable_Checked) {
        mDrawable_Normal = drawable_Normal;
        mDrawable_Checked = drawable_Checked;
    }

    public void setChecked(boolean isChecked) {
        if (isChecked) {
            mIv_icon.setBackground(mDrawable_Checked);
            mTv_title.setTextColor(mColor_Checked);
        }else {
            mIv_icon.setBackground(mDrawable_Normal);
            mTv_title.setTextColor(mColor_Normal);
        }
    }
}
