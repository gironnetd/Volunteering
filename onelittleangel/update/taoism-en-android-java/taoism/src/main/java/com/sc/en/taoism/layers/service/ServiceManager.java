package com.sc.en.taoism.layers.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sc.en.taoism.layers.service.accounts.services.GooglePlusService;
import com.sc.en.taoism.layers.service.authors.interfaces.AuthorsByIdThemeServiceInterface;
import com.sc.en.taoism.layers.service.authors.services.AuthorByNameService;
import com.sc.en.taoism.layers.service.books.interfaces.BookByNameServiceInterface;
import com.sc.en.taoism.layers.service.centuries.interfaces.CenturyByNameServiceInterface;
import com.sc.en.taoism.layers.service.centuries.services.CenturiesAllService;
import com.sc.en.taoism.layers.service.centuries.services.CenturyByIdAuthorService;
import com.sc.en.taoism.layers.service.centuries.services.CenturyByIdCenturyService;
import com.sc.en.taoism.layers.service.movements.services.MovementByIdMovementService;
import com.sc.en.taoism.layers.service.movements.services.MovementsAllService;
import com.sc.en.taoism.layers.service.pictures.services.PicturesByIdBookService;
import com.sc.en.taoism.layers.service.presentations.interfaces.PresentationByIdBookServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesAllServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByThemeServiceInterface;
import com.sc.en.taoism.layers.service.quotes.services.FavoritesQuotesService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByThemeService;
import com.sc.en.taoism.layers.service.themes.interfaces.ThemesByIdParentServiceInterface;
import com.sc.en.taoism.layers.service.themes.interfaces.ThemesWithThemesServiceInterface;
import com.sc.en.taoism.layers.service.urls.interfaces.UrlsByIdBookServiceInterface;
import com.sc.en.taoism.BuildConfig;
import com.sc.en.taoism.OnelittleAngelApplication;
import com.sc.en.taoism.layers.service.accounts.interfaces.FaceBookServiceInterface;
import com.sc.en.taoism.layers.service.accounts.interfaces.GooglePlusServiceInterface;
import com.sc.en.taoism.layers.service.accounts.interfaces.MailServiceInterface;
import com.sc.en.taoism.layers.service.accounts.interfaces.TwitterServiceInterface;
import com.sc.en.taoism.layers.service.accounts.services.FaceBookService;
import com.sc.en.taoism.layers.service.accounts.services.MailService;
import com.sc.en.taoism.layers.service.accounts.services.TwitterService;
import com.sc.en.taoism.layers.service.homepage.interfaces.HomePageServiceInterface;
import com.sc.en.taoism.layers.service.homepage.services.HomePageService;
import com.sc.en.taoism.layers.service.mails.interfaces.MailsServiceInterface;
import com.sc.en.taoism.layers.service.mails.services.MailsService;
import com.sc.en.taoism.layers.service.movements.interfaces.MovementsWithAuthorsServiceInterface;
import com.sc.en.taoism.layers.service.notifications.biographyofday.interfaces.BiographyNotificationIntentServiceInterface;
import com.sc.en.taoism.layers.service.notifications.biographyofday.services.BiographyNotificationService;
import com.sc.en.taoism.layers.service.notifications.pictureofday.interfaces.PictureNotificationIntentServiceInterface;
import com.sc.en.taoism.layers.service.notifications.pictureofday.services.PictureNotificationService;
import com.sc.en.taoism.layers.service.notifications.quoteofday.interfaces.QuoteNotificationIntentServiceInterface;
import com.sc.en.taoism.layers.service.notifications.quoteofday.services.QuoteNotificationService;
import com.sc.en.taoism.layers.service.pictures.interfaces.PicturesForCarouselInterface;
import com.sc.en.taoism.layers.service.pictures.services.PicturesForCarousel;
import com.sc.en.taoism.layers.service.quotes.interfaces.FavoritesQuotesServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByAuthorServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByBookServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByMovementServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.UpdateQuoteServiceInterface;
import com.sc.en.taoism.layers.service.quotes.services.QuotesAllService;
import com.sc.en.taoism.layers.service.authors.interfaces.AuthorByIdAuthorServiceInterface;
import com.sc.en.taoism.layers.service.authors.interfaces.AuthorByNameServiceInterface;
import com.sc.en.taoism.layers.service.authors.interfaces.AuthorsAllServiceInterface;
import com.sc.en.taoism.layers.service.authors.interfaces.AuthorsByIdMovementServiceInterface;
import com.sc.en.taoism.layers.service.authors.services.AuthorByIdAuthorService;
import com.sc.en.taoism.layers.service.authors.services.AuthorsAllService;
import com.sc.en.taoism.layers.service.authors.services.AuthorsByIdMovementService;
import com.sc.en.taoism.layers.service.authors.services.AuthorsByIdThemeService;
import com.sc.en.taoism.layers.service.books.interfaces.BookByIdBookServiceInterface;
import com.sc.en.taoism.layers.service.books.interfaces.BooksAllServiceInterface;
import com.sc.en.taoism.layers.service.books.interfaces.BooksByIdMovementServiceInterface;
import com.sc.en.taoism.layers.service.books.interfaces.BooksByIdThemeServiceInterface;
import com.sc.en.taoism.layers.service.books.services.BookByIdBookService;
import com.sc.en.taoism.layers.service.books.services.BookByNameService;
import com.sc.en.taoism.layers.service.books.services.BooksAllService;
import com.sc.en.taoism.layers.service.books.services.BooksByIdMovementService;
import com.sc.en.taoism.layers.service.books.services.BooksByIdThemeService;
import com.sc.en.taoism.layers.service.centuries.interfaces.CenturiesAllServiceInterface;
import com.sc.en.taoism.layers.service.centuries.interfaces.CenturyByIdAuthorServiceInterface;
import com.sc.en.taoism.layers.service.centuries.interfaces.CenturyByIdBookServiceInterface;
import com.sc.en.taoism.layers.service.centuries.interfaces.CenturyByIdCenturyServiceInterface;
import com.sc.en.taoism.layers.service.centuries.services.CenturyByIdBookService;
import com.sc.en.taoism.layers.service.centuries.services.CenturyByNameService;
import com.sc.en.taoism.layers.service.movements.interfaces.MovementByIdMovementServiceInterface;
import com.sc.en.taoism.layers.service.movements.interfaces.MovementByNameServiceInterface;
import com.sc.en.taoism.layers.service.movements.interfaces.MovementsAllServiceInterface;
import com.sc.en.taoism.layers.service.movements.interfaces.MovementsByIdParentServiceInterface;
import com.sc.en.taoism.layers.service.movements.interfaces.MovementsWithBooksServiceInterface;
import com.sc.en.taoism.layers.service.movements.interfaces.MovementsWithMovementsServiceInterface;
import com.sc.en.taoism.layers.service.movements.services.MovementByNameService;
import com.sc.en.taoism.layers.service.movements.services.MovementsByIdParentService;
import com.sc.en.taoism.layers.service.movements.services.MovementsWithAuthorsService;
import com.sc.en.taoism.layers.service.movements.services.MovementsWithBooksService;
import com.sc.en.taoism.layers.service.movements.services.MovementsWithMovementsService;
import com.sc.en.taoism.layers.service.pictures.interfaces.PictureByIdPictureServiceInterface;
import com.sc.en.taoism.layers.service.pictures.interfaces.PicturesAllServiceInterface;
import com.sc.en.taoism.layers.service.pictures.interfaces.PicturesByIdAuthorServiceInterface;
import com.sc.en.taoism.layers.service.pictures.interfaces.PicturesByIdBookServiceInterface;
import com.sc.en.taoism.layers.service.pictures.interfaces.PicturesByIdMovementServiceInterface;
import com.sc.en.taoism.layers.service.pictures.interfaces.PicturesByIdThemeServiceInterface;
import com.sc.en.taoism.layers.service.pictures.interfaces.PicturesByNameSmallServiceInterface;
import com.sc.en.taoism.layers.service.pictures.services.PictureByIdPictureService;
import com.sc.en.taoism.layers.service.pictures.services.PicturesAllService;
import com.sc.en.taoism.layers.service.pictures.services.PicturesByIdAuthorService;
import com.sc.en.taoism.layers.service.pictures.services.PicturesByIdMovementService;
import com.sc.en.taoism.layers.service.pictures.services.PicturesByIdThemeService;
import com.sc.en.taoism.layers.service.pictures.services.PicturesByNameSmallService;
import com.sc.en.taoism.layers.service.presentations.interfaces.PresentationByIdAuthorServiceInterface;
import com.sc.en.taoism.layers.service.presentations.interfaces.PresentationByIdMovementServiceInterface;
import com.sc.en.taoism.layers.service.presentations.interfaces.PresentationByIdPresentationServiceInterface;
import com.sc.en.taoism.layers.service.presentations.interfaces.PresentationsAllServiceInterface;
import com.sc.en.taoism.layers.service.presentations.services.PresentationByIdAuthorService;
import com.sc.en.taoism.layers.service.presentations.services.PresentationByIdBookService;
import com.sc.en.taoism.layers.service.presentations.services.PresentationByIdMovementService;
import com.sc.en.taoism.layers.service.presentations.services.PresentationByIdPresentationService;
import com.sc.en.taoism.layers.service.presentations.services.PresentationsAllService;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuoteByIdQuoteServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByIdAuthorServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByIdBookServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByIdMovementServiceInterface;
import com.sc.en.taoism.layers.service.quotes.interfaces.QuotesByIdThemeServiceInterface;
import com.sc.en.taoism.layers.service.quotes.services.QuoteByIdQuoteService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByAuthorService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByBookService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByIdAuthorService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByIdBookService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByIdMovementService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByIdThemeService;
import com.sc.en.taoism.layers.service.quotes.services.QuotesByMovementService;
import com.sc.en.taoism.layers.service.quotes.services.UpdateQuoteService;
import com.sc.en.taoism.layers.service.themes.interfaces.ThemeByIdThemeServiceInterface;
import com.sc.en.taoism.layers.service.themes.interfaces.ThemeByNameServiceInterface;
import com.sc.en.taoism.layers.service.themes.interfaces.ThemesAllServiceInterface;
import com.sc.en.taoism.layers.service.themes.services.ThemeByIdThemeService;
import com.sc.en.taoism.layers.service.themes.services.ThemeByNameService;
import com.sc.en.taoism.layers.service.themes.services.ThemesAllService;
import com.sc.en.taoism.layers.service.themes.services.ThemesByIdParentService;
import com.sc.en.taoism.layers.service.themes.services.ThemesWithThemesService;
import com.sc.en.taoism.layers.service.urls.interfaces.UrlByIdUrlServiceInterface;
import com.sc.en.taoism.layers.service.urls.interfaces.UrlsAllServiceInterface;
import com.sc.en.taoism.layers.service.urls.interfaces.UrlsByIdAuthorserviceInterface;
import com.sc.en.taoism.layers.service.urls.interfaces.UrlsByIdMovementServiceInterface;
import com.sc.en.taoism.layers.service.urls.interfaces.UrlsByIdSourceServiceInterface;
import com.sc.en.taoism.layers.service.urls.interfaces.UrlsBySourceTypeServiceInterface;
import com.sc.en.taoism.layers.service.urls.services.UrlByIdUrlService;
import com.sc.en.taoism.layers.service.urls.services.UrlsAllService;
import com.sc.en.taoism.layers.service.urls.services.UrlsByIdAuthorService;
import com.sc.en.taoism.layers.service.urls.services.UrlsByIdBookService;
import com.sc.en.taoism.layers.service.urls.services.UrlsByIdMovementService;
import com.sc.en.taoism.layers.service.urls.services.UrlsByIdSourceService;
import com.sc.en.taoism.layers.service.urls.services.UrlsBySourceTypeService;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ServiceManager implements ServiceManagerInterface {

  private static final String TAG = "ServiceManager";

  /***********************************************************
   *  Services List
   **********************************************************/

  private ArrayList<MotherBusinessServiceInterface> motherBusinessServices;

  /***********************************************************
   *  Mails Services List
   **********************************************************/

  /**
   * The Mails Service
   */

  private MailsServiceInterface mailsServiceInterface = null;

  /***********************************************************
   *  HomePage Services List
   **********************************************************/

  /**
   * The HomePage Service
   */

  private HomePageServiceInterface homePageServiceInterface = null;

  /***********************************************************
   *  Notifications Services List
   **********************************************************/

  /**
   * The Notifications Service
   */

  private BiographyNotificationIntentServiceInterface biographyNotificationIntentServiceInterface = null;

  private PictureNotificationIntentServiceInterface pictureNotificationIntentServiceInterface = null;

  private QuoteNotificationIntentServiceInterface quoteNotificationIntentServiceInterface = null;

  /***********************************************************
   *  Accounts Services List
   **********************************************************/

  /**
   * The Accounts Service
   */
  private TwitterServiceInterface twitterServiceInterface = null;

  private MailServiceInterface mailServiceInterface = null;

  private FaceBookServiceInterface faceBookServiceInterface = null;

  private GooglePlusServiceInterface googlePlusServiceInterface = null;

  /***********************************************************
   *  Authors Services List
   **********************************************************/

  /**
   * The Authors Service
   */
  private AuthorsAllServiceInterface authorsAllServiceInterface = null;

  private AuthorByIdAuthorServiceInterface authorByIdAuthorServiceInterface = null;

  private AuthorByNameServiceInterface authorByNameServiceInterface = null;

  private AuthorsByIdMovementServiceInterface authorsByIdMovementServiceInterface = null;

  private AuthorsByIdThemeServiceInterface authorsByIdThemeServiceInterface = null;

  /***********************************************************
   * Books Services List
   **********************************************************/

  /**
   * The Books Service
   */
  private BooksAllServiceInterface booksAllServiceInterface = null;

  private BookByIdBookServiceInterface bookByIdBookServiceInterface = null;

  private BookByNameServiceInterface bookByNameServiceInterface = null;

  private BooksByIdMovementServiceInterface booksByIdMovementServiceInterface = null;

  private BooksByIdThemeServiceInterface booksByIdThemeServiceInterface = null;

  /***********************************************************
   * Centuries Services List
   **********************************************************/

  /**
   * The Centuries Service
   */
  private CenturiesAllServiceInterface centuriesAllServiceInterface = null;

  private CenturyByIdCenturyServiceInterface centuryByIdCenturyServiceInterface = null;

  private CenturyByIdAuthorServiceInterface centuryByIdAuthorServiceInterface = null;

  private CenturyByIdBookServiceInterface centuryByIdBookServiceInterface = null;

  private CenturyByNameServiceInterface centuryByNameServiceInterface = null;

  /***********************************************************
   * Movements Services List
   **********************************************************/

  /**
   * The Movements Serivce
   */
  private MovementsAllServiceInterface movementsAllServiceInterface = null;

  private MovementsWithAuthorsServiceInterface movementsWithAuthorsServiceInterface = null;

  private MovementsWithBooksServiceInterface movementsWithBooksServiceInterface = null;

  private MovementsWithMovementsServiceInterface movementsWithMovementsServiceInterface = null;

  private MovementByIdMovementServiceInterface movementByIdMovementServiceInterface = null;

  private MovementByNameServiceInterface movementByNameServiceInterface = null;

  private MovementsByIdParentServiceInterface movementsByIdParentServiceInterface = null;

  /***********************************************************
   * Pictures Services List
   **********************************************************/

  /**
   * The Pictures Service
   */
  private PicturesAllServiceInterface picturesAllServiceInterface = null;

  private PictureByIdPictureServiceInterface pictureByIdPictureServiceInterface = null;

  private PicturesByNameSmallServiceInterface picturesByNameSmallServiceInterface = null;

  private PicturesByIdAuthorServiceInterface picturesByIdAuthorServiceInterface = null;

  private PicturesByIdBookServiceInterface picturesByIdBookServiceInterface = null;

  private PicturesByIdMovementServiceInterface picturesByIdMovementServiceInterface = null;

  private PicturesByIdThemeServiceInterface picturesByIdThemeServiceInterface = null;

  private PicturesForCarouselInterface picturesForCarouselInterface = null;

  /***********************************************************
   * Presentations Services List
   **********************************************************/

  /**
   * The Presentations Service
   */
  private PresentationsAllServiceInterface presentationsAllServiceInterface = null;

  private PresentationByIdAuthorServiceInterface presentationByIdAuthorServiceInterface = null;

  private PresentationByIdBookServiceInterface presentationByIdBookServiceInterface = null;

  private PresentationByIdMovementServiceInterface presentationByIdMovementServiceInterface = null;

  private PresentationByIdPresentationServiceInterface presentationByIdPresentationServiceInterface = null;

  /***********************************************************
   * Quotes Services List
   **********************************************************/

  /**
   * The Quotes Service
   */
  private QuotesAllServiceInterface quotesAllServiceInterface = null;

  private QuoteByIdQuoteServiceInterface quoteByIdQuoteServiceInterface = null;

  private QuotesByAuthorServiceInterface quotesByAuthorServiceInterface = null;

  private QuotesByIdAuthorServiceInterface quotesByIdAuthorServiceInterface = null;

  private QuotesByBookServiceInterface quotesByBookServiceInterface = null;

  private QuotesByIdBookServiceInterface quotesByIdBookServiceInterface = null;

  private QuotesByMovementServiceInterface quotesByMovementServiceInterface = null;

  private QuotesByIdMovementServiceInterface quotesByIdMovementServiceInterface = null;

  private QuotesByThemeServiceInterface quotesByThemeServiceInterface = null;

  private QuotesByIdThemeServiceInterface quotesByIdThemeServiceInterface = null;

  private UpdateQuoteServiceInterface updateQuoteServiceInterface = null;

  private FavoritesQuotesServiceInterface favoritesQuotesServiceInterface = null;

  /***********************************************************
   * Themes Services List
   **********************************************************/

  /**
   * The Themes Service
   */
  private ThemesAllServiceInterface themesAllServiceInterface = null;

  private ThemeByNameServiceInterface themeByNameServiceInterface = null;

  private ThemeByIdThemeServiceInterface themeByIdThemeServiceInterface = null;

  private ThemesByIdParentServiceInterface themesByIdParentServiceInterface = null;

  private ThemesWithThemesServiceInterface themesWithThemesServiceInterface = null;

  /***********************************************************
   * Urls Services List
   **********************************************************/

  /**
   * The Urls Service
   */
  private UrlsAllServiceInterface urlsAllServiceInterface = null;

  private UrlByIdUrlServiceInterface urlByIdUrlServiceInterface = null;

  private UrlsByIdAuthorserviceInterface urlsByIdAuthorserviceInterface = null;

  private UrlsByIdBookServiceInterface urlsByIdBookServiceInterface = null;

  private UrlsByIdMovementServiceInterface urlsByIdMovementServiceInterface = null;

  private UrlsByIdSourceServiceInterface urlsByIdSourceServiceInterface = null;

  private UrlsBySourceTypeServiceInterface urlsBySourceTypeServiceInterface = null;

  /***********************************************************
   *  Constructor and destructor
   **********************************************************/
  /**
   * Insure only the Application object can instantiate once this object
   * If not the case throw an Exception
   */
  public ServiceManager(OnelittleAngelApplication application) {
    if(BuildConfig.FLAVOR.equals("mock")){
      //do what you want
      //Log.e(TAG,"Mock Mode on ServiceManager initialization");
    }else if (application.serviceManagerAlreadyExist()) {
      throw new ExceptionInInitializerError();
    }
    motherBusinessServices= new ArrayList<>();
  }
  /**
   * To be called when you need to release all the services
   * Is managed by the MyApplication object in fact
   */
  @Override
  public void unbindAndDie() {
    //Log.e("ServiceManager", "UnbindAndDie is called");
    //kill your thread
    if (cancelableThreadsExecutor != null) {
      killCancelableThreadExecutor();
    }
    if (keepAliveThreadsExceutor != null) {
      killKeepAliveThreadExecutor();
    }
    //kill your business services
    for(MotherBusinessServiceInterface service:motherBusinessServices){
      service.onDestroy(this);
    }
    //release your pointer

    mailsServiceInterface = null;

    homePageServiceInterface = null;

    biographyNotificationIntentServiceInterface = null;
    pictureNotificationIntentServiceInterface = null;
    quoteNotificationIntentServiceInterface = null;

    twitterServiceInterface = null;
    mailServiceInterface = null;
    faceBookServiceInterface = null;
    googlePlusServiceInterface = null;

    authorsAllServiceInterface = null;
    authorByIdAuthorServiceInterface = null;
    authorByNameServiceInterface = null;
    authorsByIdMovementServiceInterface = null;
    authorsByIdThemeServiceInterface = null;

    booksAllServiceInterface = null;
    bookByIdBookServiceInterface = null;
    bookByNameServiceInterface = null;
    booksByIdMovementServiceInterface = null;
    booksByIdThemeServiceInterface = null;

    centuriesAllServiceInterface = null;
    centuryByIdCenturyServiceInterface = null;
    centuryByIdAuthorServiceInterface = null;
    centuryByIdBookServiceInterface = null;
    centuryByNameServiceInterface = null;

    movementsAllServiceInterface = null;
    movementsWithAuthorsServiceInterface = null;
    movementsWithBooksServiceInterface = null;
    movementByIdMovementServiceInterface = null;
    movementByIdMovementServiceInterface = null;
    movementByNameServiceInterface = null;
    movementsByIdParentServiceInterface = null;

    picturesAllServiceInterface = null;
    pictureByIdPictureServiceInterface = null;
    picturesByNameSmallServiceInterface = null;
    picturesByIdAuthorServiceInterface = null;
    picturesByIdBookServiceInterface = null;
    picturesByIdMovementServiceInterface = null;
    picturesByIdThemeServiceInterface = null;
  //  picturesForCarouselInterface.setBitmapsForCarousel(null);
    picturesForCarouselInterface = null;

    presentationsAllServiceInterface = null;
    presentationByIdAuthorServiceInterface = null;
    presentationByIdBookServiceInterface = null;
    presentationByIdMovementServiceInterface = null;
    presentationByIdPresentationServiceInterface = null;

    quotesAllServiceInterface = null;
    quoteByIdQuoteServiceInterface = null;
    quotesByAuthorServiceInterface = null;
    quotesByIdAuthorServiceInterface = null;
    quotesByBookServiceInterface = null;
    quotesByIdBookServiceInterface = null;
    quotesByMovementServiceInterface = null;
    quotesByIdMovementServiceInterface = null;
    quotesByThemeServiceInterface = null;
    quotesByIdThemeServiceInterface = null;
    updateQuoteServiceInterface = null;
    favoritesQuotesServiceInterface = null;

    themesAllServiceInterface = null;
    themeByNameServiceInterface = null;
    themeByIdThemeServiceInterface = null;
    themesByIdParentServiceInterface = null;
    themesWithThemesServiceInterface = null;

    urlsAllServiceInterface = null;
    urlByIdUrlServiceInterface = null;
    urlsByIdAuthorserviceInterface = null;
    urlsByIdBookServiceInterface = null;
    urlsByIdMovementServiceInterface = null;
    urlsByIdSourceServiceInterface = null;
    urlsBySourceTypeServiceInterface = null;
  }

  @Override
  public MailsServiceInterface getMailsService() {
    if(mailsServiceInterface == null){
      mailsServiceInterface = new MailsService(this);
      motherBusinessServices.add(mailsServiceInterface);
    }
    return mailsServiceInterface;
  }

  @Override
  public HomePageServiceInterface getHomePageService() {
    if(homePageServiceInterface == null){
      homePageServiceInterface = new HomePageService(this);
      motherBusinessServices.add(homePageServiceInterface);
    }
    return homePageServiceInterface;
  }

  @Override
  public BiographyNotificationIntentServiceInterface getBiographyNotificationService() {
    if(biographyNotificationIntentServiceInterface == null){
      biographyNotificationIntentServiceInterface = new BiographyNotificationService(this);
      motherBusinessServices.add(biographyNotificationIntentServiceInterface);
    }
    return biographyNotificationIntentServiceInterface;
  }

  @Override
  public PictureNotificationIntentServiceInterface getPictureNotificationService() {
    if(pictureNotificationIntentServiceInterface == null){
      pictureNotificationIntentServiceInterface = new PictureNotificationService(this);
      motherBusinessServices.add(pictureNotificationIntentServiceInterface);
    }
    return pictureNotificationIntentServiceInterface;
  }

  @Override
  public QuoteNotificationIntentServiceInterface getQuoteNotificationService() {
    if(quoteNotificationIntentServiceInterface == null){
      quoteNotificationIntentServiceInterface = new QuoteNotificationService(this);
      motherBusinessServices.add(quoteNotificationIntentServiceInterface);
    }
    return quoteNotificationIntentServiceInterface;
  }

  /***********************************************************
   *  Services Getters
   **********************************************************/

  /***********************************************************
   * Accounts Services Getters
   **********************************************************/

  @Override
  public TwitterServiceInterface getTwitterService() {
    if(twitterServiceInterface == null){
      twitterServiceInterface = new TwitterService(this);
      motherBusinessServices.add(twitterServiceInterface);
    }
    return twitterServiceInterface;
  }

  @Override
  public MailServiceInterface getMailService() {
    if(mailServiceInterface == null){
      mailServiceInterface = new MailService(this);
      motherBusinessServices.add(mailServiceInterface);
    }
    return mailServiceInterface;
  }

  @Override
  public FaceBookServiceInterface getFaceBookService() {
    if(faceBookServiceInterface == null){
      faceBookServiceInterface = new FaceBookService(this);
      motherBusinessServices.add(faceBookServiceInterface);
    }
    return faceBookServiceInterface;
  }

  @Override
  public GooglePlusServiceInterface getGooglePlusService() {
    if(googlePlusServiceInterface == null){
      googlePlusServiceInterface = new GooglePlusService(this);
      motherBusinessServices.add(googlePlusServiceInterface);
    }
    return googlePlusServiceInterface;
  }

  /***********************************************************
   * Authors Services Getters
   **********************************************************/
  /**
   * @return the authorsServie
   */
  @Override
  public AuthorsAllServiceInterface getAuthorsAllService() {
    if(authorsAllServiceInterface == null){
      authorsAllServiceInterface = new AuthorsAllService(this);
      motherBusinessServices.add(authorsAllServiceInterface);
    }
    return authorsAllServiceInterface;
  }

  /**
   * @return the authorByIdAuthorService
   */
  @Override
  public AuthorByIdAuthorServiceInterface getAuthorByIdAuthorService() {
    if(authorByIdAuthorServiceInterface == null){
      authorByIdAuthorServiceInterface = new AuthorByIdAuthorService(this);
      motherBusinessServices.add(authorByIdAuthorServiceInterface);
    }
    return authorByIdAuthorServiceInterface;
  }

  /**
   * @return the authorByNameService
   */
  @Override
  public AuthorByNameServiceInterface getAuthorByNameService() {
    if(authorByNameServiceInterface == null){
      authorByNameServiceInterface = new AuthorByNameService(this);
      motherBusinessServices.add(authorByNameServiceInterface);
    }
    return authorByNameServiceInterface;
  }

  /**
   * @return the authorsByIdMovementService
   */
  @Override
  public AuthorsByIdMovementServiceInterface getAuthorsByIdMovementService() {
    if(authorsByIdMovementServiceInterface == null){
      authorsByIdMovementServiceInterface = new AuthorsByIdMovementService(this);
      motherBusinessServices.add(authorsByIdMovementServiceInterface);
    }
    return authorsByIdMovementServiceInterface;
  }

  /**
   * @return the authorsByIdThemeService
   */
  @Override
  public AuthorsByIdThemeServiceInterface getAuthorsByIdThemeService() {
    if(authorsByIdThemeServiceInterface == null){
      authorsByIdThemeServiceInterface = new AuthorsByIdThemeService(this);
      motherBusinessServices.add(authorsByIdThemeServiceInterface);
    }
    return authorsByIdThemeServiceInterface;
  }

  /***********************************************************
   * Books Services Getters
   **********************************************************/

  /**
   * @return the booksService
   */
  @Override
  public BooksAllServiceInterface getBooksAllService() {
    if(booksAllServiceInterface == null){
      booksAllServiceInterface = new BooksAllService(this);
      motherBusinessServices.add(booksAllServiceInterface);
    }
    return booksAllServiceInterface;
  }

  /**
   * @return the bookByIdBookService
   */
  @Override
  public BookByIdBookServiceInterface getBookByIdBookService() {
    if(bookByIdBookServiceInterface == null){
      bookByIdBookServiceInterface = new BookByIdBookService(this);
      motherBusinessServices.add(bookByIdBookServiceInterface);
    }
    return bookByIdBookServiceInterface;
  }

  /**
   * @return the bookByNameService
   */
  @Override
  public BookByNameServiceInterface getBookByNameService() {
    if(bookByNameServiceInterface == null){
      bookByNameServiceInterface = new BookByNameService(this);
      motherBusinessServices.add(bookByNameServiceInterface);
    }
    return bookByNameServiceInterface;
  }

  /**
   * @return the booksByIdMovementService
   */
  @Override
  public BooksByIdMovementServiceInterface getBooksByIdMovementService() {
    if(booksByIdMovementServiceInterface == null){
      booksByIdMovementServiceInterface = new BooksByIdMovementService(this);
      motherBusinessServices.add(booksByIdMovementServiceInterface);
    }
    return booksByIdMovementServiceInterface;
  }

  /**
   * @return the booksByIdThemeService
   */
  @Override
  public BooksByIdThemeServiceInterface getBooksByIdThemeService() {
    if(booksByIdThemeServiceInterface == null){
      booksByIdThemeServiceInterface = new BooksByIdThemeService(this);
      motherBusinessServices.add(booksByIdThemeServiceInterface);
    }
    return booksByIdThemeServiceInterface;
  }

  /***********************************************************
   * Centuries Services Getters
   **********************************************************/

  /**
   * @return the centuriesService
   */
  @Override
  public CenturiesAllServiceInterface getCenturiesAllService() {
    if(centuriesAllServiceInterface == null){
      centuriesAllServiceInterface = new CenturiesAllService(this);
      motherBusinessServices.add(centuriesAllServiceInterface);
    }
    return centuriesAllServiceInterface;
  }

  /**
   * @return the centuryByIdAuthorService
   */
  @Override
  public CenturyByIdAuthorServiceInterface getCenturyByIdAuthorService() {
    if(centuryByIdAuthorServiceInterface == null){
      centuryByIdAuthorServiceInterface = new CenturyByIdAuthorService(this);
      motherBusinessServices.add(centuryByIdAuthorServiceInterface);
    }
    return centuryByIdAuthorServiceInterface;
  }

  /**
   * @return the centuryByIdBookService
   */
  @Override
  public CenturyByIdBookServiceInterface getCenturyByIdBookService() {
    if(centuryByIdBookServiceInterface == null){
      centuryByIdBookServiceInterface = new CenturyByIdBookService(this);
      motherBusinessServices.add(centuryByIdBookServiceInterface);
    }
    return centuryByIdBookServiceInterface;
  }

  /**
   * @return the centuryByIdCenturyService
   */
  @Override
  public CenturyByIdCenturyServiceInterface getCenturyByIdCenturyService() {
    if(centuryByIdCenturyServiceInterface == null){
      centuryByIdCenturyServiceInterface = new CenturyByIdCenturyService(this);
      motherBusinessServices.add(centuryByIdCenturyServiceInterface);
    }
    return centuryByIdCenturyServiceInterface;
  }

  /**
   * @return the centuryByNameService
   */
  @Override
  public CenturyByNameServiceInterface getCenturyByNameService() {
    if(centuryByNameServiceInterface == null){
      centuryByNameServiceInterface = new CenturyByNameService(this);
      motherBusinessServices.add(centuryByNameServiceInterface);
    }
    return centuryByNameServiceInterface;
  }

  /***********************************************************
   * Movements Services Getters
   **********************************************************/

  /**
   * @return the movementsService
   */
  @Override
  public MovementsAllServiceInterface getMovementsAllService() {
    if(movementsAllServiceInterface == null){
      movementsAllServiceInterface = new MovementsAllService(this);
      motherBusinessServices.add(movementsAllServiceInterface);
    }
    return movementsAllServiceInterface;
  }

  /**
   * @return the movementsWithAuthorsService
   */
  @Override
  public MovementsWithAuthorsServiceInterface getMovementsWithAuthorsService() {
    if(movementsWithAuthorsServiceInterface == null){
      movementsWithAuthorsServiceInterface = new MovementsWithAuthorsService(this);
      motherBusinessServices.add(movementsWithAuthorsServiceInterface);
    }
    return movementsWithAuthorsServiceInterface;
  }

  /**
   * @return the movementsWithBooksService
   */
  @Override
  public MovementsWithBooksServiceInterface getMovementsWithBooksService() {
    if(movementsWithBooksServiceInterface == null){
      movementsWithBooksServiceInterface = new MovementsWithBooksService(this);
      motherBusinessServices.add(movementsWithBooksServiceInterface);
    }
    return movementsWithBooksServiceInterface;
  }

  /**
   * @return the movementsWithMovementsService
   */
  @Override
  public MovementsWithMovementsServiceInterface getMovementsWithMovementsService() {
    if(movementsWithMovementsServiceInterface == null){
      movementsWithMovementsServiceInterface = new MovementsWithMovementsService(this);
      motherBusinessServices.add(movementsWithMovementsServiceInterface);
    }
    return movementsWithMovementsServiceInterface;
  }

  /**
   * @return the movementByIdMovementService
   */
  @Override
  public MovementByIdMovementServiceInterface getMovementByIdMovementService() {
    if(movementByIdMovementServiceInterface == null){
      movementByIdMovementServiceInterface = new MovementByIdMovementService(this);
      motherBusinessServices.add(movementByIdMovementServiceInterface);
    }
    return movementByIdMovementServiceInterface;
  }

  /**
   * @return the movementByNameService
   */
  @Override
  public MovementByNameServiceInterface getMovementByNameService() {
    if(movementByNameServiceInterface == null){
      movementByNameServiceInterface = new MovementByNameService(this);
      motherBusinessServices.add(movementByNameServiceInterface);
    }
    return movementByNameServiceInterface;
  }

  /**
   * @return the movementsByIdParentService
   */
  @Override
  public MovementsByIdParentServiceInterface getMovementsByIdParentService() {
    if(movementsByIdParentServiceInterface == null){
      movementsByIdParentServiceInterface = new MovementsByIdParentService(this);
      motherBusinessServices.add(movementsByIdParentServiceInterface);
    }
    return movementsByIdParentServiceInterface;
  }

  /***********************************************************
   * Pictures Services Getters
   **********************************************************/

  /**
   * @return the picturesService
   */
  @Override
  public PicturesAllServiceInterface getPicturesAllService() {
    if(picturesAllServiceInterface == null){
      picturesAllServiceInterface = new PicturesAllService(this);
      motherBusinessServices.add(picturesAllServiceInterface);
    }
    return picturesAllServiceInterface;
  }

  /**
   * @return the pictureByIdPictureService
   */
  @Override
  public PictureByIdPictureServiceInterface getPicturesByIdPictureService() {
    if(pictureByIdPictureServiceInterface == null){
      pictureByIdPictureServiceInterface = new PictureByIdPictureService(this);
      motherBusinessServices.add(pictureByIdPictureServiceInterface);
    }
    return pictureByIdPictureServiceInterface;
  }

  /**
   * @return the picturesByNameSmallService
   */
  @Override
  public PicturesByNameSmallServiceInterface getPicturesByNameSmallService() {
    if(picturesByNameSmallServiceInterface == null){
      picturesByNameSmallServiceInterface = new PicturesByNameSmallService(this);
      motherBusinessServices.add(picturesByNameSmallServiceInterface);
    }
    return picturesByNameSmallServiceInterface;
  }

  /**
   * @return the picturesByIdAuthorService
   */
  @Override
  public PicturesByIdAuthorServiceInterface getPicturesByIdAuthorService() {
    if(picturesByIdAuthorServiceInterface == null){
      picturesByIdAuthorServiceInterface = new PicturesByIdAuthorService(this);
      motherBusinessServices.add(picturesByIdAuthorServiceInterface);
    }
    return picturesByIdAuthorServiceInterface;
  }

  /**
   * @return the picturesByIdBookService
   */
  @Override
  public PicturesByIdBookServiceInterface getPicturesByIdBookService() {
    if(picturesByIdBookServiceInterface == null){
      picturesByIdBookServiceInterface = new PicturesByIdBookService(this);
      motherBusinessServices.add(picturesByIdBookServiceInterface);
    }
    return picturesByIdBookServiceInterface;
  }

  /**
   * @return the picturesByIdMovementService
   */
  @Override
  public PicturesByIdMovementServiceInterface getPicturesByIdMovementService() {
    if(picturesByIdMovementServiceInterface == null){
      picturesByIdMovementServiceInterface = new PicturesByIdMovementService(this);
      motherBusinessServices.add(picturesByIdMovementServiceInterface);
    }
    return picturesByIdMovementServiceInterface;
  }

  /**
   * @return the picturesByIdThemeService
   */
  @Override
  public PicturesByIdThemeServiceInterface getPicturesByIdThemeService() {
    if(picturesByIdThemeServiceInterface == null){
      picturesByIdThemeServiceInterface = new PicturesByIdThemeService(this);
      motherBusinessServices.add(picturesByIdThemeServiceInterface);
    }
    return picturesByIdThemeServiceInterface;
  }

  @Override
  public PicturesForCarouselInterface getPicturesForCarouselService() {
    if(picturesForCarouselInterface == null){
      picturesForCarouselInterface = new PicturesForCarousel(this);
      motherBusinessServices.add(picturesForCarouselInterface);
    }
    return picturesForCarouselInterface;
  }

  /***********************************************************
   * Presentations Services Getters
   **********************************************************/

  /**
   * @return the presentationsService
   */
  @Override
  public PresentationsAllServiceInterface getPresentationsAllService() {
    if(presentationsAllServiceInterface == null){
      presentationsAllServiceInterface = new PresentationsAllService(this);
      motherBusinessServices.add(presentationsAllServiceInterface);
    }
    return presentationsAllServiceInterface;
  }

  /**
   * @return the presentationByIdPresentationService
   */
  @Override
  public PresentationByIdPresentationServiceInterface getPresentationByIdPresentationService() {
    if(presentationByIdPresentationServiceInterface == null){
      presentationByIdPresentationServiceInterface = new PresentationByIdPresentationService(this);
      motherBusinessServices.add(presentationByIdPresentationServiceInterface);
    }
    return presentationByIdPresentationServiceInterface;
  }

  /**
   * @return the presentationByIdAuthorService
   */
  @Override
  public PresentationByIdAuthorServiceInterface getPresentationByIdAuthorService() {
    if(presentationByIdAuthorServiceInterface == null){
      presentationByIdAuthorServiceInterface = new PresentationByIdAuthorService(this);
      motherBusinessServices.add(presentationByIdAuthorServiceInterface);
    }
    return presentationByIdAuthorServiceInterface;
  }

  /**
   * @return the presentationByIdBookService
   */
  @Override
  public PresentationByIdBookServiceInterface getPresentationByIdBookService() {
    if(presentationByIdBookServiceInterface == null){
      presentationByIdBookServiceInterface = new PresentationByIdBookService(this);
      motherBusinessServices.add(presentationByIdBookServiceInterface);
    }
    return presentationByIdBookServiceInterface;
  }

  /**
   * @return the PresentationByIdMovementService
   */
  @Override
  public PresentationByIdMovementServiceInterface getPresentationByIdMovementServvice() {
    if(presentationByIdMovementServiceInterface == null){
      presentationByIdMovementServiceInterface = new PresentationByIdMovementService(this);
      motherBusinessServices.add(presentationByIdMovementServiceInterface);
    }
    return presentationByIdMovementServiceInterface;
  }

  /***********************************************************
   * Quotes Services Getters
   **********************************************************/

  /**
   * @return the quotesService
   */
  @Override
  public QuotesAllServiceInterface getQuotesAllService() {
    if(quotesAllServiceInterface == null){
      quotesAllServiceInterface = new QuotesAllService(this);
      motherBusinessServices.add(quotesAllServiceInterface);
    }
    return quotesAllServiceInterface;
  }

  /**
   * @return the quoteByIdQuoteService
   */
  @Override
  public QuoteByIdQuoteServiceInterface getQuoteByIdQuoteService() {
    if(quoteByIdQuoteServiceInterface == null){
      quoteByIdQuoteServiceInterface = new QuoteByIdQuoteService(this);
      motherBusinessServices.add(quoteByIdQuoteServiceInterface);
    }
    return quoteByIdQuoteServiceInterface;
  }

  @Override
  public QuotesByAuthorServiceInterface getQuotesByAuthorService() {
    if(quotesByAuthorServiceInterface == null){
      quotesByAuthorServiceInterface = new QuotesByAuthorService(this);
      motherBusinessServices.add(quotesByAuthorServiceInterface);
    }
    return quotesByAuthorServiceInterface;
  }

  /**
   * @return the quotesByIdAuthorService
   */
  @Override
  public QuotesByIdAuthorServiceInterface getQuotesByIdAuthorService() {
    if(quotesByIdAuthorServiceInterface == null){
      quotesByIdAuthorServiceInterface = new QuotesByIdAuthorService(this);
      motherBusinessServices.add(quotesByIdAuthorServiceInterface);
    }
    return quotesByIdAuthorServiceInterface;
  }

  @Override
  public QuotesByBookServiceInterface getQuotesByBookService() {
    if(quotesByBookServiceInterface == null){
      quotesByBookServiceInterface = new QuotesByBookService(this);
      motherBusinessServices.add(quotesByBookServiceInterface);
    }
    return quotesByBookServiceInterface;  }

  /**
   * @return the quotesByIdBookService
   */
  @Override
  public QuotesByIdBookServiceInterface getQuotesByIdBookService() {
    if(quotesByIdBookServiceInterface == null){
      quotesByIdBookServiceInterface = new QuotesByIdBookService(this);
      motherBusinessServices.add(quotesByIdBookServiceInterface);
    }
    return quotesByIdBookServiceInterface;
  }

  @Override
  public QuotesByThemeServiceInterface getQuotesByThemeService() {
    if(quotesByThemeServiceInterface == null){
      quotesByThemeServiceInterface = new QuotesByThemeService(this);
      motherBusinessServices.add(quotesByThemeServiceInterface);
    }
    return quotesByThemeServiceInterface;  }

  @Override
  public QuotesByMovementServiceInterface getQuotesByMovementService() {
    if(quotesByMovementServiceInterface == null){
      quotesByMovementServiceInterface = new QuotesByMovementService(this);
      motherBusinessServices.add(quotesByMovementServiceInterface);
    }
    return quotesByMovementServiceInterface;
  }

  /**
   * @return the quotesByIdThemeService
   */
  @Override
  public QuotesByIdThemeServiceInterface getQuotesByIdThemeService() {
    if(quotesByIdThemeServiceInterface == null){
      quotesByIdThemeServiceInterface = new QuotesByIdThemeService(this);
      motherBusinessServices.add(quotesByIdThemeServiceInterface);
    }
    return quotesByIdThemeServiceInterface;
  }

  @Override
  public QuotesByIdMovementServiceInterface getQuotesByIdMovementsService() {
    if(quotesByIdMovementServiceInterface == null){
      quotesByIdMovementServiceInterface = new QuotesByIdMovementService(this);
      motherBusinessServices.add(quotesByIdMovementServiceInterface);
    }
    return quotesByIdMovementServiceInterface;
  }

  @Override
  public UpdateQuoteServiceInterface getUpdateQuoteService() {
    if(updateQuoteServiceInterface == null){
      updateQuoteServiceInterface = new UpdateQuoteService(this);
      motherBusinessServices.add(updateQuoteServiceInterface);
    }
    return updateQuoteServiceInterface;
  }

  @Override
  public FavoritesQuotesServiceInterface getAllFavoritesService() {
    if(favoritesQuotesServiceInterface == null){
      favoritesQuotesServiceInterface = new FavoritesQuotesService(this);
      motherBusinessServices.add(favoritesQuotesServiceInterface);
    }
    return favoritesQuotesServiceInterface;
  }

  /***********************************************************
   * Themes Services Getters
   **********************************************************/

  /**
   * @return the themesService
   */
  @Override
  public ThemesAllServiceInterface getThemesAllService() {
    if(themesAllServiceInterface == null){
      themesAllServiceInterface = new ThemesAllService(this);
      motherBusinessServices.add(themesAllServiceInterface);
    }
    return themesAllServiceInterface;
  }

  /**
   *
   * @return the themeByNameService
   */
  @Override
  public ThemeByNameServiceInterface getThemeByNameService() {
    if(themeByNameServiceInterface == null){
      themeByNameServiceInterface = new ThemeByNameService(this);
      motherBusinessServices.add(themeByNameServiceInterface);
    }
    return themeByNameServiceInterface;
  }

  /**
   * @return the themeByIdThemeService
   */
  @Override
  public ThemeByIdThemeServiceInterface getThemeByIdThemeService() {
    if(themeByIdThemeServiceInterface == null){
      themeByIdThemeServiceInterface = new ThemeByIdThemeService(this);
      motherBusinessServices.add(themeByIdThemeServiceInterface);
    }
    return themeByIdThemeServiceInterface;
  }

  /**
   * @return the themesByIdParentService
   */
  @Override
  public ThemesByIdParentServiceInterface getThemesByIdParentService() {
    if(themesByIdParentServiceInterface == null){
      themesByIdParentServiceInterface = new ThemesByIdParentService(this);
      motherBusinessServices.add(themesByIdParentServiceInterface);
    }
    return themesByIdParentServiceInterface;
  }

  /**
   * @return the themesWithThemesService
   */
  @Override
  public ThemesWithThemesServiceInterface getThemesWithThemesService() {
    if(themesWithThemesServiceInterface == null){
      themesWithThemesServiceInterface = new ThemesWithThemesService(this);
      motherBusinessServices.add(themesWithThemesServiceInterface);
    }
    return themesWithThemesServiceInterface;
  }

  /***********************************************************
   * Urls Services Getters
   **********************************************************/

  /**
   * @return the urlsService
   */
  @Override
  public UrlsAllServiceInterface getUrlsAllService() {
    if(urlsAllServiceInterface == null){
      urlsAllServiceInterface = new UrlsAllService(this);
      motherBusinessServices.add(urlsAllServiceInterface);
    }
    return urlsAllServiceInterface;
  }

  /**
   * @return the urlsByIdUrlService
   */
  @Override
  public UrlByIdUrlServiceInterface getUrlByIdUrlService() {
    if(urlByIdUrlServiceInterface == null){
      urlByIdUrlServiceInterface = new UrlByIdUrlService(this);
      motherBusinessServices.add(urlByIdUrlServiceInterface);
    }
    return urlByIdUrlServiceInterface;
  }

  /**
   * @return the urlsByIdAuthorService
   */
  @Override
  public UrlsByIdAuthorserviceInterface getUrlsByIdAuthorService() {
    if(urlsByIdAuthorserviceInterface == null){
      urlsByIdAuthorserviceInterface = new UrlsByIdAuthorService(this);
      motherBusinessServices.add(urlsByIdAuthorserviceInterface);
    }
    return urlsByIdAuthorserviceInterface;
  }

  /**
   * @return the urlsByIdBookService
   */
  @Override
  public UrlsByIdBookServiceInterface getUrlsByIdBookService() {
    if(urlsByIdBookServiceInterface == null){
      urlsByIdBookServiceInterface = new UrlsByIdBookService(this);
      motherBusinessServices.add(urlsByIdBookServiceInterface);
    }
    return urlsByIdBookServiceInterface;
  }

  /**
   * @return the urlsByIdMovementService
   */
  @Override
  public UrlsByIdMovementServiceInterface getUrlsByIdMovementService() {
    if(urlsByIdMovementServiceInterface == null){
      urlsByIdMovementServiceInterface = new UrlsByIdMovementService(this);
      motherBusinessServices.add(urlsByIdMovementServiceInterface);
    }
    return urlsByIdMovementServiceInterface;
  }

  /**
   * @return the urlsByIdSourceAndSourceTypeService
   */
  @Override
  public UrlsByIdSourceServiceInterface getUrlsByIdSourceService() {
    if(urlsByIdSourceServiceInterface == null){
      urlsByIdSourceServiceInterface = new UrlsByIdSourceService(this);
      motherBusinessServices.add(urlsByIdSourceServiceInterface);
    }
    return urlsByIdSourceServiceInterface;
  }

  /**
   * @return the urlsBySourceTypeService
   */
  @Override
  public UrlsBySourceTypeServiceInterface getUrlsBySourceTypeService() {
    if(urlsBySourceTypeServiceInterface == null){
      urlsBySourceTypeServiceInterface = new UrlsBySourceTypeService(this);
      motherBusinessServices.add(urlsBySourceTypeServiceInterface);
    }
    return urlsBySourceTypeServiceInterface;
  }

  /******************************************************************************************/
  /** Pool Executor for Threads that has to cancelled when the application shutdown**/
  /******************************************************************************************/
  /**
   * The pool executor to use for all cancellable thread and Threads that has to cancelled when the application shutdown
   */
  private ExecutorService cancelableThreadsExecutor = null;

  /**
   * @return the cancelableThreadsExceutor
   */
  @Override
  public final ExecutorService getCancelableThreadsExecutor() {
    if (cancelableThreadsExecutor == null) {
      cancelableThreadsExecutor = Executors.newFixedThreadPool(12, new CancelableThreadFactory());
    }
    return cancelableThreadsExecutor;
  }

  /** * And its associated factory */
  private class CancelableThreadFactory implements ThreadFactory {
    public Thread newThread(@NonNull Runnable r) {
      Thread t = new Thread(r);
      t.setName("CancelableThread"+((int)(Math.random()*1000)));
      return t;
    }
  }

  /**
   * Kill all running Thread and destroy then all
   * Kill the cancelableThreadsExceutor
   */
  private void killCancelableThreadExecutor() {
    if (cancelableThreadsExecutor != null) {
      cancelableThreadsExecutor.shutdownNow(); // Disable new tasks from being submitted and kill every running task using Thread.interrupt
      try {// as long as your threads hasn't finished
        while (!cancelableThreadsExecutor.isTerminated()) {
          // Wait a while for existing tasks to terminate
          if (!cancelableThreadsExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
            // Cancel currently executing tasks
            cancelableThreadsExecutor.shutdownNow();
            //Log.e("MyApp", "Probably a memory leak here");
          }
        }
      } catch (InterruptedException ie) {
        // (Re-)Cancel if current thread also interrupted
        cancelableThreadsExecutor.shutdownNow();
        cancelableThreadsExecutor=null;
        //Log.e("MyApp", "Probably a memory leak here too");
      }
    }
    cancelableThreadsExecutor=null;
  }
  /******************************************************************************************/
  /** Pool Executor for Threads that has to finish they threatment when the application shutdown**/
  /******************************************************************************************/
  /**
   * The pool executor to use for all cancellable thread and Threads that has to cancelled when the application shutdown
   */
  private ExecutorService keepAliveThreadsExceutor = null;

  /**
   * @return the cancelableThreadsExceutor
   */
  @Override
  public final ExecutorService getKeepAliveThreadsExecutor() {
    if (keepAliveThreadsExceutor == null) {
      keepAliveThreadsExceutor = Executors.newFixedThreadPool(12, new BackgroundThreadFactory());
    }
    return keepAliveThreadsExceutor;
  }
  /** * And its associated factory */
  private class BackgroundThreadFactory implements ThreadFactory {
    public Thread newThread(@NonNull Runnable r) {
      Thread t = new Thread(r);
      t.setName("KeepAlive"+((int)(Math.random()*1000)));
      return t;
    }
  }

  /**
   * Kill all running Thread and destroy then all
   * Kill the cancelableThreadsExceutor
   */
  private void killKeepAliveThreadExecutor() {
    if (keepAliveThreadsExceutor != null) {
      keepAliveThreadsExceutor.shutdown(); // Disable new tasks from being submitted
      try {// as long as your threads hasn't finished
        while (!keepAliveThreadsExceutor.isTerminated()) {
          // Wait a while for existing tasks to terminate
          if (!keepAliveThreadsExceutor.awaitTermination(5, TimeUnit.SECONDS)) {
            // Cancel currently executing tasks
            keepAliveThreadsExceutor.shutdown();
            //Log.e("MyApp", "Probably a memory leak here");
          }
        }
      } catch (InterruptedException ie) {
        // (Re-)Cancel if current thread also interrupted
        keepAliveThreadsExceutor.shutdownNow();
        keepAliveThreadsExceutor=null;
        //Log.e("MyApp", "Probably a memory leak here too");
      }
    }
    keepAliveThreadsExceutor=null;
  }

}
