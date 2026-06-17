package com.sc.en.christianism.layers.mvp.common.animations.listviews.util;

import android.support.annotation.NonNull;

import com.nineoldandroids.animation.Animator;

public class AnimatorUtil {

  private AnimatorUtil() {
  }

  @NonNull
  public static Animator[] concatAnimators(@NonNull Animator[] childAnimators, @NonNull Animator[] animators, @NonNull Animator alphaAnimator) {
    Animator[] allAnimators = new Animator[childAnimators.length + animators.length + 1];
    int i;

    for (i = 0; i < childAnimators.length; ++i) {
      allAnimators[i] = childAnimators[i];
    }

    for (Animator animator : animators) {
      allAnimators[i] = animator;
      ++i;
    }

    allAnimators[allAnimators.length - 1] = alphaAnimator;
    return allAnimators;
  }

}
