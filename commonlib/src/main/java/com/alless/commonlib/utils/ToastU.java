package com.alless.commonlib.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by chengjie on 2019/3/22
 * Description:
 */
public class ToastU {

    private static Toast sToast;

    public static void show(final Context context, final String msg) {
        if (sToast == null) {
           if( Looper.getMainLooper().getThread() == Thread.currentThread()){
               makeText(context,msg);
           }else {
              new Handler(Looper.getMainLooper()).post(new Runnable() {
                  @Override
                  public void run() {
                      makeText(context,msg);
                  }
              });
           }
        }else {
            sToast.setText(msg);
            sToast.show();
        }

    }

    private static void makeText(Context context, String msg){
        sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.setText(msg);
        sToast.show();
    }
}
