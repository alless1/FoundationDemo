package com.alless.commonlib.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alless.commonlib.utils.LogU;

/**
 * Created by chengjie on 2019/3/19
 * Description: 监听activity生命周期，判断应用是否在前台，应用假重启处理。
 */
public class ActivityLifeManager {
    private static final String TAG = "ActivityLifeManager";
    private static ActivityLifeManager sActivityLifeManager;
    private Application mApplication;
    private static final int APP_STATUS_UNKNOWN = -1;
    private static final int APP_STATUS_LIVE = 0;
    private int appStatus = APP_STATUS_UNKNOWN;
    private int appCount = 0;


    private ActivityLifeManager(){}
    public static ActivityLifeManager getInstance(){
        if(sActivityLifeManager==null){
            synchronized (ActivityLifeManager.class){
                if(sActivityLifeManager==null){
                    sActivityLifeManager = new ActivityLifeManager();
                }
            }
        }
        return sActivityLifeManager;
    }
    public void init(Application application){
        mApplication = application;
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityStackManager.getInstance().addActivity(activity);

            if (appStatus == APP_STATUS_UNKNOWN) {
                appStatus = APP_STATUS_LIVE;
                startLauncherActivity(activity);
            }

            if (savedInstanceState != null && savedInstanceState.getBoolean("saveStateKey", false)) {
                LogU.e(TAG, "localTime --> " + savedInstanceState.getLong("localTime"));
            }

        }

        @Override
        public void onActivityStarted(Activity activity) {
            appCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            // 弱引用持有当前 Activity 实例
            ActivityStackManager.getInstance().setCurrentActivity(activity);
            // Activity 页面栈方式
            ActivityStackManager.getInstance().setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            appCount--;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            outState.putBoolean("saveStateKey", true);
            outState.putLong("localTime", System.currentTimeMillis());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityStackManager.getInstance().removeActivity(activity);
        }
    };
    public boolean isForegroundAppValue() {
        return appCount > 0;
    }

    private static void startLauncherActivity(Activity activity) {
        try {
            Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
            String launcherClassName = launchIntent.getComponent().getClassName();
            String className = activity.getComponentName().getClassName();

            if (TextUtils.isEmpty(launcherClassName) || launcherClassName.equals(className)) {
                return;
            }

            launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(launchIntent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
