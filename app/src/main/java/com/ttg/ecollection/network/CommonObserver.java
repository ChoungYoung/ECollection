package com.ttg.ecollection.network;

import android.util.Log;

import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.service.OnGetDataListener;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CommonObserver<T extends BaseData>{

    private Disposable disposable;
    public Observer<T> getObserver(final OnGetDataListener<T> listener){
        return new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
                listener.setDisposable(d);
            }

            @Override
            public void onNext(@NonNull T response) {
                if (response.getCode() == 0){
                    listener.success(response);
                }else if (response.getCode() == 6){
                    listener.reLogin(response.getMsg());
                }else{
                    listener.fail(response.getMsg());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                listener.fail("服务器异常");
            }

            @Override
            public void onComplete() {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        };
    }



}
