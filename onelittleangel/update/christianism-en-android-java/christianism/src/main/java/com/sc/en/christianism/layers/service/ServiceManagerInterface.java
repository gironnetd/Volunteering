package com.sc.en.christianism.layers.service;

import com.sc.en.christianism.layers.service.accounts.interfaces.FaceBookServiceInterface;
import com.sc.en.christianism.layers.service.accounts.interfaces.MailServiceInterface;
import com.sc.en.christianism.layers.service.accounts.interfaces.TwitterServiceInterface;
import com.sc.en.christianism.layers.service.authors.interfaces.AuthorByIdAuthorServiceInterface;
import com.sc.en.christianism.layers.service.authors.interfaces.AuthorsByIdMovementServiceInterface;
import com.sc.en.christianism.layers.service.authors.interfaces.AuthorsByIdThemeServiceInterface;
import com.sc.en.christianism.layers.service.books.interfaces.BooksByIdThemeServiceInterface;
import com.sc.en.christianism.layers.service.centuries.interfaces.CenturyByIdBookServiceInterface;
import com.sc.en.christianism.layers.service.centuries.interfaces.CenturyByIdCenturyServiceInterface;
import com.sc.en.christianism.layers.service.homepage.interfaces.HomePageServiceInterface;
import com.sc.en.christianism.layers.service.mails.interfaces.MailsServiceInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementByIdMovementServiceInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementByNameServiceInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementsAllServiceInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementsWithAuthorsServiceInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementsWithBooksServiceInterface;
import com.sc.en.christianism.layers.service.notifications.biographyofday.interfaces.BiographyNotificationIntentServiceInterface;
import com.sc.en.christianism.layers.service.notifications.pictureofday.interfaces.PictureNotificationIntentServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesByIdBookServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesByIdMovementServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesByIdThemeServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesForCarouselInterface;
import com.sc.en.christianism.layers.service.presentations.interfaces.PresentationByIdAuthorServiceInterface;
import com.sc.en.christianism.layers.service.presentations.interfaces.PresentationByIdMovementServiceInterface;
import com.sc.en.christianism.layers.service.presentations.interfaces.PresentationByIdPresentationServiceInterface;
import com.sc.en.christianism.layers.service.presentations.interfaces.PresentationsAllServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.FavoritesQuotesServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesAllServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByAuthorServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByBookServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByIdBookServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByThemeServiceInterface;
import com.sc.en.christianism.layers.service.themes.interfaces.ThemeByIdThemeServiceInterface;
import com.sc.en.christianism.layers.service.themes.interfaces.ThemesAllServiceInterface;
import com.sc.en.christianism.layers.service.themes.interfaces.ThemesByIdParentServiceInterface;
import com.sc.en.christianism.layers.service.urls.interfaces.UrlByIdUrlServiceInterface;
import com.sc.en.christianism.layers.service.urls.interfaces.UrlsAllServiceInterface;
import com.sc.en.christianism.layers.service.urls.interfaces.UrlsByIdBookServiceInterface;
import com.sc.en.christianism.layers.service.accounts.interfaces.GooglePlusServiceInterface;
import com.sc.en.christianism.layers.service.authors.interfaces.AuthorsAllServiceInterface;
import com.sc.en.christianism.layers.service.books.interfaces.BookByIdBookServiceInterface;
import com.sc.en.christianism.layers.service.books.interfaces.BookByNameServiceInterface;
import com.sc.en.christianism.layers.service.books.interfaces.BooksAllServiceInterface;
import com.sc.en.christianism.layers.service.books.interfaces.BooksByIdMovementServiceInterface;
import com.sc.en.christianism.layers.service.centuries.interfaces.CenturyByIdAuthorServiceInterface;
import com.sc.en.christianism.layers.service.centuries.interfaces.CenturyByNameServiceInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementsByIdParentServiceInterface;
import com.sc.en.christianism.layers.service.movements.interfaces.MovementsWithMovementsServiceInterface;
import com.sc.en.christianism.layers.service.notifications.quoteofday.interfaces.QuoteNotificationIntentServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PictureByIdPictureServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesAllServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesByNameSmallServiceInterface;
import com.sc.en.christianism.layers.service.presentations.interfaces.PresentationByIdBookServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuoteByIdQuoteServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByIdAuthorServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByIdMovementServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByIdThemeServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.QuotesByMovementServiceInterface;
import com.sc.en.christianism.layers.service.themes.interfaces.ThemesWithThemesServiceInterface;
import com.sc.en.christianism.layers.service.urls.interfaces.UrlsByIdAuthorserviceInterface;
import com.sc.en.christianism.layers.service.urls.interfaces.UrlsByIdMovementServiceInterface;
import com.sc.en.christianism.layers.service.urls.interfaces.UrlsBySourceTypeServiceInterface;
import com.sc.en.christianism.layers.service.centuries.interfaces.CenturiesAllServiceInterface;
import com.sc.en.christianism.layers.service.pictures.interfaces.PicturesByIdAuthorServiceInterface;
import com.sc.en.christianism.layers.service.quotes.interfaces.UpdateQuoteServiceInterface;
import com.sc.en.christianism.layers.service.themes.interfaces.ThemeByNameServiceInterface;
import com.sc.en.christianism.layers.service.urls.interfaces.UrlsByIdSourceServiceInterface;
import com.sc.en.christianism.layers.service.authors.interfaces.AuthorByNameServiceInterface;

