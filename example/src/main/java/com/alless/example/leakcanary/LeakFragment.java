package com.alless.example.leakcanary;

import android.util.Log;
import android.view.View;

import com.alless.commonlib.base.BaseFragment;
import com.alless.example.R;

/**
 * Created by chengjie on 2019/4/12
 * Description:
 */
public class LeakFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_leak2;
    }

    @Override
    protected void initView(View contextView) {
        Log.e(TAG, "initView: " );
        TestManager instance = TestManager.getInstance(getActivity());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
