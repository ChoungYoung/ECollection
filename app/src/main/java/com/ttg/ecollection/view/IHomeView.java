package com.ttg.ecollection.view;
import com.ttg.ecollection.base.BaseView;
import com.ttg.ecollection.data.LoginResponse;

/**
 * Created by loveb on 2018/3/12 0012.
 */

public interface IHomeView extends BaseView {

    void setCollectData(LoginResponse response);

}