import java.util.concurrent.ExecutorService;

public interface ServiceManagerInterface {
  /**
   * To be called when you need to release all the services
   * Is managed by the MyApplication object in fact
   */
  void unbindAndDie();

  /***********************************************************
   *  Mails Services List
   **********************************************************/

  MailsServiceInterface getMailsService();

  /***********************************************************
   *  HomePage Services List
   **********************************************************/

  /**
   *
   * @return the homePageService
   */
  HomePageServiceInterface getHomePageService();

  /***********************************************************
   *  Accounts Services List
   **********************************************************/

  /**
   *
   * @return the biographyNotificationService
   */
  BiographyNotificationIntentServiceInterface getBiographyNotificationService();

  /**
   *
   * @return the pictureNotificationService
   */
  PictureNotificationIntentServiceInterface getPictureNotificationService();

  /**
   *
   * @return the quoteNotificationService
   */
  QuoteNotificationIntentServiceInterface getQuoteNotificationService();

  /***********************************************************
   *  Accounts Services List
   **********************************************************/

  /**
   *
   * @return the twitterService
   */
  TwitterServiceInterface getTwitterService();

  /**
   *
   * @return the mailService
   */
  MailServiceInterface getMailService();

  /**
   *
   * @return hte facebookService
   */
  FaceBookServiceInterface getFaceBookService();

  /**
   *
   * @return the googlePlusService
   */
  GooglePlusServiceInterface getGooglePlusService();

  /***********************************************************
   *  Authors Services List
   **********************************************************/

  /**
   *
   * @return the authorsAllServie
   */
  AuthorsAllServiceInterface getAuthorsAllService();

  /**
   *
   * @return the authorByIdAuthorService
   */
  AuthorByIdAuthorServiceInterface getAuthorByIdAuthorService();

  /**
   *
   * @return the authorByNameService
   */
  AuthorByNameServiceInterface getAuthorByNameService();

  /**
   *
   * @return the authorsByIdMovementService
   */
  AuthorsByIdMovementServiceInterface getAuthorsByIdMovementService();

  /**
   *
   * @return the authorsByIdThemeService
   */
  AuthorsByIdThemeServiceInterface getAuthorsByIdThemeService();

  /***********************************************************
   *  Books Services List
   **********************************************************/

  /**
   *
   * @return the booksAllService
   */
  BooksAllServiceInterface getBooksAllService();

