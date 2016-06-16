package com.scnu.nita22.androidrss.gank;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.util.ItemClickListener;

/**
 * Created by nita22 on 2016/6/12.
 */

public class GankRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener mItemClickListener;
    TextView titleText;
    TextView dateText;
    TextView authorText;

    public GankRecyclerViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView);

        mItemClickListener = itemClickListener;
        titleText = (TextView) itemView.findViewById(R.id.list_item_title);
        authorText = (TextView) itemView.findViewById(R.id.list_item_author);
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
