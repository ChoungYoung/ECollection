package com.ttg.ecollection.base;

/**
 * Created by loveb on 2018/3/8 0008.
 */

public interface BaseView {

    void showDialog(String msg);

    void showLoading(String msg,boolean isCanCancel);

    void hideLoading();

    void reLogin(String msg);
}
