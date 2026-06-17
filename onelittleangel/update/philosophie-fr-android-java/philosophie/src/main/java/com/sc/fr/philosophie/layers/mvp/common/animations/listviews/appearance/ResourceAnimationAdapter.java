package com.sc.fr.philosophie.layers.mvp.common.animations.listviews.appearance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;

public abstract class ResourceAnimationAdapter extends AnimationAdapter {

  @NonNull
  private final Context mContext;

  @SuppressWarnings("UnusedDeclaration")
  protected ResourceAnimationAdapter(@NonNull BaseAdapter baseAdapter, @NonNull Context context) {
    super(baseAdapter);
    mContext = context;
  }

  @NonNull
  @Override
  public Animator[] getAnimators(@NonNull ViewGroup parent, @NonNull View view) {
    return new Animator[]{AnimatorInflater.loadAnimator(mContext, getAnimationResourceId())};
  }

  protected abstract int getAnimationResourceId();

}
