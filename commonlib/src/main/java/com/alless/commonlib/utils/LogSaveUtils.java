package com.alless.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by chengjie on 2018/12/24.
 * Description:保存日志到本地。
 */

public class LogSaveUtils {

    private static boolean isInit;
    private final String SEPARATOR = " ";
    private Handler mHandler;
    private Date date;
    private SimpleDateFormat dateFormat;
    private String mLogFolderPath;
    private final String fileName = "test_log.txt";

    private static LogSaveUtils sLogSaveUtils;

    static class WriteHandler extends Handler {

        @NonNull
        private final int maxFileSize = 1024 * 100;
        private String mLogFile;


        WriteHandler(@NonNull Looper looper, @NonNull String logFile) {
            super(checkNotNull(looper));
            mLogFile = checkNotNull(logFile);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            String content = (String) msg.obj;
            File logFile = new File(mLogFile);
/*            if (logFile.exists() && logFile.length() > maxFileSize) {
                logFile.delete();
            }*/

            FileWriter fileWriter = null;

            try {
                fileWriter = new FileWriter(logFile, true);
                fileWriter.append(content);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e1) {

                    }
                }
            }
        }
    }

    public static void init(Context context) {
        if (sLogSaveUtils == null) {
            synchronized (LogSaveUtils.class) {
                if (sLogSaveUtils == null) {
                    sLogSaveUtils = new LogSaveUtils(context);
                    isInit = true;
                }

            }
        }
    }

    public static LogSaveUtils getInstance() {
        return sLogSaveUtils;
    }

    private LogSaveUtils(Context context) {
        String folder = context.getExternalCacheDir().getAbsolutePath() + File.separatorChar + "logger";
        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        File file = new File(folderFile, fileName);
        mLogFolderPath = file.getAbsolutePath();
        mHandler = new WriteHandler(ht.getLooper(), mLogFolderPath);
    }

    public void saveLog(String tag, String message) {
        if (!isInit)
            return;
        if (date == null) {
            date = new Date();
        }
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        }

        date.setTime(System.currentTimeMillis());

        StringBuilder builder = new StringBuilder();

        builder.append(dateFormat.format(date));

        builder.append(SEPARATOR);
        builder.append(tag);

        builder.append(SEPARATOR);
        builder.append(message);

        // new line
        builder.append("\r\n");

        mHandler.sendMessage(mHandler.obtainMessage(0, builder.toString()));
    }

    public void clearLog() {
        File logFile = new File(mLogFolderPath);
        if (logFile.exists()) {
            logFile.delete();
        }
    }


    /**
     * 在工作线程
     *
     * @return
     */
/*    public  void printLog() {
        if(TextUtils.isEmpty(mLogFolderPath))
            return;
        File newFile = new File(mLogFolderPath);
        StringBuilder builder = new StringBuilder();
        if (!newFile.exists())
            return "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Log.e("jie", "onClick: " + line);
                builder.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }*/
    public void openLog(Context context) {

        if (TextUtils.isEmpty(mLogFolderPath))
            return;
        File file = new File(mLogFolderPath);
        if (!file.exists())
            return;
/*        File file = new File(mLogFolderPath);
        if (!file.exists())
            return;
        Uri path = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "text/plain");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);*/


        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", new File(mLogFolderPath));
            intent.setDataAndType(contentUri, "text/plain");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(mLogFolderPath)), "text/plain");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);

    }

    @NonNull
    static <T> T checkNotNull(@Nullable final T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }
}
