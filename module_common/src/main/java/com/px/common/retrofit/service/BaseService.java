package com.px.common.retrofit.service;

import com.px.common.retrofit.Result;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by patrick on 13/10/2017.
 * create time : 11:30 AM
 */

public interface BaseService {

    @GET("{url}")
    <T> Observable<Result<T>> get(@Path("url") String url, @QueryMap Map<String, String> paramsMap,
                                  T clasz);


    @POST("{url}")
    <T> Observable<Result<T>> post(@Path("url") String url, @QueryMap Map<String, String> paramsMap,
                                   T clasz);
}
