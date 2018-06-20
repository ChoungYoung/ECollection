package com.ttg.ecollection.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by loveb on 2018/3/16 0016.
 */

public class OrdersData extends BaseData{

    /**
     * prePage : 1
     * nextPage : 1
     * orderBy : 1
     * pageSize : 10
     * hasNext : false
     * totalCount : -1
     * hasPre : false
     * orderBySetted : true
     * result : []
     * pageNo : 1
     * autoCount : true
     * totalPages : -1
     * first : 1
     * order : asc
     */

    private int prePage;
    private int nextPage;
    private String orderBy;
    private int pageSize;
    private boolean hasNext;
    private int totalCount;
    private boolean hasPre;
    private boolean orderBySetted;
    private int pageNo;
    private boolean autoCount;
    private int totalPages;
    private int first;
    private String order;
    private List<ResultBean> result;

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public boolean isOrderBySetted() {
        return orderBySetted;
    }

    public void setOrderBySetted(boolean orderBySetted) {
        this.orderBySetted = orderBySetted;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean isAutoCount() {
        return autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable {

        /**
         * companyId : 1000001481618397
         * modifyTime : 1522232317000
         * paymentCode : 1000001031977236
         * merchantId : 20773219
         * companyName : 12121
         * orderStatus : 0
         * payAmt : 11.0
         * userCode : 1000001481618397
         * sid : 7
         */

        private String companyId;
        private long modifyTime;
        private String paymentCode;
        private String merchantId;
        private String companyName;
        private int orderStatus;
        private double payAmt;
        private double epayAmt;
        private double cashAmt;
        private double postAmt;
        private double receivableAmt;
        private double reciprocalAmt;
        private String userCode;
        private int sid;
        private String orderId;
        private String remark;
        private int payType;
        private String col5;//时间
        private String col1;//工厂号
        private String col3;//凭证号
        private String payName;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
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

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public double getPayAmt() {
            return payAmt;
        }

        public void setPayAmt(double payAmt) {
            this.payAmt = payAmt;
        }

        public double getReceivableAmt() {
            return receivableAmt;
        }

        public void setReceivableAmt(double receivableAmt) {
            this.receivableAmt = receivableAmt;
        }

        public double getReciprocalAmt() {
            return reciprocalAmt;
        }

        public void setReciprocalAmt(double reciprocalAmt) {
            this.reciprocalAmt = reciprocalAmt;
        }

        public double getEpayAmt() {
            return epayAmt;
        }

        public void setEpayAmt(double epayAmt) {
            this.epayAmt = epayAmt;
        }

        public double getCashAmt() {
            return cashAmt;
        }

        public void setCashAmt(double cashAmt) {
            this.cashAmt = cashAmt;
        }

        public double getPosAmt() {
            return postAmt;
        }

        public void setPosAmt(double postAmt) {
            this.postAmt = postAmt;
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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getCol5(){
            return col5;
        }

        public void setCol5(String col5){
            this.col5 = col5;
        }

        public String getCol1(){
            return col1;
        }

        public void setCol1(String col1){
            this.col1 = col1;
        }

        public String getCol3(){
            return col3;
        }

        public void setCol3(String col3){
            this.col3 = col3;
        }

        public String getPayName(){
            return payName;
        }

        public void setPayName(String payName){
            this.payAmt = payType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.companyId);
            dest.writeLong(this.modifyTime);
            dest.writeString(this.paymentCode);
            dest.writeString(this.merchantId);
            dest.writeString(this.companyName);
            dest.writeInt(this.orderStatus);
            dest.writeDouble(this.payAmt);
            dest.writeDouble(this.receivableAmt);
            dest.writeDouble(this.reciprocalAmt);
            dest.writeDouble(this.epayAmt);
            dest.writeDouble(this.cashAmt);
            dest.writeDouble(this.postAmt);
            dest.writeString(this.userCode);
            dest.writeString(this.orderId);
            dest.writeInt(this.payType);
            dest.writeInt(this.sid);
            dest.writeString(this.col5);
            dest.writeString(this.col3);
            dest.writeString(this.col1);
            dest.writeString(this.payName);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.companyId = in.readString();
            this.modifyTime = in.readLong();
            this.paymentCode = in.readString();
            this.merchantId = in.readString();
            this.companyName = in.readString();
            this.orderStatus = in.readInt();
            this.payAmt = in.readDouble();
            this.receivableAmt = in.readDouble();
            this.reciprocalAmt = in.readDouble();
            this.epayAmt = in.readDouble();
            this.cashAmt = in.readDouble();
            this.postAmt = in.readDouble();
            this.userCode = in.readString();
            this.orderId = in.readString();
            this.payType = in.readInt();
            this.sid = in.readInt();
            this.col5 = in.readString();
            this.col3 = in.readString();
            this.col1 = in.readString();
            this.payName = in.readString();
        }

        public static final Parcelable.Creator<ResultBean> CREATOR = new Parcelable.Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel source) {
                return new ResultBean(source);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };
    }

}
