package com.ttg.ecollection.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.common.StringUtils;
import com.ttg.ecollection.base.App;
import com.ttg.ecollection.base.BaseActivity;
import com.ttg.ecollection.base.Constants;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.LoginResponse;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.service.NetworkService;
import com.ttg.ecollection.util.CacheUtils;
import com.ttg.ecollection.util.CryptoUtil;
import com.ttg.ecollection.util.StreamUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

import io.reactivex.Observable;

public class RetrofitApi {

    //登录
    public static Observable<BaseData> login(Context context, String account, String password) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("username",account);
        map.put("password",password);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).login(map);

    }

    //登录
    public static Observable<BaseData> getData(Context context, String userId, String merchantId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("merchantId",merchantId);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).getData(map);

    }



    //获取付款人
   public static Observable<BaseData> getPayer(Context context, String companyName,int page) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("companyName",companyName);
        map.put("page",page);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).getPayer(map);

    }

    //生成订单
   public static Observable<BaseData> generateOrder(Context context, OrdersData.ResultBean data) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("dealerId",data.getCompanyId());
        map.put("payAmtReal",data.getPayAmt());
        map.put("remark",data.getRemark());

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).generateOrder(map);

    }

    //生成二维码
   public static Observable<BaseData> generateQrcode(Context context, int payType,String orderId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("orderId",orderId);
        map.put("payType",payType);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).generateQrCode(map);
    }

    //支付结果查询
   public static Observable<BaseData> payResult(Context context,String orderId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("orderId",orderId);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).payResult(map);

    }

    //待收款订单
   public static Observable<BaseData> getReceivable(Context context,String companyName,int page) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("companyName",companyName);
        map.put("page",page);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).getReceivable(map);

    }

    //收银台流水
   public static Observable<BaseData> getRecords(Context context,String companyName,int page) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("companyName",companyName);
        map.put("page",page);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).getRecords(map);

    }

    //已收款订单
   public static Observable<BaseData> getOrders(Context context,String companyName,int page) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("companyName",companyName);
        map.put("page",page);

        String json = StreamUtils.creatJson(map);

        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));

        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));

        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).getOrders(map);

    }

    //检查订单是否付款
   public static Observable<BaseData> checkOrder(Context context,String paymentCode,String userCode,double epayAmt,double cashAmt,double postAmt,String remark) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("paymentCode",paymentCode);
        map.put("userCode",userCode);
        map.put("epayAmt",epayAmt);
        map.put("cashAmt",cashAmt);
        map.put("postAmt",postAmt);
        if (!TextUtils.isEmpty(remark)){
            map.put("remark",remark);
        }

        String json = StreamUtils.creatJson(map);
        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));
        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));
        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).checkOrder(map);
    }

    //待付款订单现金付款
   public static Observable<BaseData> receivableCash(Context context,String orderNo, int payType) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("payType", payType);
        map.put("orderId", orderNo);


        String json = StreamUtils.creatJson(map);
        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));
        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));
        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).receivableCash(map);
    }

   //现金/pos付款
   public static Observable<BaseData> payCash(Context context,String orderNo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", App.userId);
        map.put("orderId", orderNo);


        String json = StreamUtils.creatJson(map);
        String sign = CryptoUtil.sign(StreamUtils.createLinkString(map).getBytes("utf-8"),StreamUtils.getProperties(context,"key.properties").getProperty( "tlinx_private_key"));
        map = new HashMap<>();
        map.put("data",CryptoUtil.AESEncrypt(json,Constants.AES_KEY));
        map.put("sign",sign);

        return RetrofitWrapper.getInstance(Constants.BASE_URL).create(NetworkService.class).payCash(map);
    }

}
