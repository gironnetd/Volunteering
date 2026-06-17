package com.sc.fr.islam.layers.mvp.common.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;


class ResizedWidthAnimation extends Animation {

  private final int mWidth;
  private final int mStartWidth;
  private final View mView;

  public ResizedWidthAnimation(View view, int width) {
    mView = view;
    mWidth = width;
    mStartWidth = view.getWidth();
  }

  @Override
  protected void applyTransformation(float interpolatedTime, Transformation t) {

    mView.getLayoutParams().width = mStartWidth + (int) ((mWidth - mStartWidth) * interpolatedTime);
    mView.requestLayout();
  }

  @Override
  public boolean willChangeBounds() {
    return true;
  }
}
