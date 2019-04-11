package com.alless.commonlib.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.alless.commonlib.base.BaseApplication;

/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public class LogU {
    private static int SYSTEM = 1;
    private static int VERBOSE = 2;
    private static int DEBUG = 4;
    private static int INFO = 8;
    private static int WARN = 16;
    private static int ERROR = 32;
    private static int TEST = 64;
    private static int LOG_TYPE = SYSTEM | VERBOSE | DEBUG | INFO | WARN | ERROR | TEST;
    private static String TAG_DEFAULT = BaseApplication.getInstance().getPackageName();


    public static void i(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if ((LOG_TYPE & INFO) == INFO) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if ((LOG_TYPE & ERROR) == ERROR) {
            Log.e(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if ((LOG_TYPE & DEBUG) == DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if ((LOG_TYPE & WARN) == WARN) {
            Log.w(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if ((LOG_TYPE & VERBOSE) == VERBOSE) {
            Log.v(tag, message);
        }
    }

    public static void s(String message) {
        if (message == null) {
            return;
        }
        if ((LOG_TYPE & SYSTEM) == SYSTEM) {
            System.out.println(message);
        }
    }

    public static void t(String message) {
        if (message == null) {
            return;
        }
        if ((LOG_TYPE & TEST) == TEST) {
            Log.e(TAG_DEFAULT, message);
        }
    }
}
