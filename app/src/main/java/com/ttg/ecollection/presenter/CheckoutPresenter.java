package com.ttg.ecollection.presenter;

import android.content.Context;
import android.util.Log;

import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.OrderStatusData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.QrCodeData;
import com.ttg.ecollection.model.CheckOrderModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.CheckoutFragment;
import com.ttg.ecollection.view.ICheckoutView;

import io.reactivex.disposables.Disposable;

/**
 * Created by loveb on 2018/3/16 0016.
 */

public class CheckoutPresenter extends BasePresenter<CheckoutFragment> {
    private ICheckoutView mView;
    private CheckOrderModel mModel;
    private Disposable disposable;

    public CheckoutPresenter(ICheckoutView mView){
        this.mView = mView;
        this.mModel = new CheckOrderModel();
    }

    public void checkOrder(Context context, OrdersData.ResultBean data){
        mView.showLoading("请稍候",false);
        try {
            mModel.checkOrder(context, data.getPaymentCode(), data.getUserCode(), data.getEpayAmt(),data.getCashAmt(),data.getPosAmt(), data.getRemark(), new OnGetDataListener<OrderStatusData>() {
                @Override
                public void success(OrderStatusData response) {

                    if (response.getOrderStatus() == 0 && data.getEpayAmt() > 0){
                        mView.hideLoading();
                        mView.jump2ShowCode(response);

//                        generateCode(context,response);
                    }else if (response.getOrderStatus() == 0){
                        payByCash(context,response);
                    }
                }

                @Override
                public void fail(String msg) {
                    mView.hideLoading();
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
                    CheckoutPresenter.this.disposable = disposable;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void payByCash(Context context,OrderStatusData response) {
        try {
            mModel.payCash(context,response.getOrderId(), new OnGetDataListener<String>() {
                @Override
                public void success(String baseData) {
                    mView.hideLoading();
                    mView.jump2Success(response);
                }

                @Override
                public void fail(String msg) {
                    mView.hideLoading();
                    mView.showDialog(msg);
                }

                @Override
                public void reLogin(String msg) {
                    mView.reLogin(msg);
                }

                @Override
                public void setDisposable(Disposable disposable) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void generateCode(Context context,OrderStatusData response){
        try {
            mModel.getCode(context, response.getPayType(), response.getOrderId(), new OnGetDataListener<QrCodeData>() {
                @Override
                public void success(QrCodeData response) {
                    //TODO 跳转
                }

                @Override
                public void fail(String msg) {

                }

                @Override
                public void reLogin(String msg) {
                    mView.reLogin(msg);
                }

                @Override
                public void setDisposable(Disposable disposable) {

                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public void destroy() {
        if (null != disposable && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
