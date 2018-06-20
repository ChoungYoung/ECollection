package com.ttg.ecollection.presenter;

import android.content.Context;
import android.util.Log;

import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.OrderStatusData;
import com.ttg.ecollection.model.CheckOrderModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.ReceivableDetailFragment;
import com.ttg.ecollection.view.IReceivableDetailView;

import io.reactivex.disposables.Disposable;

public class ReceivableDetailPresenter extends BasePresenter<ReceivableDetailFragment> {
    private IReceivableDetailView mView;
    private CheckOrderModel mModel;
    private Disposable disposable;

    public ReceivableDetailPresenter(IReceivableDetailView mView){
        this.mView = mView;
        this.mModel = new CheckOrderModel();
    }

    public void checkOrder(Context context, String paymentCode, String userCode, String reciprocalAmt, String remark){
//        try {
//            mModel.checkOrder(context, paymentCode, userCode, reciprocalAmt, remark, new OnGetDataListener<OrderStatusData>() {
//                @Override
//                public void success(OrderStatusData response) {
//                    mView.checkout(response);
//                }
//
//                @Override
//                public void fail(String msg) {
//                    Log.e("haha","faila: "+ msg);
//
//                    if (null != disposable && !disposable.isDisposed()){
//                        disposable.dispose();
//                    }
//                }
//
//                @Override
//                public void setDisposable(Disposable disposable) {
//                    ReceivableDetailPresenter.this.disposable = disposable;
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @Override
    public void destroy() {
        if (null != disposable && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

}
