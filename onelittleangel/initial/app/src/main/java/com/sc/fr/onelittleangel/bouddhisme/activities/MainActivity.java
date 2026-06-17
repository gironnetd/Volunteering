package com.sc.fr.onelittleangel.bouddhisme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.sc.fr.onelittleangel.bouddhisme.authors.activities.AuthorsListActivity;
import com.sc.fr.onelittleangel.bouddhisme.categories.activities.CategoriesActivity;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyPagerAdapter;
import com.sc.fr.onelittleangel.bouddhisme.fragments.MyViewPager;
import com.sc.fr.onelittleangel.bouddhisme.menu.MenuActivity;
import com.sc.fr.onelittleangel.bouddhisme.subjects.activities.SubjectsActivity;
import com.sc.fr.onelittleangel.bouddhisme.R;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends FragmentActivity {
    private static String fromActivity;
    private static Intent maIntent;
    private static MyPagerAdapter myPageAdapter;
    private static MyViewPager pager;
    Bundle b;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.b = savedInstanceState;
        setContentView(R.layout.activity_main);
        pager = (MyViewPager) findViewById(R.id.myViewPager);
        this.fragments.add(Fragment.instantiate(this, MenuActivity.class.getName()));
        this.fragments.add(new Fragment());
        this.fragments.add(Fragment.instantiate(this, AuthorsListActivity.class.getName()));
        this.fragments.add(Fragment.instantiate(this, SubjectsActivity.class.getName()));
        this.fragments.add(Fragment.instantiate(this, CategoriesActivity.class.getName()));
        this.fragments.add(Fragment.instantiate(this, BooksListActivity.class.getName()));
        this.fragments.add(new Fragment());
        myPageAdapter = new MyPagerAdapter(getSupportFragmentManager(), this.fragments);
        pager.setAdapter(myPageAdapter);
        MyViewPager.setMyPageAdapter(myPageAdapter);
        if (getFromActivity() != null) {
            if (getFromActivity().equals("authors")) {
                getPager().setCurrentItem(2);
            }
            if (getFromActivity().equals("subjects")) {
                getPager().setCurrentItem(3);
            }
            if (getFromActivity().equals("categories")) {
                getPager().setCurrentItem(4);
            }
            if (getFromActivity().equals("books")) {
                getPager().setCurrentItem(5);
            }
            if (getFromActivity().equals("")) {
                getPager().setCurrentItem(0);
            }
        }
    }

    public static Intent getMAIntent() {
        return maIntent;
    }

    public static void setMAIntent(Intent mAIntent) {
        maIntent = mAIntent;
    }

    public static MyViewPager getPager() {
        return pager;
    }

    public static void setPager(MyViewPager pager2) {
        pager = pager2;
    }

    public static String getFromActivity() {
        return fromActivity;
    }

    public static void setFromActivity(String fromActivity2) {
        fromActivity = fromActivity2;
    }

    public static MyPagerAdapter getMyPageAdapter() {
        return myPageAdapter;
    }

    public static void setMyPageAdapter(MyPagerAdapter myPageAdapter2) {
        myPageAdapter = myPageAdapter2;
    }
}
