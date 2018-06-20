package com.ttg.ecollection.model;

import android.content.Context;
import android.util.Log;

import com.ttg.ecollection.base.Constants;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.LoginResponse;
import com.ttg.ecollection.data.PayerData;
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
 * Created by loveb on 2018/3/19 0019.
 */

public class PayerModel {
    public void getPayers(final Context context, String companyName, int page,final OnGetDataListener<PayerData> listener) throws Exception{
        RetrofitApi.getPayer(context, companyName,page).map(new Function<BaseData, PayerData>() {
            @Override
            public PayerData apply(@NonNull BaseData baseBean) throws Exception {
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

                            return StreamUtils.getBean(data, PayerData.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        listener.fail("签名验证不通过");
                    }
                }else {
                    return StreamUtils.getBean(StreamUtils.creatJson(baseBean), PayerData.class);
                }

                return null;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new CommonObserver<PayerData>().getObserver(listener));
    }
}
