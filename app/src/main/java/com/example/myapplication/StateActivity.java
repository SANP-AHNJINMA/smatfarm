package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class StateActivity extends AppCompatActivity {
    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private VPAdapter vpAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_main);

        mContext = getApplicationContext();
        mTabLayout = (TabLayout) findViewById(R.id.tab);

        mTabLayout.addTab(mTabLayout.newTab().setText("온도 설정"));
        mTabLayout.addTab(mTabLayout.newTab().setText("습도 설정"));
        mTabLayout.addTab(mTabLayout.newTab().setText("조도 설정"));

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        vpAdapter = new VPAdapter(
                getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(vpAdapter);

        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    /*private View createTabView(String tabName) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.tab, null);
        TextView txt_name = (TextView) tabView.findViewById(R.id.txt_name);
        txt_name.setText(tabName);
        return tabView;
    }*/
}