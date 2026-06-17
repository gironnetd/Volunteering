package com.sc.en.hindouism.layers.mvp.biography.viewpagers.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.mvp.biography.BiographyPresenterInterface;
import com.sc.en.hindouism.layers.mvp.biography.fragments.PresentationFragment;

import java.util.ArrayList;
import java.util.List;

public class BiographyFragmentPagerAdapter extends FragmentStatePagerAdapter {

  public List<PresentationFragment> fragments;
  public ArrayMap<Integer, ArrayMap<String, String>> presentations;
  //protected Hashtable<Integer, WeakReference<PresentationFragment>> fragmentReferences;
  //public String name;

  public BiographyFragmentPagerAdapter(Context mContext, FragmentManager fm, BiographyPresenterInterface presenter, String name) {
    super(fm);
    if(fragments == null) {

      fragments = new ArrayList<>();
    //  fragmentReferences = new Hashtable<>();
      //this.presentations = presentations;
      for (int i = 0; i < presenter.getPresentations().size(); i++) {
        fragments.add(PresentationFragment.newInstance(presenter.getPresentations().valueAt(i).keyAt(0), presenter.getPresentations().valueAt(i).valueAt(0)));
      //  fragmentReferences.put(i, new WeakReference<>(fragments.get(i)));
//        for(int j = 0; j < fragments.get(i).llPresentation.getChildCount(); j++) {
//          ContentTextViewNative aNative = (ContentTextViewNative) fragments.get(i).llPresentation.getChildAt(i);
//          aNative.setCallbacks(this);
        }
      OnelittleAngelApplication.instance.manageConnectivityState();

        if(OnelittleAngelApplication.instance.isConnected()) {
          if (presenter.getAuthor() != null && presenter.getAuthor().getPictures().size() != 0) {
            fragments.add(PresentationFragment.newInstance(presenter, name));
          //  fragmentReferences.put(presenter.getPresentations().size() + 1, new WeakReference<>(fragments.get(fragments.size() - 1)));
          }

          if (presenter.getBook() != null && presenter.getBook().getPictures().size() != 0) {
            fragments.add(PresentationFragment.newInstance(presenter, name));
          //  fragmentReferences.put(presenter.getPresentations().size() + 1, new WeakReference<>(fragments.get(fragments.size() - 1)));

          }

          if (presenter.getMovement() != null && presenter.getMovement().getPictures().size() != 0) {
            fragments.add(PresentationFragment.newInstance(presenter, name));
          //  fragmentReferences.put(presenter.getPresentations().size() + 1, new WeakReference<>(fragments.get(fragments.size() - 1)));
          }
        }
    } else {
    }
   // }
  }

  @Override
  public PresentationFragment getItem(int position)
  {
//    Log.i("10", "getItem : " + position);
//
//    Log.i("10", "name : " + fragments.get(position).getName());
//    Log.i("10", "titleText : " + fragments.get(position).getTitleText());
//    Log.i("10", "presentationText : " + fragments.get(position).getPresentationText());
    return  fragments.get(position);
  }

  public PresentationFragment getFragments(int position) {
  //  WeakReference<PresentationFragment> ref = fragmentReferences.get(position);
  //  return ref == null ? null : ref.get();
    return fragments.get(position);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    //if(position == 0)
      return "";
    //else
    //return " [ " + position + " ]";
  }

//  public List<BaseFragment> getFragments() {
//    return fragments;
//  }

  @Override
  public int getCount() {
    return fragments.size();
  }


}
