
package com.sc.en.islam.layers.mvp.common.animations.listviews.appearance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.sc.en.islam.OnelittleAngelApplication;
import com.sc.en.islam.layers.mvp.common.animations.listviews.util.ListViewWrapper;
import com.sc.en.islam.layers.mvp.common.utils.Constants;
import com.sc.en.islam.layers.mvp.common.animations.listviews.BaseAdapterDecorator;
import com.sc.en.islam.layers.mvp.common.animations.listviews.util.AnimatorUtil;
import com.sc.en.islam.layers.mvp.tablecontents.TableContentsActivity;


public abstract class AnimationAdapter extends BaseAdapterDecorator {

  private static final String SAVEDINSTANCESTATE_VIEWANIMATOR = "savedinstancestate_viewanimator";
  private static final String ALPHA = "alpha";
  @Nullable
  private ViewAnimator mViewAnimator;
  private boolean mIsRootAdapter;
  private boolean mGridViewPossiblyMeasuring;
  private int mGridViewMeasuringPosition;
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;

  protected AnimationAdapter(@NonNull BaseAdapter baseAdapter) {
    super(baseAdapter);

    mGridViewPossiblyMeasuring = true;
    mGridViewMeasuringPosition = -1;
    mIsRootAdapter = true;

    if (baseAdapter instanceof AnimationAdapter) {
      ((AnimationAdapter) baseAdapter).setIsWrapped();
    }
  }

  @Override
  public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
    super.setListViewWrapper(listViewWrapper);
    mViewAnimator = new ViewAnimator(listViewWrapper);
  }

  private void setIsWrapped() {
    mIsRootAdapter = false;
  }


  private void reset() {
    if (getListViewWrapper() == null) {
      throw new IllegalStateException("Call setAbsListView() on this AnimationAdapter first!");
    }

    assert mViewAnimator != null;
    mViewAnimator.reset();

    mGridViewPossiblyMeasuring = true;
    mGridViewMeasuringPosition = -1;

    if (getDecoratedBaseAdapter() instanceof AnimationAdapter) {
      ((AnimationAdapter) getDecoratedBaseAdapter()).reset();
    }
  }

  @Nullable
  public ViewAnimator getViewAnimator() {
    return mViewAnimator;
  }

  @NonNull
  @Override
  public final View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    if (mIsRootAdapter) {
      if (getListViewWrapper() == null) {
        throw new IllegalStateException("Call setAbsListView() on this AnimationAdapter first!");
      }

      assert mViewAnimator != null;
      if (convertView != null) {
        mViewAnimator.cancelExistingAnimation(convertView);
      }
    }

    View itemView = super.getView(position, convertView, parent);

    if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    if (editor == null) editor = settings.edit();

    if(settings.getInt(Constants.TABLE_CONTENTS_LISTVIEW_COUNT, -1) != getCount())
      editor.putInt(Constants.TABLE_CONTENTS_LISTVIEW_COUNT, getCount()).commit();

    if (mIsRootAdapter) {
      animateViewIfNecessary(position, itemView, parent);
    }
    return itemView;
  }

  private void animateViewIfNecessary(int position, @NonNull View view, @NonNull ViewGroup parent) {
    assert mViewAnimator != null;

    mGridViewPossiblyMeasuring = mGridViewPossiblyMeasuring && (mGridViewMeasuringPosition == -1 || mGridViewMeasuringPosition == position);

    if (mGridViewPossiblyMeasuring) {
      mGridViewMeasuringPosition = position;
      mViewAnimator.setLastAnimatedPosition(-1);
    }

    Animator[] childAnimators;
    if (getDecoratedBaseAdapter() instanceof AnimationAdapter) {
      childAnimators = ((AnimationAdapter) getDecoratedBaseAdapter()).getAnimators(parent, view);
    } else {
      childAnimators = new Animator[0];
    }
    Animator[] animators = getAnimators(parent, view);
    Animator alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0, 1);

    Animator[] concatAnimators = AnimatorUtil.concatAnimators(childAnimators, animators, alphaAnimator);
    mViewAnimator.animateViewIfNecessary(position, view, concatAnimators);
  }

  @NonNull
  protected abstract Animator[] getAnimators(@NonNull ViewGroup parent, @NonNull View view);

  @NonNull
  public Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();

    if (mViewAnimator != null) {
      bundle.putParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR, mViewAnimator.onSaveInstanceState());
    }

    return bundle;
  }

  public void onRestoreInstanceState(@Nullable Parcelable parcelable) {
    if (parcelable instanceof Bundle) {
      Bundle bundle = (Bundle) parcelable;
      if (mViewAnimator != null) {
        mViewAnimator.onRestoreInstanceState(bundle.getParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR));
      }
    }
  }
}
