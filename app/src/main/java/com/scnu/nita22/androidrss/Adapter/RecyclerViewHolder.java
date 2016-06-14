package com.scnu.nita22.androidrss.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scnu.nita22.androidrss.Interface.ItemClickListener;
import com.scnu.nita22.androidrss.R;

/**
 * Created by nita22 on 2016/6/12.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener mItemClickListener;
    TextView titleText;
    TextView desText;
    TextView dateText;

    public RecyclerViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView);

        mItemClickListener = itemClickListener;
        titleText = (TextView) itemView.findViewById(R.id.list_item_title);
        desText = (TextView) itemView.findViewById(R.id.list_item_desc);
        dateText = (TextView) itemView.findViewById(R.id.list_item_date);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
