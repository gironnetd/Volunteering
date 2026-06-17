package com.sc.en.bouddhism.layers.mvp.common.listeners.cardviews;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.sc.en.bouddhism.exceptions.PersonalRuntimeException;

public class CardViewNativeGestureListener implements View.OnTouchListener {

  private final GestureDetector mCommonGestureDetector;

  public CardViewNativeGestureListener(Context mContext) {
    mCommonGestureDetector = new GestureDetector(mContext, new GestureListener());
    //ScaleGestureDetector mScaleGestureDetector = new ScaleGestureDetector(mContext, new simpleOnScaleGestureListener());
  }


  @Override
  public boolean onTouch(View v, MotionEvent event) {

    mCommonGestureDetector.onTouchEvent(event);
    return true;
  }

  public void onLongPressed() {
  }

  private void onSingleTapUped() {
  }

  private void onFlingGesture(){

  }

  public void onSingleTapConfirm() {
  }

  public void onDoubleTaped() {
  }

  private void onScaleGesture(ScaleGestureDetector detector){

  }

  private class simpleOnScaleGestureListener extends
    ScaleGestureDetector.SimpleOnScaleGestureListener {

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      // TODO Auto-generated method stub
      onScaleGesture(detector);
      return true;
    }
  }

  private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static final int SLIDE_THRESHOLD = 1;

    private float x;
    private float y;
    private int i;

    @Override
    public boolean onDown(MotionEvent e) {
      x = e.getXPrecision();
      y = e.getYPrecision();
      return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

      try {
        float deltaY = e2.getY() - e1.getY();
        float deltaX = e2.getX() - e1.getX();

        if (Float.floatToRawIntBits(deltaX) != 0 ||
          Float.floatToRawIntBits(deltaY) != 0) {
          if (i == 0) {
            i++;
            return false;
          } else {
            i++;
            return false;
          }
        }
      } catch (Exception exception) {
         //Log.e("ERROR", exception.getMessage());
        throw new PersonalRuntimeException(exception);
      }
      return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      onSingleTapConfirm();
      return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
      onSingleTapUped();
      return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

      onDoubleTaped();
      return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
      onLongPressed();
    }



    // Determines the fling velocity and then fires the appropriate swipe event accordingly
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      onFlingGesture();
      return false;
    }
  }

}
