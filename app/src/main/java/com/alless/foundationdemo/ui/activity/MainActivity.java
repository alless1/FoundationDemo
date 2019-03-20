package com.alless.foundationdemo.ui.activity;

import android.content.Context;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alless.commonlib.adapter.list.ListBaseAdapter;
import com.alless.commonlib.adapter.list.ViewHolder;
import com.alless.commonlib.base.BaseActivity;
import com.alless.commonlib.http.retrofit.RequestCallBack;
import com.alless.commonlib.http.retrofit.RequestManager;
import com.alless.foundationdemo.R;
import com.alless.foundationdemo.ui.fragment.TestFragment1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chengjie on 2019/3/19
 * Description:
 */
public class MainActivity extends BaseActivity {

    private Button mBtn_1;
    private FrameLayout mFragment_container;
    private ListView mList_view;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mBtn_1 = findViewById(R.id.btn_1);
        mList_view = findViewById(R.id.list_view);
        mFragment_container = findViewById(R.id.fragment_container);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,TestActivity1.class));
                //showList();
                requestGet();
            }
        });
    }

    private void showFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new TestFragment1();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<String>() {{
            add("test1");
            add("test2");
            add("test3");
        }};
        BaseAdapter listAdapter = new MyAdapter(this, arrayList);
        mList_view.setAdapter(listAdapter);
    }

    private class MyAdapter extends ListBaseAdapter<String> {

        public MyAdapter(Context context, List<String> list) {
            super(context, list);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.list_item;
        }

        @Override
        protected void bindData(ViewHolder holder, String s, int position) {
            TextView tv_title = holder.getView(R.id.tv_title);
            tv_title.setText("String = " + s + " position = " + position);
        }
    }

    private void requestGet() {
        RequestManager.getInstance().doGetRequest("https://www.baidu.com", new RequestCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "onSuccess: " + s);
            }
            @Override
            public void onFail(int code, String msg) {
                Log.e(TAG, "onFail: code =" + code + " msg =" + msg);
                Log.e(TAG, "onFail: Thread =" + Thread.currentThread().getName());
                Log.e(TAG, "onFail: "+isMainThread() );
            }
        });
    }

    private void request() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder().url("https://www.baidu.com").get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "+Thread.currentThread());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: "+isMainThread() );
            }
        });

    }
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
