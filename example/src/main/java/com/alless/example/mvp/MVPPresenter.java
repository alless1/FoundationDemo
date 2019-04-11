package com.alless.example.mvp;

import com.alless.commonlib.base.BasePresenter;

/**
 * Created by chengjie on 2019/4/11
 * Description:
 */
public class MVPPresenter extends BasePresenter<MVPPresenter.View> {

    public MVPPresenter(View view) {
        super(view);
    }

    interface View {
        void onLoginSuccess();
        void onLoginFail();
    }

    public void onLogin(){
        //todo
        if(!isViewAttached())
            return;
        getView().onLoginSuccess();
    }
}
