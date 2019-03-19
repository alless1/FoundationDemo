package com.alless.foundationdemo;

import com.alless.commonlib.base.BaseApplication;
import com.alless.commonlib.manager.ActivityLifeManager;

/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public class App extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifeManager.getInstance().init(this);
    }
}
