package com.scnu.nita22.androidrss.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scnu.nita22.androidrss.R;

/**
 * Created by nita22 on 2016/6/12.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView titleText;
    TextView desText;
    TextView dateText;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.list_item_title);
        desText = (TextView) itemView.findViewById(R.id.list_item_desc);
        dateText = (TextView) itemView.findViewById(R.id.list_item_date);
    }

}
