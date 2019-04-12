package com.alless.example.leakcanary;

import android.content.Context;

/**
 * Created by chengjie on 2019/4/12
 * Description:
 */
public class TestManager {
    private static TestManager sTestManager;
    private Context sContext;

    private TestManager(Context context){
        sContext = context;
    }
    public static TestManager getInstance(Context context){
        if(sTestManager==null){
            synchronized (TestManager.class){
                if(sTestManager==null){
                    sTestManager = new TestManager(context);
                }
            }
        }
        return sTestManager;
    }
}
