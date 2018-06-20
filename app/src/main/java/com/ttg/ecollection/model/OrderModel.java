package com.ttg.ecollection.model;

import android.content.Context;
import android.util.Log;

import com.ttg.ecollection.base.Constants;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.OrderData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.PayResult;
import com.ttg.ecollection.data.QrCodeData;
import com.ttg.ecollection.network.CommonObserver;
import com.ttg.ecollection.network.RetrofitApi;
import com.ttg.ecollection.service.OnGetDataListener;
import com.ttg.ecollection.util.CryptoUtil;
import com.ttg.ecollection.util.StreamUtils;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by loveb on 2018/3/16 0016.
 */

public class OrderModel {
    public void generateOrder(final Context context, OrdersData.ResultBean data, final OnGetDataListener<OrderData> listener)throws Exception{
        RetrofitApi.generateOrder(context,data).map(new Function<BaseData, OrderData>() {
            @Override
            public OrderData apply(@NonNull BaseData baseBean) throws Exception {
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
                            return StreamUtils.getBean(data, OrderData.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        listener.fail("签名验证不通过");
                    }
                }else {
                    return StreamUtils.getBean(StreamUtils.creatJson(baseBean), OrderData.class);
                }

                return null;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<OrderData>().getObserver(listener));
    }

    public void getReceivable(final Context context, String companyName ,int page, final OnGetDataListener<OrdersData> listener)throws Exception{
        RetrofitApi.getReceivable(context,companyName,page).map(new Function<BaseData, OrdersData>() {
            @Override
            public OrdersData apply(@NonNull BaseData baseBean) throws Exception {
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
                            return StreamUtils.getBean(data, OrdersData.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        listener.fail("签名验证不通过");
                    }
                }else {
                    return StreamUtils.getBean(StreamUtils.creatJson(baseBean), OrdersData.class);
                }

                return null;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<OrdersData>().getObserver(listener));
    }

    public void getRecords(final Context context, String companyName ,int page, final OnGetDataListener<OrdersData> listener)throws Exception{
        RetrofitApi.getRecords(context,companyName,page).map(new Function<BaseData, OrdersData>() {
            @Override
            public OrdersData apply(@NonNull BaseData baseBean) throws Exception {
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
                            return StreamUtils.getBean(data, OrdersData.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        listener.fail("签名验证不通过");
                    }
                }else {
                    return StreamUtils.getBean(StreamUtils.creatJson(baseBean), OrdersData.class);
                }

                return null;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<OrdersData>().getObserver(listener));
    }

    public void getOrders(final Context context, String companyName ,int page, final OnGetDataListener<OrdersData> listener)throws Exception{
        RetrofitApi.getOrders(context,companyName,page).map(baseBean ->  {
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

                        return StreamUtils.getBean(data, OrdersData.class);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    listener.fail("签名验证不通过");
                }
            }else {
                return StreamUtils.getBean(StreamUtils.creatJson(baseBean), OrdersData.class);
            }

            return null;
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<OrdersData>().getObserver(listener));
    }

    public void submitReceivableOrder(final Context context,String orderNo, int payType,final OnGetDataListener<OrderData> listener)throws Exception{
        RetrofitApi.receivableCash(context,orderNo,payType).map(new Function<BaseData, OrderData>() {
            @Override
            public OrderData apply(@NonNull BaseData baseBean) throws Exception {
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
                            return StreamUtils.getBean(data, OrderData.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        listener.fail("签名验证不通过");
                    }
                }else {
                    return StreamUtils.getBean(StreamUtils.creatJson(baseBean), OrderData.class);
                }

                return null;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<OrderData>().getObserver(listener));
    }

    public void queryPayResult(final Context context,String orderId ,final OnGetDataListener<PayResult> listener) throws Exception{
        try {
            RetrofitApi.payResult(context, orderId)
                    .map(new Function<BaseData, PayResult>() {
                        @Override
                        public PayResult apply(BaseData baseData) throws Exception{
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
                                        String data = CryptoUtil.AESDecrypt(strData, Constants.AES_KEY);
                                        return StreamUtils.getBean(data, PayResult.class);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    listener.fail("签名验证不通过");
                                }
                            }else {
                                return StreamUtils.getBean(StreamUtils.creatJson(baseData), PayResult.class);
                            }

                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CommonObserver<PayResult>().getObserver(listener));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
