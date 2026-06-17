package com.sc.fr.christianisme.layers.mvp.tablecontents.fragments.homepage.fragments;

import android.graphics.Bitmap;

import com.sc.fr.christianisme.transverse.orms.realm.models.Book;
import com.sc.fr.christianisme.transverse.orms.realm.models.Quote;
import com.sc.fr.christianisme.transverse.orms.realm.models.Author;

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
