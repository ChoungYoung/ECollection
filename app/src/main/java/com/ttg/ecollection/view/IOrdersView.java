package com.ttg.ecollection.view;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.OrdersData;

/**
 * Created by loveb on 2018/3/22 0022.
 */

public interface IOrdersView extends BaseView {
    void setData(OrdersData data);
    void getDataFail();
}
