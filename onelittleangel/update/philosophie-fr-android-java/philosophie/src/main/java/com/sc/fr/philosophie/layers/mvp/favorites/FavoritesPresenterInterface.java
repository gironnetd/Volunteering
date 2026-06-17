package com.sc.fr.philosophie.layers.mvp.favorites;

import com.sc.fr.philosophie.layers.mvp.common.models.PageModel;

public interface FavoritesPresenterInterface {

  /**
   *
   */
  void loadFavoritesPageModels();

  /**
   *
   * @param idQuote
   */
  void toggleQuoteIsFavorites(int idQuote);

  /**
   *
   * @return
   */
  PageModel[] getFavoritesPageModels();

  /**
   *
   */
  void setFavoritesPageModels();

  /**
   *
   * @return
   */
  boolean isPageModelsIsSizeOfTwo();

  /**
   *
   * @return
   */
  boolean isInit();

  /**
   *
   * @param init
   */
  void init(boolean init);

  /**
   *
   */
  void loadMailAccount();


  void setFavoritesViewInterface();
}
