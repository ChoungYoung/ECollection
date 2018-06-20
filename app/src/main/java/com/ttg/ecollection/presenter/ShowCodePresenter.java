package com.ttg.ecollection.presenter;

import android.content.Context;
import android.util.Log;

import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.PayResult;
import com.ttg.ecollection.data.QrCodeData;
import com.ttg.ecollection.model.CodeModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.ShowCodeFragment;
import com.ttg.ecollection.view.IShowCodeView;
import com.ttg.ecollection.view.LoadingDialog;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by loveb on 2018/3/20 0020.
 */

public class ShowCodePresenter extends BasePresenter<ShowCodeFragment> {

    private IShowCodeView mView;
    private CodeModel mModel;
    private Disposable disposable,queryDisposable;
    private String orderId;

    public ShowCodePresenter(IShowCodeView mView){
        this.mView = mView;
        this.mModel = new CodeModel();
    }

    public void getQrCode(Context context,int payType,String orderId){
        if (queryDisposable != null && queryDisposable.isDisposed()){
            queryDisposable.dispose();
        }
        this.orderId = orderId;
        try {
            mModel.getCode(context,payType,orderId, new OnGetDataListener<QrCodeData>() {
                @Override
                public void success(QrCodeData response) {
                    mView.hideLoading();
                    mView.setCode(response.getUrl());
                    ShowCodePresenter.this.orderId = response.getOrderId();
                }

                @Override
                public void fail(String msg) {
                    if (null != disposable ){
                        disposable.dispose();
                    }
                    mView.hideLoading();
                    mView.showDialog(msg);
                }

                @Override
                public void reLogin(String msg) {
                    mView.reLogin(msg);
                }

                @Override
                public void setDisposable(Disposable disposable) {
                    ShowCodePresenter.this.disposable = disposable;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void queryPayResult(final Context context) {
        Observable.interval(2,3,TimeUnit.SECONDS).doOnNext((integer)->{
            try {
                mModel.queryPayResult(context, orderId, new OnGetDataListener<PayResult>() {
                    @Override
                    public void success(PayResult response) {
                        if (response.getOrderStatus() == 1){
                            if (null != queryDisposable){
                                queryDisposable.dispose();
                            }
                            mView.jumpToSuccess(response);
                        }
                    }

                    @Override
                    public void fail(String msg) {
                        if (null != queryDisposable){
                            queryDisposable.dispose();
                        }
                    }

                    @Override
                    public void reLogin(String msg) {
                        if (null != queryDisposable){
                            queryDisposable.dispose();
                        }
                        mView.reLogin(msg);
                    }

                    @Override
                    public void setDisposable(Disposable disposable) {

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                queryDisposable = d;
            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void destroy() {

        super.destroy();
        if (null != disposable){
            disposable.dispose();
        }
        if (null != queryDisposable){
            queryDisposable.dispose();
        }

    }
}
