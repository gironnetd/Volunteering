package com.sc.fr.islam.layers.mvp.contents;

import android.graphics.Bitmap;
import android.view.View;

import com.sc.fr.islam.layers.mvp.common.models.PageModel;
import com.sc.fr.islam.transverse.orms.realm.models.Book;
import com.sc.fr.islam.transverse.orms.realm.models.Movement;
import com.sc.fr.islam.transverse.orms.realm.models.Author;
import com.sc.fr.islam.transverse.orms.realm.models.Theme;

import java.util.List;

public interface ContentsPresenterInterface {

  /**
   *
   * @param idQuote
   */
  void toggleQuoteIsFavorites(int idQuote);

  /**
   *
   */
  void loadPageModels(String from, String name);

  /**
   *
   * @return
   */
  PageModel[] getPageModels();

  /**
   *
   */
  void setPageModels();

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

  void sendEmail(List<String> mailTo, String emailSubject, String emailBody);

  void setContentsViewInterface();

  /**
   *
   * @return
   */
  List<View> getCarouselView();

  /**
   *
   * @param carouselViews
   */
  void setCarouselView(List<View> carouselViews);

  /**
   *
   * @return
   */
  List<Bitmap> getBitmapsView();

  /**
   *
   * @param bitmapsView
   */
  void setBitmapsView(List<Bitmap> bitmapsView);

  /**
   *
   * @param type
   * @param name
   */
  void loadPictures(String type, String name);

  /**
   *
   * @return
   */
  boolean picturesAlreadyLoaded();

  /**
   *
   */
  void releaseBitmapsCarousel();

  /**
   *
   * @return
   */
  Author getAuthor();

  /**
   *
   * @return
   */
  Book getBook();

  /**
   *
   * @return
   */
  Movement getMovement();

  /**
   *
   * @return
   */
  Theme getTheme();
}
