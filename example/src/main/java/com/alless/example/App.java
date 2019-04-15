package com.alless.example;

import com.alless.commonlib.base.BaseApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * Created by chengjie on 2019/4/12
 * Description:
 */
public class App extends BaseApplication {
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
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
}
