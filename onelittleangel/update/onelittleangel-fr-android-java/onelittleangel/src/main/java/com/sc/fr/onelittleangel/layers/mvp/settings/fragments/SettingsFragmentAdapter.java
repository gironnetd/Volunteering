package com.sc.fr.onelittleangel.layers.mvp.settings.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sc.fr.onelittleangel.R;
import com.sc.fr.onelittleangel.layers.mvp.settings.fragments.mails.MailFragment;
import com.sc.fr.onelittleangel.layers.mvp.settings.fragments.movements.MovementsFragment;
import com.sc.fr.onelittleangel.layers.mvp.settings.fragments.notifications.NotificationsFragment;
import com.sc.fr.onelittleangel.layers.mvp.settings.fragments.tips.TipsFragment;
import com.sc.fr.onelittleangel.layers.mvp.settings.fragments.typefaces.TypefacesFragment;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragmentAdapter extends BaseFragmentAdapter {

 // private Context mContext;
  private final List<BaseFragment> fragments;

  public SettingsFragmentAdapter(Context mContext, FragmentManager fm) {
    super(fm);
    this.mContext = mContext;
    fragments = new ArrayList<>();
    fragments.add(MovementsFragment.newInstance());
    fragments.add(TypefacesFragment.newInstance());
    fragments.add(TipsFragment.newInstance());
    fragments.add(MailFragment.newInstance());
    fragments.add(NotificationsFragment.newInstance());
  }

  @Override
  public Fragment getItem(int position)
  {
    return fragments.get(position);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position){
      case 0:
        return mContext.getResources().getString(R.string.movements_nav_title);
      case 1:
        return mContext.getResources().getString(R.string.typefaces_nav_title);
      case 2:
        return mContext.getResources().getString(R.string.tips_nav_title);
      case 3:
        return mContext.getResources().getString(R.string.mail);
      case 4:
        return mContext.getResources().getString(R.string.notifications_nav_title);
      default:
        return null;
    }
  }

  public List<BaseFragment> getFragments() {
    return fragments;
  }

  @Override
  public int getCount() {
    return 5;
  }
}

