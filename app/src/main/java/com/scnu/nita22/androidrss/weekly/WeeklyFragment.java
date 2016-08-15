package com.scnu.nita22.androidrss.weekly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
 * Created by nita22 on 2016/6/15.
 * Edited by nita22 on 2016/6/27.
 */

public class WeeklyFragment extends Fragment implements WeeklyContract.WeeklyView, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String WEEKLY_BASE_URL = "http://www.androidweekly.cn";

    private int currentPageNumber = 0;
    private int pageNumber = 1;

    private int delayMillis = 1000;

    private WeeklyContract.WeeklyPresenter mWeeklyPresenter;
    private List<WeeklyData> mWeeklyDataList;
    private WeeklyRecyclerAdapter mWeeklyRecyclerAdapter;

    private CoordinatorLayout mCoordinatorLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private CircleProgressBar mCircleProgressBar;

    public static WeeklyFragment mWeeklyFragment;

    public static WeeklyFragment newInstance() {
        if (mWeeklyFragment == null) {
            mWeeklyFragment = new WeeklyFragment();
        }
        return mWeeklyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeeklyDataList = new ArrayList<WeeklyData>();
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
        mCircleProgressBar.setVisibility(View.VISIBLE);

        mWeeklyPresenter.getPageNumber(WEEKLY_BASE_URL);
        mWeeklyPresenter.getData(WEEKLY_BASE_URL);

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.weekly_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeeklyDataList.clear();
                currentPageNumber = 0;
                mWeeklyRecyclerAdapter.setNewData(mWeeklyDataList);
                mWeeklyRecyclerAdapter.openLoadMore(9, true);
                mWeeklyPresenter.getData(WEEKLY_BASE_URL);
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
        mWeeklyRecyclerAdapter = new WeeklyRecyclerAdapter(mWeeklyDataList);
        mRecyclerView.setAdapter(mWeeklyRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mWeeklyRecyclerAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("webUrl", mWeeklyDataList.get(i).getUrl());
                startActivity(intent);
            }
        });
        mWeeklyRecyclerAdapter.setOnLoadMoreListener(this);
        mWeeklyRecyclerAdapter.openLoadMore(9, true);
    }

    @Override
    public void updateRecyclerView() {
        mWeeklyRecyclerAdapter.notifyDataSetChanged();
        currentPageNumber++;
    }

    @Override
    public void updateData(WeeklyData weeklyData) {
        mWeeklyDataList.add(weeklyData);
    }

    @Override
    public void updatePageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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
    public void hideProgressBar() {
        mCircleProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(WeeklyContract.WeeklyPresenter presenter) {
        mWeeklyPresenter = presenter;
    }

    @Override
    public synchronized void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (currentPageNumber >= pageNumber) {
                    mWeeklyRecyclerAdapter.notifyDataChangedAfterLoadMore(false);
                    View footView = getActivity().getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                    mWeeklyRecyclerAdapter.addFooterView(footView);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWeeklyRecyclerAdapter.notifyDataChangedAfterLoadMore(true);
                            String webUrl = WEEKLY_BASE_URL + "/page/" + (currentPageNumber + 1);
                            mWeeklyPresenter.getData(webUrl);
                        }
                    }, delayMillis);
                }
            }
        });
    }
}
