package com.alless.commonlib.http.retrofit;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by chengjie on 2019/3/20
 * Description:
 */
public abstract class RequestCallBack<T> implements Callback {
    @Override
    public void onResponse(Call call, Response response) {
        String result = (String) response.body();
        try {
            //todo 解码
            //result = Base64Util.decryptString(AESUtils.decode(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int code = response.code();
        if (code == 200) {
            try {
                Gson gson = new Gson();
                Type mySuperClass = this.getClass().getGenericSuperclass();
                Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
                T object = gson.fromJson(result, type);
                onSuccess(object);
            } catch (Exception e) {
                e.printStackTrace();
                onFail(code, result);
            }
        } else {
            onFail(code, result);
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        onFail(-1, t.getMessage());
    }

    public abstract void onSuccess(T t);

    public abstract void onFail(int code, String msg);
}
