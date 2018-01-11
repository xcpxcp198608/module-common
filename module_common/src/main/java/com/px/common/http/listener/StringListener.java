package com.px.common.http.listener;

import android.support.annotation.NonNull;

import com.px.common.utils.EmojiToast;

import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class StringListener implements Callback {

    public abstract void onSuccess(String s) throws IOException;
    public abstract void onFailure (String e);

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        if(e.getMessage() == null){
            return;
        }
        Flowable.just(e.getMessage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        onFailure(s);
                    }
                });
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if(response.code() == 400){
            EmojiToast.showLong("request error", EmojiToast.EMOJI_SAD);
            return;
        }
        if(response.code() == 404){
            EmojiToast.showLong("resource no found", EmojiToast.EMOJI_SAD);
            return;
        }
        if(response.code() == 408){
            EmojiToast.showLong("request timeout", EmojiToast.EMOJI_SAD);
            return;
        }
        if(response.code() == 500){
            EmojiToast.showLong("server exception", EmojiToast.EMOJI_SAD);
            return;
        }
        Observable.just(response)
                .map(new Function<Response, String>() {
                    @Override
                    public String apply(Response response) throws Exception {
                        try {
                            return response.body().string();
                        } catch (IOException e) {
                            return null;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        try {
                            if(value!=null) {
                                onSuccess(value);
                            }else{
                                onFailure("response data is empty");
                            }
                        } catch (IOException e) {
                            onFailure("response data is empty");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e.getMessage() != null) {
                            onFailure(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
