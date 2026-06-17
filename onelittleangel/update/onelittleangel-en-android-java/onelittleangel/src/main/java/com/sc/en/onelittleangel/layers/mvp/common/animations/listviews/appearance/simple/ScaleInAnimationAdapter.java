package com.sc.en.onelittleangel.layers.mvp.common.animations.listviews.appearance.simple;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.sc.en.onelittleangel.layers.mvp.common.animations.listviews.appearance.AnimationAdapter;

public class ScaleInAnimationAdapter extends AnimationAdapter {

  private static final float DEFAULT_SCALE_FROM = 0.8f;

  private static final String SCALE_X = "scaleX";
  private static final String SCALE_Y = "scaleY";

  private final float mScaleFrom;

  public ScaleInAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
    this(baseAdapter, DEFAULT_SCALE_FROM);
  }

  private ScaleInAnimationAdapter(@NonNull BaseAdapter baseAdapter, float scaleFrom) {
    super(baseAdapter);
    mScaleFrom = scaleFrom;
  }

  @NonNull
  @Override
  public Animator[] getAnimators(@NonNull ViewGroup parent, @NonNull View view) {
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, SCALE_X, mScaleFrom, 1f);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, SCALE_Y, mScaleFrom, 1f);
    return new ObjectAnimator[]{scaleX, scaleY};
  }
}
