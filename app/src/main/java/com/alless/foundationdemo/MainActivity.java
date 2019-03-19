package com.alless.foundationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alless.commonlib.base.BaseActivity;

/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public class MainActivity extends BaseActivity {

    private Button mBtn_1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mBtn_1 = findViewById(R.id.btn_1);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TestActivity1.class));
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
    }
}
