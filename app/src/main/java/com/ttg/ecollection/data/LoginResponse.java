package com.ttg.ecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by loveb on 2018/3/8 0008.
 */

public class LoginResponse extends BaseData implements Parcelable {
    private int payableCount;
    private int paidInCount;
    private double payableAmt;
    private double paidInAmt;
    private String merchantId;
    private String paymentCode;
    private String paymentName;
    private String cashierId;
    private String merchantName;
    private String userName;
    private String userId;

    public int getPayableCount() {
        return payableCount;
    }

    public void setPayableCount(int payableCount) {
        this.payableCount = payableCount;
    }

    public int getPaidInCount() {
        return paidInCount;
    }

    public void setPaidInCount(int paidInCount) {
        this.paidInCount = paidInCount;
    }

    public double getPayableAmt() {
        return payableAmt;
    }

    public void setPayableAmt(double payableAmt) {
        this.payableAmt = payableAmt;
    }

    public double getPaidInAmt() {
        return paidInAmt;
    }

    public void setPaidInAmt(double paidInAmt) {
        this.paidInAmt = paidInAmt;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.payableCount);
        dest.writeInt(this.paidInCount);
        dest.writeDouble(this.payableAmt);
        dest.writeDouble(this.paidInAmt);
        dest.writeString(this.merchantId);
        dest.writeString(this.paymentCode);
        dest.writeString(this.paymentName);
        dest.writeString(this.cashierId);
        dest.writeString(this.merchantName);
        dest.writeString(this.userName);
        dest.writeString(this.userId);
    }

    public LoginResponse() {
    }

    protected LoginResponse(Parcel in) {
        this.payableCount = in.readInt();
        this.paidInCount = in.readInt();
        this.payableAmt = in.readDouble();
        this.paidInAmt = in.readDouble();
        this.merchantId = in.readString();
        this.paymentCode = in.readString();
        this.paymentName = in.readString();
        this.cashierId = in.readString();
        this.merchantName = in.readString();
        this.userName = in.readString();
        this.userId = in.readString();
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
