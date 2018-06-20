package com.ttg.ecollection.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.adapter.PayerAdapter;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.data.PayerData;
import com.ttg.ecollection.presenter.ChoosePayerPresenter;
import com.ttg.ecollection.view.IChoosePayerView;

/**
 * Created by loveb on 2018/3/19 0019.
 */

public class ChoosePayerFragment extends BaseFragment implements IChoosePayerView{

    private ChoosePayerPresenter presenter;
    private ImageView clear;
    private EditText search;
    private RecyclerView recyclerView;
    private TextView nothing;

    @Override
    public ChoosePayerPresenter initPresent() {
        presenter = new ChoosePayerPresenter(this);
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_choose_payer;
    }

    @Override
    public void initView(View view) {
        RelativeLayout back = getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.choose_payer);

        clear = (ImageView) getView(view,R.id.iv_search_clear);
        search = (EditText) getView(view,R.id.et_search);
        recyclerView = (RecyclerView) getView(view,R.id.rv_receivable);
        nothing = (TextView) getView(view,R.id.tv_nothing);

        presenter.getPayers(getActivity(),"",1);


        clear.setOnClickListener(this);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP){
                    presenter.getPayers(getActivity(),search.getText().toString().trim(),1);
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.iv_search_clear:
                search.setText("");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void setRecyclerView(final PayerData data) {
        if (null == data || data.getTotalCount() < 1){
            recyclerView.setVisibility(View.INVISIBLE);
            nothing.setVisibility(View.VISIBLE);
        }else{

            recyclerView.setVisibility(View.VISIBLE);
            nothing.setVisibility(View.GONE);

            PayerAdapter adapter = new PayerAdapter(getActivity(),data);
            LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
            //设置RecyclerView 布局
            recyclerView.setLayoutManager(layoutmanager);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new PayerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Intent intent = new Intent();
                    intent.putExtra("shopName",data.getResult().get(position).getDealerName());
                    intent.putExtra("shopId",data.getResult().get(position).getDealerId());

                    getActivity().setResult(ChoosePayerActivity.RESULT_OK,intent);
                    getActivity().finish();
                }
            });
        }
    }
}
