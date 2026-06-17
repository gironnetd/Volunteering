package com.sc.fr.taoisme.layers.mvp.common.animations.listviews.swipedismiss;


import android.graphics.Rect;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;


public class SwipeDismissListViewTouchListener implements OnTouchListener {
  // Cached ViewConfiguration and system-wide constant values
  private final int mSlop;
  private final int mMinFlingVelocity;
  private final int mMaxFlingVelocity;
  private final long mAnimationTime;

  // Fixed properties
  private final ListView mListView;
  private final DismissCallbacks mCallbacks;
  private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

  // Transient properties
  private final List<PendingDismissData> mPendingDismisses = new ArrayList<>();
  private int mDismissAnimationRefCount;
  private float mDownX;
  private float mDownY;
  private boolean mSwiping;
  private int mSwipingSlop;
  private VelocityTracker mVelocityTracker;
  private int mDownPosition;
  private View mDownView;
  private boolean mPaused;

  /**
   * The callback interface used by {@link SwipeDismissListViewTouchListener} to inform its client
   * about a successful dismissal of one or more list item positions.
   */
  public interface DismissCallbacks {
    /**
     * Called to determine whether the given position can be dismissed.
     */
    boolean canDismiss();

    /**
     * Called when the user has indicated they she would like to dismiss one or more list item
     * positions.
     *
     * @param reverseSortedPositions An array of positions to dismiss, sorted in descending
     *                               order for convenience.
     */
    void onDismiss(int[] reverseSortedPositions);
  }

  /**
   * Constructs a new swipe-to-dismiss touch listener for the given list view.
   *
   * @param listView  The list view whose items should be dismissable.
   * @param callbacks The callback to trigger when the user has indicated that she would like to
   *                  dismiss one or more list items.
   */
  public SwipeDismissListViewTouchListener(ListView listView, DismissCallbacks callbacks) {
    ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
    mSlop = vc.getScaledTouchSlop();
    mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
    mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
    mAnimationTime = 500;
    mListView = listView;
    mCallbacks = callbacks;
  }

  /**
   * Enables or disables (pauses or resumes) watching for swipe-to-dismiss gestures.
   *
   * @param enabled Whether or not to watch for gestures.
   */
  private void setEnabled(boolean enabled) {
    mPaused = !enabled;
  }

