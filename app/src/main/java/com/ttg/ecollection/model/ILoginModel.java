package com.ttg.ecollection.model;

import android.content.Context;

import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.LoginResponse;
import com.ttg.ecollection.service.OnGetDataListener;

public interface ILoginModel {

    void login(Context context, String phone, String password, OnGetDataListener<LoginResponse> listener) throws Exception;

}
