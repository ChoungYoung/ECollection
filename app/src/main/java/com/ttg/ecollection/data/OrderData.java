package com.ttg.ecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderData extends BaseData implements Parcelable {

    /**
     * payAmtReal : 0.1
     * payTypeCode : Cash
     * payType : 0
     * merchantId : 20773219
     * orderId : ebe854b48410e7e099845dbe26791ac5
     * notifyUrl : http://epay.ardy0220.top//pay/trade/serverCall
     * callbackUrl : http://epay.ardy0220.top//pay/trade/payCall
     * payAmtOriginal : 0.1
     * userCode : 1000001031977236
     */

    private double payAmtReal;
    private String payTypeCode;
    private int payType;
    private String merchantId;
    private String orderId;
    private String notifyUrl;
    private String callbackUrl;
    private double payAmtOriginal;
    private String userCode;

    public double getPayAmtReal() {
        return payAmtReal;
    }

    public void setPayAmtReal(double payAmtReal) {
        this.payAmtReal = payAmtReal;
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public double getPayAmtOriginal() {
        return payAmtOriginal;
    }

    public void setPayAmtOriginal(double payAmtOriginal) {
        this.payAmtOriginal = payAmtOriginal;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.payAmtReal);
        dest.writeString(this.payTypeCode);
        dest.writeInt(this.payType);
        dest.writeString(this.merchantId);
        dest.writeString(this.orderId);
        dest.writeString(this.notifyUrl);
        dest.writeString(this.callbackUrl);
        dest.writeDouble(this.payAmtOriginal);
        dest.writeString(this.userCode);
    }

    public OrderData() {
    }

    protected OrderData(Parcel in) {
        this.payAmtReal = in.readDouble();
        this.payTypeCode = in.readString();
        this.payType = in.readInt();
        this.merchantId = in.readString();
        this.orderId = in.readString();
        this.notifyUrl = in.readString();
        this.callbackUrl = in.readString();
        this.payAmtOriginal = in.readDouble();
        this.userCode = in.readString();
    }

    public static final Parcelable.Creator<OrderData> CREATOR = new Parcelable.Creator<OrderData>() {
        @Override
        public OrderData createFromParcel(Parcel source) {
            return new OrderData(source);
        }

        @Override
        public OrderData[] newArray(int size) {
            return new OrderData[size];
        }
    };
}
