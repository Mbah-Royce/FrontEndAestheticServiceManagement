package com.se3.ase;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class swipeAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private final ArrayList<String> fragmentTile = new ArrayList<>();
    private Context context;
    int totalTabs;

    public swipeAdapter(@NonNull FragmentManager fm, int behavior) {
        // Required empty public constructor
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position){
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount(){
        return  fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragmentArrayList.add(fragment);
        fragmentTile.add(title);
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position){

        return fragmentTile.get(position);
    }
}