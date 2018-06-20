package com.ttg.ecollection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ttg.ecollection.R;
import com.ttg.ecollection.base.BaseActivity;
import com.ttg.ecollection.data.BackHandlerHelper;

/**
 * Created by loveb on 2018/3/12 0012.
 */

public class MainActivity extends BaseActivity {
    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = new HomeFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("data",getIntent().getParcelableExtra("data"));
        fragment.setArguments(bundle);

        transaction.add(R.id.content,fragment,HomeFragment.class.getName()).commit();
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }
}
