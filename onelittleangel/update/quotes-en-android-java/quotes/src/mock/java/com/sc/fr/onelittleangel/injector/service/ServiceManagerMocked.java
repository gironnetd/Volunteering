/**
 * <ul>
 * <li>ServiceManager</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.service</li>
 * <li>25/02/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.sc.fr.onelittleangel.injector.service;

/**
 * Created by Mathias Seguy - Android2EE on 25/02/2016.
 */

import android.util.Log;

//import com.android2ee.formation.restservice.forecastyahoo.withlibs.MyApplication;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.MotherBusinessServiceIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.ServiceManagerIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.city.CityServiceIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.ForecastServiceIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.WeatherDataUpdaterIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.service.weather.WeatherServiceIntf;
import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.onelittleangel.layers.service.ServiceManagerInterface;
import com.sc.fr.onelittleangel.layers.service.accounts.interfaces.FaceBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.accounts.interfaces.GooglePlusServiceInterface;
import com.sc.fr.onelittleangel.layers.service.accounts.interfaces.MailServiceInterface;
import com.sc.fr.onelittleangel.layers.service.accounts.interfaces.TwitterServiceInterface;
import com.sc.fr.onelittleangel.layers.service.authors.interfaces.AuthorByIdAuthorServiceInterface;
import com.sc.fr.onelittleangel.layers.service.authors.interfaces.AuthorByNameServiceInterface;
import com.sc.fr.onelittleangel.layers.service.authors.interfaces.AuthorsAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.authors.interfaces.AuthorsByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.authors.interfaces.AuthorsByIdThemeServiceInterface;
import com.sc.fr.onelittleangel.layers.service.authors.services.AuthorsAllService;
import com.sc.fr.onelittleangel.layers.service.books.interfaces.BookByIdBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.books.interfaces.BookByNameServiceInterface;
import com.sc.fr.onelittleangel.layers.service.books.interfaces.BooksAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.books.interfaces.BooksByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.books.interfaces.BooksByIdThemeServiceInterface;
import com.sc.fr.onelittleangel.layers.service.books.services.BooksAllService;
import com.sc.fr.onelittleangel.layers.service.centuries.interfaces.CenturiesAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.centuries.interfaces.CenturyByIdAuthorServiceInterface;
import com.sc.fr.onelittleangel.layers.service.centuries.interfaces.CenturyByIdBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.centuries.interfaces.CenturyByIdCenturyServiceInterface;
import com.sc.fr.onelittleangel.layers.service.centuries.interfaces.CenturyByNameServiceInterface;
import com.sc.fr.onelittleangel.layers.service.centuries.services.CenturiesAllService;
import com.sc.fr.onelittleangel.layers.service.homepage.interfaces.HomePageServiceInterface;
import com.sc.fr.onelittleangel.layers.service.mails.interfaces.MailsServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementByNameServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementsAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementsByIdParentServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementsWithAuthorsServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementsWithBooksServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.interfaces.MovementsWithMovementsServiceInterface;
import com.sc.fr.onelittleangel.layers.service.movements.services.MovementsAllService;
import com.sc.fr.onelittleangel.layers.service.notifications.biographyofday.interfaces.BiographyNotificationIntentServiceInterface;
import com.sc.fr.onelittleangel.layers.service.notifications.pictureofday.interfaces.PictureNotificationIntentServiceInterface;
import com.sc.fr.onelittleangel.layers.service.notifications.quoteofday.interfaces.QuoteNotificationIntentServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PictureByIdPictureServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PicturesByNameSmallServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PicturesAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PicturesByIdAuthorServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PicturesByIdBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PicturesByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PicturesByIdThemeServiceInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.interfaces.PicturesForCarouselInterface;
import com.sc.fr.onelittleangel.layers.service.pictures.services.PicturesAllService;
import com.sc.fr.onelittleangel.layers.service.presentations.interfaces.PresentationByIdAuthorServiceInterface;
import com.sc.fr.onelittleangel.layers.service.presentations.interfaces.PresentationByIdBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.presentations.interfaces.PresentationByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.presentations.interfaces.PresentationByIdPresentationServiceInterface;
import com.sc.fr.onelittleangel.layers.service.presentations.interfaces.PresentationsAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.presentations.services.PresentationsAllService;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.FavoritesQuotesServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuoteByIdQuoteServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByAuthorServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByIdAuthorServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByIdBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByIdThemeServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.QuotesByThemeServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.interfaces.UpdateQuoteServiceInterface;
import com.sc.fr.onelittleangel.layers.service.quotes.services.QuotesAllService;
import com.sc.fr.onelittleangel.layers.service.themes.interfaces.ThemeByIdThemeServiceInterface;
import com.sc.fr.onelittleangel.layers.service.themes.interfaces.ThemeByNameServiceInterface;
import com.sc.fr.onelittleangel.layers.service.themes.interfaces.ThemesAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.themes.interfaces.ThemesByIdParentServiceInterface;
import com.sc.fr.onelittleangel.layers.service.themes.interfaces.ThemesWithThemesServiceInterface;
import com.sc.fr.onelittleangel.layers.service.themes.services.ThemesAllService;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlByIdUrlServiceInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsAllServiceInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsByIdAuthorserviceInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsByIdBookServiceInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsByIdMovementServiceInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsByIdSourceServiceInterface;
import com.sc.fr.onelittleangel.layers.service.urls.interfaces.UrlsBySourceTypeServiceInterface;
import com.sc.fr.onelittleangel.layers.service.urls.services.UrlsAllService;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals This class aims to manage services
 * It can be access only through MyApplication object
 * by calling MyApplication.getServiceManager()
 */
