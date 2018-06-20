package com.ttg.ecollection.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseActivity;

public class ChoosePayerActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = new ChoosePayerFragment();

        transaction.add(R.id.content,fragment,HomeFragment.class.getName()).commit();
    }

    @Override
    public void widgetClick(View v) {

    }
}
