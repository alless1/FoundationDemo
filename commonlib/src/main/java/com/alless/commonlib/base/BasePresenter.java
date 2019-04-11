package com.alless.commonlib.base;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * Created by chengjie on 2019/4/11
 * Description:
 */
public class BasePresenter<T> {
    protected Reference<T> mViewRef;

    public BasePresenter(T view){
        attachView(view);
    }

    private void attachView(T view) {
        mViewRef = new SoftReference<T>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null)
            mViewRef.clear();
    }
}
