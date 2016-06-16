package com.scnu.nita22.androidrss.gank;

import com.scnu.nita22.androidrss.util.HttpUtils;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nita22 on 2016/6/14.
 */

public class GankPresenter implements GankContract.GankPresenter {

    private GankContract.GankView mGankView;
    private static final String GANK_BASE_URL = "http://gank.io/";

    private Observable<GankData> gankDataObservable;
    private Subscriber<GankData.ResultsBean> mSubscriber;

    public GankPresenter(GankContract.GankView gankView) {
        mGankView = gankView;
    }

    @Override
    public void getData() {
        Retrofit retrofit = HttpUtils.initRetrofit(GANK_BASE_URL);
        GankService gankService = retrofit.create(GankService.class);
        gankDataObservable = gankService.gankPost(100, 1);
        mSubscriber = new Subscriber<GankData.ResultsBean>() {
            @Override
            public void onCompleted() {
                mGankView.updateRecyclerView();
                mGankView.showFinishedSnackBar();
            }

            @Override
            public void onError(Throwable e) {
                mGankView.showErrorSnackBar();
            }

            @Override
            public void onNext(GankData.ResultsBean resultsBean) {
                mGankView.updateData(resultsBean);
            }
        };
        gankDataObservable
                .flatMap(new Func1<GankData, Observable<GankData.ResultsBean>>() {
                    @Override
                    public Observable<GankData.ResultsBean> call(GankData gankData) {
                        Observable<GankData.ResultsBean> resultsBeanObservable = null;
                        if (!gankData.isError()) {
                            resultsBeanObservable = Observable.from(gankData.getResults());
                        } else {
                            mGankView.showErrorSnackBar();
                        }
                        return resultsBeanObservable;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);
    }

    @Override
    public void disconnect() {
        if (!mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
    }
}
