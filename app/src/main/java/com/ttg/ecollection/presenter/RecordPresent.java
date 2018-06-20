package com.ttg.ecollection.presenter;

import android.content.Context;

import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.model.OrderModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.RecordFragment;
import com.ttg.ecollection.view.IRecordView;

import io.reactivex.disposables.Disposable;

/**
 * Created by loveb on 2018/3/21 0021.
 */

public class RecordPresent extends BasePresenter<RecordFragment> {
    private IRecordView mView;
    private OrderModel mModel;
    private Context mContext;
    private Disposable disposable;

    public RecordPresent(IRecordView mView,Context mContext){
        this.mContext = mContext;
        this.mView = mView;
        this.mModel = new OrderModel();
    }

    public void getData(Context context,String companyName,int page){
        try {
            mModel.getRecords(context,companyName, page,new OnGetDataListener<OrdersData>() {
                @Override
                public void success(OrdersData response) {
                    mView.setData(response);
                    mView.hideLoading();
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
                    RecordPresent.this.disposable = disposable;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        if (null != disposable && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
