package com.sc.en.confucianism.layers.mvp.common.customs.viewpagers.fragmentpageradapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sc.en.confucianism.R;
import com.sc.en.confucianism.layers.mvp.tablecontents.fragments.BaseFragment;

import java.util.List;

public abstract class BaseFragmentAdapter extends FragmentStatePagerAdapter {

  Context mContext;
  List<BaseFragment> fragments;

  BaseFragmentAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    return null;
  }

  @Override
  public int getCount() {
    return 0;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position){
      case 0:
        return mContext.getResources().getString(R.string.homepage_title);
      case 1:
        return mContext.getResources().getString(R.string.homepage_title);
      case 2:
        return mContext.getResources().getString(R.string.homepage_title);
      case 3:
        return mContext.getResources().getString(R.string.homepage_title);
      case 4:
        return mContext.getResources().getString(R.string.homepage_title);
      default:
        return null;
    }
  }

  public List<BaseFragment> getFragments() {
    return fragments;
  }
}
