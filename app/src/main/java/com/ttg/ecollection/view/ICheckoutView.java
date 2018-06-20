package com.ttg.ecollection.view;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.OrderStatusData;

/**
 * Created by loveb on 2018/3/16 0016.
 */

public interface ICheckoutView extends BaseView {
    void jump2ShowCode(OrderStatusData response);
    void jump2Success(OrderStatusData data);
}