public class ServiceManagerMocked implements ServiceManagerInterface {

    /***********************************************************
     *  Services List
     **********************************************************/
    ArrayList<MotherBusinessServiceInterface> motherBusinessServices;
    /**
     * The Authors Service
     */
    AuthorsAllServiceInterface authorsAllServiceInterface = null;

    /**
     * The Books Service
     */
    BooksAllServiceInterface booksAllServiceInterface = null;

    /**
     * The Centuries Service
     */
    CenturiesAllServiceInterface centuriesAllServiceInterface = null;

    /**
     * The Movements Serivce
     */
    MovementsAllServiceInterface movementsAllServiceInterface = null;

    /**
     * The Pictures Service
     */
    PicturesAllServiceInterface picturesAllServiceInterface = null;

    /**
     * The Presentations Service
     */
    PresentationsAllServiceInterface presentationsAllServiceInterface = null;

    /**
     * The Quotes Service
     */
    QuotesAllServiceInterface quotesAllServiceInterface = null;

    /**
     * The Themes Service
     */
    ThemesAllServiceInterface themesAllServiceInterface = null;

    /**
     * The Urls Service
     */
    UrlsAllServiceInterface urlsAllServiceInterface = null;
     /***********************************************************
      *  Constructor and destructor
      **********************************************************/
    /**
     * Insure only the Application object can instantiate once this object
     * If not the case throw an Exception
     */
    public ServiceManagerMocked(OnelittleAngelApplication application) {
        motherBusinessServices=new ArrayList<MotherBusinessServiceInterface>();
    }
    /**
     * To be called when you need to release all the services
     * Is managed by the MyApplication object in fact
     */
    public void unbindAndDie() {
        Log.e("ServiceManager", "UnbindAndDie is called");
        //kill your thread
        if (cancelableThreadsExecutor != null) {
            killCancelableThreadExecutor();
        }
        if (keepAliveThreadsExceutor != null) {
            killKeepAliveThreadExecutor();
        }
        //kill your business services
        for(MotherBusinessServiceInterface service:motherBusinessServices){
            //TODO
            //service.onDestroy(this);
        }
        //release your pointer
        authorsAllServiceInterface = null;
        booksAllServiceInterface = null;
        centuriesAllServiceInterface = null;
        movementsAllServiceInterface = null;
        picturesAllServiceInterface = null;
        presentationsAllServiceInterface = null;
        quotesAllServiceInterface = null;
        themesAllServiceInterface = null;
        urlsAllServiceInterface = null;
    }

    @Override
    public MailsServiceInterface getMailsService() {
        return null;
    }

    @Override
    public HomePageServiceInterface getHomePageService() {
        return null;
    }

    @Override
    public BiographyNotificationIntentServiceInterface getBiographyNotificationService() {
        return null;
    }

    @Override
    public PictureNotificationIntentServiceInterface getPictureNotificationService() {
        return null;
    }

