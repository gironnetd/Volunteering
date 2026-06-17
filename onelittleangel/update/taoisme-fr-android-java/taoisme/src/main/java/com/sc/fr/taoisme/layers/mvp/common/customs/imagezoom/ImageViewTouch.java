package com.sc.fr.taoisme.layers.mvp.common.customs.imagezoom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.ViewConfiguration;

public class ImageViewTouch extends ImageViewTouchBase {
    private static final float SCROLL_DELTA_THRESHOLD = 1.0f;
    /**
     * minimum time between a scale event and a valid fling event
     */
    private static final long MIN_FLING_DELTA_TIME = 150;
    private float mScaleFactor;
  //  protected ScaleGestureDetector mScaleDetector;
  private GestureDetector mGestureDetector;
  private int mDoubleTapDirection;
  private boolean mDoubleTapEnabled = true;
    private boolean mScaleEnabled = true;
    private boolean mScrollEnabled = true;
    private OnImageViewTouchDoubleTapListener mDoubleTapListener;
    private OnImageViewTouchSingleTapListener mSingleTapListener;
    private CallBacks mCallbacks;
    private float mPosX;
    private float mPosY;
    private float mLastTouchX;
    private float mLastTouchY;


    public ImageViewTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewTouch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init(Context context, AttributeSet attrs, int defStyle) {
        super.init(context, attrs, defStyle);
      int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
      OnGestureListener mGestureListener = getGestureListener();
      OnScaleGestureListener mScaleListener = getScaleListener();

        mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
        mGestureDetector = new GestureDetector(getContext(), mGestureListener, null, true);
        mDoubleTapDirection = 1;
        setQuickScaleEnabled();
    }

    @TargetApi(19)
    private void setQuickScaleEnabled() {
        if (Build.VERSION.SDK_INT >= 19) {
            mScaleDetector.setQuickScaleEnabled(false);
        }
    }

    @TargetApi(19)
    @SuppressWarnings("unused")
    public boolean getQuickScaleEnabled() {
      return Build.VERSION.SDK_INT >= 19 && mScaleDetector.isQuickScaleEnabled();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        float x = 0, y = 0;
        x = mPosX /*+ mPosX0*/;
        y = mPosY /*+ mPosY0*/;

        if(!mScaleDetector.isInProgress()) {
          canvas.translate(x, x);
        }

    //    drawOnCanvas (canvas);

        canvas.restore();
    }

    public void drawOnCanvas (Canvas canvas) {
        if(super.getDrawable() != null) getDrawable().draw(canvas);
    }

    public void setCallbacks(CallBacks listener) {
        mCallbacks = listener;
    }

    @SuppressWarnings("unused")
    public float getScaleFactor() {
        return mScaleFactor;
    }

    public void setDoubleTapListener(OnImageViewTouchDoubleTapListener listener) {
        mDoubleTapListener = listener;
    }

    public void setSingleTapListener(OnImageViewTouchSingleTapListener listener) {
        mSingleTapListener = listener;
    }

    public void setDoubleTapEnabled(boolean value) {
        mDoubleTapEnabled = value;
    }

    public void setScaleEnabled(boolean value) {
        mScaleEnabled = value;
    }

    public void setScrollEnabled(boolean value) {
        mScrollEnabled = value;
    }

    public boolean getDoubleTapEnabled() {
        return mDoubleTapEnabled;
    }

    private OnGestureListener getGestureListener() {
        return new GestureListener();
    }

    private OnScaleGestureListener getScaleListener() {
        return new ScaleListener();
    }

    @Override
    protected void onLayoutChanged(final int left, final int top, final int right, final int bottom) {
        super.onLayoutChanged(left, top, right, bottom);
        //Log.v(TAG, "min: " + getMinScale() + ", max: " + getMaxScale() + ", result: " + (getMaxScale() - getMinScale()) / 2f);
        mScaleFactor = ((getMaxScale() - getMinScale()) /*/ 2f*/) + 0.5f;
    }

