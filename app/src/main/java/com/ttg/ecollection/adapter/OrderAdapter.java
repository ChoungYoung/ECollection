package com.ttg.ecollection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.data.OrdersData;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by loveb on 2018/3/16 0016.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.SearchViewHolder> {

    private OrdersData data;
    private Context context;

    public OrderAdapter(Context context,OrdersData data){
        this.context = context;
        this.data = data;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, null);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {

        holder.shopName.setText(data.getResult().get(position).getCompanyName());

        String amount = context.getString(R.string.order_amount,data.getResult().get(position).getPayAmt());
//        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
//        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_color_black)),5,amount.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.amount.setText(amount);


//        String time = context.getString(R.string.order_time_,SimpleDateFormat.getDateInstance().format(data.getResult().get(position).getModifyTime()));
        String time = context.getString(R.string.order_time_,data.getResult().get(position).getCol5());
//        builder = new SpannableStringBuilder(time);
//        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_color_black)),5,time.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.time.setText(time);

        holder.voucher.setText(context.getString(R.string.voucher,data.getResult().get(position).getCol3()));

    }

    @Override
    public int getItemCount() {
        return data.getResult().size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView shopName,amount,time,voucher;

        SearchViewHolder(View view) {
            super(view);

            shopName = view.findViewById(R.id.tv_shop_name);
            time = view.findViewById(R.id.tv_order_time);
            amount = view.findViewById(R.id.tv_order_amount);
            voucher = view.findViewById(R.id.tv_voucher);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (null != mOnItemClickListener)
                mOnItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

}
