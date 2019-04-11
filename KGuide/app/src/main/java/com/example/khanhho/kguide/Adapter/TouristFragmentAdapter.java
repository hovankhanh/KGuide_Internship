package com.example.khanhho.kguide.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.khanhho.kguide.Fragment.GuideFragment;
import com.example.khanhho.kguide.Fragment.TourFragment;

public class TouristFragmentAdapter extends FragmentStatePagerAdapter {
    private String listTab[] = {"Tour","Guide"};
    private TourFragment nTourFragment;
    private GuideFragment nGuideFragment;
    public TouristFragmentAdapter(FragmentManager fm) {
        super(fm);
        nTourFragment = new TourFragment();
        nGuideFragment = new GuideFragment();
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return nTourFragment;
        }else if (i == 1){
            return nGuideFragment;
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
