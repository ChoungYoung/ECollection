package com.ttg.ecollection.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.data.FragmentBackHandler;
import com.ttg.ecollection.data.LoginResponse;
import com.ttg.ecollection.presenter.HomePresenter;
import com.ttg.ecollection.view.IHomeView;

import java.text.DecimalFormat;

/**
 * Created by loveb on 2018/3/12 0012.
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView, FragmentBackHandler {

    private ImageView head;
    private LoginResponse data;
    private HomePresenter presenter;
    private TextView collected,collectable;

    @Override
    public HomePresenter initPresent() {
        presenter =  new HomePresenter(this);
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {

        data = getArguments().getParcelable("data");

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.user_center);
        getActivity().findViewById(R.id.back).setVisibility(View.GONE);

        collectable = getView(view, R.id.need_collect);



        collected = getView(view,R.id.collected);

        head = getView(view,R.id.head);
        getView(view,R.id.setting).setOnClickListener(this);
//        getView(view,R.id.cashier).setOnClickListener(this);
        getView(view,R.id.receivable).setOnClickListener(this);
//        getView(view,R.id.records).setOnClickListener(this);
        getView(view,R.id.orders).setOnClickListener(this);

        DecimalFormat format = new DecimalFormat("0.00");

        if (null != data){
            ((TextView) getView(view, R.id.shop_name)).setText(data.getMerchantName());
            ((TextView) getView(view, R.id.merchandiser)).setText(data.getUserName());
            collected.setText(format.format(data.getPaidInAmt()));
            collectable.setText(format.format(data.getPayableAmt()));
        }
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.setting:
                Fragment fragment = new SettingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userName",data.getUserName());
                bundle.putString("merchantName",data.getMerchantName());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content,fragment,SettingFragment.class.getName());
                transaction.addToBackStack(HomeFragment.class.getName());
                transaction.commit();
                break;
//            case R.id.cashier://收银台
//                goToFragment(new CheckoutFragment(),CheckoutFragment.class.getName());
//                break;
            case R.id.receivable://待收款订单
                goToFragment(new ReceivableFragment(),ReceivableFragment.class.getName());
                break;
//            case R.id.records://收银台流水
//                goToFragment(new RecordFragment(),RecordFragment.class.getName());
//                break;
            case R.id.orders://已收款订单
                goToFragment(new OrdersFragment(),OrdersFragment.class.getName());
                break;
        }
    }

    private void goToFragment(Fragment fragment,String tag){
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromHome",true);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment,tag);
        transaction.addToBackStack(HomeFragment.class.getName());
        transaction.commit();
    }

    @Override
    public void onResume() {

        presenter.getData(getActivity());

        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setCollectData(LoginResponse response) {
        DecimalFormat format = new DecimalFormat("0.00");
        if (null != response)
            collected.setText(format.format(response.getPaidInAmt()));
            collectable.setText(format.format(response.getPayableAmt()));
    }

    private long lastTime = System.currentTimeMillis();

    @Override
    public boolean onBackPressed() {
        if (System.currentTimeMillis() - lastTime > 2000){
            lastTime = System.currentTimeMillis();
            showDialog("再次按返回退出应用");
            return true;
        }else {
            getActivity().finish();
            return true;
        }
    }
}
