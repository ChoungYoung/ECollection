package com.ttg.ecollection.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.data.FragmentBackHandler;
import com.ttg.ecollection.data.OrderStatusData;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.presenter.CheckoutPresenter;
import com.ttg.ecollection.presenter.ShowCodePresenter;
import com.ttg.ecollection.util.StreamUtils;
import com.ttg.ecollection.util.UIUtil;
import com.ttg.ecollection.view.ICheckoutView;

import org.w3c.dom.Text;

/**
 * Created by loveb on 2018/3/14 0014.
 */

public class CheckoutFragment extends BaseFragment<CheckoutPresenter> implements ICheckoutView ,FragmentBackHandler{

    private EditText onlineAmount,posAmount,cashAmount,remarks;
    private TextView payer,total;
    private String orderNo;
    private OrdersData.ResultBean data;
    private String payerId;
    private CheckoutPresenter presenter;
//    private boolean fromHome = false;

    @Override
    public CheckoutPresenter initPresent() {
        presenter = new CheckoutPresenter(this);
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_checkout;
    }

    @Override
    public void initView(View view) {
        RelativeLayout back = getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.set_amount);

//        getView(view,R.id.prepayments).setOnClickListener(this);
//        getView(view,R.id.ll_choose_payer).setOnClickListener(this);
        getView(view,R.id.charge).setOnClickListener(this);
        onlineAmount =  getView(view,R.id.et_online_amount);
        posAmount =  getView(view,R.id.et_pos_amount);
        cashAmount =  getView(view,R.id.et_cash_amount);
        remarks =  getView(view,R.id.et_remarks);
        payer = getView(view, R.id.tv_payer);
        total = getView(view,R.id.tv_total_amount);

        onlineAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                double amount = 0;
//                if (StreamUtils.isNumber(input) && Double.parseDouble(input) > data.getPayAmt()){
//                    UIUtil.showToast(getActivity(),"收款金额不能大于总金额");
//                    onlineAmount.setText(input.substring(0,input.length() - 1));
//                    onlineAmount.setSelection(input.length() -1);
//                }else
                if (StreamUtils.isNumber(input)){
                    amount = Double.parseDouble(input);
                    if (!TextUtils.isEmpty(cashAmount.getText().toString()) && StreamUtils.isNumber(cashAmount.getText().toString())){
                        amount += Double.parseDouble(cashAmount.getText().toString());
                    }
                    if (!TextUtils.isEmpty(posAmount.getText().toString()) && StreamUtils.isNumber(posAmount.getText().toString())){
                        amount += Double.parseDouble(posAmount.getText().toString());
                    }

                    if (amount > data.getPayAmt()){
                        UIUtil.showToast(getActivity(),"收款金额不能大于总金额");
                        onlineAmount.setText(input.substring(0,input.length() - 1));
                        onlineAmount.setSelection(input.length() -1);
                        return;
                    }

                    total.setText(getString(R.string.yuan_,amount));
                }else if (TextUtils.isEmpty(input)){
                    if (!TextUtils.isEmpty(cashAmount.getText().toString()) && StreamUtils.isNumber(cashAmount.getText().toString())){
                        amount += Double.parseDouble(cashAmount.getText().toString());
                    }
                    if (!TextUtils.isEmpty(posAmount.getText().toString()) && StreamUtils.isNumber(posAmount.getText().toString())){
                        amount += Double.parseDouble(posAmount.getText().toString());
                    }

                    total.setText(getString(R.string.yuan_,amount));
                }
            }
        });
        cashAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                double amount = 0;
                if (StreamUtils.isNumber(input)){
                    amount = Double.parseDouble(input);
                    if (!TextUtils.isEmpty(onlineAmount.getText().toString()) && StreamUtils.isNumber(onlineAmount.getText().toString())){
                        amount += Double.parseDouble(onlineAmount.getText().toString());
                    }
                    if (!TextUtils.isEmpty(posAmount.getText().toString()) && StreamUtils.isNumber(posAmount.getText().toString())){
                        amount += Double.parseDouble(posAmount.getText().toString());
                    }

                    if (amount > data.getPayAmt()){
                        cashAmount.setText(input.substring(0,input.length() - 1));
                        cashAmount.setSelection(input.length() -1);
                        UIUtil.showToast(getActivity(),"收款金额不能大于总金额");
                        return;
                    }

                    total.setText(getString(R.string.yuan_,amount));
                }else if (TextUtils.isEmpty(input)){
                    if (!TextUtils.isEmpty(onlineAmount.getText().toString()) && StreamUtils.isNumber(onlineAmount.getText().toString())){
                        amount += Double.parseDouble(onlineAmount.getText().toString());
                    }
                    if (!TextUtils.isEmpty(posAmount.getText().toString()) && StreamUtils.isNumber(posAmount.getText().toString())){
                        amount += Double.parseDouble(posAmount.getText().toString());
                    }

                    total.setText(getString(R.string.yuan_,amount));
                }
            }
        });
        posAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                double amount = 0;
                if (StreamUtils.isNumber(input)){
                    amount = Double.parseDouble(input);
                    if (!TextUtils.isEmpty(cashAmount.getText().toString()) && StreamUtils.isNumber(cashAmount.getText().toString())){
                        amount += Double.parseDouble(cashAmount.getText().toString());
                    }
                    if (!TextUtils.isEmpty(onlineAmount.getText().toString()) && StreamUtils.isNumber(onlineAmount.getText().toString())){
                        amount += Double.parseDouble(onlineAmount.getText().toString());
                    }

                    if (amount > data.getPayAmt()){
                        UIUtil.showToast(getActivity(),"收款金额不能大于总金额");
                        posAmount.setText(input.substring(0,input.length() - 1));
                        posAmount.setSelection(input.length() -1);
                        return;
                    }

                    total.setText(getString(R.string.yuan_,amount));
                }else if (TextUtils.isEmpty(input)){
                    if (!TextUtils.isEmpty(cashAmount.getText().toString()) && StreamUtils.isNumber(cashAmount.getText().toString())){
                        amount += Double.parseDouble(cashAmount.getText().toString());
                    }
                    if (!TextUtils.isEmpty(onlineAmount.getText().toString()) && StreamUtils.isNumber(onlineAmount.getText().toString())){
                        amount += Double.parseDouble(onlineAmount.getText().toString());
                    }

                    total.setText(getString(R.string.yuan_,amount));
                }
            }
        });

