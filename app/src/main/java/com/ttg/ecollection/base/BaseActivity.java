package com.ttg.ecollection.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ttg.ecollection.R;
import com.ttg.ecollection.util.StatusBarCompat;

/**
 * Created by loveb on 2018/3/8 0008.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式状态栏
        StatusBarCompat.compat(this, getResources().getColor(R.color.status_bar));

        View view = LayoutInflater.from(this).inflate(bindLayout(), null);

        setContentView(view);

        initView(view);

    }

    public abstract int bindLayout();
    public abstract void initView(View view);

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick()) widgetClick(v);
    }


    private long lastClick = 0;
    /**
     * [防止快速点击] * * @return
     */
    private boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

}
