package com.sc.en.philosophy.layers.mvp.favorites;

import com.sc.en.philosophy.OnelittleAngelApplication;
import com.sc.en.philosophy.transverse.orms.realm.models.Quote;
import com.sc.en.philosophy.layers.mvp.MotherPresenter;
import com.sc.en.philosophy.layers.mvp.common.models.PageModel;

import java.util.List;

public class FavoritesPresenter extends MotherPresenter implements FavoritesPresenterInterface {


  private static final String TAG = "FavoritesPresenter";

  private PageModel[] mFavoritesPageModel;
  private boolean isOfSizeTwo = false;
  private boolean init =false;
  private FavoritesViewInterface favoritesViewInterface = null;

  public FavoritesPresenter(FavoritesViewInterface favoritesViewInterface) {
    this.favoritesViewInterface = favoritesViewInterface;
    mFavoritesPageModel = new PageModel[0];
  }

  @Override
  public void loadFavoritesPageModels() {
    OnelittleAngelApplication.instance.getServiceManager().getAllFavoritesService().loadAllFavoritesQuotesAsync().subscribe(this::initFavoritesPageModels);
  }

  @Override
  public void toggleQuoteIsFavorites(int idQuote) {
    OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(idQuote, true).subscribe(this::initFavoritesPageModels);
  }


  @Override
  public void loadMailAccount() {
  }

  @Override
  public PageModel[] getFavoritesPageModels() {
    return mFavoritesPageModel;
  }

  @Override
  public void setFavoritesPageModels() {
    //this.mFavoritesPageModel = favoritesPageModels;
  }

  @Override
  public boolean isPageModelsIsSizeOfTwo() {
    return isOfSizeTwo;
  }

  @Override
  public boolean isInit() {
    return init;
  }

  @Override
  public void init(boolean init) {
    this.init = init;
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  private void initFavoritesPageModels(List<Quote> quotes) {

    if(mFavoritesPageModel != null && (mFavoritesPageModel.length == 0 || init)) {

      if (quotes.size() != 2) {
        isOfSizeTwo = false;
        mFavoritesPageModel = new PageModel[quotes.size()];
        for (int i = 0; i < quotes.size(); i++) {
          mFavoritesPageModel[i] = new PageModel(i - 1, quotes.get(i));
        }
      } else {
        isOfSizeTwo = true;
        mFavoritesPageModel = new PageModel[6];
        int increment = 0;

        for (int i = 0; i < 3; i++) {
          for (int j = 0; j < quotes.size(); j++) {
            mFavoritesPageModel[increment] = new PageModel(increment - 1, quotes.get(j));
            increment++;
          }
        }
      }
      favoritesViewInterface.updatePageModels();
    }
  }

  @Override
  public void setFavoritesViewInterface() {
    this.favoritesViewInterface = favoritesViewInterface;
  }
}
