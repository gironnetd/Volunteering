package com.sc.en.philosophy.layers.service.quotes.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;
import com.sc.en.philosophy.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface FavoritesQuotesServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  Observable<List<Quote>> loadAllFavoritesQuotesAsync();
}
