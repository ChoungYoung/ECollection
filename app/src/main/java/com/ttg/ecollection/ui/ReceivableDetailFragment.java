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
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.data.OrderStatusData;
import com.ttg.ecollection.presenter.ReceivableDetailPresenter;
import com.ttg.ecollection.view.IReceivableDetailView;

import java.text.DecimalFormat;

/**
 * Created by loveb on 2018/3/19 0019.
 */

public class ReceivableDetailFragment extends BaseFragment<ReceivableDetailPresenter> implements IReceivableDetailView{

    private OrdersData.ResultBean data;
    private ReceivableDetailPresenter presenter;

    @Override
    public ReceivableDetailPresenter initPresent() {
        presenter = new ReceivableDetailPresenter(this);
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_receivable_detail;
    }

    @Override
    public void initView(View view) {
        RelativeLayout back =  getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.order_detail);

        data =  getArguments().getParcelable("data");

        ((TextView) getView(view, R.id.tv_amount)).setText(getString(R.string.yuan_,data.getPayAmt()));
        ((TextView) getView(view, R.id.tv_payer_name)).setText(data.getCompanyName());
        int status = data.getOrderStatus();
        switch (status){
            case 0:
                ((TextView) getView(view, R.id.tv_state)).setText("待支付");
                break;
            case 1:
                ((TextView) getView(view, R.id.tv_state)).setText("已支付");
                break;
            case 4:
                ((TextView) getView(view, R.id.tv_state)).setText("失败");
                break;
        }

        ((TextView) getView(view, R.id.tv_order_no)).setText(data.getCol1());

        getView(view,R.id.confirm).setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.confirm:
//                if (data.getOrderStatus() == 0 && TextUtils.isEmpty(data.getOrderId())){
//                    presenter.checkOrder(getActivity(),data.getPaymentCode(),data.getUserCode(),data.getPayAmt()+"",data.getRemark());
//                }else{
                    Fragment fragment = new CheckoutFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data",data);
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
//                }
                break;
        }
    }

    @Override
    public void checkout(OrderStatusData orderStatusData) {
        data.setOrderId(orderStatusData.getOrderId());
        data.setPayType(orderStatusData.getPayType());

        Fragment fragment = new CheckoutFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",data);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
