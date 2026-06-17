/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package com.sc.en.confucianism.layers.mvp.contents.listgridviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.mvp.common.customs.circle.Circle;
import com.sc.en.confucianism.layers.mvp.common.customs.imagezoom.ImageViewTouch;
import com.sc.en.confucianism.layers.mvp.common.customs.scrollviews.ObservableScrollView;
import com.sc.en.confucianism.layers.mvp.common.customs.viewpagers.ViewPagerNative;
import com.sc.en.confucianism.layers.mvp.common.listeners.cardviews.CardViewNativeGestureListener;
import com.sc.en.confucianism.R;
import com.sc.en.confucianism.layers.mvp.common.customs.textviews.TextViewNative;
//import com.sc.fr.onelittleangel.layers.mvp.common.panzoom.PanZoomView;
import com.sc.en.confucianism.layers.mvp.contents.ContentsActivity;
import com.sc.en.confucianism.layers.mvp.contents.ContentsPresenterInterface;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class CardGridArrayAdapter extends BaseAdapter {

  protected static String TAG = "CardGridArrayAdapter";

  public int itemHeight = 0;
  public static int pos;
  private final List<ViewHolder> holders;
  private Animator mCurrentAnimator;
  private final int mShortAnimationDuration = 800;
  private final Context context;
  private final ImageViewTouch expandedImageView;
  private final TextViewNative txtnExpandedImageComment;
  public LinearLayout rlHeader;
  private final CardGridView cardGridView;
  private FrameLayout flHeader;
  private final FrameLayout flImageComment;
  private final Circle detailsCircle;
  private final ImageView ivDetailsCircle;
  private final Circle wallpapersCircle;
  private final ImageView ivWallpapersCircle;

  private final CardView cvImageComment;
  private float startScaleFinal;
  private float startScale;
  private final Rect startBounds = new Rect();
  private final Rect finalBounds = new Rect();
  private final Point globalOffset = new Point();
  public static View thumbBaseView;
  private String comment = "";
  private final Animation rlnumber_alpha_in;
  private final Animation rlnumber_alpha_out;
  private final LinearLayout llHeader;
  private int height ;
  private int width;
  private final int widthMeasureSpec;
  private final int heightMeasureSpec;
  public static SparseArray<CardView> thumbViewList;

  private int mRowLayoutId = R.layout.gridviewlist_carrousel;
  private final ContentsPresenterInterface presenter;

  public CardGridArrayAdapter(Context context, ContentsPresenterInterface presenter) {
    this.context = context;
    this.presenter = presenter;
    holders = new ArrayList<>();
    expandedImageView = (ImageViewTouch) ((Activity) context).findViewById(R.id.expanded_image);
    txtnExpandedImageComment = (TextViewNative) ((Activity) context).findViewById(R.id.txtn_expanded_image_comment);
    animate(txtnExpandedImageComment).alpha(0).start();

    ObservableScrollView observableScrollView = (ObservableScrollView) ((Activity) context).findViewById(R.id.scroll_view);
    cardGridView = (CardGridView) ((Activity) context).findViewById(R.id.gv_carrousel);
    ViewPagerNative viewPagerNative = (ViewPagerNative) ((Activity) context).findViewById(R.id.viewpager);
    FrameLayout flImage = (FrameLayout) ((Activity) context).findViewById(R.id.fl_image);
    FrameLayout flDrawerLayout = (FrameLayout) ((Activity) context).findViewById(R.id.drawer_layout);
    flHeader = (FrameLayout) ((Activity) context).findViewById(R.id.fl_header);
    detailsCircle = (Circle) ((Activity) context).findViewById(R.id.details_circle);
    ivDetailsCircle = (ImageView) ((Activity) context).findViewById(R.id.iv_details_circle);
    wallpapersCircle = (Circle) ((Activity) context).findViewById(R.id.wallpapers_add_circle);
    ivWallpapersCircle = (ImageView) ((Activity) context).findViewById(R.id.iv_wallpapers_add_circle);
    flImageComment = (FrameLayout) ((Activity) context).findViewById(R.id.fl_image_comment);
    cvImageComment = (CardView) ((Activity) context).findViewById(R.id.cv_image_comment);

    flHeader = (FrameLayout) ((Activity) context).findViewById(R.id.fl_header);
    LinearLayout rlNumber = (LinearLayout) ((Activity) context).findViewById(R.id.rl_number);
    llHeader = (LinearLayout) ((Activity) context).findViewById(R.id.ll_header);
    rlnumber_alpha_in = AnimationUtils.loadAnimation(context, R.anim.rlnumber_alpha_in);
    rlnumber_alpha_out = AnimationUtils.loadAnimation(context, R.anim.rlnumber_alpha_out);

    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(expandedImageView.getWidth(), View.MeasureSpec.AT_MOST);
    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    if(thumbViewList == null) thumbViewList = new SparseArray<>();
  }

  public void setRowLayoutId(int rowLayoutId) {
    mRowLayoutId = rowLayoutId;
  }

  @Override
  public int getCount() {
    return presenter.getBitmapsView().size();
  }

  @Override
  public Object getItem(int position) {
    return presenter.getBitmapsView().get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {

    View view = convertView;

    final ViewHolder holder;
    if (view == null) {

      LayoutInflater inflater =
              (LayoutInflater) OnelittleAngelApplication.instance.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(this.mRowLayoutId, parent, false);

      holder = new ViewHolder();
      holder.picture = (ImageView) view.findViewById(R.id.iv_picture);
      holder.comments = (TextViewNative) view.findViewById(R.id.txt_picture_comment);
      holder.picture.setImageBitmap(presenter.getBitmapsView().get(position));
      holder.cardView = (CardView) view.findViewById(R.id.cv_carrousel);

      view.setOnTouchListener( new CardViewNativeGestureListener(context) {
        @Override
        public void onLongPressed() {
          super.onLongPressed();
          pos = position;
          zoomImageFromThumb2(holder.cardView, false, false);
        }

        @Override
        public void onSingleTapConfirm() {
          super.onSingleTapConfirm();
          pos = position;
          zoomImageFromThumb2(holder.cardView, false, false);
        }

        @Override
        public void onDoubleTaped() {
          super.onDoubleTaped();
          pos = position;
          zoomImageFromThumb2(holder.cardView, false, false);
        }
      });

      view.setTag(holder);
      holders.add(holder);
      if(thumbViewList == null) thumbViewList = new SparseArray<>();
      thumbViewList.put(position, holder.cardView);

      if(position == presenter.getBitmapsView().size() - 1) {

        if(((ContentsActivity) context).isImageCommentDisplaying) {
          showImageComment();
          ((ContentsActivity) context).isImageCommentDisplaying = false;
        }

        if(((ContentsActivity) context).isExpandedImageDipslaying) {
          showExpandedImage();
          ((ContentsActivity) context).isExpandedImageDipslaying = false;
        }
      }
    } else {
      holder = (ViewHolder) view.getTag();

      if (itemHeight == 0) {
        itemHeight = view.getMeasuredHeight();
      }
    }
    return view;
  }

  public static class ViewHolder {
    ImageView picture;
    TextViewNative comments;
    CardView cardView;
  }

  private Bitmap scaleBitmap(Bitmap bm) {
    int width = bm.getWidth();
    int height = bm.getHeight();

    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    if (Build.VERSION.SDK_INT >= 17){
      //new pleasant way to get real metrics
      ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
      //  realWidth = realMetrics.widthPixels;
      //  realHeight = realMetrics.heightPixels;
      this.width = displaymetrics.widthPixels;
      this.height = displaymetrics.heightPixels;

    } else if (Build.VERSION.SDK_INT >= 14) {
      //reflection for this weird in-between time
      try {
        Method mGetRawH = Display.class.getMethod("getRawHeight");
        Method mGetRawW = Display.class.getMethod("getRawWidth");
        this.width = (Integer) mGetRawW.invoke(displaymetrics);
        this.height = (Integer) mGetRawH.invoke(displaymetrics);
      } catch (Exception e) {
        //this may not be 100% accurate, but it's all we've got
        //  realWidth = display.getWidth();
        this.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        this.height = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
      }

    } else {
      //This should be close, as lower API devices should not have window navigation bars
      //  realWidth = display.getWidth();
      width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
      height = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
    }
    int maxWidth = this.width ;
    int maxHeight = this.height;

    if (width > height) {
      // landscape

      float ratio = (float) width / maxWidth;
      width = maxWidth;
      height = (int)(height / ratio);
    } else if (height > width) {
      // portrait

      float ratio = (float) height / maxHeight;
      height = maxHeight;
      width = (int)(width / ratio);
    } else {
      // square
      height = maxHeight;
      width = maxWidth;
    }

    bm = Bitmap.createScaledBitmap(bm, width, height, true);
    return bm;
  }

  private void showImageComment() {
    zoomImageFromThumb2(null, true, true);
  }

  private void showExpandedImage() {
    zoomImageFromThumb2(null, true, false);
  }

  public void zoomImageFromThumb2(View thumbView, boolean isImageOpened, boolean isImageCommentOpen) {
//     If there's an animation in progress, cancel it immediately and
//     proceed with this one.
    if (mCurrentAnimator != null) {
      mCurrentAnimator.cancel();
    }

    DisplayMetrics displaymetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    int height = displaymetrics.heightPixels;
    int width = displaymetrics.widthPixels;

    if(thumbView != null || isImageOpened) {

      if (thumbView != null) thumbView.getGlobalVisibleRect(startBounds);
      ((Activity) context).findViewById(R.id.drawer_layout)
              .getGlobalVisibleRect(finalBounds, globalOffset);

      startBounds.offset(-globalOffset.x, -globalOffset.y);

      finalBounds.offset(-globalOffset.x, -globalOffset.y);

      // Adjust the start bounds to be the same aspect ratio as the final
      // bounds using the
      // "center crop" technique. This prevents undesirable stretching during
      // the animation.
      // Also calculate the start scaling factor (the end scaling factor is
      // always 1.0).
      if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
              .width() / startBounds.height()) {
        // Extend start bounds horizontally
        startScale = (float) startBounds.height() / finalBounds.height();
        float startWidth = startScale * finalBounds.width();
        float deltaWidth = (startWidth - startBounds.width()) / 2;
        startBounds.left -= deltaWidth;
        startBounds.right += deltaWidth;
      } else {
        // Extend start bounds vertically
        startScale = (float) startBounds.width() / finalBounds.width();
        float startHeight = startScale * finalBounds.height();
        float deltaHeight = (startHeight - startBounds.height()) / 2;
        startBounds.top -= deltaHeight;
        startBounds.bottom += deltaHeight;
      }
      Bitmap bitmap = scaleBitmap(presenter.getBitmapsView().get(pos));
      expandedImageView.setImageBitmap(bitmap, -1, -1);

      if (presenter.getAuthor() != null) {
        comment = presenter.getAuthor().getPictures().get(pos).getComment();
      }

      if (presenter.getBook() != null) {
        comment = presenter.getBook().getPictures().get(pos).getComment();
        txtnExpandedImageComment.setText(presenter.getBook().getPictures().get(pos).getComment());
      }

      if (presenter.getTheme() != null) {
        comment = presenter.getTheme().getPictures().get(pos).getComment();
        txtnExpandedImageComment.setText(presenter.getTheme().getPictures().get(pos).getComment());
      }

      if (presenter.getMovement() != null) {
        comment = presenter.getMovement().getPictures().get(pos).getComment();
        txtnExpandedImageComment.setText(presenter.getMovement().getPictures().get(pos).getComment());
      }

      if (comment.equals("")) {
        txtnExpandedImageComment.setVisibility(View.GONE);
      } else {
        txtnExpandedImageComment.setText(String.format("%s%s", comment, context.getResources().getString(R.string.space)));
        txtnExpandedImageComment.setVisibility(View.VISIBLE);
      }

      if (isImageOpened && !isImageCommentOpen) {
        expandedImageView.setBackgroundColor(Color.WHITE);
        flImageComment.setBackgroundColor(Color.TRANSPARENT);

        animate(cvImageComment).alpha(0).start();
        cvImageComment.setVisibility(View.GONE);
        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        if (thumbViewList.get(pos) != null) thumbViewList.get(pos).setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        animate(expandedImageView).alpha(1).start();
        expandedImageView.bringToFront();
      } else if (isImageCommentOpen && !isImageOpened) {
        flImageComment.setVisibility(View.VISIBLE);
        cvImageComment.setVisibility(View.VISIBLE);
        expandedImageView.setBackgroundColor(Color.TRANSPARENT);
        flImageComment.setBackgroundColor(Color.WHITE);
        animate(txtnExpandedImageComment).alpha(1).start();
        txtnExpandedImageComment.measure(widthMeasureSpec, heightMeasureSpec);
        if (thumbViewList.get(pos) != null) thumbViewList.get(pos).setAlpha(0f);

        animate(cvImageComment).alpha(1).setDuration(0).start();
        animate(expandedImageView).alpha(0).setDuration(0).setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter() {

          @Override
          public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
            super.onAnimationEnd(animation);
            expandedImageView.setVisibility(View.GONE);
            animate(expandedImageView).setListener(null);
          }
        }).start();
      } else if (isImageCommentOpen && isImageOpened) {
        flImageComment.setVisibility(View.VISIBLE);
        cvImageComment.setVisibility(View.VISIBLE);
        expandedImageView.setBackgroundColor(Color.TRANSPARENT);
        flImageComment.setBackgroundColor(Color.WHITE);
        animate(txtnExpandedImageComment).alpha(1).start();
        txtnExpandedImageComment.measure(widthMeasureSpec, heightMeasureSpec);
        if (thumbViewList.get(pos) != null) thumbViewList.get(pos).setAlpha(0f);

        animate(cvImageComment).alpha(1).setDuration(0).start();
        animate(expandedImageView).alpha(0).setDuration(0).setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter() {

          @Override
          public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
            super.onAnimationEnd(animation);
            expandedImageView.setVisibility(View.GONE);
            animate(expandedImageView).setListener(null);
          }
        }).start();
      }  else {
        expandedImageView.setBackgroundColor(Color.TRANSPARENT);
        flImageComment.setBackgroundColor(Color.TRANSPARENT);
        animate(cvImageComment).alpha(0).start();
        cvImageComment.setVisibility(View.GONE);

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        if (thumbViewList.get(pos) != null) thumbViewList.get(pos).setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        animate(expandedImageView).alpha(1).start();
        expandedImageView.bringToFront();
      }

      llHeader.startAnimation(rlnumber_alpha_out);
      expandedImageView.measure(widthMeasureSpec, heightMeasureSpec);

      int i = expandedImageView.getMeasuredHeight();

      expandedImageView.bringToFront();
      animate(cardGridView).alpha(0).setDuration(isImageOpened ? 0 : mShortAnimationDuration).setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
          super.onAnimationEnd(animation);
          cardGridView.setVisibility(View.GONE);
          animate(cardGridView).setListener(null);
        }
      }).start();

      detailsCircle.bringToFront();
      ivDetailsCircle.bringToFront();
      wallpapersCircle.bringToFront();
      ivWallpapersCircle.bringToFront();
      detailsCircle.setBitmap(presenter.getBitmapsView().get(pos));
      wallpapersCircle.setBitmap(presenter.getBitmapsView().get(pos));
      detailsCircle.setVisibility(View.VISIBLE);
      ivDetailsCircle.setVisibility(View.VISIBLE);
      wallpapersCircle.setVisibility(View.VISIBLE);
      ivWallpapersCircle.setVisibility(View.VISIBLE);

      if(comment.equals("")) {
        detailsCircle.setVisibility(View.GONE);
        ivDetailsCircle.setVisibility(View.GONE);
      }

      detailsCircle.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

              if (ViewHelper.getAlpha(cvImageComment) == 0) {
                animate(detailsCircle).scaleX(1.65f).scaleY(1.65f).setDuration(200).start();
                //  animate(ivDetailsCircle).scaleX(1.65f).scaleY(1.65f).setDuration(200).start();

              } else {
                animate(detailsCircle).scaleX(1).scaleY(1).setDuration(200).start();
                //  animate(ivDetailsCircle).scaleX(1).scaleY(1).setDuration(200).start();

              }

              return true;
            case MotionEvent.ACTION_MOVE:
              return true;
            case MotionEvent.ACTION_UP:
              if (ViewHelper.getAlpha(cvImageComment) == 0) {
                flImageComment.setVisibility(View.VISIBLE);
                cvImageComment.setVisibility(View.VISIBLE);
                txtnExpandedImageComment.measure(widthMeasureSpec, heightMeasureSpec);
                animate(cvImageComment).alpha(1).setDuration(mShortAnimationDuration).start();
                animate(expandedImageView).alpha(0).setDuration(mShortAnimationDuration).setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter() {

                  @Override
                  public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                    super.onAnimationEnd(animation);
                    expandedImageView.setVisibility(View.GONE);
                    animate(expandedImageView).setListener(null);

                  }
                }).start();
              } else {
                animate(expandedImageView).alpha(0).setDuration(0).start();
                expandedImageView.setVisibility(View.VISIBLE);
                animate(expandedImageView).alpha(1).setDuration(mShortAnimationDuration).start();
                animate(cvImageComment).alpha(0).setDuration(mShortAnimationDuration).setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter() {

                  @Override
                  public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                    super.onAnimationEnd(animation);
                    cvImageComment.setVisibility(View.GONE);
                    flImageComment.setVisibility(View.GONE);
                    animate(cvImageComment).setListener(null);
                  }
                }).start();
              }
              return false;

            default:
              return false;
          }
        }
      });

      llHeader.startAnimation(rlnumber_alpha_out);

      // Set the pivot point for SCALE_X and SCALE_Y transformations to the
      // top-left corner of
      // the zoomed-in view (the default is the center of the view).
      expandedImageView.setPivotX(0f);
      expandedImageView.setPivotY(0f);
      animate(txtnExpandedImageComment).alpha(1).setDuration(isImageOpened ? 0 : mShortAnimationDuration).start();
      animate(flHeader).alpha(0).setDuration(isImageOpened ? 0 : mShortAnimationDuration).start();

      // Construct and run the parallel animation of the four translation and
      // scale properties
      // (X, Y, SCALE_X, and SCALE_Y).
      thumbBaseView = thumbViewList.get(pos);
      if (thumbViewList.get(pos) != null && !isImageOpened) {

        thumbBaseView = thumbView;
        AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(expandedImageView,"x" /*View.X*/,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView,"y" /*View.Y*/,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView,"scaleX" /*View.SCALE_X*/,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,"scaleY" /*View.SCALE_Y*/,
                        startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            mCurrentAnimator = null;
            if (thumbViewList.get(pos) != null) thumbBaseView = thumbViewList.get(pos);
          }

          @Override
          public void onAnimationCancel(Animator animation) {
            mCurrentAnimator = null;
          }
        });
        set.start();
        mCurrentAnimator = set;
      }

      startScaleFinal = startScale;
      // Upon clicking the zoomed-in image, it should zoom back down to the
      // original bounds
      // and show the thumbnail instead of the expanded image.


      View.OnClickListener listener = view -> {
        if (mCurrentAnimator != null) {
          mCurrentAnimator.cancel();
        }
        if (ViewHelper.getAlpha(cvImageComment) == 1) {

          animate(detailsCircle).scaleX(1).scaleY(1).setDuration(200).start();
          //animate(ivDetailsCircle).scaleX(1).scaleY(1).setDuration(200).start();

          expandedImageView.setVisibility(View.VISIBLE);
          animate(expandedImageView).alpha(1).setDuration(mShortAnimationDuration).start();
          animate(cvImageComment).alpha(0).setDuration(mShortAnimationDuration).setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
              super.onAnimationEnd(animation);
              cvImageComment.setVisibility(View.GONE);
              flImageComment.setVisibility(View.GONE);
              animate(cvImageComment).setListener(null);
            }
          }).start();
        } else {


          cardGridView.setVisibility(View.VISIBLE);
          animate(cardGridView).alpha(1).setDuration(mShortAnimationDuration).start();
          llHeader.startAnimation(rlnumber_alpha_in);
          FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) expandedImageView.getLayoutParams();
          lp.height = FrameLayout.LayoutParams.MATCH_PARENT;

          expandedImageView.setLayoutParams(lp);

          AnimatorSet set1 = new AnimatorSet();
          set1.play(
                  ObjectAnimator.ofFloat(expandedImageView,"x" /*View.X*/,
                          startBounds.left))
                  .with(ObjectAnimator.ofFloat(expandedImageView,"y" /*View.Y*/,
                          startBounds.top))
                  .with(ObjectAnimator.ofFloat(expandedImageView,
                          "scaleX"/*View.SCALE_X*/, startScaleFinal))
                  .with(ObjectAnimator.ofFloat(expandedImageView,
                          "scaleY"/*View.SCALE_Y*/, startScaleFinal))
                  .with(ObjectAnimator.ofFloat(expandedImageView, "alpha" /*View.ALPHA*/, 1, 0));

          set1.setDuration(mShortAnimationDuration);
          set1.setInterpolator(new DecelerateInterpolator());
          set1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              if (thumbViewList.get(pos) != null) animate(thumbViewList.get(pos)).alpha(1).setDuration(400).start();
              else if(thumbBaseView != null) animate(thumbBaseView).alpha(1).setDuration(400).start();
              FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) expandedImageView.getLayoutParams();
              lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
              lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
              expandedImageView.setLayoutParams(lp);
              AnimatorSet set1 = new AnimatorSet();
              set1.play(
                      ObjectAnimator.ofFloat(expandedImageView, "x"/*View.X*/,
                              startBounds.left, finalBounds.left))
                      .with(ObjectAnimator.ofFloat(expandedImageView, "y"/*View.Y*/,
                              startBounds.top, finalBounds.top))
                      .with(ObjectAnimator.ofFloat(expandedImageView, "scaleX"/*View.SCALE_X*/,
                              startScale, 1f))
                      .with(ObjectAnimator.ofFloat(expandedImageView, "scaleY"/*View.SCALE_Y*/,
                              startScale, 1f));
              set1.setDuration(0);
              set1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                  mCurrentAnimator = null;
                  expandedImageView.setVisibility(View.GONE);
                  expandedImageView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                  mCurrentAnimator = null;
                  expandedImageView.setVisibility(View.GONE);
                  expandedImageView.setVisibility(View.GONE);
                }
              });
              set1.start();
              mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
              if (thumbViewList.get(pos) != null) thumbViewList.get(pos).setAlpha(1f);
              mCurrentAnimator = null;
            }
          });
          set1.start();
          mCurrentAnimator = set1;
        }
      };

      flImageComment.setOnClickListener(listener);
      txtnExpandedImageComment.setOnClickListener(listener);
    } else {

      if (ViewHelper.getAlpha(cvImageComment) == 1) {
        expandedImageView.setVisibility(View.VISIBLE);
        animate(expandedImageView).alpha(1).setDuration(mShortAnimationDuration).start();
        animate(detailsCircle).scaleX(1).scaleY(1).setDuration(200).start();
        //animate(ivDetailsCircle).scaleX(1).scaleY(1).setDuration(200).start();

        animate(cvImageComment).alpha(0).setDuration(mShortAnimationDuration).setListener(new com.nineoldandroids.animation.AnimatorListenerAdapter() {

          @Override
          public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
            super.onAnimationEnd(animation);
            cvImageComment.setVisibility(View.GONE);
            flImageComment.setVisibility(View.GONE);
            animate(cvImageComment).setListener(null);
          }
        }).start();
      } else {

        thumbViewList.get(pos).getGlobalVisibleRect(startBounds);

        ((Activity) context).findViewById(R.id.drawer_layout)
                .getGlobalVisibleRect(finalBounds, globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the
        // "center crop" technique. This prevents undesirable stretching during
        // the animation.
        // Also calculate the start scaling factor (the end scaling factor is
        // always 1.0).
        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
                .width() / startBounds.height()) {
          // Extend start bounds horizontally
          startScale = (float) startBounds.height() / finalBounds.height();
          float startWidth = startScale * finalBounds.width();
          float deltaWidth = (startWidth - startBounds.width()) / 2;
          startBounds.left -= deltaWidth;
          startBounds.right += deltaWidth;
        } else {
          // Extend start bounds vertically
          startScale = (float) startBounds.width() / finalBounds.width();
          float startHeight = startScale * finalBounds.height();
          float deltaHeight = (startHeight - startBounds.height()) / 2;
          startBounds.top -= deltaHeight;
          startBounds.bottom += deltaHeight;
        }
        startScaleFinal = startScale;

        cardGridView.setVisibility(View.VISIBLE);
        animate(cardGridView).alpha(1).setDuration(mShortAnimationDuration).start();
        detailsCircle.setVisibility(View.GONE);
        ivDetailsCircle.setVisibility(View.GONE);
        wallpapersCircle.setVisibility(View.GONE);
        ivWallpapersCircle.setVisibility(View.GONE);
        cvImageComment.setVisibility(View.GONE);
        //  rlNumber.startAnimation(rlnumber_alpha_in);
        llHeader.startAnimation(rlnumber_alpha_in);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) expandedImageView.getLayoutParams();
        lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
        lp.topMargin = 0;
        lp.bottomMargin = 0;
        expandedImageView.setLayoutParams(lp);

        animate(flHeader).alpha(1).setDuration(mShortAnimationDuration).start();

        AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(expandedImageView, "x"/*View.X*/,
                        startBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, "y"/*View.Y*/,
                        startBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        "scaleX"/*View.SCALE_X*/, startScaleFinal))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        "scaleY"/*View.SCALE_Y*/, startScaleFinal))
                .with(ObjectAnimator.ofFloat(expandedImageView, "alpha"/*View.ALPHA*/, 1, 0));

        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {

            if (thumbViewList.get(pos) != null) {
              animate(thumbViewList.get(pos)).alpha(1).setDuration(mShortAnimationDuration).start();
            }
            if (thumbBaseView != null) {
              animate(thumbBaseView).alpha(1).setDuration(mShortAnimationDuration).start();
            }
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) expandedImageView.getLayoutParams();
            lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
            lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
            expandedImageView.setLayoutParams(lp);

            AnimatorSet set = new AnimatorSet();
            set.play(
                    ObjectAnimator.ofFloat(expandedImageView, "x"/*View.X*/,
                            startBounds.left, finalBounds.left))
                    .with(ObjectAnimator.ofFloat(expandedImageView, "y"/*View.Y*/,
                            startBounds.top, finalBounds.top))
                    .with(ObjectAnimator.ofFloat(expandedImageView, "scaleX"/*View.SCALE_X*/,
                            startScale, 1f))
                    .with(ObjectAnimator.ofFloat(expandedImageView, "scaleY"/*View.SCALE_Y*/,
                            startScale, 1f));
            set.setDuration(0);
            set.addListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
                expandedImageView.setVisibility(View.GONE);

              }

              @Override
              public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
                expandedImageView.setVisibility(View.GONE);
              }
            });
            set.start();
            mCurrentAnimator = null;
          }

          @Override
          public void onAnimationCancel(Animator animation) {
            thumbViewList.get(pos).setAlpha(1f);
            mCurrentAnimator = null;
          }
        });
        set.start();
        mCurrentAnimator = set;
      }
    }
  }

  // -------------------------------------------------------------
  //  Getters and Setters
  // -------------------------------------------------------------

  /**
   * @return {@link CardGridView}
   */
//  public CardGridView getCardGridView() {
//    return mCardGridView;
//  }

  /**
   * Sets the {@link CardGridView}
   *
   * @param cardGridView cardGridView
   */
  public void setCardGridView(CardGridView cardGridView) {
    /*
    {@link CardGridView}
   */
  }
}
