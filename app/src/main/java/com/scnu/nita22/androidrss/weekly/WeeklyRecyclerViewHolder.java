package com.scnu.nita22.androidrss.weekly;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.util.ItemClickListener;

/**
 * Created by nita22 on 2016/6/16.
 */

public class WeeklyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener mItemClickListener;
    TextView titleText;
    TextView dateText;

    public WeeklyRecyclerViewHolder(View itemView, ItemClickListener itemClickListener) {
        super(itemView);
        mItemClickListener = itemClickListener;
        titleText = (TextView) itemView.findViewById(R.id.weekly_title);
        dateText = (TextView) itemView.findViewById(R.id.weekly_date);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onItemClick(v, getAdapterPosition());
    }
}
