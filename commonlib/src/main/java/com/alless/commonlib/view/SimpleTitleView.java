package com.alless.commonlib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alless.commonlib.R;
import com.alless.commonlib.utils.ResUtils;
import com.alless.commonlib.utils.UnitUtils;

/**
 * Created by chengjie on 2019/4/16
 * Description:简单标题，只支持左侧
 */
public class SimpleTitleView extends RelativeLayout {

    private View mInflate;
    private ImageView mIv_left_icon;
    private ImageView mIv_right_icon;
    private TextView mTv_left_text;
    private TextView mTv_right_text;
    private TextView mTv_middle_text;

    public SimpleTitleView(Context context) {
        this(context,null);
    }

    public SimpleTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        iniView(context);
        initData();
        initHight();
    }


   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initHight() {
        setElevation(UnitUtils.dp2px(2));
    }

    private void iniView(Context context) {
        mInflate = LayoutInflater.from(context).inflate(R.layout.layout_title, this);
        mIv_left_icon = mInflate.findViewById(R.id.iv_left_icon);
        mIv_right_icon = mInflate.findViewById(R.id.iv_right_icon);
        mTv_left_text = mInflate.findViewById(R.id.tv_left_text);
        mTv_right_text = mInflate.findViewById(R.id.tv_right_text);
        mTv_middle_text = mInflate.findViewById(R.id.tv_middle_text);
        mIv_left_icon.setVisibility(GONE);
        mTv_left_text.setVisibility(GONE);
        mTv_middle_text.setVisibility(GONE);
        mIv_right_icon.setVisibility(GONE);
        mTv_right_text.setVisibility(GONE);
    }
    private void initData() {
        setBackgroundColor(ResUtils.getColor(R.color.sample_title_bg));
        mTv_middle_text.setTextColor(Color.WHITE);
        mTv_left_text.setTextColor(Color.WHITE);
        mTv_right_text.setTextColor(Color.WHITE);
    }

    public void setLeftIcon(@DrawableRes int imageId){
        mIv_left_icon.setImageResource(imageId);
        mIv_left_icon.setVisibility(VISIBLE);
        mTv_left_text.setVisibility(GONE);
    }
    public void setLeftText(@StringRes int stringId){
        mTv_left_text.setText(stringId);
        mTv_left_text.setVisibility(VISIBLE);
        mIv_left_icon.setVisibility(GONE);
    }

    public void setMidText(@StringRes int stringId){
        mTv_middle_text.setVisibility(VISIBLE);
        mTv_middle_text.setText(stringId);
    }

    public void setRightIcon(@DrawableRes int resId){
        mIv_right_icon.setImageResource(resId);
        mIv_right_icon.setVisibility(VISIBLE);
        mTv_right_text.setVisibility(GONE);
    }
    public void setRightText(@StringRes int resId){
        mTv_right_text.setText(resId);
        mTv_right_text.setVisibility(VISIBLE);
        mIv_right_icon.setVisibility(GONE);
    }

    public ImageView getIv_left_icon() {
        return mIv_left_icon;
    }

    public ImageView getIv_right_icon() {
        return mIv_right_icon;
    }

    public TextView getTv_left_text() {
        return mTv_left_text;
    }

    public TextView getTv_right_text() {
        return mTv_right_text;
    }
}
