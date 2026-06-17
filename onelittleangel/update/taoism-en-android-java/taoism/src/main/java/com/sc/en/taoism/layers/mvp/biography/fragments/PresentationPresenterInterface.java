package com.sc.en.taoism.layers.mvp.biography.fragments;

import android.graphics.Bitmap;

import java.util.List;

public interface PresentationPresenterInterface {

  /**
   *
   */
  void loadAuthorPictures(String author);

  /**
   *
   */
  void loadBookPictures(String book);

  /**
   *
   */
  void loadMovementPictures(String movement);

  /**
   *
   * @return
   */
  List<Bitmap> getBitmapsView();

  /**
   *
   * @param bitmapsView
   */
  void setBitmapsView(List<Bitmap> bitmapsView);

  /**
   *
   */
  void releaseBitmapsCarousel();

  /**
   *
   * @return
   */
  boolean picturesAlreadyLoaded();

}
