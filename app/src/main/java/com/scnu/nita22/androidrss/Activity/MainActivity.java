package com.scnu.nita22.androidrss.Activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.scnu.nita22.androidrss.Fragment.AndroidFragment;
import com.scnu.nita22.androidrss.R;

import static com.scnu.nita22.androidrss.R.id.bar_android;
import static com.scnu.nita22.androidrss.R.id.bar_search;
import static com.scnu.nita22.androidrss.R.id.bar_setting;

/**
 * Created by nita22 on 2016/6/12.
 */

public class MainActivity extends AppCompatActivity {

    private static final int BOTTOM_ITEM_TITLE_ANDROID_INDEX = 0;
    private static final int BOTTOM_ITEM_TITLE_SEARCH_INDEX = 1;
    private static final int BOTTOM_ITEM_TITLE_SETTING_INDEX = 2;
    private static final String BOTTOM_ITEM_TITLE_ANDROID = "Android";
    private static final String BOTTOM_ITEM_TITLE_SEARCH = "搜索";
    private static final String BOTTOM_ITEM_TITLE_SETTING = "设置";

    private BottomBar mBottomBar;
    private CoordinatorLayout mCoordinatorLayout;
    private NestedScrollView mNestedScrollView;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);

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
                        changeFragment(BOTTOM_ITEM_TITLE_ANDROID_INDEX);
                        break;
                    case bar_search:
                        changeFragment(BOTTOM_ITEM_TITLE_SEARCH_INDEX);
                        break;
                    case bar_setting:
                        changeFragment(BOTTOM_ITEM_TITLE_SETTING_INDEX);
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
                    case bar_search:
                        break;
                    case bar_setting:
                        break;
                    default:
                        break;
                }
            }
        });
        mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_ANDROID_INDEX, ContextCompat.getColor(this, R.color.colorPrimary));
        mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_SEARCH_INDEX, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mBottomBar.mapColorForTab(BOTTOM_ITEM_TITLE_SETTING_INDEX, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.setActiveTabColor("#009688");
    }

    private void changeFragment(int i) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (i) {
            case BOTTOM_ITEM_TITLE_ANDROID_INDEX:
                fragmentTransaction.add(R.id.fragment_container, getFragment(BOTTOM_ITEM_TITLE_ANDROID_INDEX));
                break;
            case BOTTOM_ITEM_TITLE_SEARCH_INDEX:
                break;
            case BOTTOM_ITEM_TITLE_SETTING_INDEX:
                break;
        }
        fragmentTransaction.commit();
    }

    private Fragment getFragment(int i) {

        switch (i) {
            case BOTTOM_ITEM_TITLE_ANDROID_INDEX:
                return new AndroidFragment();
            case BOTTOM_ITEM_TITLE_SEARCH_INDEX:
                break;
            case BOTTOM_ITEM_TITLE_SETTING_INDEX:
                break;
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