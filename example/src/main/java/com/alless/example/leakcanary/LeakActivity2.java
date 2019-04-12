package com.alless.example.leakcanary;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;

import com.alless.commonlib.base.BaseActivity;
import com.alless.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chengjie on 2019/4/12
 * Description:
 */
public class LeakActivity2 extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_leak2;
    }

    @Override
    protected void initView() {
        //TestManager instance = TestManager.getInstance(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout,new LeakFragment());
        transaction.commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }



}
