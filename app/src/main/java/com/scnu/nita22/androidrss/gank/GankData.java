package com.scnu.nita22.androidrss.gank;

import java.util.List;

/**
 * Created by nita22 on 2016/6/12.
 */

public class GankData {

    /**
     * error : false
     * results : [{"_id":"575cb383421aa96b20cafaca","createdAt":"2016-06-12T08:57:39.269Z","desc":"滤镜SDK","publishedAt":"2016-06-12T12:04:04.308Z","source":"chrome","type":"Android","url":"https://github.com/Zomato/AndroidPhotoFilters","used":true,"who":"MVP"},{"_id":"5757d1a0421aa90ec0cb273b","createdAt":"2016-06-08T16:04:48.304Z","desc":"LoadingDrawable 一些酷炫的加载动画， 可以与任何View配合使用，作为加载动画或者Progressbar, 此外很适合与RecyclerRefreshLayout 配合使用作为刷新的loading 动画","publishedAt":"2016-06-12T12:04:04.308Z","source":"web","type":"Android","url":"https://github.com/dinuscxj/LoadingDrawable","used":true,"who":"郑铉"},{"_id":"5756c258421aa90ec3956a5b","createdAt":"2016-06-07T20:47:20.837Z","desc":"一个可以把数据库存在sdcard的工具","publishedAt":"2016-06-08T12:39:36.270Z","source":"chrome","type":"Android","url":"https://github.com/yaming116/android-sdcard-helper","used":true,"who":"花开堪折枝"},{"_id":"5754fd03421aa948eea75a3d","createdAt":"2016-06-06T12:33:07.589Z","desc":"Google Agera Wiki 中文版","publishedAt":"2016-06-07T11:43:18.947Z","source":"chrome","type":"Android","url":"https://github.com/captain-miao/AndroidAgeraTutorial/wiki","used":true,"who":"马琳"},{"_id":"5754cf0e421aa949027bcbdf","createdAt":"2016-06-06T09:17:02.449Z","desc":"绘制允许任何对SVG图形复杂的变形动画工具","publishedAt":"2016-06-06T12:24:22.149Z","source":"chrome","type":"Android","url":"https://github.com/bonnyfone/vectalign#vectalign----","used":true,"who":"有时放纵"},{"_id":"5754cab6421aa948fe1f7573","createdAt":"2016-06-06T08:58:30.415Z","desc":"jakewharton 出品 ，DrawerBehavior\n","publishedAt":"2016-06-06T12:24:22.149Z","source":"chrome","type":"Android","url":"https://github.com/JakeWharton/DrawerBehavior","used":true,"who":"花开堪折枝"},{"_id":"5754a587421aa948eea75a34","createdAt":"2016-06-06T06:19:51.521Z","desc":"类似ios的动态模糊效果","publishedAt":"2016-06-06T12:24:22.149Z","source":"chrome","type":"Android","url":"https://github.com/Dimezis/BlurView","used":true,"who":"大熊"},{"_id":"575408fc421aa948fe1f756a","createdAt":"2016-06-05T19:11:56.256Z","desc":"展开收缩的View","publishedAt":"2016-06-06T12:24:22.149Z","source":"chrome","type":"Android","url":"https://github.com/diegodobelo/AndroidExpandingViewLibrary","used":true,"who":"大熊"},{"_id":"575406f6421aa948eea75a2e","createdAt":"2016-06-05T19:03:18.536Z","desc":"A Funny ToggleButton for day and night change ","publishedAt":"2016-06-06T12:24:22.149Z","source":"web","type":"Android","url":"https://github.com/SilenceDut/DayNightToggleButton","used":true,"who":null},{"_id":"57529f1c421aa95653f1b944","createdAt":"2016-06-04T17:27:56.617Z","desc":"(MVP+RxJava+Retrofit)解耦+Mockito单元测试","publishedAt":"2016-06-07T11:43:18.947Z","source":"chrome","type":"Android","url":"http://www.jianshu.com/p/cdfeb6c3d099?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin","used":true,"who":"AndWang"}]
     */

    private boolean error;
    /**
     * _id : 575cb383421aa96b20cafaca
     * createdAt : 2016-06-12T08:57:39.269Z
     * desc : 滤镜SDK
     * publishedAt : 2016-06-12T12:04:04.308Z
     * source : chrome
     * type : Android
     * url : https://github.com/Zomato/AndroidPhotoFilters
     * used : true
     * who : MVP
     */

    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
