package com.ttg.ecollection.presenter;

import android.content.Context;

import com.ttg.ecollection.base.App;
import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.LoginResponse;
import com.ttg.ecollection.model.HomeModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.HomeFragment;
import com.ttg.ecollection.view.IHomeView;

import io.reactivex.disposables.Disposable;

/**
 * Created by loveb on 2018/3/12 0012.
 */

public class HomePresenter extends BasePresenter<HomeFragment> {

    private IHomeView mView;
    private Disposable disposable;
    private HomeModel mModel;

    public HomePresenter(IHomeView mView){
        this.mView = mView;
        this.mModel = new HomeModel();
    }

    public void getData(Context context){
        try {
            mModel.getData(context, App.userId, App.merchantId, new OnGetDataListener<LoginResponse>() {
                @Override
                public void success(LoginResponse response) {
                    mView.setCollectData(response);
                }

                @Override
                public void fail(String msg) {
                    if (null != disposable){
                        disposable.dispose();
                    }
                }

                @Override
                public void reLogin(String msg) {
                    mView.reLogin(msg);
                }

                @Override
                public void setDisposable(Disposable disposable) {
                    HomePresenter.this.disposable = disposable;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        if (null != disposable){
            disposable.dispose();
        }
    }

}
