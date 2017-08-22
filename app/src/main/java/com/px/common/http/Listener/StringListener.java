package com.px.common.http.Listener;

import java.io.IOException;

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

    public abstract void onSuccess (String  s) throws IOException;
    public abstract void onFailure (String e);

    @Override
    public void onFailure(Call call, IOException e) {
        if(e.getMessage() == null){
            return;
        }
        Observable.just(e.getMessage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        onFailure(s);
                    }
                });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
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
