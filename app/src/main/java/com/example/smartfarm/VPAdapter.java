package com.example.smartfarm;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VPAdapter extends FragmentPagerAdapter {
    private int mPageCount;

    public VPAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TemFragment temFragment = new TemFragment();
                return temFragment;
            case 1:
                MoisFragment moisFragment = new MoisFragment();
                return moisFragment;
            case 2:
                LuxFragment luxFragment = new LuxFragment();
                return luxFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}