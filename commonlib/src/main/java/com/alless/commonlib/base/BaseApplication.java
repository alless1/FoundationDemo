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
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifeManager.getInstance().init(this);
    }
}
