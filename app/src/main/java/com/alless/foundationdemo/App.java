package com.alless.foundationdemo;

import com.alless.commonlib.base.BaseApplication;
import com.alless.commonlib.manager.ActivityLifeManager;
import com.umeng.commonsdk.UMConfigure;


/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public class App extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        initUM();
    }

    private void initUM() {
        String appKey = "5cb3fc13570df312e6000947";
        String channel = "test1";
        int deviceType = UMConfigure.DEVICE_TYPE_PHONE;
        String pushSecret = null;
        UMConfigure.init(this,appKey,channel,deviceType,pushSecret);
    }
}
