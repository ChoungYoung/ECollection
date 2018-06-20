package com.ttg.ecollection.data;

import java.util.List;

/**
 * Created by loveb on 2018/3/19 0019.
 */

public class PayerData extends BaseData {

    /**
     * prePage : 1
     * nextPage : 1
     * orderBy : 1
     * pageSize : 10
     * hasNext : false
     * totalCount : 1
     * hasPre : false
     * orderBySetted : true
     * result : [{"dealerName":"12121","createTime":1521444636000,"merchantId":"20773219","dealerId":"1000001481618397","mobile":"null","shopName":"今天晚上","shopId":"20268130","status":1}]
     * pageNo : 1
     * autoCount : true
     * totalPages : 1
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

    public static class ResultBean {
        /**
         * dealerName : 12121
         * createTime : 1521444636000
         * merchantId : 20773219
         * dealerId : 1000001481618397
         * mobile : null
         * shopName : 今天晚上
         * shopId : 20268130
         * status : 1
         */

        private String dealerName;
        private long createTime;
        private String merchantId;
        private String dealerId;
        private String mobile;
        private String shopName;
        private String shopId;
        private int status;

        public String getDealerName() {
            return dealerName;
        }

        public void setDealerName(String dealerName) {
            this.dealerName = dealerName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getDealerId() {
            return dealerId;
        }

        public void setDealerId(String dealerId) {
            this.dealerId = dealerId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
