package com.sc.fr.onelittleangel.bouddhisme.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MyPageAdapterFragment extends FragmentStatePagerAdapter {
    ArrayList<CustomFragment> fragments;

    public MyPageAdapterFragment(FragmentManager fm, ArrayList<CustomFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override // android.support.v4.app.FragmentStatePagerAdapter
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.fragments.size();
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public ArrayList<CustomFragment> getFragments() {
        return this.fragments;
    }

    public void setFragments(ArrayList<CustomFragment> fragments) {
        this.fragments = fragments;
    }
}
