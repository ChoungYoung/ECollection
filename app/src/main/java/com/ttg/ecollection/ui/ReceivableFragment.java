package com.ttg.ecollection.ui;

import com.ttg.ecollection.R;
import com.ttg.ecollection.adapter.OrderAdapter;
import com.ttg.ecollection.base.BaseFragment;
import com.ttg.ecollection.data.OrdersData;
import com.ttg.ecollection.presenter.ReceivablePresenter;
import com.ttg.ecollection.util.UIUtil;
import com.ttg.ecollection.view.IReceivableView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * Created by loveb on 2018/3/15 0015.
 */

public class ReceivableFragment extends BaseFragment<ReceivablePresenter> implements IReceivableView {

    private EditText search;
//    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ReceivablePresenter presenter;
    private OrderAdapter adapter;
    private TextView nothing;
    private OrdersData data;
    private int page = 1;
    private String companyName = "";
    private LinearLayoutManager layoutManager;

    @Override
    public ReceivablePresenter initPresent() {
        presenter = new ReceivablePresenter(this,getActivity());
        return presenter;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_receivable;
    }

    @Override
    public void initView(View view) {
        RelativeLayout back = getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

        ((TextView) getActivity().findViewById(R.id.title)).setText(R.string.main_receivables);

        getView(view,R.id.iv_search).setOnClickListener(this);
        search = getView(view,R.id.et_search);
        recyclerView = getView(view,R.id.rv_receivable);
        nothing = getView(view,R.id.tv_nothing);

        layoutManager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//        refreshLayout = getView(view,R.id.refreshLayout);

//        initRefreshLayout();

        if (null == data){
            presenter.getData(getActivity(),companyName,page);
        }else{
            setRecyclerView();
        }

        search.setOnKeyListener((view1, keyCode, keyEvent) ->  {
            if(keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

                data = null;

                page = 1;
                companyName = search.getText().toString().trim();

                presenter.getData(getActivity(),companyName,page);

                return true;
            }
            return false;
        });
    }

//    private void initRefreshLayout() {
//        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
//                android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                 设置可见
//                refreshLayout.setRefreshing(true);
//                // 重置adapter的数据源为空
////                setData(null);
//                page = 1;
//                data = null;
//                // 获取第第0条到第PAGE_COUNT（值为10）条的数据
//                presenter.getData(getContext(),"",page);
//            }
//        });
//    }

    @Override
    public void setData(final OrdersData ordersData) {
//        refreshLayout.setRefreshing(false);
        if (ordersData.getResult().size() <= 0){
            showDialog("无更多数据");
        }
        if (null == data || null == data.getResult()){
            this.data = ordersData;
        }else {
            data.getResult().addAll(ordersData.getResult());
            adapter.notifyDataSetChanged();
            return;
        }

        if (data.getResult().size() < 1){
            recyclerView.setVisibility(View.INVISIBLE);
            nothing.setVisibility(View.VISIBLE);
        }else{

            recyclerView.setVisibility(View.VISIBLE);
            nothing.setVisibility(View.GONE);

            setRecyclerView();
        }
    }

    @Override
    public void getDataFail() {
        recyclerView.setVisibility(View.INVISIBLE);
        nothing.setVisibility(View.VISIBLE);
    }

    private boolean scrollUp;
    public void setRecyclerView(){
        adapter = new OrderAdapter(getActivity(),data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view, position) -> {
            Fragment fragment = new ReceivableDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("data",data.getResult().get(position));
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content,fragment,ReceivableDetailFragment.class.getName());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem + 1 == adapter.getItemCount() && scrollUp) {
                        page ++;
                        presenter.getData(getActivity(),companyName,page);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                scrollUp = dy > 0;
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.back:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_search:
                data = null;
                page = 1;
                companyName = search.getText().toString().trim();
                presenter.getData(getActivity(),companyName,page);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

}
