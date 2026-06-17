package com.sc.fr.onelittleangel.layers.dao;

import com.sc.fr.onelittleangel.layers.dao.accounts.AccountsDao;
import com.sc.fr.onelittleangel.layers.dao.accounts.AccountsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.authors.AuthorsDao;
import com.sc.fr.onelittleangel.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.books.BooksDao;
import com.sc.fr.onelittleangel.layers.dao.books.BooksDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.centuries.CenturiesDao;
import com.sc.fr.onelittleangel.layers.dao.centuries.CenturiesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.movements.MovementsDao;
import com.sc.fr.onelittleangel.layers.dao.movements.MovementsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.pictures.PicturesDao;
import com.sc.fr.onelittleangel.layers.dao.pictures.PicturesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.presentations.PresentationsDao;
import com.sc.fr.onelittleangel.layers.dao.presentations.PresentationsDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.quotes.QuotesDao;
import com.sc.fr.onelittleangel.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.themes.ThemesDao;
import com.sc.fr.onelittleangel.layers.dao.themes.ThemesDaoInterface;
import com.sc.fr.onelittleangel.layers.dao.urls.UrlsDao;
import com.sc.fr.onelittleangel.layers.dao.urls.UrlsDaoInterface;

public class DaoManager {
  /***********************************************************
   *  Managing LifeCycle
   **********************************************************/

  private static DaoManager INSTANCE=null;
  public static DaoManager getInstance() {
    if(INSTANCE==null){
      INSTANCE=new DaoManager();
    }
    return INSTANCE;
  }

//  private DaoManager(){};
  /**
   * To be called when the application died
   * This is the main problem of the singleton pattern
   * They live as long as the process live
   */
  public void releaseMemory(){
    INSTANCE=null;
    accountsDaoInterface = null;
    authorsDaoInterface = null;
    booksDaoInterface = null;
    centuriesDaoInterface = null;
    movementsDaoInterface = null;
    picturesDaoInterface = null;
    presentationsDaoInterface = null;
    quotesDaoInterface = null;
    themesDaoInterface = null;
    urlsDaoInterface = null;
  }
  /***********************************************************
   *  Attributes
   **********************************************************/

  /**
   * The AccountsDao
   */
  private AccountsDaoInterface accountsDaoInterface = null;

  /**
   * The AuthorsDao
   */
  private AuthorsDaoInterface authorsDaoInterface =null;

  /**
   * The BooksDao
   */
  private BooksDaoInterface booksDaoInterface =null;

  /**
   * The CenturiesDao
   */
  private CenturiesDaoInterface centuriesDaoInterface =null;

  /**
   * The MovementsDao
   */
  private MovementsDaoInterface movementsDaoInterface =null;

  /**
   * The PicturesDao
   */
  private PicturesDaoInterface picturesDaoInterface =null;

  /**
   * The PresentationsDao
   */
  private PresentationsDaoInterface presentationsDaoInterface =null;

  /**
   * The QuotesDao
   */
  private QuotesDaoInterface quotesDaoInterface =null;

  /**
   * The ThemesDao
   */
  private ThemesDaoInterface themesDaoInterface =null;

  /**
   * The UrlsDao
   */
  private UrlsDaoInterface urlsDaoInterface =null;

  /***********************************************************
   *  Getters/Setters And
   **********************************************************/

  public AccountsDaoInterface getAccountsDao() {
    if(accountsDaoInterface == null){
      accountsDaoInterface = new AccountsDao(this);
    }
    return accountsDaoInterface;
  }

  public AuthorsDaoInterface getAuthorsDao() {
    if(authorsDaoInterface == null){
      authorsDaoInterface = new AuthorsDao(this);
    }
    return authorsDaoInterface;
  }

  public BooksDaoInterface getBooksDao() {
    if(booksDaoInterface == null){
      booksDaoInterface = new BooksDao(this);
    }
    return booksDaoInterface;
  }

  public CenturiesDaoInterface getCenturiesDao() {
    if(centuriesDaoInterface == null){
      centuriesDaoInterface = new CenturiesDao(this);
    }
    return centuriesDaoInterface;
  }

  public MovementsDaoInterface getMovementsDao() {
    if(movementsDaoInterface == null){
      movementsDaoInterface = new MovementsDao(this);
    }
    return movementsDaoInterface;
  }

  public PicturesDaoInterface getPicturesDao() {
    if(picturesDaoInterface == null){
      picturesDaoInterface = new PicturesDao(this);
    }
    return picturesDaoInterface;
  }

  public PresentationsDaoInterface getPresentationsDao() {
    if(presentationsDaoInterface == null){
      presentationsDaoInterface = new PresentationsDao(this);
    }
    return presentationsDaoInterface;
  }

  public QuotesDaoInterface getQuotesDao() {
    if(quotesDaoInterface == null){
      quotesDaoInterface = new QuotesDao(this);
    }
    return quotesDaoInterface;
  }

  public ThemesDaoInterface getThemesDao() {
    if(themesDaoInterface == null){
      themesDaoInterface = new ThemesDao(this);
    }
    return themesDaoInterface;
  }

  public UrlsDaoInterface getUrlsDao() {
    if(urlsDaoInterface == null){
      urlsDaoInterface = new UrlsDao(this);
    }
    return urlsDaoInterface;
  }
}
