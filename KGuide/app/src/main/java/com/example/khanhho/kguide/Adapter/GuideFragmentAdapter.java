package com.example.khanhho.kguide.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.khanhho.kguide.Fragment.HomeFragment;
import com.example.khanhho.kguide.Fragment.MyTourFragment;

public class GuideFragmentAdapter extends FragmentStatePagerAdapter {
    private String listTab[] = {"Home","My Tour"};
    private HomeFragment nHomeFragment;
    private MyTourFragment nMyTourFragment;


    public GuideFragmentAdapter(FragmentManager fm) {
        super(fm);
        nHomeFragment = new HomeFragment();
        nMyTourFragment = new MyTourFragment();
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return nHomeFragment;
        }else if (i == 1){
            return nMyTourFragment;
        }else {

        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
