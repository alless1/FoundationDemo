package com.alless.commonlib.http.retrofit;

import android.database.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by chengjie on 2019/3/20
 * Description:
 */
public interface IRetrofitService {
    @GET
    Call<String> doGetRequest(@Url String url);

    @POST
    Call<String> doPostRequest(@Url String url, @Body RequestBody body);

    @Multipart
    @POST
    Call<String> doUploadFile(@Url String url, @Part("description") RequestBody description, @Part MultipartBody.Part file);

    @Streaming
    @GET
    Observable<ResponseBody> doDownloadFile(@Url String url);
}
