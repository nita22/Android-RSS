package com.scnu.nita22.androidrss.info;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.scnu.nita22.androidrss.DetailActivity;
import com.scnu.nita22.androidrss.R;

public class InfoFragment extends Fragment {

    private static final String GITHUB_BASE_URL = "https://github.com/nita22/Android-RSS";


    private LinearLayout githubLinearLayout;
    private LinearLayout thankLinearLayout;

    public static InfoFragment mInfoFragment;

    public static InfoFragment newInstance() {
        if (mInfoFragment == null) {
            mInfoFragment = new InfoFragment();
        }
        return mInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);
        githubLinearLayout = (LinearLayout) rootView.findViewById(R.id.github_ll);
        githubLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("webUrl", GITHUB_BASE_URL);
                startActivity(intent);
            }
        });
        thankLinearLayout=(LinearLayout) rootView.findViewById(R.id.thanks_ll);
        thankLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.thanks)
                        .customView(R.layout.dialog_view, true)
                        .positiveText(R.string.close)
                        .show();
            }
        });
        return rootView;
    }

}
