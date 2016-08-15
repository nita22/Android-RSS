package com.scnu.nita22.androidrss.weekly;

import com.scnu.nita22.androidrss.BasePresenter;
import com.scnu.nita22.androidrss.BaseView;

/**
 * Created by nita22 on 2016/6/16.
 */

public interface WeeklyContract {

    interface WeeklyView extends BaseView<WeeklyContract.WeeklyPresenter> {
        void showRecyclerView();

        void updateRecyclerView();

        void updateData(WeeklyData weeklyData);

        void updatePageNumber(int pageNumber);

        void showFinishedSnackBar();

        void showErrorSnackBar();

        void hideProgressBar();
    }

    interface WeeklyPresenter extends BasePresenter {
        @Override
        void getData(String webUrl);

        @Override
        void disconnect();

        void getPageNumber(String webUrl);
    }
}
