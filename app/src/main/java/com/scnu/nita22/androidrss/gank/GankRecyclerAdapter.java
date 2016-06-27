package com.scnu.nita22.androidrss.gank;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.util.TimeUtils;

import java.util.List;

/**
 * Edited by nita22 on 2016/6/27.
 */

public class GankRecyclerAdapter extends BaseQuickAdapter<GankData.ResultsBean> {

    public GankRecyclerAdapter(List<GankData.ResultsBean> data) {
        super(R.layout.gank_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GankData.ResultsBean resultsBean) {
        baseViewHolder.setText(R.id.list_item_title, resultsBean.getDesc());
        baseViewHolder.setText(R.id.list_item_author, resultsBean.getWho());
        baseViewHolder.setText(R.id.list_item_date, TimeUtils.getFormatTime(resultsBean.getPublishedAt()));
    }
}
