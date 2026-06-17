package com.sc.fr.onelittleangel.bouddhisme.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = new ArrayList<>();
        if (fragments.size() != 0) {
            this.fragments.clear();
        }
        this.fragments = fragments;
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getItemPosition(Object object) {
        return -2;
    }

    @Override // android.support.v4.app.FragmentStatePagerAdapter
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.fragments.size();
    }
}
