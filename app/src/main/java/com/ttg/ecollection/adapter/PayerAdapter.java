package com.ttg.ecollection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttg.ecollection.R;
import com.ttg.ecollection.data.PayerData;

import java.util.List;

/**
 * Created by loveb on 2018/3/19 0019.
 */

public class PayerAdapter extends RecyclerView.Adapter<PayerAdapter.PayerViewHolder>{

    private Context context;
    private PayerData payers;

    public PayerAdapter(Context context,PayerData payers){
        this.context = context;
        this.payers = payers;
    }

    @Override
    public PayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payer, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new PayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PayerViewHolder holder, int position) {
        holder.name.setText(payers.getResult().get(position).getDealerName());
    }

    @Override
    public int getItemCount() {
        return payers.getResult().size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private PayerAdapter.OnItemClickListener mOnItemClickListener;//声明接口

    public void setOnItemClickListener(PayerAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class PayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;

        PayerViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_payer_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

}
