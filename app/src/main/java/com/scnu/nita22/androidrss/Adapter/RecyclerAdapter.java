package com.scnu.nita22.androidrss.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.nita22.androidrss.Interface.ItemClickListener;
import com.scnu.nita22.androidrss.Model.GankData;
import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.Utils.TimeUtils;

import java.util.List;

/**
 * Created by nita22 on 2016/6/12.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context mContext;
    private List<GankData.ResultsBean> mArrayList;
    private ItemClickListener mItemClickListener;

    public RecyclerAdapter(Context context, List<GankData.ResultsBean> dataList) {
        mContext = context;
        mArrayList = dataList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(mContext).inflate(R.layout.android_list_item,
                parent, false);
        RecyclerViewHolder mHolder = new RecyclerViewHolder(listView, mItemClickListener);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

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
