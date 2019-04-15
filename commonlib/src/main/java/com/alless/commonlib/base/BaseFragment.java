package com.alless.commonlib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alless.commonlib.utils.EventU;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by chengjie on 2019/3/20
 * Description:
 */
public abstract class BaseFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isUserEvent()) {
            EventU.register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(mRootView);
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
            EventU.unregister(this);
        }
    }

}
