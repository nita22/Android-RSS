package com.scnu.nita22.androidrss.weekly;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scnu.nita22.androidrss.R;

import java.util.List;

/**
 * Created by nita22 on 2016/6/16.
 * Edited by nita22 on 2016/6/27.
 */

public class WeeklyRecyclerAdapter extends BaseQuickAdapter<WeeklyData> {

    public WeeklyRecyclerAdapter(List<WeeklyData> data) {
        super(R.layout.weekly_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WeeklyData weeklyData) {
        baseViewHolder.setText(R.id.weekly_title, weeklyData.getTitle());
        baseViewHolder.setText(R.id.weekly_date, weeklyData.getPublishTime());
    }


}