//        fromHome = getArguments().getBoolean("fromHome",false);

        data = getArguments().getParcelable("data");
        if (null != data){
            onlineAmount.setText(String.valueOf(data.getPayAmt()));
            total.setText(getString(R.string.yuan_,data.getPayAmt()));

            remarks.setText(data.getRemark());
            payer.setText(data.getCompanyName());

            if (TextUtils.isEmpty(data.getOrderId())){
                orderNo = data.getOrderId();
            }
        }

    }

//    private PopupWindow window;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
//                if (null != window && window.isShowing()){
//                    window.dismiss();
//                    return;
//                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
//            case R.id.prepayments:
//                UIUtil.showToast(getActivity(),"暂不可用");
//                break;
//            case R.id.ll_choose_payer: {//选择付款人
//                Intent intent = new Intent(getActivity(),ChoosePayerActivity.class);
//                startActivityForResult(intent,1);
//                break;
//            }
            case R.id.charge://收款
//                double totalAmt = 0;

                if (TextUtils.isEmpty(onlineAmount.getText().toString()) && TextUtils.isEmpty(cashAmount.getText().toString())
                        && TextUtils.isEmpty(posAmount.getText().toString())){
                    UIUtil.showToast(getActivity(),"请输入收款金额");
                    return;
                }

                if (!TextUtils.isEmpty(onlineAmount.getText().toString())){
//                    totalAmt += Double.parseDouble(onlineAmount.getText().toString());
                    data.setEpayAmt(Double.parseDouble(onlineAmount.getText().toString()));

                    if (Double.parseDouble(onlineAmount.getText().toString()) > data.getPayAmt()){
                        UIUtil.showToast(getActivity(),"在线收款金额不能大于总金额");
                        return;
                    }
                }

                if (!TextUtils.isEmpty(cashAmount.getText().toString())){
//                    totalAmt += Double.parseDouble(cashAmount.getText().toString());
                    data.setCashAmt(Double.parseDouble(cashAmount.getText().toString()));
                }
                if (!TextUtils.isEmpty(posAmount.getText().toString())){
//                    totalAmt += Double.parseDouble(posAmount.getText().toString());
                    data.setPosAmt(Double.parseDouble(posAmount.getText().toString()));
                }

                data.setRemark(remarks.getText().toString());

                presenter.checkOrder(getActivity(),data);


