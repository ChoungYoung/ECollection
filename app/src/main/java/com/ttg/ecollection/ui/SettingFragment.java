package com.ttg.ecollection.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.base.BasePresenter;
import com.ttg.ecollection.util.CacheUtils;

/**
 * Created by loveb on 2018/3/13 0013.
 */

public class SettingFragment extends BaseFragment {

    private TextView userName,accountName,shopName;

    @Override
    public BasePresenter initPresent() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(View view) {

        RelativeLayout back =  getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.setting);

        ((TextView) getView(view,R.id.name)).setText(getArguments().getString("userName",""));
        ((TextView) getView(view,R.id.account)).setText(CacheUtils.getString(getActivity(),"account",""));
        ((TextView) getView(view,R.id.shop_name)).setText(getArguments().getString("merchantName",""));

        getView(view,R.id.back_home).setOnClickListener(this);
        getView(view,R.id.logout).setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back_home:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.logout:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
