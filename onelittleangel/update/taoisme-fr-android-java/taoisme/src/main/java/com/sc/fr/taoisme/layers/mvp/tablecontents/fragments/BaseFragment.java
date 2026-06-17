package com.sc.fr.taoisme.layers.mvp.tablecontents.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.sc.fr.taoisme.R;
import com.sc.fr.taoisme.layers.mvp.common.animations.listviews.appearance.AnimationAdapter;
import com.sc.fr.taoisme.layers.mvp.common.animations.listviews.appearance.simple.AlphaInAnimationAdapter;
import com.sc.fr.taoisme.layers.mvp.common.animations.listviews.appearance.simple.ScaleInAnimationAdapter;
import com.sc.fr.taoisme.layers.mvp.common.animations.listviews.appearance.simple.SwingBottomInAnimationAdapter;
import com.sc.fr.taoisme.layers.mvp.common.animations.listviews.appearance.simple.SwingLeftInAnimationAdapter;
import com.sc.fr.taoisme.layers.mvp.common.animations.listviews.appearance.simple.SwingRightInAnimationAdapter;
import com.sc.fr.taoisme.layers.mvp.common.layouts.smarttablayouts.SmartTabLayout;
import com.sc.fr.taoisme.layers.mvp.tablecontents.models.base.adapters.listadapters.BaseListAdapter;
import com.sc.fr.taoisme.layers.mvp.tablecontents.models.base.views.listviews.BaseListView;

public abstract class BaseFragment extends Fragment {

  protected static final String TYPE = "TYPE";
  protected String type;
  protected BaseListAdapter baseListAdapter;
  protected BaseListView baseListView;
  private View root;
  private Bundle mSavedInstanceState;
  private ViewStub mViewStub;
  private LayoutInflater inflater;
  private ViewGroup container;
  protected FrameLayout flViewStubFragment;
  protected CardView cvNoContents;
  protected View inflatedView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    this.inflater = inflater;
    this.container = container;
    root = inflater.inflate(getLayoutResource(), container, false);
    SmartTabLayout tabLayout = (SmartTabLayout) getActivity().findViewById(R.id.view_pager_tab);

    if (tabLayout instanceof SmartTabLayout)
      container.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);


    mViewStub = (ViewStub) root.findViewById(R.id.fragmentViewStub);
    mViewStub.setLayoutResource(getViewStubLayoutResource());
    mSavedInstanceState = savedInstanceState;

    if (getUserVisibleHint() && !getHasInflated()) {
      View inflatedView = mViewStub.inflate();
      onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState);
      afterViewStubInflated(root);
    }
    return root;
  }

  protected abstract void onCreateViewAfterViewStubInflated(View inflatedView, Bundle savedInstanceState);

  public abstract String getFragmentType();

  protected abstract boolean getHasInflated();


  /**
   * The layout ID associated with this ViewStub
   * @see ViewStub#setLayoutResource(int)
   * @return
   */
  @LayoutRes
  protected abstract int getViewStubLayoutResource();

  @LayoutRes
  protected abstract int getLayoutResource();
  /**
   *
   * @param originalViewContainerWithViewStub
   */
  @CallSuper
  protected void afterViewStubInflated(View originalViewContainerWithViewStub) {
  }


  protected abstract void populateViewForOrientation(LayoutInflater inflater);

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);

    if (isVisibleToUser && mViewStub != null && !getHasInflated()) {

      mViewStub = (ViewStub) root.findViewById(R.id.fragmentViewStub);
      mViewStub.setLayoutResource(getViewStubLayoutResource());
    //  mSavedInstanceState = savedInstanceState;
      View inflatedView = mViewStub.inflate();
//      if (this instanceof HomePageFragment) {
//        onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState);
//        afterViewStubInflated(getView());
//      } else {
        afterViewStubInflated(getView());
        onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState);
    //  }
    }
    else if(!isVisibleToUser && mViewStub != null && getHasInflated()){
     // LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      populateViewForOrientation(inflater);
    }
  }

  protected void setScaleAdapter() {
    AnimationAdapter animCardArrayAdapter = new ScaleInAnimationAdapter(baseListAdapter);
    animCardArrayAdapter.setAbsListView(baseListView);
    if (baseListView != null) {
      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
    }
  }

  public void setAlphaAdapter() {
    AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(baseListAdapter);
    animCardArrayAdapter.setAbsListView(baseListView);
    if (baseListView != null) {
      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
    }
  }

  public void setBottomAdapter() {
    AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(baseListAdapter);
    animCardArrayAdapter.setAbsListView(baseListView);
    if(baseListView != null) {
      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
    }
    //baseListView.setExternalAdapter(null, baseListAdapter);
  }

  protected void setLeftAdapter() {
    AnimationAdapter animCardArrayAdapter = new SwingLeftInAnimationAdapter(baseListAdapter);
    animCardArrayAdapter.setAbsListView(baseListView);
    if (baseListView != null) {
      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
    }
    //baseListView.setExternalAdapter(null, baseListAdapter);
  }

  protected void setRightAdapter() {
    AnimationAdapter animCardArrayAdapter = new SwingRightInAnimationAdapter(baseListAdapter);
    animCardArrayAdapter.setAbsListView(baseListView);
    if (baseListView != null) {
      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
    }
    //baseListView.setExternalAdapter(null, baseListAdapter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    container = null;
    inflater = null;
    mViewStub = null;
    root = null;
  }
}
