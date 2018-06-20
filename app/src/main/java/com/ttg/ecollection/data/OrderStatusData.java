package com.ttg.ecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderStatusData extends BaseData implements Parcelable {


    /**
     * receivableAmt : 2.1
     * payType : 0
     * paymentCode : 1000001031977236
     * merchantId : 20773219
     * orderId : a37764f5f68fea7347f16cf39362e437
     * cashAmt : 0.0
     * epayAmt : 2.1
     * postAmt : 0.0
     * orderStatus : 0
     * reciprocalAmt : 2.1
     * userCode : dbdfe252f9e4935bd193
     */

    private double receivableAmt;
    private int payType;
    private String paymentCode;
    private String merchantId;
    private String orderId;
    private double cashAmt;
    private double epayAmt;
    private double postAmt;
    private int orderStatus;
    private double reciprocalAmt;
    private String userCode;
    private String companyName;

    public double getReceivableAmt() {
        return receivableAmt;
    }

    public void setReceivableAmt(double receivableAmt) {
        this.receivableAmt = receivableAmt;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.receivableAmt);
        dest.writeInt(this.payType);
        dest.writeString(this.paymentCode);
        dest.writeString(this.merchantId);
        dest.writeString(this.orderId);
        dest.writeDouble(this.cashAmt);
        dest.writeDouble(this.epayAmt);
        dest.writeDouble(this.postAmt);
        dest.writeInt(this.orderStatus);
        dest.writeDouble(this.reciprocalAmt);
        dest.writeString(this.userCode);
        dest.writeString(this.companyName);
    }

    public OrderStatusData() {
    }

    protected OrderStatusData(Parcel in) {
        this.receivableAmt = in.readDouble();
        this.payType = in.readInt();
        this.paymentCode = in.readString();
        this.merchantId = in.readString();
        this.orderId = in.readString();
        this.cashAmt = in.readDouble();
        this.epayAmt = in.readDouble();
        this.postAmt = in.readDouble();
        this.orderStatus = in.readInt();
        this.reciprocalAmt = in.readDouble();
        this.userCode = in.readString();
        this.companyName = in.readString();
    }

    public static final Parcelable.Creator<OrderStatusData> CREATOR = new Parcelable.Creator<OrderStatusData>() {
        @Override
        public OrderStatusData createFromParcel(Parcel source) {
            return new OrderStatusData(source);
        }

        @Override
        public OrderStatusData[] newArray(int size) {
            return new OrderStatusData[size];
        }
    };
}
