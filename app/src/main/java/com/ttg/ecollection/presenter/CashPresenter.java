package com.ttg.ecollection.presenter;

import android.content.Context;

import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.OrderData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.PayResult;
import com.ttg.ecollection.data.QrCodeData;
import com.ttg.ecollection.model.OrderModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.CashFragment;
import com.ttg.ecollection.view.ICashView;

import io.reactivex.disposables.Disposable;

/**
 * Created by loveb on 2018/3/22 0022.
 */

public class CashPresenter extends BasePresenter<CashFragment> {

    private ICashView mView;
    private OrderModel mModel;
    private Disposable disposable;

    public CashPresenter(ICashView mView){
        this.mView = mView;
        this.mModel = new OrderModel();
    }

    public void generateOrder(final Context context, final OrdersData.ResultBean data){
        try {
            mModel.generateOrder(context, data, new OnGetDataListener<OrderData>() {
                @Override
                public void success(OrderData response) {
                    submitReceivableOrder(context,response.getOrderId(),data.getPayType());
                }

                @Override
                public void fail(String msg) {
                    if (null != disposable && !disposable.isDisposed()){
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
                    CashPresenter.this.disposable = disposable;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void submitReceivableOrder(final Context context, String orderId,int payType){
        try {
            mModel.submitReceivableOrder(context,orderId,payType, new OnGetDataListener<OrderData>() {
                @Override
                public void success(OrderData response) {

                    checkPayResult(context,response);

//                    mView.jumpToSuccess(response);
                }

                @Override
                public void fail(String msg) {
                    mView.showDialog(msg);
                    if (null != disposable && !disposable.isDisposed()){
                        disposable.dispose();
                    }
                }

                @Override
                public void reLogin(String msg) {
                    mView.reLogin(msg);
                }

                @Override
                public void setDisposable(Disposable disposable) {
                    CashPresenter.this.disposable = disposable;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkPayResult(Context context,OrderData orderData){
        try {
            mModel.queryPayResult(context, orderData.getOrderId(), new OnGetDataListener<PayResult>() {
                @Override
                public void success(PayResult response) {
                    if (response.getOrderStatus() == 1){
                        mView.jumpToSuccess(response);
                    }else{
                        mView.showDialog("支付失败");
                    }
                }

                @Override
                public void fail(String msg) {
                    mView.showDialog(msg);
                    if (null != disposable && !disposable.isDisposed()){
                        disposable.dispose();
                    }
                }

                @Override
                public void reLogin(String msg) {
                    mView.reLogin(msg);
                }

                @Override
                public void setDisposable(Disposable disposable) {
                   CashPresenter.this.disposable = disposable;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
