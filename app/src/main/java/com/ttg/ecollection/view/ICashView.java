package com.ttg.ecollection.view;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.OrderData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.PayResult;

/**
 * Created by loveb on 2018/3/22 0022.
 */

public interface ICashView extends BaseView {
    void jumpToSuccess(PayResult data);
}
