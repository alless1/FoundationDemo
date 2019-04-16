package com.alless.example.umeng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alless.commonlib.base.BaseActivity;
import com.alless.example.R;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chengjie on 2019/4/15
 * Description:
 */
public class UMTestActivity1 extends BaseActivity {
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;

    public static final String key1 = "person";
    public static final String key2 = "startActivity2";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_um_test1;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    int error = 10000;

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                //点击事件
                String name = "ZhangSan";
                String age = "23";
                String gender = "man";
                Map<String, Object> map = new HashMap<>();
                map.put("error", error++);

                MobclickAgent.onEventObject(this, key1, map);
   /*             View aa = null;
                aa.getWidth();*/
                break;
            case R.id.btn2:
                //跳转
                MobclickAgent.onEvent(this, key2);
                startActivity(new Intent(this, UMTestActivity2.class));
                break;
        }
    }
}
