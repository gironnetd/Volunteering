package com.sc.en.hindouism.layers.mvp.contents;

import android.graphics.Bitmap;
import android.view.View;

import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.mvp.common.models.PageModel;
import com.sc.en.hindouism.transverse.orms.realm.models.Quote;
import com.sc.en.hindouism.transverse.orms.realm.models.Author;
import com.sc.en.hindouism.transverse.orms.realm.models.Theme;
import com.sc.en.hindouism.R;
import com.sc.en.hindouism.layers.mvp.MotherPresenter;
import com.sc.en.hindouism.layers.mvp.biography.carousel.panel.ImagePanel;
import com.sc.en.hindouism.transverse.orms.realm.models.Book;
import com.sc.en.hindouism.transverse.orms.realm.models.Movement;

import java.util.ArrayList;
import java.util.List;

public class ContentsPresenter extends MotherPresenter implements ContentsPresenterInterface {

  private static final String TAG = "ContentsPresenter";

  private ContentsViewInterface contentsViewInterface = null;
  private  PageModel[] mPageModel;
  private boolean isOfSizeTwo = false;
  private boolean init =false;
  private boolean picturesAlreadyLoaded = false;
//  private Account twitterAccount;
//  private Account mailAccount;
//  private Account facebookAccount;
//  private Account googlePlusAccount;
  private Author author;
  private Book book;
  private Movement movement;
  private Theme theme;
  private List<View> carouselViews;
  private List<Bitmap> bitmapsView;


  public ContentsPresenter(ContentsViewInterface contentsViewInterface) {
    this.contentsViewInterface = contentsViewInterface;
    mPageModel = new PageModel[0];
    carouselViews = new ArrayList<>();
    bitmapsView = new ArrayList<>();
  }

  @Override
  public void toggleQuoteIsFavorites(int idQuote) {
    OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(idQuote);
  }

  @Override
  public void loadPageModels(String from, String name) {
    if (from.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.authors))) {
      OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(name).subscribe(this::initAuthor);
    }

    if (from.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.books))) {
      OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(name).subscribe(this::initBook);
    }

    if (from.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.themes))) {
      OnelittleAngelApplication.instance.getServiceManager().getThemeByNameService().loadThemeByNameAsync(name).subscribe(this::initTheme);
    }

    if (from.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.movements))) {
      OnelittleAngelApplication.instance.getServiceManager().getMovementByNameService().loadMovementByNameAsync(name).subscribe(this::initMovement);
    }
  }

  @Override
  public PageModel[] getPageModels() {
    return mPageModel;
  }

  @Override
  public void setPageModels() {
    //this.mPageModel = pageModels;
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


  @Override
  public void loadMailAccount() {
  }

  @Override
  public void sendEmail(List<String> mailTo, String emailSubject, String emailBody) {
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  private void initAuthor(Author author){
    this.author = author;
    initPageModels(author.getQuotes());
  }

  private void initBook(Book book) {
    this.book = book;
    initPageModels(book.getQuotes());
  }

  private void initMovement(Movement movement) {
    this.movement = movement;
    OnelittleAngelApplication.instance.getServiceManager().getQuotesByMovementService().loadQuotesByMovementAsync(movement.getMovement()).subscribe(this::initPageModels);
  }

  private void initTheme(Theme theme) {
    this.theme = theme;
    initPageModels(theme.getQuotes());
  }

  private void initPageModels(List<Quote> quotes) {

    if(mPageModel != null && (mPageModel.length == 0 || init) /*|| mFavoritesPageModel.length != quotes.size()*/) {
      if(quotes.size() != 2) {
        isOfSizeTwo = false;
        mPageModel = new PageModel[quotes.size()];
        for (int i = 0; i < quotes.size(); i++) {
          mPageModel[i] = new PageModel(i - 1, quotes.get(i));
        }
      } else {
        isOfSizeTwo = true;
        mPageModel = new PageModel[6];
        int increment = 0;

        for(int i = 0; i < 3; i++) {
          for(int j = 0; j < quotes.size(); j++) {
            mPageModel[increment] = new PageModel(increment - 1, quotes.get(j));
            increment++;
          }
        }
      }
      contentsViewInterface.updatePageModels();
    }
  }

  @Override
  public void setContentsViewInterface() {
    this.contentsViewInterface = contentsViewInterface;
  }

  @Override
  public List<View> getCarouselView() {
    return carouselViews;
  }

  @Override
  public void setCarouselView(List<View> carouselViews) {
    this.carouselViews = carouselViews;
  }

  @Override
  public List<Bitmap> getBitmapsView() {
    return bitmapsView;
  }

  @Override
  public void setBitmapsView(List<Bitmap> bitmapsView) {
    this.bitmapsView = bitmapsView;
  }

  @Override
  public void loadPictures(String type, String name) {
    picturesAlreadyLoaded = true;

    if (type.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.authors))) {
      OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.authors), name).subscribe(this::initCarouselView);
    }

    if (type.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.books))) {
      OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.books), name).subscribe(this::initCarouselView);
    }

    if (type.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.themes))) {
      OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.themes), name).subscribe(this::initCarouselView);
    }

    if (type.equals(OnelittleAngelApplication.instance.getResources().getString(R.string.movements))) {
      OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.movements), name).subscribe(this::initCarouselView);
    }
  }

  @Override
  public boolean picturesAlreadyLoaded() {
    return picturesAlreadyLoaded;
  }

  @Override
  public void releaseBitmapsCarousel() {
  }

  @Override
  public Author getAuthor() {
    return author;
  }

  @Override
  public Book getBook() {
    return book;
  }

  @Override
  public Movement getMovement() {
    return movement;
  }

  @Override
  public Theme getTheme() {
    return theme;
  }

  private void initCarouselView(List<Bitmap> carouselViews) {
    this.bitmapsView = carouselViews;

    for (Bitmap bitmap : carouselViews) {
      ImagePanel imagePanel = new ImagePanel(OnelittleAngelApplication.instance.getApplicationContext());
      imagePanel.setDrawingCacheEnabled(true);
      imagePanel.setImageBitmap(bitmap);
      this.carouselViews.add(imagePanel);
    }
  }
}
