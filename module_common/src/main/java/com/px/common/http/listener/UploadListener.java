package com.px.common.http.listener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class UploadListener implements Callback {

    public abstract void onSuccess(Response response) throws IOException;
    public abstract void onFailure(String e);

    @Override
    public void onFailure(Call call, IOException e) {
        onFailure(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        onSuccess(response);
    }
}