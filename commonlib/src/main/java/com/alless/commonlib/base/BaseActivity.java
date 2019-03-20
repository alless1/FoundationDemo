package com.alless.commonlib.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.alless.commonlib.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG;
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = false;
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = false;
    /**
     * 是否允许旋转屏幕
     **/
    private boolean isAllowScreenRotate = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBase();
        initLayout();
        initView();
        initData();
        initListener();
    }

    private void initBase() {
        TAG = getComponentName().getShortClassName();
        if (mAllowFullScreen) {
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isSetStatusBar) {
            setStatusBar();
        }
        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (isUserEvent()) {
            EventBus.getDefault().register(this);
        }
        setContentView(getLayoutId());
    }

    protected void initLayout() {
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected boolean isUserEvent() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isUserEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * [沉浸状态栏]
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //5.0以上修改,设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //   getWindow().setStatusBarColor(ResUtils.getColor(R.color.window_background_black));
        }
    }



}
