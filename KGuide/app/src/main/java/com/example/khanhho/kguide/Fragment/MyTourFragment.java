package com.example.khanhho.kguide.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khanhho.kguide.R;

public class MyTourFragment extends Fragment {
    private View nRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nRootView = inflater.inflate(R.layout.fragment_my_tour, container, false);
        return nRootView;
    }
}
