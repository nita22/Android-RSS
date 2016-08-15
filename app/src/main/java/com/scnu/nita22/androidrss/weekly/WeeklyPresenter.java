package com.scnu.nita22.androidrss.weekly;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.scnu.nita22.androidrss.weekly.WeeklyFragment.WEEKLY_BASE_URL;

/**
 * Created by nita22 on 2016/6/16.
 * Edited by nita22 on 2016/6/28.
 */

public class WeeklyPresenter implements WeeklyContract.WeeklyPresenter {

    private WeeklyContract.WeeklyView mWeeklyView;
    private Observable<WeeklyData> observable;
    private Subscriber<WeeklyData> subscriber;
    private ArrayList<String> dateList = new ArrayList<String>();

    public WeeklyPresenter(WeeklyContract.WeeklyView weeklyView) {
        mWeeklyView = weeklyView;
    }

    @Override
    public void getData(final String webUrl) {
        observable = Observable.create(new Observable.OnSubscribe<WeeklyData>() {
            @Override
            public void call(Subscriber<? super WeeklyData> subscriber) {
                dateList.clear();
                int j = 0;
                Document doc = null;//通过url获取到网页内容
                try {
                    doc = Jsoup.connect(webUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements dateElements = doc.getElementsByClass("date");
                for (Element e : dateElements) {
                    String date = e.ownText();
                    dateList.add(date);
                }

                Elements titleElements = doc.select(".h4").select(".title");
                for (Element e : titleElements) {
                    Elements title = e.getElementsByTag("a");//在每一个找到的元素中，查找<a>标签
                    WeeklyData weeklyData = new WeeklyData();
                    weeklyData.setTitle(e.text());
                    weeklyData.setUrl(WEEKLY_BASE_URL + title.attr("href"));
                    weeklyData.setPublishTime(dateList.get(j));
                    j++;
                    subscriber.onNext(weeklyData);
                }
                subscriber.onCompleted();
            }
        });
        subscriber = new Subscriber<WeeklyData>() {
            @Override
            public void onCompleted() {
                mWeeklyView.updateRecyclerView();
                if (webUrl.equals(WEEKLY_BASE_URL)) {
                    mWeeklyView.hideProgressBar();
                    mWeeklyView.showFinishedSnackBar();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (webUrl.equals(WEEKLY_BASE_URL)) {
                    mWeeklyView.hideProgressBar();
                }
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

    @Override
    public void getPageNumber(final String webUrl) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int pageNumber = 0;
                Document doc = null;//通过url获取到网页内容
                try {
                    doc = Jsoup.connect(webUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements pageElements = doc.getElementsByClass("page-number");
                String pageText = pageElements.text();
                Pattern p = Pattern.compile("(\\d+)");
                Matcher m = p.matcher(pageText);
                int i = 0;
                while (m.find()) {
                    if (!"".equals(m.group())) {
                        if (i == 1) {
                            pageNumber = Integer.parseInt(m.group());
                        }
                        i++;
                    }
                }
                subscriber.onNext(pageNumber);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        mWeeklyView.updatePageNumber(integer);
                    }
                });
    }

}
