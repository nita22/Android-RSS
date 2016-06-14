package com.scnu.nita22.androidrss.gank;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nita22 on 2016/6/12.
 */

public interface GankService {
    @GET("api/data/Android/{count}/{page}")
    Observable<GankData> gankPost(
            @Path("count") int count,
            @Path("page") int page
    );
}
