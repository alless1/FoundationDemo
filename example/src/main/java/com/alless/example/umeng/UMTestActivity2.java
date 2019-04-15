package com.alless.example.umeng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.alless.commonlib.base.BaseActivity;
import com.alless.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chengjie on 2019/4/15
 * Description:
 */
public class UMTestActivity2 extends BaseActivity {
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_um_test2;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.frame_layout);
        if(fragment==null){
            fragment = new UMTestFragment2();
            fm.beginTransaction()
                    .add(R.id.frame_layout,fragment)
                    .commit();
        }
    }

    @Override
    protected void initListener() {

    }

}
