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

import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nita22 on 2016/6/12.
 */

public class GankFragment extends Fragment implements ItemClickListener, GankContract.GankView {

    private CoordinatorLayout mCoordinatorLayout;
    private RecyclerView mRecyclerView;
    private GankRecyclerAdapter mGankRecyclerAdapter;
    private List<GankData.ResultsBean> mGankDataList;
    private FloatingActionButton refreshFAB;

    private GankContract.GankPresenter mGankPresenter;

    public static GankFragment mGankFragment;

    public static GankFragment getInstance() {
        if (mGankFragment == null) {
            mGankFragment = new GankFragment();
        }
        return mGankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankDataList = new ArrayList<GankData.ResultsBean>();
        mGankRecyclerAdapter = new GankRecyclerAdapter(getActivity(), mGankDataList);
        setPresenter(new GankPresenter(this));
        mGankPresenter.getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_android, container, false);

        mCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.fragment_container);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        showRecyclerView();
        refreshFAB = (FloatingActionButton) rootView.findViewById(R.id.refresh_fab);
        refreshFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGankPresenter.getData();
            }
        });
        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), GankDetailActivity.class);
        intent.putExtra("webUrl", mGankDataList.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void showRecyclerView() {
        mRecyclerView.setAdapter(mGankRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mGankRecyclerAdapter.setOnItemClickListener(this);
    }

    @Override
    public void updateList() {
        mGankRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(GankData.ResultsBean resultsBean) {
        mGankDataList.add(resultsBean);
    }

    @Override
    public void showfinishedSnackBar() {
        Snackbar.make(mCoordinatorLayout, R.string.finished, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorSnackBar() {
        Snackbar.make(mCoordinatorLayout, R.string.error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(GankContract.GankPresenter gankPresenter) {
        mGankPresenter = gankPresenter;
    }
}
