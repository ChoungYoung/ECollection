package com.ttg.ecollection.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.data.BackHandlerHelper;
import com.ttg.ecollection.data.FragmentBackHandler;
import com.ttg.ecollection.data.OrderData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.PayResult;
import com.ttg.ecollection.util.StreamUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by loveb on 2018/3/21 0021.
 */

public class RecordDetailFragment extends BaseFragment implements FragmentBackHandler{

    @Override
    public BasePresenter initPresent() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_record_detail;
    }

    @Override
    public void initView(View view) {

        RelativeLayout back = getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.order_detail);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        OrdersData.ResultBean data = getArguments().getParcelable("data");

        double amount = 0;
        String payName = "";
        if (!TextUtils.isEmpty(data.getEpayAmt() + "") && data.getEpayAmt() > 0){
            amount += data.getEpayAmt();
            payName += "线上支付";
        }
        if (!TextUtils.isEmpty(data.getCashAmt() + "") && data.getCashAmt() > 0){
            amount += data.getCashAmt();
            if (payName.length() > 0){
                payName += "、";
            }
            payName += "现金支付";
        }
        if (!TextUtils.isEmpty(data.getPosAmt() + "") && data.getPosAmt() > 0){
            amount += data.getPosAmt();
            if (payName.length() > 0){
                payName += "、";
            }
            payName += "POS支付";
        }

        if (!TextUtils.isEmpty(data.getPayName())){
            payName = data.getPayName();
        }

        if (amount <= 0){
            amount = data.getReciprocalAmt();
        }


        ((TextView) getView(view, R.id.tv_amount)).setText(getString(R.string.yuan_,amount));
        ((TextView) getView(view, R.id.tv_shop_name)).setText(data.getCompanyName());
        ((TextView) getView(view, R.id.tv_state)).setText("已支付");

        Date date = new Date(data.getModifyTime());

        ((TextView) getView(view, R.id.tv_order_time)).setText(formatter.format(date));
        ((TextView) getView(view, R.id.tv_payment_type)).setText(payName);
        ((TextView) getView(view, R.id.tv_order_no)).setText(data.getOrderId());
//        ((TextView) getView(view, R.id.tv_order_type)).setText(getString(R.string.cash_receipt));

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
                if (getArguments().getBoolean("fromOrder",true)){
                    Fragment fragment ;

                    FragmentManager manager = getActivity().getSupportFragmentManager();

                    fragment = manager.findFragmentByTag(HomeFragment.class.getName());

                    FragmentTransaction transaction = manager.beginTransaction();
                    for(int i = 0; i < manager.getBackStackEntryCount(); i++) {
                        if (null == manager.getBackStackEntryAt(i).getName() || !manager.getBackStackEntryAt(i).getName().equals(HomeFragment.class.getName())){
                            manager.popBackStack();
                        }
                    }

                    transaction.replace(R.id.content,fragment);
                    transaction.commit();
                }else{
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                break;
        }
    }


    @Override
    public boolean onBackPressed() {
        if (getArguments().getBoolean("fromOrder",true)){
            Fragment fragment ;

            FragmentManager manager = getActivity().getSupportFragmentManager();

            fragment = manager.findFragmentByTag(HomeFragment.class.getName());

            FragmentTransaction transaction = manager.beginTransaction();
            for(int i = 0; i < manager.getBackStackEntryCount(); i++) {
                if (null == manager.getBackStackEntryAt(i).getName() || !manager.getBackStackEntryAt(i).getName().equals(HomeFragment.class.getName())){
                    manager.popBackStack();
                }
            }

            transaction.replace(R.id.content,fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}
