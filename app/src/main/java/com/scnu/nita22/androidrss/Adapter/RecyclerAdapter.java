package com.scnu.nita22.androidrss.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.scnu.nita22.androidrss.Model.GankData;
import com.scnu.nita22.androidrss.R;

import java.util.List;

/**
 * Created by nita22 on 2016/6/12.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;
    private List<GankData.ResultsBean> mArrayList;

    public RecyclerAdapter(Context context, List<GankData.ResultsBean> dataList) {
        mContext = context;
        mArrayList = dataList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder mHolder = new RecyclerViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.android_list_item,
                        parent, false));
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.titleText.setText(mArrayList.get(position).getDesc());
        holder.desText.setText(mArrayList.get(position).getType());
        holder.dateText.setText(mArrayList.get(position).getPublishedAt());
    }

    @Override
    public int getItemCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        return 0;
    }


}