    private long mPointerUpTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getBitmapChanged()) {
            return false;
        }

        final int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_POINTER_UP) {
            mPointerUpTime = event.getEventTime();
        }

        mScaleDetector.onTouchEvent(event);

        if (!mScaleDetector.isInProgress()) {
            mGestureDetector.onTouchEvent(event);
        }

        switch (action) {
          //        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                final float x = event.getX();
                final float y = event.getY();

                mLastTouchX = x;
                mLastTouchY = y;
                return true;

            case MotionEvent.ACTION_MOVE:
                final float x1 = event.getX(/*pointerIndex*/);
                final float y1 = event.getY(/*pointerIndex*/);

                // Only move if the view supports panning and
                // ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress()) {
                    final float dx = x1 - mLastTouchX;
                    final float dy = y1 - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;
                    invalidate();
                }
                mLastTouchX = x1;
                mLastTouchY = y1;
                return true;

          case MotionEvent.ACTION_UP:
                return onUp(event);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onZoomAnimationCompleted(float scale) {

        if (DEBUG) {
        //    Log.d(TAG, "onZoomAnimationCompleted. scale: " + scale + ", minZoom: " + getMinScale());
        }

        if (scale < getMinScale()) {
            zoomTo(getMinScale(), 150);
        }
    }

    private float onDoubleTapPost(float scale, final float maxZoom, final float minScale) {
        if ((scale + mScaleFactor) <= maxZoom) {
            return scale + mScaleFactor;
        } else {
            return minScale;
        }
    }

    private boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    private boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (canScroll()) {
            return false;
        }
        mUserScaled = true;
        scrollBy(-distanceX, -distanceY);
        invalidate();
        return true;
    }

    private boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (canScroll()) {
            return false;
        }

        if (DEBUG) {
        //    Log.i(TAG, "onFling");
        }

        if (Math.abs(velocityX) > (mMinFlingVelocity * 8) || Math.abs(velocityY) > (mMinFlingVelocity * 8)) {
            if (DEBUG) {
            //    Log.v(TAG, "velocity: " + velocityY);
            //    Log.v(TAG, "diff: " + (e2.getY() - e1.getY()));
            }

            final float scale = Math.min(Math.max(2f, getScale() / 2), 3.f);

            float scaledDistanceX = ((velocityX) / mMaxFlingVelocity) * (getWidth() * scale);
            float scaledDistanceY = ((velocityY) / mMaxFlingVelocity) * (getHeight() * scale);

            if (DEBUG) {
            //    Log.v(TAG, "scale: " + getScale() + ", scale_final: " + scale);
            //    Log.v(TAG, "scaledDistanceX: " + scaledDistanceX);
            //    Log.v(TAG, "scaledDistanceY: " + scaledDistanceY);
            }

            mUserScaled = true;

            double total = Math.sqrt(Math.pow(scaledDistanceX, 4) + Math.pow(scaledDistanceY, 4));

            scrollBy(scaledDistanceX, scaledDistanceY, (long) Math.min(Math.max(800, total / 5), 1300));

            postInvalidate();
            return true;
        }
        return false;
    }

    private boolean onDown(MotionEvent e) {
      return !getBitmapChanged();
    }

    private boolean onUp(MotionEvent e) {
        if (getBitmapChanged()) {
            return false;
        }
        if (getScale() < getMinScale()) {
            zoomTo(getMinScale(), 150);
        }
        return true;
    }

    private boolean onSingleTapUp(MotionEvent e) {
      return !getBitmapChanged();
    }

    private boolean canScroll() {
        if (getScale() > 4) {
            return false;
        }
        RectF bitmapRect = getBitmapRect();
        return mViewPort.contains(bitmapRect);
    }

    /**
     * Determines whether this ImageViewTouch can be scrolled.
     *
     * @param direction - positive direction value means scroll from right to left,
     *                  negative value means scroll from left to right
     * @return true if there is some more place to scroll, false - otherwise.
     */
    @SuppressWarnings("unused")
    public boolean canScroll(int direction) {
        RectF bitmapRect = getBitmapRect();
        updateRect(bitmapRect, mScrollPoint);
        Rect imageViewRect = new Rect();
        getGlobalVisibleRect(imageViewRect);

        if (null == bitmapRect) {
            return false;
        }

        if (bitmapRect.right >= imageViewRect.right) {
            if (direction < 0) {
                return Math.abs(bitmapRect.right - imageViewRect.right) > SCROLL_DELTA_THRESHOLD;
            }
        }

        double bitmapScrollRectDelta = Math.abs(bitmapRect.left - mScrollPoint.x);
        return bitmapScrollRectDelta > SCROLL_DELTA_THRESHOLD;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            if (null != mSingleTapListener) {
                //mSingleTapListener.onSingleTapConfirmed();
            //    mCallbacks.returnToCardGridView();
            }
            if(mCallbacks != null) mCallbacks.returnToCardGridView();
            return ImageViewTouch.this.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (DEBUG) {
            //    Log.i(TAG, "onDoubleTap. double tap enabled? " + mDoubleTapEnabled);
            }
            if (mDoubleTapEnabled) {
                if (Build.VERSION.SDK_INT >= 19) {
                    if (mScaleDetector.isQuickScaleEnabled()) {
                        return true;
                    }
                }

                mUserScaled = true;

                float scale = getScale();
                float targetScale;
                targetScale = onDoubleTapPost(scale, getMaxScale(), getMinScale());
                targetScale = Math.min(getMaxScale(), Math.max(targetScale, getMinScale()));
                zoomTo(targetScale, e.getX(), e.getY(), mDefaultAnimationDuration);

            }

            if (null != mDoubleTapListener) {
                mDoubleTapListener.onDoubleTap();
            }

            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            //if (isLongClickable()) {
                //setLongClickable(true);
                if (!mScaleDetector.isInProgress()) {
                    //setPressed(true);
                    //performLongClick();
                    mCallbacks.showWallpaperDialog();
            //    }
            }
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
          return mScrollEnabled && !(e1 == null || e2 == null) && !(e1.getPointerCount() > 1 || e2.getPointerCount() > 1) && !mScaleDetector.isInProgress() && ImageViewTouch.this.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
          if (!mScrollEnabled) {
            return false;
          }
          if (e1 == null || e2 == null) {
            return false;
          }
          if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1) {
            return false;
          }
          if (mScaleDetector.isInProgress()) {
            return false;
          }

          final long delta = (SystemClock.uptimeMillis() - mPointerUpTime);

          // prevent fling happening just
          // after a quick pinch to zoom
          return delta > MIN_FLING_DELTA_TIME && ImageViewTouch.this.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return ImageViewTouch.this.onSingleTapUp(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            if (DEBUG) {
            //    Log.i(TAG, "onDown");
            }
            stopAllAnimations();

            return ImageViewTouch.this.onDown(e);
        }
    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        boolean mScaled = false;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float span = detector.getCurrentSpan() - detector.getPreviousSpan();
            float targetScale = getScale() * detector.getScaleFactor();

            if (mScaleEnabled) {
                if (mScaled && span != 0) {
                    mUserScaled = true;
                    targetScale = Math.min(getMaxScale(), Math.max(targetScale, getMinScale() - MIN_SCALE_DIFF));
                    zoomTo(targetScale, detector.getFocusX(), detector.getFocusY());
                    mDoubleTapDirection = 1;
                    invalidate();
                    return true;
                }

                // This is to prevent a glitch the first time
                // image is scaled.
                if (!mScaled) {
                    mScaled = true;
                }
            }
            return true;
        }

    }

    public interface OnImageViewTouchDoubleTapListener {
        void onDoubleTap();
    }

    public interface OnImageViewTouchSingleTapListener {
        void onSingleTapConfirmed();
    }

    public interface CallBacks {

        void showWallpaperDialog();
        void returnToCardGridView();
    }
}
