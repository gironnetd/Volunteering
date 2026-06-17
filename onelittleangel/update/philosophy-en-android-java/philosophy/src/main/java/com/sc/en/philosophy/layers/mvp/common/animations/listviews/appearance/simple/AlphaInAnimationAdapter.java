package com.sc.en.philosophy.layers.mvp.common.animations.listviews.appearance.simple;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.sc.en.philosophy.layers.mvp.common.animations.listviews.appearance.AnimationAdapter;

public class AlphaInAnimationAdapter extends AnimationAdapter {

  public AlphaInAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
    super(baseAdapter);
  }

  @NonNull
  @Override
  public Animator[] getAnimators(@NonNull ViewGroup parent, @NonNull View view) {
    return new Animator[0];
  }
}
