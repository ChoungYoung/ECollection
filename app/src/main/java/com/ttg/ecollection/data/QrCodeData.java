package com.ttg.ecollection.data;

public class QrCodeData extends BaseData {

    /**
     * payType : 0
     * orderId : 123456
     * url : http://dengyh.imwork.net//pay/trade/
     * token : e13287d9ebe46c80c092c6d65b794670
     */

    private int payType;
    private String orderId;
    private String url;
    private String token;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
