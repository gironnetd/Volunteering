package com.sc.fr.onelittleangel.bouddhisme.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MyViewPager extends ViewPager {
    private static MyPagerAdapter myPageAdapter;
    private ArrayList<Fragment> fragments;
    private String fromActivity;

    public MyViewPager(Context context) {
        super(context);
        this.fragments = new ArrayList<>();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.fragments = new ArrayList<>();
    }

    public ArrayList<Fragment> getFragments() {
        return this.fragments;
    }

    @Override // android.support.v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override // android.support.v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
    }

    public Fragment getFragmentById(int i) {
        return this.fragments.get(i);
    }

    public String getFromActivity() {
        return this.fromActivity;
    }

    public void setFromActivity(String fromActivity) {
        this.fromActivity = fromActivity;
    }

    public static MyPagerAdapter getMyPageAdapter() {
        return myPageAdapter;
    }

    public static void setMyPageAdapter(MyPagerAdapter myPageAdapter2) {
        myPageAdapter = myPageAdapter2;
    }
}
