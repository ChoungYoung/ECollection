package com.ttg.ecollection.view;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.OrdersData;

/**
 * Created by loveb on 2018/3/16 0016.
 */

public interface IReceivableView extends BaseView {

    void setData(OrdersData data);

    void getDataFail();

}