  /**
   * Returns an {@link OnScrollListener} to be added to the {@link
   * ListView} using {@link ListView#setOnScrollListener(OnScrollListener)}.
   * If a scroll listener is already assigned, the caller should still pass scroll changes through
   * to this listener. This will ensure that this {@link SwipeDismissListViewTouchListener} is
   * paused during list view scrolling.</p>
   *
   * @see SwipeDismissListViewTouchListener
   */
  public OnScrollListener makeScrollListener() {
    return new OnScrollListener() {
      @Override
      public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        setEnabled(scrollState != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
      }


      @Override
      public void onScroll(AbsListView absListView, int i, int i1, int i2) {
      //do nothing for the moment
      }
    };
  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    if (mViewWidth < 2) {
      mViewWidth = mListView.getWidth();
    }

    switch (motionEvent.getActionMasked()) {
      case MotionEvent.ACTION_DOWN:
        if (mPaused) {
          return false;
        }


        // Find the child view that was touched (perform a hit test)
        Rect rect = new Rect();
        int childCount = mListView.getChildCount();
        int[] listViewCoords = new int[2];
        mListView.getLocationOnScreen(listViewCoords);
        int x = (int) motionEvent.getRawX() - listViewCoords[0];
        int y = (int) motionEvent.getRawY() - listViewCoords[1];
        View child;
        for (int i = 0; i < childCount; i++) {
          child = mListView.getChildAt(i);
          child.getHitRect(rect);
          if (rect.contains(x, y)) {
            mDownView = child;
            break;
          }
        }

        if (mDownView != null) {
          mDownX = motionEvent.getRawX();
          mDownY = motionEvent.getRawY();
          mDownPosition = mListView.getPositionForView(mDownView);
          if (mCallbacks.canDismiss()) {
            mVelocityTracker = VelocityTracker.obtain();
            mVelocityTracker.addMovement(motionEvent);
          } else {
            mDownView = null;
          }
        }
        return false;

      case MotionEvent.ACTION_CANCEL:
        if (mVelocityTracker == null) {
          break;
        }

        if (mDownView != null && mSwiping) {
          // cancel
          animate(mDownView)
            .translationX(0)
            .alpha(1)
            .setDuration(mAnimationTime)
            .setListener(null);
        }
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mDownX = 0;
        mDownY = 0;
        mDownView = null;
        mDownPosition = ListView.INVALID_POSITION;
        mSwiping = false;
        break;

      case MotionEvent.ACTION_UP: {
        if (mVelocityTracker == null) {
          break;
        }

        float deltaX = motionEvent.getRawX() - mDownX;
        mVelocityTracker.addMovement(motionEvent);
        mVelocityTracker.computeCurrentVelocity(1000);
        float velocityX = mVelocityTracker.getXVelocity();
        float absVelocityX = Math.abs(velocityX);
        float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
        boolean dismiss = false;
        boolean dismissRight = false;
        if (Math.abs(deltaX) > mViewWidth / 2 && mSwiping) {
          dismiss = true;
          dismissRight = deltaX > 0;
        } else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity
          && absVelocityY < absVelocityX && mSwiping) {
          // dismiss only if flinging in the same direction as dragging
          dismiss = velocityX < 0 == deltaX < 0;
          dismissRight = mVelocityTracker.getXVelocity() > 0;
        }
        if (dismiss && mDownPosition != ListView.INVALID_POSITION) {
          // dismiss
          final View downView = mDownView; // mDownView gets null'd before animation ends
          final int downPosition = mDownPosition;
          ++mDismissAnimationRefCount;
          animate(mDownView)
            .translationX(dismissRight ? mViewWidth : -mViewWidth)
            .alpha(0)
            .setDuration(mAnimationTime)
            .setListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                performDismiss(downView, downPosition);
              }
            });

        } else {
          // cancel
          animate(mDownView)
            .translationX(0)
            .alpha(1)
            .setDuration(mAnimationTime)
            .setListener(null);
        }
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mDownX = 0;
        mDownY = 0;
        mDownView = null;
        mDownPosition = ListView.INVALID_POSITION;
        mSwiping = false;
        break;
      }

      case MotionEvent.ACTION_MOVE:
        if (mVelocityTracker == null || mPaused) {
          break;
        }

        mVelocityTracker.addMovement(motionEvent);
        float deltaX = motionEvent.getRawX() - mDownX;
        float deltaY = motionEvent.getRawY() - mDownY;
        if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
          mSwiping = true;
          mSwipingSlop = deltaX > 0 ? mSlop : -mSlop;
          mListView.requestDisallowInterceptTouchEvent(true);

          // Cancel ListView's touch (un-highlighting the item)
          MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
          cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
            motionEvent.getActionIndex()
              << MotionEvent.ACTION_POINTER_INDEX_SHIFT);
          mListView.onTouchEvent(cancelEvent);
          cancelEvent.recycle();
        }

        if (mSwiping) {
          ViewHelper.setTranslationX(mDownView, deltaX - mSwipingSlop);
          ViewHelper.setAlpha(mDownView,Math.max(0f, Math.min(1f,
            1f - 2f * Math.abs(deltaX) / mViewWidth)));

          return true;
        }
        break;
      default:
        break;
    }
    return false;
  }

  class PendingDismissData implements Comparable<PendingDismissData> {
    private final int position;
    private final View view;

    public PendingDismissData(int position, View view) {
      this.position = position;
      this.view = view;
    }

    @Override
    public int compareTo(@NonNull PendingDismissData other) {
      // Sort by descending position
      return other.position - position;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof PendingDismissData)) return false;

      PendingDismissData that = (PendingDismissData) o;

      return getPosition() == that.getPosition() && (getView() != null ? getView().equals(that.getView()) : that.getView() == null);
    }

    @Override
    public int hashCode() {
      int result = getPosition();
      result = 31 * result + (getView() != null ? getView().hashCode() : 0);
      return result;
    }

    public int getPosition() {
      return position;
    }

    public View getView() {
      return view;
    }
  }

  private void performDismiss(final View dismissView, int dismissPosition) {
    // Animate the dismissed list item to zero-height and fire the dismiss callback when
    // all dismissed list item animations have completed. This triggers layout on each animation
    // frame; in the future we may want to do something smarter and more performant.

    final LayoutParams lp = dismissView.getLayoutParams();
    final int originalHeight = dismissView.getHeight();

    ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

    animator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        --mDismissAnimationRefCount;
        if (mDismissAnimationRefCount == 0) {
          // No active animations, process all pending dismisses.
          // Sort by descending position
          Collections.sort(mPendingDismisses);

          int[] dismissPositions = new int[mPendingDismisses.size()];
          for (int i = mPendingDismisses.size() - 1; i >= 0; i--) {
            dismissPositions[i] = mPendingDismisses.get(i).position;
          }
          mCallbacks.onDismiss(dismissPositions);

          // Reset mDownPosition to avoid MotionEvent.ACTION_UP trying to start a dismiss
          // animation with a stale position
          mDownPosition = ListView.INVALID_POSITION;

          LayoutParams lp;
          for (PendingDismissData pendingDismiss : mPendingDismisses) {
            // Reset view presentation
            ViewHelper.setAlpha(pendingDismiss.view,1f);
            ViewHelper.setTranslationX(pendingDismiss.view,0);
            lp = pendingDismiss.view.getLayoutParams();
            lp.height = originalHeight;
            pendingDismiss.view.setLayoutParams(lp);
          }

          // Send a cancel event
          long time = SystemClock.uptimeMillis();
          MotionEvent cancelEvent = MotionEvent.obtain(time, time,
            MotionEvent.ACTION_CANCEL, 0, 0, 0);
          mListView.dispatchTouchEvent(cancelEvent);

          mPendingDismisses.clear();
        }
      }
    });

    animator.addUpdateListener(valueAnimator -> {
      lp.height = (Integer) valueAnimator.getAnimatedValue();
      dismissView.setLayoutParams(lp);
    });

    mPendingDismisses.add(new PendingDismissData(dismissPosition, dismissView));
    animator.start();
  }
}
