package com.ttg.ecollection.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.data.OrderData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.PayResult;
import com.ttg.ecollection.presenter.CashPresenter;
import com.ttg.ecollection.view.ICashView;

/**
 * Created by loveb on 2018/3/21 0021.
 */

public class CashFragment extends BaseFragment<CashPresenter> implements ICashView{

    private CashPresenter presenter;
    private OrdersData.ResultBean data;

    @Override
    public CashPresenter initPresent() {
        presenter = new CashPresenter(this);
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_cash;
    }

    @Override
    public void initView(View view) {
        RelativeLayout back = getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.confirm_transaction);

        data = getArguments().getParcelable("data");

        getView(view,R.id.confirm).setOnClickListener(this);
        ((TextView) getView(view, R.id.tv_amount)).setText(getString(R.string.yuan) + data.getPayAmt());

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.confirm:
                if (TextUtils.isEmpty(data.getOrderId())){
                    presenter.generateOrder(getActivity(),data);
                }else{
                    presenter.submitReceivableOrder(getActivity(),data.getOrderId(),data.getPayType());
                }
                break;
        }
    }

    @Override
    public void jumpToSuccess(PayResult data) {
        Fragment fragment = new RecordDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",data);
        bundle.putBoolean("fromCash",true);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
