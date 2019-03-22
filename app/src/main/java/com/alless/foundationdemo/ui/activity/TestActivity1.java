package com.alless.foundationdemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alless.commonlib.base.BaseActivity;
import com.alless.commonlib.utils.EventU;
import com.alless.commonlib.utils.ToastU;
import com.alless.foundationdemo.R;
import com.alless.foundationdemo.ui.event.MessageEvent;

import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public class TestActivity1 extends BaseActivity {


    private TextView mTv;
    private EditText mEt;
    private Button mBtn;

    private List<Integer> mList = new LinkedList<>();
    private Button mBtn2;
    private Button mBtn3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test1;
    }

    @Override
    protected void initView() {
        mTv = findViewById(R.id.tv);
        mEt = findViewById(R.id.et);
        mBtn = findViewById(R.id.btn);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int speed = Integer.parseInt(mEt.getText().toString());
                addData(speed);
                Log.e(TAG, "onClick: " + mList.toString());
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastU.show(TestActivity1.this,"当前线程"+Thread.currentThread().getName());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                         ToastU.show(TestActivity1.this,"当前线程"+Thread.currentThread().getName());
                    }
                }).start();

            }
        });
        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventU.post(new MessageEvent());
            }
        });
    }

    @Override
    protected boolean isUserEvent() {
        return true;
    }


    private void addData(int value) {
        int size = mList.size();
        if(size==0){
            mList.add(value);
            return;
        }
        int maxValue = mList.get(0);
        int minValue = mList.get(size - 1);
        if(value>maxValue){
            mList.add(0,value);
            return;
        }
        if(value<minValue){
            mList.add(size,value);
            return;
        }
        int leftIndex = 0;
        int rightIndex = size - 1;
        int midIndex = (leftIndex + rightIndex) / 2;
        for (int i = 0; i < size; i++) {
            if (leftIndex < rightIndex) {
                int midValue = mList.get(midIndex);
                if (value > midValue) {
                    rightIndex = midIndex;
                    midIndex = (leftIndex + rightIndex) / 2;
                } else {
                    leftIndex = midIndex;
                    midIndex = (leftIndex + rightIndex) / 2;
                }
            } else {
                break;
            }
        }

        Log.e(TAG, "addData: leftIndex ="+leftIndex+" midIndex="+midIndex+" maxIndex="+rightIndex );
        mList.add(++midIndex, value);
    }


}
