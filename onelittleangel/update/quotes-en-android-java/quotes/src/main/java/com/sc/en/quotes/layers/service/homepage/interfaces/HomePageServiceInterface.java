package com.sc.en.quotes.layers.service.homepage.interfaces;

import android.graphics.Bitmap;

import com.sc.en.quotes.transverse.orms.realm.models.Author;
import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;
import com.sc.en.quotes.transverse.orms.realm.models.Book;
import com.sc.en.quotes.transverse.orms.realm.models.Quote;

import io.reactivex.Observable;

public interface HomePageServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Author> loadBiographyOfDayAsync(boolean isFirstOpenOfDay, boolean isForNotification);

  /**
   *
   * @return
   */
  Observable<Quote> loadQuoteOfDayAsync(boolean isForNotification);

  /**
   *
   * @return
   */
  Observable<String> loadNameOfPictureOfDayAsync();
  /**
   *
   * @return
   */
  Observable<Bitmap> loadPictureOfDayAsync(boolean isForNotification);

  /**
   *
   * @return
   */
  Observable<Author> loadAuthorPictureOfDayAsync();

  /**
   *
   * @return
   */
  Observable<Book> loadBookPictureOfDayAsync();
}
