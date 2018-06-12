package com.px.common.retrofit;

import com.px.common.retrofit.service.BaseService;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by patrick on 13/10/2017.
 * create time : 11:09 AM
 */

public class RetrofitMaster {

    private Retrofit retrofit;

    private RetrofitMaster(String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static RetrofitMaster instance;

    public static RetrofitMaster getInstance(String baseUrl){
        if(instance == null){
            synchronized (RetrofitMaster.class){
                if(instance == null){
                    instance = new RetrofitMaster(baseUrl);
                }
            }
        }
        return instance;
    }

    public <T> ResultHandler get(String url, Map<String, String> paramsMap, T clasz){
        BaseService baseService = retrofit.create(BaseService.class);
        Observable<Result<T>> observable = baseService.get(url, paramsMap, clasz);
        ResultHandler resultHandler = new ResultHandler();
        resultHandler.handle(observable);
        return resultHandler;
    }



}
