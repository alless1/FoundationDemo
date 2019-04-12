package com.alless.commonlib.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.alless.commonlib.manager.ActivityLifeManager;
import com.alless.commonlib.utils.PathUtil;
import com.alless.commonlib.utils.UncaughtExceptionUtils;
import com.squareup.leakcanary.LeakCanary;

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

        initActivityLife();

        initCrashHandler();

        initLeakCanary();
    }

    private void initActivityLife() {
        ActivityLifeManager.getInstance().init(this);
    }

    private void initCrashHandler() {
        UncaughtExceptionUtils.getInstance().init(PathUtil.getExternalFilesDir(this));
    }

    private void initLeakCanary() {
        if(!LeakCanary.isInAnalyzerProcess(this)){
            LeakCanary.install(this);
        }
    }
}
