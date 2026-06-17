package com.sc.fr.bouddhisme.layers.mvp.common.animations.listviews.appearance;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;
import com.sc.fr.bouddhisme.OnelittleAngelApplication;
import com.sc.fr.bouddhisme.layers.mvp.common.utils.Constants;
import com.sc.fr.bouddhisme.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.bouddhisme.layers.mvp.common.animations.listviews.util.ListViewWrapper;

public class ViewAnimator {

  private static final String SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION = "savedinstancestate_firstanimatedposition";
  private static final String SAVEDINSTANCESTATE_LASTANIMATEDPOSITION = "savedinstancestate_lastanimatedposition";
  private static final String SAVEDINSTANCESTATE_SHOULDANIMATE = "savedinstancestate_shouldanimate";
  private static final int INITIAL_DELAY_MILLIS = 150;
  private static final int DEFAULT_ANIMATION_DELAY_MILLIS = 100;
  private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 300;
  @NonNull
  private final ListViewWrapper mListViewWrapper;
  @NonNull
  private final SparseArray<Animator> mAnimators = new SparseArray<>();
  private int mInitialDelayMillis = INITIAL_DELAY_MILLIS;
  private int mAnimationDelayMillis = DEFAULT_ANIMATION_DELAY_MILLIS;
  private int mAnimationDurationMillis = DEFAULT_ANIMATION_DURATION_MILLIS;
  private long mAnimationStartMillis;
  private int mFirstAnimatedPosition;
  private int mLastAnimatedPosition;
  private boolean mShouldAnimate = true;
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;

  public ViewAnimator(@NonNull ListViewWrapper listViewWrapper) {
    mListViewWrapper = listViewWrapper;
    mAnimationStartMillis = -1;
    mFirstAnimatedPosition = -1;
    mLastAnimatedPosition = -1;
  }

  public void reset() {
    for (int i = 0; i < mAnimators.size(); i++) {
      mAnimators.get(mAnimators.keyAt(i)).cancel();
    }
    mAnimators.clear();
    mFirstAnimatedPosition = -1;
    mLastAnimatedPosition = -1;
    mAnimationStartMillis = -1;
    mShouldAnimate = true;
  }

  public void setShouldAnimateFromPosition(int position) {
    enableAnimations();
    mFirstAnimatedPosition = position - 1;
    mLastAnimatedPosition = position - 1;
  }

  public void setShouldAnimateNotVisible() {
    enableAnimations();
    mFirstAnimatedPosition = mListViewWrapper.getLastVisiblePosition();
    mLastAnimatedPosition = mListViewWrapper.getLastVisiblePosition();
  }

  void setLastAnimatedPosition(int lastAnimatedPosition) {
    mLastAnimatedPosition = lastAnimatedPosition;
  }

  public void setInitialDelayMillis(int delayMillis) {
    mInitialDelayMillis = delayMillis;
  }

  public void setAnimationDelayMillis(int delayMillis) {
    mAnimationDelayMillis = delayMillis;
  }

  public void setAnimationDurationMillis(int durationMillis) {
    mAnimationDurationMillis = durationMillis;
  }

  private void enableAnimations() {
    mShouldAnimate = true;
  }

  public void disableAnimations() {
    mShouldAnimate = false;
  }

  void cancelExistingAnimation(@NonNull View view) {
    int hashCode = view.hashCode();
    Animator animator = mAnimators.get(hashCode);
    if (animator != null) {
      animator.end();
      mAnimators.remove(hashCode);
    }
  }

  public void animateViewIfNecessary(int position, @NonNull View view, @NonNull Animator[] animators) {
    if (settings == null) settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    if (editor == null) editor = settings.edit();

    if(settings.getBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, false)) {
      if (mShouldAnimate && position > mLastAnimatedPosition) {
        if (mFirstAnimatedPosition == -1) {
          mFirstAnimatedPosition = position;
        }

        animateView(position, view, animators);
        mLastAnimatedPosition = position;

        if(position + 1 == settings.getInt(Constants.TABLE_CONTENTS_LISTVIEW_COUNT, -1))
        editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, false).apply();
      }
    }
  }

  private void animateView(int position, @NonNull View view, @NonNull Animator[] animators) {
    if (mAnimationStartMillis == -1) {
      mAnimationStartMillis = SystemClock.uptimeMillis();
    }



    //if(settings.getBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, false)) {

      ViewHelper.setAlpha(view, 0);

      AnimatorSet set = new AnimatorSet();
      set.playTogether(animators);
      set.setStartDelay(calculateAnimationDelay(position));
      set.setDuration(mAnimationDurationMillis);
      set.start();

      mAnimators.put(view.hashCode(), set);

    //  editor.putBoolean(Constants.IS_ANIMATING_LISTVIEW_ENABLED, false).apply();
    //}
  }

  @SuppressLint("NewApi")
  private int calculateAnimationDelay(int position) {
    int delay;

    int lastVisiblePosition = mListViewWrapper.getLastVisiblePosition();
    int firstVisiblePosition = mListViewWrapper.getFirstVisiblePosition();

    int numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition;
    int numberOfAnimatedItems = position - 1 - mFirstAnimatedPosition;

    if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
      delay = mAnimationDelayMillis;

      if (mListViewWrapper.getListView() instanceof GridView && VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
        int numColumns = ((GridView) mListViewWrapper.getListView()).getNumColumns();
        delay += mAnimationDelayMillis * (position % numColumns);
      }
    } else {
      int delaySinceStart = (position - mFirstAnimatedPosition) * mAnimationDelayMillis;
      delay = Math.max(0, (int) (-SystemClock.uptimeMillis() + mAnimationStartMillis + mInitialDelayMillis + delaySinceStart));
    }
    return delay;
  }

  @NonNull
  public Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();

    bundle.putInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION, mFirstAnimatedPosition);
    bundle.putInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION, mLastAnimatedPosition);
    bundle.putBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE, mShouldAnimate);

    return bundle;
  }

  public void onRestoreInstanceState(@Nullable Parcelable parcelable) {
    if (parcelable instanceof Bundle) {
      Bundle bundle = (Bundle) parcelable;
      mFirstAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION);
      mLastAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION);
      mShouldAnimate = bundle.getBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE);
    }
  }
}
