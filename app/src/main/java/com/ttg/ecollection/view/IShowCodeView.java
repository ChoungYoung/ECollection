package com.ttg.ecollection.view;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.PayResult;

/**
 * Created by loveb on 2018/3/20 0020.
 */

public interface IShowCodeView extends BaseView {

    void setCode(String code);

    void jumpToSuccess(PayResult result);

}
