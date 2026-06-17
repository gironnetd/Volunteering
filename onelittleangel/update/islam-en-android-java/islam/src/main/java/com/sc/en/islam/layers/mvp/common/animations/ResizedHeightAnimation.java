package com.sc.en.islam.layers.mvp.common.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;


public class ResizedHeightAnimation extends Animation {

  private final int mHeight;
  private final int mStartHeight;
  private final View mView;

  public ResizedHeightAnimation(View view, int height) {
    mView = view;
    mHeight = height;
    mStartHeight = view.getHeight();
  }

  @Override
  protected void applyTransformation(float interpolatedTime, Transformation t) {

    mView.getLayoutParams().height = mStartHeight + (int) ((mHeight - mStartHeight) * interpolatedTime);
    mView.requestLayout();
  }

  @Override
  public boolean willChangeBounds() {
    return true;
  }
}
