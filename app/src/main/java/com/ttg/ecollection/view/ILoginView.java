package com.ttg.ecollection.view;
import android.util.Base64;

import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.BaseData;
import com.ttg.ecollection.data.LoginResponse;

/**
 * Created by loveb on 2018/3/12 0012.
 */

public interface ILoginView extends BaseView {

    void jumpToActivity(Class clazz, boolean needFinish, LoginResponse response);

}
