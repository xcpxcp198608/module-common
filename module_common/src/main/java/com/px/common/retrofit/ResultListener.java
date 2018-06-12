package com.px.common.retrofit;

/**
 * Created by patrick on 2018/6/1.
 * create time : 2:12 PM
 */

public interface ResultListener<T> {

    void onSuccess(Boolean execute, T t);
    void onFailure(int code, String message);
}
