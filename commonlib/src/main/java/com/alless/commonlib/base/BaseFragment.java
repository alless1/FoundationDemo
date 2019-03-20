package com.alless.commonlib.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chengjie on 2019/3/20
 * Description:
 */
public abstract class BaseFragment extends Fragment {

    private View mContextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isUserEvent()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContextView = inflater.inflate(getLayoutId(), container, false);
        return mContextView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mContextView);
        initData();
        initListener();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View contextView);

    protected abstract void initData();

    protected abstract void initListener();

    protected boolean isUserEvent() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isUserEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