  /**
   *
   * @return the bookByIdBookService
   */
  BookByIdBookServiceInterface getBookByIdBookService();

  /**
   *
   * @return the bookByNameService
   */
  BookByNameServiceInterface getBookByNameService();

  /**
   *
   * @return the booksByIdMovementService
   */
  BooksByIdMovementServiceInterface getBooksByIdMovementService();

  /**
   *
   * @return the booksByIdThemeService
   */
  BooksByIdThemeServiceInterface getBooksByIdThemeService();


  /***********************************************************
   *  Centuries Services List
   **********************************************************/

  /**
   *
   * @return the centuriesAllService
   */
  CenturiesAllServiceInterface getCenturiesAllService();

  /**
   *
   * @return the centuryByIdAuthorService
   */
  CenturyByIdAuthorServiceInterface getCenturyByIdAuthorService();

  /**
   *
   * @return the centuryByIdBookService
   */
  CenturyByIdBookServiceInterface getCenturyByIdBookService();

  /**
   *
   * @return the centuryByIdCenturyService
   */
  CenturyByIdCenturyServiceInterface getCenturyByIdCenturyService();

  /**
   *
   * @return the centuryByNameService
   */
  CenturyByNameServiceInterface getCenturyByNameService();

  /***********************************************************
   *  Movements Services List
   **********************************************************/

  /**
   *
   * @return the movementsAllService
   */
  MovementsAllServiceInterface getMovementsAllService();

  /**
   *
   * @return the movementsWithAuthorsService
   */
  MovementsWithAuthorsServiceInterface getMovementsWithAuthorsService();

  /**
   *
   * @return the movementsWithBooksService
   */
  MovementsWithBooksServiceInterface getMovementsWithBooksService();

  /**
   *
   * @return the movementsWithMovementsService
   */
  MovementsWithMovementsServiceInterface getMovementsWithMovementsService();
  /**
   *
   * @return the movementByIdMovementService
   */
  MovementByIdMovementServiceInterface getMovementByIdMovementService();

  /**
   *
   * @return the movementByNameService
   */
  MovementByNameServiceInterface getMovementByNameService();

  /**
   *
   * @return the movementsByIdParentService
   */
  MovementsByIdParentServiceInterface getMovementsByIdParentService();

  /***********************************************************
   *  Pictures Services List
   **********************************************************/

  /**
   *
   * @return the picturesAllService
   */
  PicturesAllServiceInterface getPicturesAllService();

  /**
   *
   * @return the pictureByIdPictureService
   */
  PictureByIdPictureServiceInterface getPicturesByIdPictureService();

  /**
   *
   * @return the pictureByNameSmallService
   */
  PicturesByNameSmallServiceInterface getPicturesByNameSmallService();

  /**
   *
   * @return the picturesByIdAuthorService
   */
  PicturesByIdAuthorServiceInterface getPicturesByIdAuthorService();

  /**
   *
   * @return the picturesByIdBookService
   */
  PicturesByIdBookServiceInterface getPicturesByIdBookService();

  /**
   *
   * @return the picturesByIdMovementService
   */
  PicturesByIdMovementServiceInterface getPicturesByIdMovementService();

  /**
   *
   * @return the picturesByIdThemeService
   */
  PicturesByIdThemeServiceInterface getPicturesByIdThemeService();

  /**
   *
   * @return
   */
  PicturesForCarouselInterface getPicturesForCarouselService();

  /***********************************************************
   *  Presentations Services List
   **********************************************************/

  /**
   *
   * @return the presentationsAllService
   */
  PresentationsAllServiceInterface getPresentationsAllService();

  /**
   *
   * @return the presentationByIdPresentationService
   */
  PresentationByIdPresentationServiceInterface getPresentationByIdPresentationService();

  /**
   *
   * @return the presentationByIdAuthorService
   */
  PresentationByIdAuthorServiceInterface getPresentationByIdAuthorService();

