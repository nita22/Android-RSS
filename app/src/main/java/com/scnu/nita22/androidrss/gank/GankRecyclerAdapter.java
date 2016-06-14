package com.scnu.nita22.androidrss.gank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.util.ItemClickListener;
import com.scnu.nita22.androidrss.util.TimeUtils;

import java.util.List;

/**
 * Created by nita22 on 2016/6/12.
 */

public class GankRecyclerAdapter extends RecyclerView.Adapter<GankRecyclerViewHolder> {

    private Context mContext;
    private List<GankData.ResultsBean> mArrayList;
    private ItemClickListener mItemClickListener;

    public GankRecyclerAdapter(Context context, List<GankData.ResultsBean> dataList) {
        mContext = context;
        mArrayList = dataList;
    }

    @Override
    public GankRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(mContext).inflate(R.layout.android_list_item,
                parent, false);
        GankRecyclerViewHolder mHolder = new GankRecyclerViewHolder(listView, mItemClickListener);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(GankRecyclerViewHolder holder, int position) {

        holder.titleText.setText(mArrayList.get(position).getDesc());
        holder.desText.setText(mArrayList.get(position).getType());
        holder.dateText.setText(TimeUtils.getFormatTime(mArrayList.get(position).getPublishedAt()));
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
