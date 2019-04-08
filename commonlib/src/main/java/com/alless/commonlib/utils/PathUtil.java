package com.alless.commonlib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by chengjie on 2018/11/13
 * Description:无需存储权限，获取应用包名下的目录
 */
public class PathUtil {

    /**
     * 对应系统设置 清除缓存
     * @param context
     * @return
     * /storage/emulated/0/Android/data/应用包名/cache
     */
    public static String getExternalCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     *对应系统设置 清除数据
     * @param context
     * @return
     * /storage/emulated/0/Android/data/应用包名/files
     */
    public static String getExternalFilesDir(Context context) {
        String filesDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            filesDir = context.getExternalFilesDir(null).getPath();
        } else {
            filesDir = context.getFilesDir().getPath();
        }
        return filesDir;
    }

    public static String createEmptyFile(Context context, int size) {
        String cacheDir = getExternalCacheDir(context);
        File file = new File(cacheDir, "uploadEmpty.zip");
        if (file.exists()) {
            if (file.length() == size) {
                return file.getAbsolutePath();
            } else {
                file.delete();
            }
        }
        try {
            create(file, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public static void create(File file, long length) throws IOException {
        RandomAccessFile r = null;
        try {
            r = new RandomAccessFile(file, "rw");
            r.setLength(length);
        } finally {
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
