package com.sc.en.christianism.layers.mvp.tablecontents.fragments.homepage.fragments;

import android.graphics.Bitmap;

import com.sc.en.christianism.transverse.orms.realm.models.Author;
import com.sc.en.christianism.transverse.orms.realm.models.Quote;
import com.sc.en.christianism.transverse.orms.realm.models.Book;

public interface HomePagePresenterInterface {

  /**
   *
   */
  void updateHomePage();

  /**
   *
   */
  void removeResources();

  /**
   *
   * @return
   */
  Author getAuthorPresentationOfDay();


  /**
   *
   * @return
   */
  Book getBookPresentationOfDay();

  /**
   *
   * @return
   */
  Quote getQuoteOfDay();

  /**
   *
   * @return
   */
  String getNamePictureOfDay();

  /**
   *
   * @return
   */
  Bitmap getPictureOfDay();

  /**
   *
   * @return
   */
  Author getAuthorPictureOfDay();

  /**
   *
   * @return
   */
  Book getBookPictureOfDay();
}
