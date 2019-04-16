package com.alless.commonlib.utils;

import android.content.Context;
import android.util.TypedValue;

import com.alless.commonlib.base.BaseApplication;

/**
 * Created by chengjie on 2019/4/16
 * Description:
 */
public class UnitUtils {


    public static int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BaseApplication.getInstance().getResources().getDisplayMetrics());
    }
}