//                if (null == data){
//                    data = new OrdersData.ResultBean();
//                }
//                data.setRemark(remarks.getText().toString());
//                data.setPayAmt(Double.parseDouble(onlineAmount.getText().toString()));
//                if (!TextUtils.isEmpty(payerId)){
//                    data.setCompanyId(payerId);
//                    data.setCompanyName(payer.getText().toString());
//                }
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("data",data);
//                jumpToFragment(new ShowCodeFragment(),bundle,true,ShowCodeFragment.class.getName());

//                if (null == window){
//                    window = new PopupWindow(getActivity());
//                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_payment, null);
//
//                    view.findViewById(R.id.tv_show_code).setOnClickListener(this);
//                    view.findViewById(R.id.tv_cash).setOnClickListener(this);
//                    view.findViewById(R.id.ib_close).setOnClickListener(this);
//
//                    window.setContentView(view);
//                    window.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//                    window.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//                    window.setOutsideTouchable(true);
//                    window.setBackgroundDrawable(new BitmapDrawable());
//                }
//                window.showAtLocation(v, Gravity.BOTTOM,0,0);

                break;
//            case R.id.tv_show_code:{
//                if (TextUtils.isEmpty(amount.getText().toString()) || TextUtils.isEmpty(payer.getText().toString())){
//                    UIUtil.showToast(getActivity(),"信息不完整");
//                    return;
//                }
//                if (null == data){
//                    data = new OrdersData.ResultBean();
//                }
//                data.setRemark(remarks.getText().toString());
//                data.setPayAmt(Double.parseDouble(amount.getText().toString()));
//                if (!TextUtils.isEmpty(payerId)){
//                    data.setCompanyId(payerId);
//                    data.setCompanyName(payer.getText().toString());
//                }
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("data",data);
//                jumpToFragment(new ShowCodeFragment(),bundle,true,ShowCodeFragment.class.getName());
//
//                if (null != window && window.isShowing()){
//                    window.dismiss();
//                    window = null;
//                }
//                break;
//            }
//            case R.id.tv_cash:
//                if (TextUtils.isEmpty(amount.getText().toString()) || TextUtils.isEmpty(payer.getText().toString())){
//                    UIUtil.showToast(getActivity(),"信息不完整");
//                    return;
//                }
//                if (null == data){
//                    data = new OrdersData.ResultBean();
//                }
//                data.setRemark(remarks.getText().toString());
//                data.setPayAmt(Double.parseDouble(amount.getText().toString()));
//                if (!TextUtils.isEmpty(payerId)){
//                    data.setCompanyId(payerId);
//                    data.setCompanyName(payer.getText().toString());
//                }
//
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("data",data);
//                jumpToFragment(new CashFragment(),bundle,true,CashFragment.class.getName());
//
//                if (null != window && window.isShowing()){
//                    window.dismiss();
//                    window = null;
//                }
//                break;
//            case R.id.ib_close:
//                if (null != window && window.isShowing()){
//                    window.dismiss();
//                    window = null;
//                }
//                break;
        }
    }

    @Override
    public void jump2ShowCode(OrderStatusData response) {
        response.setCompanyName(data.getCompanyName());
        Bundle bundle = new Bundle();
        bundle.putParcelable("data",response);
        bundle.putBoolean("fromOrder",true);
        jumpToFragment(new ShowCodeFragment(),bundle,true,ShowCodeFragment.class.getName());
    }

    @Override
    public void jump2Success(OrderStatusData statusData) {
        data.setOrderId(statusData.getOrderId());
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromOrder",true);
        bundle.putParcelable("data",data);

        jumpToFragment(new RecordDetailFragment(),bundle,true, RecordDetailFragment.class.getName());
    }

    public void jumpToFragment(Fragment fragment, Bundle bundle, boolean addToBackStack, String tag){
        if (null != bundle){
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment,tag);
        if (addToBackStack){
            transaction.addToBackStack("checkout");
        }
        transaction.commit();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1 && resultCode == MainActivity.RESULT_OK){
//            payer.setText(data.getStringExtra("shopName"));
//            payerId = data.getStringExtra("shopId") ;
//        }
//    }

    @Override
    public boolean onBackPressed() {
//        if (null != window && window.isShowing()){
//            window.dismiss();
//            return true;
//        }
        return false;
    }
}
