package com.alless.example.leakcanary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
public class LeakActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leak;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.btn1)
    public void onViewClicked() {
        startActivity(new Intent(this,LeakActivity2.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
