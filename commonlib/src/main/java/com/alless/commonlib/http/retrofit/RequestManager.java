package com.alless.commonlib.http.retrofit;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by chengjie on 2019/3/20
 * Description:
 */
public class RequestManager {
    private static RequestManager sRequestManager;
    private static IRetrofitService sIRetrofitService;

    private RequestManager(){}
    static {
        init();
    }

    private static void init() {
        sRequestManager = new RequestManager();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.google.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();
        sIRetrofitService = retrofit.create(IRetrofitService.class);
    }

    public static RequestManager getInstance() {
        return sRequestManager;
    }
    public void doGetRequest(String url,@NonNull RequestCallBack callBack){
        Call<String> call = sIRetrofitService.doGetRequest(url);
        call.enqueue(callBack);
    }
    public void doPostRequest(String url,String json,@NonNull RequestCallBack callBack){
        try {
            //todo 加密请求内容
            //json = AESUtils.encode(Base64Util.encryptString(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Call<String> call = sIRetrofitService.doPostRequest(url, body);
        call.enqueue(callBack);
    }
    public static void doUploadFileRequest(String url, @NonNull String filePath, @NonNull RequestCallBack callBack) {
        //构建要上传的文件
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imgfile", file.getName(), requestFile);
        String descriptionString = "This is a description";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        Call<String> call = sIRetrofitService.doUploadFile(url, description, body);
        call.enqueue(callBack);
    }
}
