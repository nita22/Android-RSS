package com.scnu.nita22.androidrss.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.scnu.nita22.androidrss.DetailActivity;
import com.scnu.nita22.androidrss.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nita22 on 2016/6/12.
 * Edited by nita22 on 2016/6/27.
 */

public class GankFragment extends Fragment implements GankContract.GankView {

    private CoordinatorLayout mCoordinatorLayout;
    private RecyclerView mRecyclerView;
    private GankRecyclerAdapter mGankRecyclerAdapter;
    private List<GankData.ResultsBean> mGankDataList;
    private FloatingActionButton refreshFAB;
    private CircleProgressBar mCircleProgressBar;

    private GankContract.GankPresenter mGankPresenter;

    public static GankFragment mGankFragment;

    public static GankFragment newInstance() {
        if (mGankFragment == null) {
            mGankFragment = new GankFragment();
        }
        return mGankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankDataList = new ArrayList<GankData.ResultsBean>();
        setPresenter(new GankPresenter(this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gank, container, false);

        mCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.gank_container);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.gank_recyclerview);
        showRecyclerView();

        mCircleProgressBar = (CircleProgressBar) rootView.findViewById(R.id.gank_progressBar);
        mCircleProgressBar.setCircleBackgroundEnabled(false);
        mGankPresenter.getData();

        refreshFAB = (FloatingActionButton) rootView.findViewById(R.id.gank_fab);
        refreshFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGankDataList.clear();
                mGankPresenter.getData();
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mGankPresenter.disconnect();
    }

    @Override
    public void showRecyclerView() {
        mGankRecyclerAdapter = new GankRecyclerAdapter(mGankDataList);
        mRecyclerView.setAdapter(mGankRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mGankRecyclerAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("webUrl", mGankDataList.get(i).getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void updateRecyclerView() {
        mGankRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(GankData.ResultsBean resultsBean) {
        mGankDataList.add(resultsBean);
    }

    @Override
    public void showFinishedSnackBar() {
        Snackbar.make(mCoordinatorLayout, R.string.finished, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorSnackBar() {
        Snackbar.make(mCoordinatorLayout, R.string.error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mCircleProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        mCircleProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(GankContract.GankPresenter gankPresenter) {
        mGankPresenter = gankPresenter;
    }
}