  /**
   *
   * @return the presentationByIdBookService
   */
  PresentationByIdBookServiceInterface getPresentationByIdBookService();

  /**
   *
   * @return the PresentationByIdMovementService
   */
  PresentationByIdMovementServiceInterface getPresentationByIdMovementServvice();

  /***********************************************************
   * Quotes Services List
   **********************************************************/
  
  /**
   *
   * @return the quotesAllService
   */
  QuotesAllServiceInterface getQuotesAllService();

  /**
   *
   * @return the quoteByIdQuoteService
   */
  QuoteByIdQuoteServiceInterface getQuoteByIdQuoteService();

  /**
   *
   * @return the quotesByAuthorService
   */
  QuotesByAuthorServiceInterface getQuotesByAuthorService();
  /**
   *
   * @return the quotesByIdAuthorService
   */
  QuotesByIdAuthorServiceInterface getQuotesByIdAuthorService();

  /**
   *
   * @return the quotesBYBookService
   */
  QuotesByBookServiceInterface getQuotesByBookService();

  /**
   *
   * @return the quotesByIdBookService
   */
  QuotesByIdBookServiceInterface getQuotesByIdBookService();

  /**
   *
   * @return the quotesByThemeService
   */
  QuotesByThemeServiceInterface getQuotesByThemeService();

  /**
   *
   * @return the quotesByMovementService
   */
  QuotesByMovementServiceInterface getQuotesByMovementService();

  /**
   *
   * @return the quotesByIdThemeService
   */
  QuotesByIdThemeServiceInterface getQuotesByIdThemeService();

  /**
   *
   * @return the quotesByIdMovementService
   */
  QuotesByIdMovementServiceInterface getQuotesByIdMovementsService();

  /**
   *
   * @return the updateQuoteService
   */
  UpdateQuoteServiceInterface getUpdateQuoteService();

  /**
   *
   * @return the favoritesQuotesService
   */
  FavoritesQuotesServiceInterface getAllFavoritesService();

  /***********************************************************
   *  Themes Services List
   **********************************************************/
  
  /**
   *
   * @return the themesAllService
   */
  ThemesAllServiceInterface getThemesAllService();

  /**
   *
   * @return the themeByNameService
   */
  ThemeByNameServiceInterface getThemeByNameService();

  /**
   *
   * @return the themeByIdThemeService
   */
  ThemeByIdThemeServiceInterface getThemeByIdThemeService();

  /**
   *
   * @return the themesByIdParentService
   */
  ThemesByIdParentServiceInterface getThemesByIdParentService();

  /**
   *
   * @return the themesWithThemesService
   */
  ThemesWithThemesServiceInterface getThemesWithThemesService();

  /***********************************************************
   *  Urls Services List
   **********************************************************/
  
  /**
   *
   * @return the urlsAllService
   */
  UrlsAllServiceInterface getUrlsAllService();

  /**
   *
   * @return the urlsByIdUrlService
   */
  UrlByIdUrlServiceInterface getUrlByIdUrlService();

  /**
   *
   * @return the urlsByIdAuthorService
   */
  UrlsByIdAuthorserviceInterface getUrlsByIdAuthorService();

  /**
   *
   * @return the urlsByIdBookService
   */
  UrlsByIdBookServiceInterface getUrlsByIdBookService();

  /**
   *
   * @return the urlsByIdMovementService
   */
  UrlsByIdMovementServiceInterface getUrlsByIdMovementService();

  /**
   *
   * @return the urlsByIdSourceAndSourceTypeService
   */
  UrlsByIdSourceServiceInterface getUrlsByIdSourceService();

  /**
   *
   * @return the urlsBySourceTypeService
   */
  UrlsBySourceTypeServiceInterface getUrlsBySourceTypeService();

  /**
   * @return the cancelableThreadsExceutor
   */
  ExecutorService getCancelableThreadsExecutor();

  /**
   * @return the cancelableThreadsExceutor
   */
  ExecutorService getKeepAliveThreadsExecutor();
}
