package com.example.learnmusic;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Tutor_tab_pager_adapter extends FragmentPagerAdapter {
    private int tabsNumber;


    public Tutor_tab_pager_adapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tab_tutor_profile();
            case 1:
                return new tab_tutor_student();
            case 2:
                return new tab_tutor_request();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
