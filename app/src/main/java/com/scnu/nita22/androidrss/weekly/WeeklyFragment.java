package com.scnu.nita22.androidrss.weekly;

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

import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.scnu.nita22.androidrss.DetailActivity;
import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.util.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nita22 on 2016/6/15.
 */

public class WeeklyFragment extends Fragment implements WeeklyContract.WeeklyView, ItemClickListener {

    private WeeklyContract.WeeklyPresenter mWeeklyPresenter;
    private List<WeeklyData> mWeeklyDataList;
    private WeeklyRecyclerAdapter mWeeklyRecyclerAdapter;

    private CoordinatorLayout mCoordinatorLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private CircleProgressBar mCircleProgressBar;

    public static WeeklyFragment mWeeklyFragment;

    public static WeeklyFragment getInstance() {
        if (mWeeklyFragment == null) {
            mWeeklyFragment = new WeeklyFragment();
        }
        return mWeeklyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeeklyDataList = new ArrayList<WeeklyData>();
        mWeeklyRecyclerAdapter = new WeeklyRecyclerAdapter(getActivity(), mWeeklyDataList);
        setPresenter(new WeeklyPresenter(this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weekly, container, false);
        mCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.weekly_container);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.weekly_recyclerview);
        showRecyclerView();
        mCircleProgressBar = (CircleProgressBar) rootView.findViewById(R.id.weekly_progressBar);
        mCircleProgressBar.setCircleBackgroundEnabled(false);
        mWeeklyPresenter.getData();

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.weekly_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeeklyDataList.clear();
                mWeeklyPresenter.getData();
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mWeeklyPresenter.disconnect();
    }

    @Override
    public void showRecyclerView() {
        mRecyclerView.setAdapter(mWeeklyRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mWeeklyRecyclerAdapter.setOnItemClickListener(this);
    }

    @Override
    public void updateRecyclerView() {
        mWeeklyRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(WeeklyData weeklyData) {
        mWeeklyDataList.add(weeklyData);
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
    public void setPresenter(WeeklyContract.WeeklyPresenter presenter) {
        mWeeklyPresenter = presenter;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("webUrl", mWeeklyDataList.get(position).getUrl());
        startActivity(intent);
    }
}
