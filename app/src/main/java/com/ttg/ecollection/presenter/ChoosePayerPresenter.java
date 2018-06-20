package com.ttg.ecollection.presenter;

import android.content.Context;

import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.PayerData;
import com.ttg.ecollection.model.PayerModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.ChoosePayerFragment;
import com.ttg.ecollection.view.IChoosePayerView;

import io.reactivex.disposables.Disposable;

/**
 * Created by loveb on 2018/3/19 0019.
 */

public class ChoosePayerPresenter extends BasePresenter<ChoosePayerFragment>{

    private IChoosePayerView mView;
    private PayerModel mModel;
    private Disposable disposable;

    public ChoosePayerPresenter(IChoosePayerView mView){
        this.mView = mView;
        mModel = new PayerModel();
    }

    public void getPayers(Context context,String companyName,int page){
        mView.showLoading("数据加载中",false);
        try {
            mModel.getPayers(context, companyName ,page,new OnGetDataListener<PayerData>() {
                @Override
                public void success(PayerData response) {
                    mView.hideLoading();
                    mView.setRecyclerView(response);
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
                    ChoosePayerPresenter.this.disposable = disposable;
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
