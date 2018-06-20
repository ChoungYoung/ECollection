package com.ttg.ecollection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.haha.perflib.Main;
import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.data.LoginResponse;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.presenter.LoginPresenter;
import com.ttg.ecollection.util.CacheUtils;
import com.ttg.ecollection.util.ImageCodeUtil;
import com.ttg.ecollection.util.UIUtil;
import com.ttg.ecollection.view.ILoginView;
import com.ttg.ecollection.view.LoadingDialog;

/**
 * Created by loveb on 2018/3/8 0008.
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements ILoginView{

    private LoginPresenter presenter;
    private EditText account,password,code;
    private ImageView image;

    @Override
    public LoginPresenter initPresent() {
        presenter = new LoginPresenter(this);
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(View view) {

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.title_login);

        account = getView(view,R.id.account);
        password = getView(view,R.id.password);
        code = getView(view,R.id.verification_code);
        image = getView(view,R.id.verification_image);
        image.setOnClickListener(this);
        getView(view, R.id.login).setOnClickListener(this);

        image.setImageBitmap(ImageCodeUtil.getInstance().createBitmap());

        account.setText("lesli");
        password.setText("a123456");
        code.setText(ImageCodeUtil.getInstance().getCode());

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.verification_image:

                image.setImageBitmap(ImageCodeUtil.getInstance().createBitmap());

                break;
            case R.id.login:
                if (TextUtils.isEmpty(account.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    showDialog("账号密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code.getText().toString())){
                    showDialog("验证码不能为空");
                    return;
                }
                if(!code.getText().toString().equals(ImageCodeUtil.getInstance().getCode())) {
                    showDialog("验证码错误");
                    return;
                }
                presenter.login(getActivity(),account.getText().toString(),password.getText().toString());
                break;
        }
    }

    @Override
    public void onDestroy() {

        presenter.destroy();

        super.onDestroy();
    }

    @Override
    public void jumpToActivity(Class clazz, boolean needFinish, LoginResponse  response) {

        CacheUtils.putString(getActivity(),"account",account.getText().toString().trim());

        Intent intent = new Intent(getActivity(),clazz);
        intent.putExtra("data",response);
        startActivity(intent);
        if (needFinish)
            getActivity().finish();
    }
}
