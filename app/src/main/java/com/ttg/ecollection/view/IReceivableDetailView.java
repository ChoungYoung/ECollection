package com.ttg.ecollection.view;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.OrderStatusData;

public interface IReceivableDetailView extends BaseView {
    void checkout(OrderStatusData data);
}
