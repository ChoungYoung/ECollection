package com.ttg.ecollection.model;

import android.content.Context;
import android.util.Log;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.base.Constants;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.OrderData;
import com.ttg.ecollection.data.OrderStatusData;
import com.ttg.ecollection.data.QrCodeData;
import com.ttg.ecollection.network.CommonObserver;
import com.ttg.ecollection.network.RetrofitApi;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.util.CryptoUtil;
import com.ttg.ecollection.util.StreamUtils;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CheckOrderModel {

    public void checkOrder(final Context context, String paymentCode, String userCode, double ePayAmt, double cashAmt,double posAmt,String remark,final OnGetDataListener<OrderStatusData> listener)throws Exception{
        RetrofitApi.checkOrder(context,paymentCode,userCode,ePayAmt,cashAmt,posAmt,remark).map(new Function<BaseData, OrderStatusData>() {
            @Override
            public OrderStatusData apply(@NonNull BaseData baseBean) throws Exception {
                String strData = baseBean.getData();
                String sign = baseBean.getSign();
                if (null != strData && baseBean.getCode() == 0){

                    JSONObject object = new JSONObject(StreamUtils.creatJson(baseBean));
                    object.remove("sign");

                    String respSign= StreamUtils.sortjson(object);
                    String sha= CryptoUtil.SHA1(respSign);
                    //验证签名
                    if(CryptoUtil.verify(sha.getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_public_key"),sign)){
                        try {
                            String data = CryptoUtil.AESDecrypt(strData, Constants.AES_KEY);

                            return StreamUtils.getBean(data, OrderStatusData.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        listener.fail("签名验证不通过");
                    }
                }else {
                    return StreamUtils.getBean(StreamUtils.creatJson(baseBean), OrderStatusData.class);
                }

                return null;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<OrderStatusData>().getObserver(listener));
    }

    public void getCode(final Context context, int payType, String orderId,final OnGetDataListener<QrCodeData> listener)throws Exception{
        RetrofitApi.generateQrcode(context,payType,orderId).map(new Function<BaseData, QrCodeData>() {
            @Override
            public QrCodeData apply(@NonNull BaseData baseBean) throws Exception {
                String strData = baseBean.getData();
                String sign = baseBean.getSign();
                if (null != strData && baseBean.getCode() == 0){

                    JSONObject object = new JSONObject(StreamUtils.creatJson(baseBean));
                    object.remove("sign");

                    String respSign= StreamUtils.sortjson(object);
                    String sha= CryptoUtil.SHA1(respSign);
                    //验证签名
                    if(CryptoUtil.verify(sha.getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_public_key"),sign)){
                        try {
                            String data = CryptoUtil.AESDecrypt(strData, Constants.AES_KEY);
                            return StreamUtils.getBean(data, QrCodeData.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        listener.fail("签名验证不通过");
                    }
                }else {
                    return StreamUtils.getBean(StreamUtils.creatJson(baseBean), QrCodeData.class);
                }

                return null;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<QrCodeData>().getObserver(listener));
    }

    public void payCash(final Context context, String orderId,final OnGetDataListener<String> listener)throws Exception{
        RetrofitApi.payCash(context,orderId).map(((baseData) -> {
            String strData = baseData.getData();
            String sign = baseData.getSign();
            if (null != strData && baseData.getCode() == 0){

                JSONObject object = new JSONObject(StreamUtils.creatJson(baseData));
                object.remove("sign");

                String respSign= StreamUtils.sortjson(object);
                String sha= CryptoUtil.SHA1(respSign);
                //验证签名
                if(CryptoUtil.verify(sha.getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_public_key"),sign)){
                    try {
                        return CryptoUtil.AESDecrypt(strData, Constants.AES_KEY);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }else{
                    listener.fail("签名验证不通过");
                }
            }else if(baseData.getCode() == 6){
                return "请重新登录";
            }else {
                return baseData.getMsg();
            }

            return null;
        }))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String response) {
                        if (response.equals("请重新登录")){
                            listener.reLogin(response);
                        }
                        listener.success(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.fail("服务器异常");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}
