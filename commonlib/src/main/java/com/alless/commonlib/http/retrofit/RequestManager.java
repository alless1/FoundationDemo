package com.alless.commonlib.http.retrofit;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.ErrorCode;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
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
    private static int CONNECT_TIMEOUT = 15;

    private RequestManager(){}
    static {
        init();
    }

    private static void init() {
        sRequestManager = new RequestManager();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
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

    /**
     * okHttp的方式下载文件
     * @param url
     * @param saveFilePath
     */
    public static void downloadFile(String url, final String saveFilePath){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .header("Cache-Control", "no-cache")
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.code() != 200 || response.body() == null) {
                    //error
                    return;
                }
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    int contentLength = (int) response.body().contentLength();
                    File file = new File(saveFilePath);
                    if (file.exists())
                        file.delete();
                    fos = new FileOutputStream(file);
                    byte[] buffer = new byte[10*1024];
                    int len = -1;
                    int lengthProgress = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        //lengthProgress += len;
                        //Log.e(TAG, "onResponse: lengthProgress =" + lengthProgress);
                    }
                    fos.close();
                    is.close();
                } catch (Exception e) {
                    try {
                        if (is != null)
                            is.close();
                        if (fos != null)
                            fos.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }

            }
        });
    }
    private static boolean isSecondTime = false;
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");
    public static void uploadFile(String url,String uploadFilePath){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        File file = new File(uploadFilePath);
        if (!file.exists()) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0以下 writeTo会调用两次
            isSecondTime = true;
        } else {
            isSecondTime = false;
        }

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Cache-Control", "no-cache")
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    //successCallBack((T) string, callBack);
                }
            }
        });
    }
    private static RequestBody createProgressRequestBody(final MediaType contentType, final File file) {
        return new RequestBody() {


            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

                Source source;
                source = Okio.source(file);
                Buffer buf = new Buffer();
                int remaining = (int) contentLength();
                int current = 0;
                long read = 0;
                while ((read = source.read(buf, 2048)) != -1) {
                    sink.write(buf, read);
                    current += read;
                }
            }
        };
    }
}
