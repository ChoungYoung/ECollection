package com.ttg.ecollection.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ttg.ecollection.ui.LoginActivity;
import com.ttg.ecollection.util.UIUtil;
import com.ttg.ecollection.view.LoadingDialog;

/**
 * Created by loveb on 2018/3/8 0008.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements View.OnClickListener,BaseView{

    protected T basePresenter;

    public abstract T initPresent();

    @Override
    public void showDialog(String msg) {
        UIUtil.showToast(getActivity(),msg);
    }

    @Override
    public void showLoading(String msg,boolean isCanCancel) {
        LoadingDialog.showProgress(getActivity(),msg,isCanCancel);
    }

    @Override
    public void hideLoading() {
        LoadingDialog.dismissProgress();
    }

    @Override
    public void reLogin(String msg) {
        showDialog(msg);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        basePresenter = initPresent();

        View view = inflater.inflate(bindLayout(), container, false);
        initView(view);
        return view;
    }

    public abstract int bindLayout() ;
    public abstract void initView(View view) ;

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    private long lastClick = 0;
    /**防止快速点击 */
    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    /**View点击**/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }

}
