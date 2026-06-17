package com.sc.en.hindouism.layers.service.quotes.interfaces;

import com.sc.en.hindouism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.hindouism.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface FavoritesQuotesServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  Observable<List<Quote>> loadAllFavoritesQuotesAsync();
}
