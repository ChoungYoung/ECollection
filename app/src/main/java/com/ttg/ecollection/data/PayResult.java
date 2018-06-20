package com.ttg.ecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PayResult extends BaseData implements Parcelable {


    /**
     * receivableAmt : 0.5
     * orderId : c5c6f653fdc581c73bb33b16c34188b8
     * cashAmt : 0.0
     * epayAmt : 0.01
     * postAmt : 0.0
     * orderStatus : 1
     * reciprocalAmt : 0.01
     * userCode : dbdfe252f9e4935bd177
     * sid : 115
     * pOrderId : 9152758518928999425635730
     * orderTime : 1527585183000
     * payType : 0
     * paymentCode : 1000001031977236
     * merchantId : 20773219
     * payId : 1
     * payCode : WEIXIN
     * payName : 微信支付
     */

    private double receivableAmt;
    private String orderId;
    private double cashAmt;
    private double epayAmt;
    private double postAmt;
    private int orderStatus;
    private double reciprocalAmt;
    private String userCode;
    private int sid;
    private String pOrderId;
    private long orderTime;
    private int payType;
    private String paymentCode;
    private String merchantId;
    private String payId;
    private String payCode;
    private String payName;

    public double getReceivableAmt() {
        return receivableAmt;
    }

    public void setReceivableAmt(double receivableAmt) {
        this.receivableAmt = receivableAmt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(double cashAmt) {
        this.cashAmt = cashAmt;
    }

    public double getEpayAmt() {
        return epayAmt;
    }

    public void setEpayAmt(double epayAmt) {
        this.epayAmt = epayAmt;
    }

    public double getPostAmt() {
        return postAmt;
    }

    public void setPostAmt(double postAmt) {
        this.postAmt = postAmt;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getReciprocalAmt() {
        return reciprocalAmt;
    }

    public void setReciprocalAmt(double reciprocalAmt) {
        this.reciprocalAmt = reciprocalAmt;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPOrderId() {
        return pOrderId;
    }

    public void setPOrderId(String pOrderId) {
        this.pOrderId = pOrderId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.receivableAmt);
        dest.writeString(this.orderId);
        dest.writeDouble(this.cashAmt);
        dest.writeDouble(this.epayAmt);
        dest.writeDouble(this.postAmt);
        dest.writeInt(this.orderStatus);
        dest.writeDouble(this.reciprocalAmt);
        dest.writeString(this.userCode);
        dest.writeInt(this.sid);
        dest.writeString(this.pOrderId);
        dest.writeLong(this.orderTime);
        dest.writeInt(this.payType);
        dest.writeString(this.paymentCode);
        dest.writeString(this.merchantId);
        dest.writeString(this.payId);
        dest.writeString(this.payCode);
        dest.writeString(this.payName);
    }

    public PayResult() {
    }

    protected PayResult(Parcel in) {
        this.receivableAmt = in.readDouble();
        this.orderId = in.readString();
        this.cashAmt = in.readDouble();
        this.epayAmt = in.readDouble();
        this.postAmt = in.readDouble();
        this.orderStatus = in.readInt();
        this.reciprocalAmt = in.readDouble();
        this.userCode = in.readString();
        this.sid = in.readInt();
        this.pOrderId = in.readString();
        this.orderTime = in.readLong();
        this.payType = in.readInt();
        this.paymentCode = in.readString();
        this.merchantId = in.readString();
        this.payId = in.readString();
        this.payCode = in.readString();
        this.payName = in.readString();
    }

    public static final Creator<PayResult> CREATOR = new Creator<PayResult>() {
        @Override
        public PayResult createFromParcel(Parcel source) {
            return new PayResult(source);
        }

        @Override
        public PayResult[] newArray(int size) {
            return new PayResult[size];
        }
    };
}
