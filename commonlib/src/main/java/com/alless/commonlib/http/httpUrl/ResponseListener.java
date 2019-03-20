package com.alless.commonlib.http.httpUrl;

/**
 * Created by alless on 2018/8/4.
 * Description:
 */

public interface ResponseListener {
    void onResult(int code, String result);
    //void onFailure(int code,T response);
}
