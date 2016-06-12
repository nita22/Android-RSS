package com.scnu.nita22.androidrss;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;


public class MainActivity extends AppCompatActivity {

    private static final int BOTTOM_ITEM_TITLE_ANDROID_INDEX = 0;
    private static final int BOTTOM_ITEM_TITLE_SEARCH_INDEX = 1;
    private static final int BOTTOM_ITEM_TITLE_SETTING_INDEX = 2;
    private static final String BOTTOM_ITEM_TITLE_ANDROID = "Android";
    private static final String BOTTOM_ITEM_TITLE_SEARCH = "搜索";
    private static final String BOTTOM_ITEM_TITLE_SETTING = "设置";
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    case BOTTOM_ITEM_TITLE_ANDROID_INDEX:
                        break;
                    case BOTTOM_ITEM_TITLE_SEARCH_INDEX:
                        break;
                    case BOTTOM_ITEM_TITLE_SETTING_INDEX:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                switch (menuItemId) {
                    case BOTTOM_ITEM_TITLE_ANDROID_INDEX:
                        break;
                    case BOTTOM_ITEM_TITLE_SEARCH_INDEX:
                        break;
                    case BOTTOM_ITEM_TITLE_SETTING_INDEX:
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
}
