package com.sc.en.bouddhism.layers.mvp.tablecontents.fragments.homepage.fragments;

import android.graphics.Bitmap;

import com.sc.en.bouddhism.transverse.orms.realm.models.Author;
import com.sc.en.bouddhism.transverse.orms.realm.models.Quote;
import com.sc.en.bouddhism.transverse.orms.realm.models.Book;

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
