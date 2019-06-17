package com.example.khanhho.kguide.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.khanhho.kguide.Fragment.RatingFragment;
import com.example.khanhho.kguide.Fragment.TourDetailFragment;

public class GuideDetailFragmentAdapter extends FragmentStatePagerAdapter {
    private String listTab[] = {"Tour","Rating"};
    private TourDetailFragment nTourDetailFragment;
    private RatingFragment nRatingFragment;
    public GuideDetailFragmentAdapter(FragmentManager fm) {
        super(fm);
        nTourDetailFragment = new TourDetailFragment();
        nRatingFragment = new RatingFragment();
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return nTourDetailFragment;
        }else if (i == 1){
            return nRatingFragment;
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
