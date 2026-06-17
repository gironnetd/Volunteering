package com.sc.fr.taoisme.layers.mvp.settings.fragments;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.sc.fr.taoisme.R;
import com.sc.fr.taoisme.layers.mvp.tablecontents.models.base.adapters.listadapters.BaseListAdapter;
import com.sc.fr.taoisme.layers.mvp.tablecontents.models.base.views.listviews.BaseListView;

public abstract class BaseFragment extends Fragment {

  protected static final String TYPE = "TYPE";
  protected String type;
  public BaseListAdapter baseListAdapter;
  public BaseListView baseListView;
  private View root;
  private Bundle mSavedInstanceState;
  private ViewStub mViewStub;
  private LayoutInflater inflater;
  protected ViewGroup container;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    this.inflater = inflater;
    this.container = container;
    root = inflater.inflate(getLayoutResource(), container, false);

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
      onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState);
      afterViewStubInflated(getView());
    }
    else if(!isVisibleToUser && mViewStub != null && getHasInflated()){
      populateViewForOrientation(inflater);
    }
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
