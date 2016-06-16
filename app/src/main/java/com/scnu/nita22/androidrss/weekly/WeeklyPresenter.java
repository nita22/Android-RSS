package com.scnu.nita22.androidrss.weekly;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nita22 on 2016/6/16.
 */

public class WeeklyPresenter implements WeeklyContract.WeeklyPresenter {

    private static final String WEEKLY_BASE_URL = "http://www.androidweekly.cn";

    private WeeklyContract.WeeklyView mWeeklyView;
    private Observable<WeeklyData> observable;
    private Subscriber<WeeklyData> subscriber;

    public WeeklyPresenter(WeeklyContract.WeeklyView weeklyView) {
        mWeeklyView = weeklyView;
    }

    @Override
    public void getData() {
        observable = Observable.create(new Observable.OnSubscribe<WeeklyData>() {
            @Override
            public void call(Subscriber<? super WeeklyData> subscriber) {
                Document doc = null;//通过url获取到网页内容
                try {
                    doc = Jsoup.connect(WEEKLY_BASE_URL).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements elements = doc.getElementsByClass("post-title");//查找所有class为"link_title"的元素
                for (Element e : elements) {
                    Elements titles = e.getElementsByTag("a");//在每一个找到的元素中，查找<a>标签
                    for (Element title : titles) {
                        WeeklyData weeklyData = new WeeklyData();
                        weeklyData.setTitle(title.text());
                        weeklyData.setUrl(WEEKLY_BASE_URL + title.attr("href"));
                        subscriber.onNext(weeklyData);
                    }
                }
                subscriber.onCompleted();
            }
        });
        subscriber = new Subscriber<WeeklyData>() {
            @Override
            public void onCompleted() {
                mWeeklyView.updateRecyclerView();
                mWeeklyView.showFinishedSnackBar();
            }

            @Override
            public void onError(Throwable e) {
                mWeeklyView.showErrorSnackBar();
            }

            @Override
            public void onNext(WeeklyData weeklyData) {
                mWeeklyView.updateData(weeklyData);
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void disconnect() {
        if (subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }
}
