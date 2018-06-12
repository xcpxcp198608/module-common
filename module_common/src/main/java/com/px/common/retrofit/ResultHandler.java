package com.px.common.retrofit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * Created by patrick on 2018/6/1.
 * create time : 2:14 PM
 */

public class ResultHandler {

    private ResultListener resultListener;

    public <T> void listener(ResultListener<T> resultListener){
        this.resultListener = resultListener;
    }

    public <T> void handle(Observable<Result<T>> observable){
        observable.map(new Function<Result<T>, Result<T>>() {
            @Override
            public Result<T> apply(Result<T> tResult) throws Exception {
                return tResult;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<T> tResult) {
                        if(tResult.getCode() == 200) {
                            resultListener.onSuccess(true, tResult.getData());
                        }else{
                            resultListener.onSuccess(false, null);
                            resultListener.onFailure(tResult.getCode(), tResult.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultListener.onFailure(0, e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
