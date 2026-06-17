/**
 * <ul>
 * <li>DaoManager</li>
 * <li>com.android2ee.formation.restservice.forecastyahoo.withlibs.dao</li>
 * <li>09/04/2016</li>
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

package com.sc.fr.onelittleangel.injector.dao;

//import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.city.CityDaoIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.cityforecast.CityForecastDaoIntf;
//import com.android2ee.formation.restservice.forecastyahoo.withlibs.dao.weather.WeatherDaoIntf;

import com.sc.fr.onelittleangel.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.books.BooksDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.movements.MovementsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.pictures.PicturesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.urls.UrlsDaoInterface;

/**
 * Created by Mathias Seguy - Android2EE on 09/04/2016.
 * This class aims to centralize the access to the Dao classes
 * This is usefull for injection (for the test)
 */
public class DaoManagerMocked {
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    private static DaoManagerMocked INSTANCE=null;
    public static DaoManagerMocked getInstance() {
        if(INSTANCE==null){
            INSTANCE=new DaoManagerMocked();
        }
        return INSTANCE;
    }
    private DaoManagerMocked(){};
    /**
     * To be called when the application died
     * This is the main problem of the singleton pattern
     * They live as long as the process live
     */
    public void releaseMemory(){
        INSTANCE=null;
        authorsDao = null;
        booksDao = null;
        centuriesDao = null;
        movementsDao = null;
        picturesDao = null;
        presentationsDao = null;
        quotesDao = null;
        themesDao = null;
        urlsDao = null;
        
    }
    /***********************************************************
    *  Attributes
    **********************************************************/
    
    /**
     * The Authors Dao
     */
    AuthorsDaoInterface authorsDao = null;

    /**
     * The Books Dao
     */
    BooksDaoInterface booksDao = null;

    /**
     * The Centuries Dao
     */
    CenturiesDaoInterface centuriesDao = null;

    /**
     * The Movements Serivce
     */
    MovementsDaoInterface movementsDao = null;

    /**
     * The Pictures Dao
     */
    PicturesDaoInterface picturesDao = null;

    /**
     * The Presentations Dao
     */
    PresentationsDaoInterface presentationsDao = null;

    /**
     * The Quotes Dao
     */
    QuotesDaoInterface quotesDao = null;

    /**
     * The Themes Dao
     */
    ThemesDaoInterface themesDao = null;

    /**
     * The Urls Dao
     */
    UrlsDaoInterface urlsDao = null;

    /***********************************************************
    *  Getters/Setters And
    **********************************************************/
    public AuthorsDaoInterface getAuthorsDao() {
        if(authorsDao == null){
            authorsDao = new AuthorsDaoMocked();
        }
        return authorsDao;
    }

    public BooksDaoInterface getBooksDao() {
        if(booksDao == null){
            booksDao = new BooksDaoMocked();
        }
        return booksDao;
    }

    public CenturiesDaoInterface getCenturiesDao() {
        if(centuriesDao == null){
            centuriesDao = new CenturiesDaoMocked();
        }
        return centuriesDao;
    }

    public MovementsDaoInterface getMovementsDao() {
        if(movementsDao == null){
            movementsDao = new MovementsDaoMocked();
        }
        return movementsDao;
    }

    public PicturesDaoInterface getPicturesDao() {
        if(picturesDao == null){
            picturesDao = new PicturesDaoMocked();
        }
        return picturesDao;
    }

    public PresentationsDaoInterface getPresentationsDao() {
        if(presentationsDao == null){
            presentationsDao = new PresentationsDaoMocked();
        }
        return presentationsDao;
    }

    public QuotesDaoInterface getQuotesDao() {
        if(quotesDao == null){
            quotesDao = new QuotesDaoMocked();
        }
        return quotesDao;
    }

    public ThemesDaoInterface getThemesDao() {
        if(themesDao == null){
            themesDao = new ThemesDaoMocked();
        }
        return themesDao;
    }

    public UrlsDaoInterface getUrlsDao() {
        if(urlsDao == null){
            urlsDao = new UrlsDaoMocked();
        }
        return urlsDao;
    }
}
