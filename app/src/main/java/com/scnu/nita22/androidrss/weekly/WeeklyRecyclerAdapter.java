package com.scnu.nita22.androidrss.weekly;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.util.ItemClickListener;

import java.util.List;

/**
 * Created by nita22 on 2016/6/16.
 */

public class WeeklyRecyclerAdapter extends RecyclerView.Adapter<WeeklyRecyclerViewHolder> {
    private Context mContext;
    private List<WeeklyData> mArrayList;
    private ItemClickListener mItemClickListener;

    public WeeklyRecyclerAdapter(Context context, List<WeeklyData> arrayList) {
        mContext = context;
        mArrayList = arrayList;
    }

    @Override
    public WeeklyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(mContext).inflate(R.layout.weekly_list_item,
                parent, false);
        WeeklyRecyclerViewHolder mHolder = new WeeklyRecyclerViewHolder(listView, mItemClickListener);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(WeeklyRecyclerViewHolder holder, int position) {
        holder.titleText.setText(mArrayList.get(position).getTitle());
        holder.dateText.setText(mArrayList.get(position).getPublishTime());
    }

    @Override
    public int getItemCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
