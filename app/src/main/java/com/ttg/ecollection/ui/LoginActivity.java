package com.ttg.ecollection.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseActivity;
import com.ttg.ecollection.ui.LoginFragment;

public class LoginActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        Toolbar toolbar = findViewById(R.id.toolbar);

        findViewById(R.id.back).setVisibility(View.GONE);

        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = new LoginFragment();

        transaction.add(R.id.content,fragment).commit();
    }

    @Override
    public void widgetClick(View v) {

    }

}
