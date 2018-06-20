package com.ttg.ecollection.presenter;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.model.OrderModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.ReceivableFragment;
import com.ttg.ecollection.view.IReceivableView;

import org.w3c.dom.Text;

import io.reactivex.disposables.Disposable;

/**
 * Created by loveb on 2018/3/16 0016.
 */

public class ReceivablePresenter extends BasePresenter<ReceivableFragment> {
    private IReceivableView mView;
    private OrderModel mModel;
    private Context mContext;
    private Disposable disposable;
    private long time;
    private long currentTime;

    public ReceivablePresenter(IReceivableView mView,Context mContext){
        this.mContext = mContext;
        this.mView = mView;
        this.mModel = new OrderModel();
    }

    public void getData(Context context,String companyName,int page){
        mView.showLoading("获取数据中",false);
        time = System.currentTimeMillis();
        try {
            mModel.getReceivable(context, companyName,page,new OnGetDataListener<OrdersData>() {
                @Override
                public void success(OrdersData response) {
                    currentTime = System.currentTimeMillis();
                    if (currentTime - time < 1000){
                        SystemClock.sleep(1000 - (currentTime - time));
                    }
                    mView.hideLoading();
                    mView.setData(response);
                }

                @Override
                public void fail(String msg) {
                    currentTime = System.currentTimeMillis();
                    if (currentTime - time < 1000){
                        SystemClock.sleep(1000 - (currentTime - time));
                    }
                    mView.hideLoading();
                    mView.showDialog(msg);
                    mView.getDataFail();
                    if (null != disposable && !disposable.isDisposed()){
                        disposable.dispose();
                    }
                }

                @Override
                public void reLogin(String msg) {
                    currentTime = System.currentTimeMillis();
                    if (currentTime - time < 1000){
                        SystemClock.sleep(1000 - (currentTime - time));
                    }
                    mView.hideLoading();
                    mView.reLogin(msg);
                }

                @Override
                public void setDisposable(Disposable disposable) {
                    ReceivablePresenter.this.disposable = disposable;
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
