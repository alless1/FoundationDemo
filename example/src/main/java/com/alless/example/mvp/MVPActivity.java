package com.alless.example.mvp;

import com.alless.commonlib.base.BaseActivity;
import com.alless.example.R;

/**
 * Created by chengjie on 2019/4/11
 * Description:
 */
public class MVPActivity extends BaseActivity implements MVPPresenter.View {

    private MVPPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter = new MVPPresenter(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onLoginSuccess() {
        showToast("登录成功");
    }

    @Override
    public void onLoginFail() {
        showToast("登录失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
