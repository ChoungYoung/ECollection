package com.ttg.ecollection.base;

import android.app.Application;

//import com.squareup.leakcanary.LeakCanary;

/**
 * Created by loveb on 2018/3/8 0008.
 */

public class App extends Application {

    public static App mContext;

    public static String userId;
    public static String merchantId;

    @Override
    public void onCreate() {
        super.onCreate();

//        LeakCanary.install(this);

        mContext = this;

    }
}
