package com.sc.fr.philosophie.layers.mvp.common.customs.viewpagers.fragmentpageradapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.sc.fr.philosophie.R;
import com.sc.fr.philosophie.layers.mvp.tablecontents.fragments.authors.AuthorsFragment;
import com.sc.fr.philosophie.layers.mvp.tablecontents.fragments.homepage.fragments.HomePageFragment;
import com.sc.fr.philosophie.layers.mvp.tablecontents.fragments.themes.ThemesFragment;
import com.sc.fr.philosophie.layers.mvp.tablecontents.fragments.books.BooksFragment;
import com.sc.fr.philosophie.layers.mvp.tablecontents.fragments.movements.MovementsFragment;
import com.sc.fr.philosophie.layers.mvp.tablecontents.fragments.BaseFragment;

import java.util.ArrayList;

public class FragmentNativePagerAdapter extends BaseFragmentAdapter {

  public FragmentNativePagerAdapter(Context mContext, FragmentManager fm) {
    super(fm);
    this.mContext = mContext;
    fragments = new ArrayList<>();
    fragments.add(HomePageFragment.newInstance());
    fragments.add(AuthorsFragment.newInstance());
    fragments.add(MovementsFragment.newInstance());
    fragments.add(ThemesFragment.newInstance());
    fragments.add(BooksFragment.newInstance());
  }

  @Override
  public BaseFragment getItem(int position)
  {
    return fragments.get(position);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position){
      case 0:
        return mContext.getResources().getString(R.string.homepage_title);
      case 1:
        return mContext.getResources().getString(R.string.authors_title);
      case 2:
        return mContext.getResources().getString(R.string.faiths_title);
      case 3:
        return mContext.getResources().getString(R.string.themes_title);
      case 4:
        return mContext.getResources().getString(R.string.books_title);
      default:
        return null;
    }
  }

  @Override
  public int getCount() {
    return 5;
  }
}
