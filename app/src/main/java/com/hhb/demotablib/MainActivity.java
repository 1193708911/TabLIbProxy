package com.hhb.demotablib;


import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hhb.demotablib.tab.MFragment;
import com.hhb.demotablib.tab.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPagerIndicator mScrollView;

    private LinearLayout ll_root;
    private ViewPager viewpager;
    private int mOldX;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScrollView = findViewById(R.id.scroll);
        viewpager = findViewById(R.id.viewpager);


        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                MFragment fragment = new MFragment();
                Bundle bundle = new Bundle();
                bundle.putString("key", "button" + (position + 1));
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return 10;
            }
        });
        mScrollView.addTabs(generateTabs());
        mScrollView.setViewPager(viewpager);


    }

    private List<String> generateTabs() {
        List<String> tabs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tabs.add("新闻" + i);
        }
        return tabs;
    }


}