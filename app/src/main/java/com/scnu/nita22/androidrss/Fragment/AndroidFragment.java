package com.scnu.nita22.androidrss.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.scnu.nita22.androidrss.Adapter.RecyclerAdapter;
import com.scnu.nita22.androidrss.Interface.GankService;
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

public class AndroidFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private List<GankData.ResultsBean> mGankDataList;
    private FloatingActionButton refreshFAB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankDataList = new ArrayList<GankData.ResultsBean>();
        mRecyclerAdapter = new RecyclerAdapter(getActivity(), mGankDataList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_android, null);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        refreshFAB = (FloatingActionButton) rootView.findViewById(R.id.refresh_fab);
        refreshFAB.attachToRecyclerView(mRecyclerView);
        refreshFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(getActivity());
            }
        });
        return rootView;
    }

    public void getData(final Context mContext) {
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
                            Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                        }
                        return resultsBeanObservable;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankData.ResultsBean>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(mContext, R.string.finished, Toast.LENGTH_SHORT).show();
                        mRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GankData.ResultsBean resultsBean) {
                        mGankDataList.add(resultsBean);
                    }
                });
    }
}