    @Override
    public QuoteNotificationIntentServiceInterface getQuoteNotificationService() {
        return null;
    }

    @Override
    public TwitterServiceInterface getTwitterService() {
        return null;
    }

    @Override
    public MailServiceInterface getMailService() {
        return null;
    }

    @Override
    public FaceBookServiceInterface getFaceBookService() {
        return null;
    }

    @Override
    public GooglePlusServiceInterface getGooglePlusService() {
        return null;
    }

    /***********************************************************
     *  Services Getters
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
        return null;
    }

    /**
     * @return the authorByNameService
     */
    @Override
    public AuthorByNameServiceInterface getAuthorByNameService() {
        return null;
    }

    /**
     * @return the authorsByIdMovementService
     */
    @Override
    public AuthorsByIdMovementServiceInterface getAuthorsByIdMovementService() {
        return null;
    }

    /**
     * @return the authorsByIdThemeService
     */
    @Override
    public AuthorsByIdThemeServiceInterface getAuthorsByIdThemeService() {
        return null;
    }

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
        return null;
    }

    /**
     * @return the bookByNameService
     */
    @Override
    public BookByNameServiceInterface getBookByNameService() {
        return null;
    }

    /**
     * @return the booksByIdMovementService
     */
    @Override
    public BooksByIdMovementServiceInterface getBooksByIdMovementService() {
        return null;
    }

    /**
     * @return the booksByIdThemeService
     */
    @Override
    public BooksByIdThemeServiceInterface getBooksByIdThemeService() {
        return null;
    }

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
        return null;
    }

    /**
     * @return the centuryByIdBookService
     */
    @Override
    public CenturyByIdBookServiceInterface getCenturyByIdBookService() {
        return null;
    }

    /**
     * @return the centuryByIdCenturyService
     */
    @Override
    public CenturyByIdCenturyServiceInterface getCenturyByIdCenturyService() {
        return null;
    }

    /**
     * @return the centuryByNameService
     */
    @Override
    public CenturyByNameServiceInterface getCenturyByNameService() {
        return null;
    }

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
        return null;
    }

    /**
     * @return the movementsWithBooksService
     */
    @Override
    public MovementsWithBooksServiceInterface getMovementsWithBooksService() {
        return null;
    }

    /**
     * @return the movementsWithMovementsService
     */
    @Override
    public MovementsWithMovementsServiceInterface getMovementsWithMovementsService() {
        return null;
    }

    /**
     * @return the movementByIdMovementService
     */
    @Override
    public MovementByIdMovementServiceInterface getMovementByIdMovementService() {
        return null;
    }

    /**
     * @return the movementByNameService
     */
    @Override
    public MovementByNameServiceInterface getMovementByNameService() {
        return null;
    }

    /**
     * @return the movementsByIdParentService
     */
    @Override
    public MovementsByIdParentServiceInterface getMovementsByIdParentService() {
        return null;
    }

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
        return null;
    }

    /**
     * @return the pictureByNameSmallService
     */
    @Override
    public PicturesByNameSmallServiceInterface getPicturesByNameSmallService() {
        return null;
    }

    /**
     * @return the picturesByIdAuthorService
     */
    @Override
    public PicturesByIdAuthorServiceInterface getPicturesByIdAuthorService() {
        return null;
    }

    /**
     * @return the picturesByIdBookService
     */
    @Override
    public PicturesByIdBookServiceInterface getPicturesByIdBookService() {
        return null;
    }

    /**
     * @return the picturesByIdMovementService
     */
    @Override
    public PicturesByIdMovementServiceInterface getPicturesByIdMovementService() {
        return null;
    }

    /**
     * @return the picturesByIdThemeService
     */
    @Override
    public PicturesByIdThemeServiceInterface getPicturesByIdThemeService() {
        return null;
    }

    @Override
    public PicturesForCarouselInterface getPicturesForCarouselService() {
        return null;
    }

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
        return null;
    }

    /**
     * @return the presentationByIdAuthorService
     */
    @Override
    public PresentationByIdAuthorServiceInterface getPresentationByIdAuthorService() {
        return null;
    }

    /**
     * @return the presentationByIdBookService
     */
    @Override
    public PresentationByIdBookServiceInterface getPresentationByIdBookService() {
        return null;
    }

    /**
     * @return the PresentationByIdMovementService
     */
    @Override
    public PresentationByIdMovementServiceInterface getPresentationByIdMovementServvice() {
        return null;
    }

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
        return null;
    }

    @Override
    public QuotesByAuthorServiceInterface getQuotesByAuthorService() {
        return null;
    }

    /**
     * @return the quotesByIdAuthorService
     */
    @Override
    public QuotesByIdAuthorServiceInterface getQuotesByIdAuthorService() {
        return null;
    }

    @Override
    public QuotesByBookServiceInterface getQuotesByBookService() {
        return null;
    }

    /**
     * @return the quotesByIdBookService
     */
    @Override
    public QuotesByIdBookServiceInterface getQuotesByIdBookService() {
        return null;
    }

    @Override
    public QuotesByThemeServiceInterface getQuotesByThemeService() {
        return null;
    }

    @Override
    public QuotesByMovementServiceInterface getQuotesByMovementService() {
        return null;
    }

    /**
     * @return the quotesByIdThemeService
     */
    @Override
    public QuotesByIdThemeServiceInterface getQuotesByIdThemeService() {
        return null;
    }

    @Override
    public QuotesByIdMovementServiceInterface getQuotesByIdMovementsService() {
        return null;
    }

    @Override
    public UpdateQuoteServiceInterface getUpdateQuoteService() {
        return null;
    }

    @Override
    public FavoritesQuotesServiceInterface getAllFavoritesService() {
        return null;
    }

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

    @Override
    public ThemeByNameServiceInterface getThemeByNameService() {
        return null;
    }

    /**
     * @return the themeByIdThemeService
     */
    @Override
    public ThemeByIdThemeServiceInterface getThemeByIdThemeService() {
        return null;
    }

    /**
     * @return the themesByIdParentService
     */
    @Override
    public ThemesByIdParentServiceInterface getThemesByIdParentService() {
        return null;
    }

    /**
     * @return the themesWithThemesService
     */
    @Override
    public ThemesWithThemesServiceInterface getThemesWithThemesService() {
        return null;
    }

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
        return null;
    }

    /**
     * @return the urlsByIdAuthorService
     */
    @Override
    public UrlsByIdAuthorserviceInterface getUrlsByIdAuthorService() {
        return null;
    }

    /**
     * @return the urlsByIdBookService
     */
    @Override
    public UrlsByIdBookServiceInterface getUrlsByIdBookService() {
        return null;
    }

    /**
     * @return the urlsByIdMovementService
     */
    @Override
    public UrlsByIdMovementServiceInterface getUrlsByIdMovementService() {
        return null;
    }

    /**
     * @return the urlsByIdSourceAndSourceTypeService
     */
    @Override
    public UrlsByIdSourceServiceInterface getUrlsByIdSourceService() {
        return null;
    }

    /**
     * @return the urlsBySourceTypeService
     */
    @Override
    public UrlsBySourceTypeServiceInterface getUrlsBySourceTypeService() {
        return null;
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
    public final ExecutorService getCancelableThreadsExecutor() {
        if (cancelableThreadsExecutor == null) {
            cancelableThreadsExecutor = Executors.newFixedThreadPool(12, new CancelableThreadFactory());
        }
        return cancelableThreadsExecutor;
    }

    /** * And its associated factory */
    private class CancelableThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
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
                        Log.e("MyApp", "Probably a memory leak here");
                    }
                }
            } catch (InterruptedException ie) {
                // (Re-)Cancel if current thread also interrupted
                cancelableThreadsExecutor.shutdownNow();
                cancelableThreadsExecutor=null;
                Log.e("MyApp", "Probably a memory leak here too");
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
    public final ExecutorService getKeepAliveThreadsExecutor() {
        if (keepAliveThreadsExceutor == null) {
            keepAliveThreadsExceutor = Executors.newFixedThreadPool(12, new BackgroundThreadFactory());
        }
        return keepAliveThreadsExceutor;
    }
    /** * And its associated factory */
    private class BackgroundThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
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
                        Log.e("MyApp", "Probably a memory leak here");
                    }
                }
            } catch (InterruptedException ie) {
                // (Re-)Cancel if current thread also interrupted
                keepAliveThreadsExceutor.shutdownNow();
                keepAliveThreadsExceutor=null;
                Log.e("MyApp", "Probably a memory leak here too");
            }
        }
        keepAliveThreadsExceutor=null;
    }
}

