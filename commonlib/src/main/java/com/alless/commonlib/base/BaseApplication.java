package com.alless.commonlib.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.alless.commonlib.manager.ActivityLifeManager;

/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    public static BaseApplication getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ActivityLifeManager.getInstance().init(this);
    }
}
