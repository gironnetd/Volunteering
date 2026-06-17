package com.sc.fr.philosophie.layers.mvp.common.animations.listviews.appearance.simple;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.sc.fr.philosophie.layers.mvp.common.animations.listviews.appearance.SingleAnimationAdapter;

public class SwingBottomInAnimationAdapter extends SingleAnimationAdapter {

  private static final String TRANSLATION_Y = "translationY";

  public SwingBottomInAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
    super(baseAdapter);
  }

  @Override
  @NonNull
  protected Animator getAnimator(@NonNull ViewGroup parent, @NonNull View view) {
    return ObjectAnimator.ofFloat(view, TRANSLATION_Y, parent.getMeasuredHeight() >> 1, 0);
  }

}
