package com.ttg.ecollection.base;


public class Constants {
    public static final boolean isDebug = true;

    public static final String APP_NAME = "E Collection";

    public static final String BASE_URL = "https://epay.ardy0220.top/";

    //用户登录
    public static final String USER_LOGIN = "auth/verify";
    //首页数据
    public static final String HOME_DATA = "epay/index";
    //收银台订单生成
    public static final String GENERATE_ORDER = "paycheckstand/checkstand_payment";
    //生成支付二维码
    public static final String GENERATE_QRCODE = "pay/qr";
    //查询支付结果
    public static final String PAY_RESULT = "pay/result";
    //待收款订单
    public static final String RECEIVABLE = "userClient/getOrderNotPayList";
    //收银台流水
    public static final String RECORDS = "userClient/getCheckstandOrderList";
    //已收款订单
    public static final String ORDERS = "userClient/getOrderPayedList";
    //选择付款人
    public static final String CHOOSE_PAYER = "userClient/choosePayee";
    //检查订单是否支付
    public static final String CHECK_ORDER = "pay/pre_order";
    //待收款现金支付
    public static final String RECEIVABLE_CASH = "paycheckstand/paycash";
    //收银台下单
    public static final String CHECK_OUT = "paycheckstand/checkstand_payment";
    //现金支付
    public static final String CASH = "pay/tradeOrderAndPost";

    public static final int SERVER_RESPONCE_SUCCESS = 0;


    public static final String USER_ID = "userId";

    public static final String AES_KEY = "b8c0882d38d74262e0a6d19c2b51cd6d";
}
