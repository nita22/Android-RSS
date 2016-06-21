package com.scnu.nita22.androidrss;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.scnu.nita22.androidrss.gank.GankFragment;
import com.scnu.nita22.androidrss.info.InfoFragment;
import com.scnu.nita22.androidrss.weekly.WeeklyFragment;

import static com.scnu.nita22.androidrss.R.id.bar_android;
import static com.scnu.nita22.androidrss.R.id.bar_info;
import static com.scnu.nita22.androidrss.R.id.bar_weekly;

/**
 * Created by nita22 on 2016/6/12.
 */

public class MainActivity extends AppCompatActivity {

    private static final int BOTTOM_ITEM_TITLE_GANK_INDEX = 0;
    private static final int BOTTOM_ITEM_TITLE_WEEKLY_INDEX = 1;
    private static final int BOTTOM_ITEM_TITLE_INFO_INDEX = 2;

    private static int CURRENT_INDEX = 0;
    private boolean mGankFragmentAdded = false;
    private boolean mWeeklyFragmentAdded = false;
    private boolean mInfoFragmentAdded = false;

    private BottomBar mBottomBar;

    private FragmentManager mFragmentManager;
    private GankFragment mGankFragment;
    private WeeklyFragment mWeeklyFragment;
    private InfoFragment mInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        initBottomBar(savedInstanceState);
    }

    private void initBottomBar(Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noNavBarGoodness();
        mBottomBar.setMaxFixedTabs(2);   //less than 3 items should do it
        mBottomBar.setItems(R.menu.bottom_bar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case bar_android:
                        changeFragment(BOTTOM_ITEM_TITLE_GANK_INDEX);
                        break;
                    case bar_weekly:
                        changeFragment(BOTTOM_ITEM_TITLE_WEEKLY_INDEX);
                        break;
                    case bar_info:
                        changeFragment(BOTTOM_ITEM_TITLE_INFO_INDEX);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case bar_android:
                        break;
                    case bar_weekly:
                        break;
                    case bar_info:
                        break;
                    default:
                        break;
                }
            }
        });
        mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_GANK_INDEX, ContextCompat.getColor(this, R.color.colorPrimary));
        mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_WEEKLY_INDEX, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_INFO_INDEX, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.setActiveTabColor("#009688");
    }

    private void changeFragment(int i) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (i == CURRENT_INDEX && (!mGankFragmentAdded)) {
            fragmentTransaction.add(R.id.fragment_container, getFragment(BOTTOM_ITEM_TITLE_GANK_INDEX));
            mGankFragmentAdded = true;
            CURRENT_INDEX = BOTTOM_ITEM_TITLE_GANK_INDEX;
        } else {
            fragmentTransaction.hide(getFragment(CURRENT_INDEX));

            switch (i) {
                case BOTTOM_ITEM_TITLE_GANK_INDEX:
                    if (mGankFragmentAdded) {
                        fragmentTransaction.show(getFragment(BOTTOM_ITEM_TITLE_GANK_INDEX));
                    } else {
                        fragmentTransaction.add(R.id.fragment_container, getFragment(BOTTOM_ITEM_TITLE_GANK_INDEX));
                        mGankFragmentAdded = true;
                    }
                    CURRENT_INDEX = BOTTOM_ITEM_TITLE_GANK_INDEX;
                    break;
                case BOTTOM_ITEM_TITLE_WEEKLY_INDEX:
                    if (mWeeklyFragmentAdded) {
                        fragmentTransaction.show(getFragment(BOTTOM_ITEM_TITLE_WEEKLY_INDEX));
                    } else {
                        fragmentTransaction.add(R.id.fragment_container, getFragment(BOTTOM_ITEM_TITLE_WEEKLY_INDEX));
                        mWeeklyFragmentAdded = true;
                    }
                    CURRENT_INDEX = BOTTOM_ITEM_TITLE_WEEKLY_INDEX;
                    break;
                case BOTTOM_ITEM_TITLE_INFO_INDEX:
                    if (mInfoFragmentAdded) {
                        fragmentTransaction.show(getFragment(BOTTOM_ITEM_TITLE_INFO_INDEX));
                    } else {
                        fragmentTransaction.add(R.id.fragment_container, getFragment(BOTTOM_ITEM_TITLE_INFO_INDEX));
                        mInfoFragmentAdded = true;
                    }
                    CURRENT_INDEX = BOTTOM_ITEM_TITLE_INFO_INDEX;
                    break;
            }
        }
        fragmentTransaction.commit();
    }

    private Fragment getFragment(int i) {
        switch (i) {
            case BOTTOM_ITEM_TITLE_GANK_INDEX:
                if (mGankFragment == null) {
                    mGankFragment = GankFragment.newInstance();
                }
                return mGankFragment;
            case BOTTOM_ITEM_TITLE_WEEKLY_INDEX:
                if (mWeeklyFragment == null) {
                    mWeeklyFragment = WeeklyFragment.newInstance();
                }
                return mWeeklyFragment;
            case BOTTOM_ITEM_TITLE_INFO_INDEX:
                if (mInfoFragment == null) {
                    mInfoFragment = InfoFragment.newInstance();
                }
                return mInfoFragment;
            default:
                break;
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
}
