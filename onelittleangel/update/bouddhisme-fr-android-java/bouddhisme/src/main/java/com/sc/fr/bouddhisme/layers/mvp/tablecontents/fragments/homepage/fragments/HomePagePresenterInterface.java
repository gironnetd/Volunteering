package com.sc.fr.bouddhisme.layers.mvp.tablecontents.fragments.homepage.fragments;

import android.graphics.Bitmap;

import com.sc.fr.bouddhisme.transverse.orms.realm.models.Author;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Book;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Quote;

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
