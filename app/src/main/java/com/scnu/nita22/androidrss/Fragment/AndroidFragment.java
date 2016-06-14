package com.scnu.nita22.androidrss.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.melnykov.fab.FloatingActionButton;
import com.scnu.nita22.androidrss.Activity.DetailActivity;
import com.scnu.nita22.androidrss.Adapter.RecyclerAdapter;
import com.scnu.nita22.androidrss.Interface.GankService;
import com.scnu.nita22.androidrss.Interface.ItemClickListener;
import com.scnu.nita22.androidrss.Model.GankData;
import com.scnu.nita22.androidrss.R;
import com.scnu.nita22.androidrss.Utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nita22 on 2016/6/12.
 */

public class AndroidFragment extends Fragment implements ItemClickListener {

    private FrameLayout mFrameLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private List<GankData.ResultsBean> mGankDataList;
    private FloatingActionButton refreshFAB;

    public static AndroidFragment mAndroidFragment;

    public static AndroidFragment getInstance() {
        if (mAndroidFragment == null) {
            mAndroidFragment = new AndroidFragment();
        }
        return mAndroidFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankDataList = new ArrayList<GankData.ResultsBean>();
        mRecyclerAdapter = new RecyclerAdapter(getActivity(), mGankDataList);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_android, container, false);

        mFrameLayout = (FrameLayout) rootView.findViewById(R.id.fragment_container);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerAdapter.setOnItemClickListener(this);

        refreshFAB = (FloatingActionButton) rootView.findViewById(R.id.refresh_fab);
        refreshFAB.attachToRecyclerView(mRecyclerView);
        refreshFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        return rootView;
    }

    public void getData() {
        Retrofit retrofit = HttpUtils.initRetrofit();
        GankService gankService = retrofit.create(GankService.class);
        Observable<GankData> gankDataObservable = gankService.gankPost(100, 1);
        gankDataObservable
                .flatMap(new Func1<GankData, Observable<GankData.ResultsBean>>() {
                    @Override
                    public Observable<GankData.ResultsBean> call(GankData gankData) {
                        Observable<GankData.ResultsBean> resultsBeanObservable = null;
                        if (!gankData.isError()) {
                            resultsBeanObservable = Observable.from(gankData.getResults());
                        } else {
                            Snackbar.make(mFrameLayout, R.string.error, Snackbar.LENGTH_SHORT).show();
                        }
                        return resultsBeanObservable;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankData.ResultsBean>() {
                    @Override
                    public void onCompleted() {
                        Snackbar.make(mFrameLayout, R.string.finished, Snackbar.LENGTH_SHORT).show();
                        mRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(mFrameLayout, R.string.error, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GankData.ResultsBean resultsBean) {
                        mGankDataList.add(resultsBean);
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("webUrl", mGankDataList.get(position).getUrl());
        startActivity(intent);
    }
}
