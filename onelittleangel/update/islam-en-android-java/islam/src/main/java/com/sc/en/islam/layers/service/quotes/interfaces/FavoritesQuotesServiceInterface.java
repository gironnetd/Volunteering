package com.sc.en.islam.layers.service.quotes.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.en.islam.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface FavoritesQuotesServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  Observable<List<Quote>> loadAllFavoritesQuotesAsync();
}
