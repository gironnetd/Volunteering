package com.sc.en.islam.layers.mvp.biography.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.sc.en.islam.R;

public abstract class BaseFragment extends Fragment {

  public static final String TYPE = "TYPE";
  public String type;
//  private final BaseListAdapter baseListAdapter;
//  private final BaseListView baseListView;
  private View root;
  private Bundle mSavedInstanceState;
  private ViewStub mViewStub;
  private LayoutInflater inflater;
  //public View inflatedView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    this.inflater = inflater;
    root = inflater.inflate(getLayoutResource(), container, false);
      mViewStub = (ViewStub) root.findViewById(R.id.fragmentViewStub);
      mViewStub.setLayoutResource(getViewStubLayoutResource());
      mSavedInstanceState = savedInstanceState;

    container.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);


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
  void afterViewStubInflated(View originalViewContainerWithViewStub) {
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
      onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState);
      afterViewStubInflated(getView());
    }
    else if(!isVisibleToUser && mViewStub != null && getHasInflated()){
      populateViewForOrientation(inflater);
    }
  }

//  public void setScaleAdapter() {
//    AnimationAdapter animCardArrayAdapter = new ScaleInAnimationAdapter(baseListAdapter);
//    animCardArrayAdapter.setAbsListView(baseListView);
//    if (baseListView != null) {
//      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
//    }
//  }
//
//  public void setAlphaAdapter() {
//    AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(baseListAdapter);
//    animCardArrayAdapter.setAbsListView(baseListView);
//    if (baseListView != null) {
//      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
//    }
//  }
//
//  public void setBottomAdapter() {
//    AnimationAdapter animCardArrayAdapter = new SwingBottomInAnimationAdapter(baseListAdapter);
//    animCardArrayAdapter.setAbsListView(baseListView);
//    if(baseListView != null) {
//      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
//    }
//  }
//
//  public void setLeftAdapter() {
//    AnimationAdapter animCardArrayAdapter = new SwingLeftInAnimationAdapter(baseListAdapter);
//    animCardArrayAdapter.setAbsListView(baseListView);
//    if (baseListView != null) {
//      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
//    }
//  }
//
//  public void setRightAdapter() {
//    AnimationAdapter animCardArrayAdapter = new SwingRightInAnimationAdapter(baseListAdapter);
//    animCardArrayAdapter.setAbsListView(baseListView);
//    if (baseListView != null) {
//      baseListView.setExternalAdapter(animCardArrayAdapter, baseListAdapter);
//    }
//  }

//  @Override
//  public void onDestroy() {
//    super.onDestroy();
//    container = null;
//    inflater = null;
//    mViewStub = null;
//    root = null;
//  }
}
