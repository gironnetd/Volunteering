package com.sc.fr.philosophie.layers.service.quotes.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface FavoritesQuotesServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   */
  Observable<List<Quote>> loadAllFavoritesQuotesAsync();
}
