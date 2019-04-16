package com.alless.example.title;

import android.view.View;

import com.alless.commonlib.base.BaseActivity;
import com.alless.commonlib.utils.ResUtils;
import com.alless.commonlib.view.SimpleTitleView;
import com.alless.example.R;

/**
 * Created by chengjie on 2019/4/16
 * Description:
 */
public class TitleActivity extends BaseActivity {

    private SimpleTitleView mTitle_view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_title;
    }

    @Override
    protected void initView() {
        mTitle_view = findViewById(R.id.title_view);
        mTitle_view.setLeftIcon(R.mipmap.title_bar_back_white);
        mTitle_view.setMidText(R.string.app_name);
        mTitle_view.setRightIcon(R.mipmap.icon_setting_black);
        mTitle_view.getIv_left_icon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("leftIcon");
            }
        });
        mTitle_view.getIv_right_icon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("rightIcon");
            }
        });
        mTitle_view.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
