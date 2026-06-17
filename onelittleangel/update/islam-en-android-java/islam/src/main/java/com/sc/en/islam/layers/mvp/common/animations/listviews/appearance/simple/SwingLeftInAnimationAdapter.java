package com.sc.en.islam.layers.mvp.common.animations.listviews.appearance.simple;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.sc.en.islam.layers.mvp.common.animations.listviews.appearance.SingleAnimationAdapter;

@SuppressWarnings("UnusedDeclaration")
public class SwingLeftInAnimationAdapter extends SingleAnimationAdapter {

  private static final String TRANSLATION_X = "translationX";

  public SwingLeftInAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
    super(baseAdapter);
  }

  @NonNull
  @Override
  protected Animator getAnimator(@NonNull ViewGroup parent, @NonNull View view) {
    return ObjectAnimator.ofFloat(view, TRANSLATION_X, 0 - parent.getWidth(), 0);
  }
}
