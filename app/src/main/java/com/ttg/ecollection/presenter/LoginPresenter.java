package com.ttg.ecollection.presenter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.ttg.ecollection.base.App;
import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.base.Constants;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.LoginResponse;
import com.ttg.ecollection.model.LoginModel;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.ui.LoginFragment;
import com.ttg.ecollection.ui.MainActivity;
import com.ttg.ecollection.util.CacheUtils;
import com.ttg.ecollection.view.ILoginView;

import io.reactivex.disposables.Disposable;

/**
 * Created by loveb on 2018/3/8 0008.
 */

public class LoginPresenter extends BasePresenter<LoginFragment> {
    private ILoginView mView;
    private LoginModel mModel;
    private Disposable disposable;

    public LoginPresenter(ILoginView iLoginView) {
        this.mView = iLoginView;
        this.mModel = new LoginModel();
    }

    public void login(final Context context, String account, String password){
        //TODO
//        mView.jumpToActivity(MainActivity.class,true,null);


        mView.showLoading("正在登陆中",false);
        try{
            mModel.login(context, account, password, new OnGetDataListener<LoginResponse>() {
                @Override
                public void success(LoginResponse response) {

                    App.userId = response.getUserId();
                    App.merchantId = response.getMerchantId();

                    mView.hideLoading();
                    mView.jumpToActivity(MainActivity.class,true,response);
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
                    LoginPresenter.this.disposable = disposable;
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
