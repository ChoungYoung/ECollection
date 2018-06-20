package com.ttg.ecollection.service;

import com.ttg.ecollection.base.Constants;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.LoginResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkService {
    @FormUrlEncoded
    @POST(Constants.USER_LOGIN)
    Observable<BaseData> login(
            @FieldMap Map<String, Object> map
    );

    @FormUrlEncoded
    @POST(Constants.HOME_DATA)
    Observable<BaseData> getData(
            @FieldMap Map<String, Object> map
    );

    @FormUrlEncoded
    @POST(Constants.GENERATE_ORDER)
    Observable<BaseData> generateOrder(
            @FieldMap Map<String, Object> map
    );

    @FormUrlEncoded
    @POST(Constants.CHOOSE_PAYER)
    Observable<BaseData> getPayer(
            @FieldMap Map<String, Object> map
    );

    @FormUrlEncoded
    @POST(Constants.GENERATE_QRCODE)
    Observable<BaseData> generateQrCode(
            @FieldMap Map<String, Object> map
    );

   @FormUrlEncoded
    @POST(Constants.PAY_RESULT)
    Observable<BaseData> payResult(
            @FieldMap Map<String, Object> map
    );

   @FormUrlEncoded
    @POST(Constants.RECEIVABLE)
    Observable<BaseData> getReceivable(
            @FieldMap Map<String, Object> map
    );

   @FormUrlEncoded
    @POST(Constants.RECORDS)
    Observable<BaseData> getRecords(
            @FieldMap Map<String, Object> map
    );

   @FormUrlEncoded
    @POST(Constants.ORDERS)
    Observable<BaseData> getOrders(
            @FieldMap Map<String, Object> map
    );

   @FormUrlEncoded
    @POST(Constants.CHECK_ORDER)
    Observable<BaseData> checkOrder(
            @FieldMap Map<String, Object> map
    );

  @FormUrlEncoded
    @POST(Constants.RECEIVABLE_CASH)
    Observable<BaseData> receivableCash(
            @FieldMap Map<String, Object> map
    );

  @FormUrlEncoded
    @POST(Constants.CASH)
    Observable<BaseData> payCash(
            @FieldMap Map<String, Object> map
    );


    @FormUrlEncoded
    @POST(Constants.USER_LOGIN + "?mct_no={mct_no}")
    Observable<LoginResponse> loginWithMctKey(
            @Path("mct_no") String mct_no,
            @FieldMap Map<String, String> map
    );


}
