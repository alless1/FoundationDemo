package com.alless.commonlib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.alless.commonlib.base.BaseApplication;
import com.alless.commonlib.manager.ActivityStackManager;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by chengjie on 2018/8/1
 * Description:崩溃捕获 本地保存 工具类
 * 示例：
 * File crash = getExternalFilesDir("crash_gsl");
 * UncaughtExceptionUtils.getInstance().init(crash.getAbsolutePath());
 */
public class UncaughtExceptionUtils {
    private static UncaughtExceptionUtils sUncaughtExceptionUtils;
    private Thread.UncaughtExceptionHandler mExceptionHandler;

    private UncaughtExceptionUtils() {
    }

    public static UncaughtExceptionUtils getInstance() {
        if (sUncaughtExceptionUtils == null) {
            synchronized (UncaughtExceptionUtils.class) {
                if (sUncaughtExceptionUtils == null) {
                    sUncaughtExceptionUtils = new UncaughtExceptionUtils();
                }
            }
        }
        return sUncaughtExceptionUtils;
    }

    public void init(String pathDir) {
        Thread.setDefaultUncaughtExceptionHandler(getExceptionHandler(pathDir));
    }

    private Thread.UncaughtExceptionHandler getExceptionHandler(final String pathDir) {
        mExceptionHandler = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread thread, Throwable ex) {
                saveCatchInfo2File(ex, pathDir);
                //MobclickAgent.reportError(BaseApplication.getInstance(),ex);
                ActivityStackManager.getInstance().finishAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        };
        return mExceptionHandler;
    }


    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称
     */
    private String saveCatchInfo2File(Throwable ex, String pathDir) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String sb = writer.toString();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String time = formatter.format(new Date());
            String fileName = time + ".txt";
            File filePath = new File(pathDir + File.separator + fileName);
            if (!filePath.exists()) {
                filePath.createNewFile();
                FileOutputStream fos = new FileOutputStream(filePath, true);
                fos.write(sb.getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            //System.out.println("an error occured while writing file..." + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
