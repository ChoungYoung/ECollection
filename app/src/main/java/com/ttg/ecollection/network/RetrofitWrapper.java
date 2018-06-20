package com.ttg.ecollection.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.ttg.ecollection.base.App;
import com.ttg.ecollection.base.Constants;
import com.ttg.ecollection.util.NetWorkUtils;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitWrapper {
    //单例
    private static RetrofitWrapper INSTANCE;
    // Retrofit 对象
    private Retrofit mRetrofit;

    private static final int NET_MAX = 30; //30秒  有网超时时间
    private static final int NO_NET_MAX = 60 * 60 * 24 * 7; //7天 无网超时时间

    private RetrofitWrapper(String BASE_URL){
        long maxSize = 10 * 1024 * 1024; // 10 MB 最大缓存数
        File mFile = new File(App.mContext.getCacheDir() + "http");//储存目录
        Cache mCache = new Cache(mFile, maxSize);

        //网络拦截器
        Interceptor mNetInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//                Log.e("TAG", "拦截 应用 缓存");

                Request request = chain.request();
                if (!NetWorkUtils.isNetAvailable(App.mContext,true)) {//判断网络状态 无网络时
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .removeHeader("Pragma")
                            .header("Cache-Control", "private, only-if-cached, max-stale=" + NO_NET_MAX)
                            .build();
                } else {
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .removeHeader("Pragma")
                            .header("Cache-Control", "private, max-age=" + NET_MAX)//添加缓存请求头
                            .build();
                }
                return chain.proceed(request);
            }
        };

        Interceptor mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//                Log.e("TAG", "拦截 网络 缓存");
                Request request = chain.request();
                if (!NetWorkUtils.isNetAvailable(App.mContext, true)) {//判断网络状态 无网络时
//                    Log.e("TAG", "无网~~ 缓存");
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .removeHeader("Pragma")
                            .header("Cache-Control", "private, only-if-cached, max-stale=" + NO_NET_MAX)
                            .build();
                } else {//有网状态
//                    Log.e("TAG", "有网~~ 缓存");
                    request = request.newBuilder()
                            //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                            .removeHeader("Pragma")
                            .header("Cache-Control", "private, max-age=" + NET_MAX)//添加缓存请求头
                            .build();
                }
                return chain.proceed(request);
            }
        };
        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)//应用程序拦截器
                .addNetworkInterceptor(mNetInterceptor)//网络拦截器
                .addInterceptor(new AddCookiesInterceptor(App.mContext , "lang=ch"))
                .addInterceptor(new ReceivedCookiesInterceptor(App.mContext))
                .cache(mCache)//添加缓存
                .build();


        if (TextUtils.isEmpty(BASE_URL)){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)  //添加baseurl
                    .client(mClient)//设置okhttp
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(ScalarsConverterFactory.create()) //添加返回为字符串的支持
                    .addConverterFactory(GsonConverterFactory.create()) //create中可以传入其它json对象，默认Gson
                    .build();
        }else{
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  //添加baseurl
                    .client(mClient)//设置okhttp
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(ScalarsConverterFactory.create()) //添加返回为字符串的支持
                    .addConverterFactory(GsonConverterFactory.create()) //create中可以传入其它json对象，默认Gson
                    .build();
        }
    }


    static RetrofitWrapper getInstance(String BASE_URL) {

        if(INSTANCE == null) {
            synchronized(RetrofitWrapper.class) {
                if(INSTANCE == null) {
                    INSTANCE = new RetrofitWrapper(BASE_URL);
                }
            }
        }

        return INSTANCE;
    }

    /**
     * 转换为对象的Service
     * @param service 服务
     * @param <T> 类型
     * @return 传入的类型
     */
    <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }

    public class AddCookiesInterceptor implements Interceptor {
        private Context context;
        private String lang;

        private AddCookiesInterceptor(Context context, String lang) {
            super();
            this.context = context;
            this.lang = lang;

        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request.Builder builder = chain.request().newBuilder();
            SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
            Observable.just(sharedPreferences.getString("cookie", ""))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String cookie) throws Exception {
                            if (cookie.contains("lang=ch")){
                                cookie = cookie.replace("lang=ch","lang="+lang);
                            }
                            if (cookie.contains("lang=en")){
                                cookie = cookie.replace("lang=en","lang="+lang);
                            }
                            //添加cookie
                            builder.addHeader("cookie", cookie);
                        }
                    });
            return chain.proceed(builder.build());
        }
    }

    public class ReceivedCookiesInterceptor implements Interceptor {
        private Context context;
        SharedPreferences sharedPreferences;

        private ReceivedCookiesInterceptor(Context context) {
            super();
            this.context = context;
            sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (!originalResponse.headers("set-cookie").isEmpty()) {
                final StringBuffer cookieBuffer = new StringBuffer();
                Observable.fromIterable(originalResponse.headers("set-cookie"))
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) throws Exception {
                                String[] cookieArray = s.split(";");
                                return cookieArray[0];
                            }
                        })
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String cookie) throws Exception {
                                cookieBuffer.append(cookie).append(";");
                            }
                        });
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cookie", cookieBuffer.toString());
                editor.commit();
            }

            return originalResponse;
        }
    }

}
