package com.sc.en.confucianism.layers.mvp.common.animations.listviews.appearance;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;

public abstract class SingleAnimationAdapter extends AnimationAdapter {

  protected SingleAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
    super(baseAdapter);
  }

  @NonNull
  @Override
  public Animator[] getAnimators(@NonNull ViewGroup parent, @NonNull View view) {
    Animator animator = getAnimator(parent, view);
    return new Animator[]{animator};
  }

  @NonNull
  protected abstract Animator getAnimator(@NonNull ViewGroup parent, @NonNull View view);

}
