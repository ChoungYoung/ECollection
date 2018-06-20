package com.ttg.ecollection.view;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.PayerData;
import com.ttg.ecollection.model.PayerModel;

/**
 * Created by loveb on 2018/3/19 0019.
 */

public interface IChoosePayerView extends BaseView {
    void setRecyclerView(PayerData data);
}
