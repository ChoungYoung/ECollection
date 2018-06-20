package com.ttg.ecollection.service;

import io.reactivex.disposables.Disposable;

public interface OnGetDataListener<T> {
    void success(T response); // 网络操作成功
    void fail(String msg); // 网络操作失败
    void reLogin(String msg); // 重新登录
    void setDisposable(Disposable disposable);
}
