package com.sc.fr.bouddhisme.layers.mvp.tablecontents.fragments.homepage.fragments;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sc.fr.bouddhisme.OnelittleAngelApplication;
import com.sc.fr.bouddhisme.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.fr.bouddhisme.injector.Injector;
import com.sc.fr.bouddhisme.layers.dao.authors.AuthorsDaoInterface;
import com.sc.fr.bouddhisme.layers.dao.books.BooksDaoInterface;
import com.sc.fr.bouddhisme.layers.dao.quotes.QuotesDaoInterface;
import com.sc.fr.bouddhisme.layers.mvp.MotherPresenter;
import com.sc.fr.bouddhisme.layers.mvp.common.utils.Constants;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Author;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Book;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Quote;

import java.io.File;
import java.util.Calendar;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomePagePresenter extends MotherPresenter implements HomePagePresenterInterface {

  private HomePageViewInterface homePageViewInterface = null;
  private Author authorPresentationOfDay = null;
  private Book bookPresentationOfDay = null;
  private Quote quoteOfDay = null;
  private String namePictureOfDay = null;
  private Bitmap pictureOfDay = null;
  private Author authorPictureOfDay = null;
  private Book bookPictureOfDay = null;
  private SharedPreferences settings;
  private boolean isInit = false;

  public HomePagePresenter(HomePageViewInterface homePageViewInterface) {
    this.homePageViewInterface = homePageViewInterface;
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    //editor = settings.edit();
    authorPictureOfDay = new Author();
    bookPictureOfDay = new Book();
  }

  @Override
  public void updateHomePage() {

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    if((settings.getInt(Constants.QUOTE_OF_DAY_ID, -1) == -1 || settings.getInt(Constants.DAY_OF_MONTH, -1) != i && OnelittleAngelApplication.instance.isConnected()) && !settings.getBoolean(Constants.WAS_NOTIFICATION_SENT_ON_DAY, false)) {

      OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadBiographyOfDayAsync(true, false).subscribe(this::initAuthorPresentationOfDay);
    } else {

      if (settings.getBoolean(Constants.WAS_NOTIFICATION_SENT_ON_DAY, false)) {

        if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i && settings.getBoolean(Constants.IS_OPEN_FROM_PICTURE_NOTIFICATION, false)) {

          //settings.edit().putBoolean(Constants.IS_OPEN_FROM_PICTURE_NOTIFICATION, false).commit();

          OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadBiographyOfDayAsync(true, false).subscribe(this::initAuthorPresentationOfDay);

        } else if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i && settings.getBoolean(Constants.IS_OPEN_FROM_BIOGRAPHY_NOTIFICATION, false)) {

          settings.edit().putBoolean(Constants.IS_OPEN_FROM_BIOGRAPHY_NOTIFICATION, false).commit();

          if (settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false)) {
            OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::initAuthorPresentationOfDayFromRealm);
          } else {
            OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::initBookPresentationOfDay);
          }

//          OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadQuoteOfDayAsync(false).subscribe(this::initQuoteOfDay);
//
//          OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync(false).subscribe(new Observer<Bitmap>() {
//
//            @Override
//            public void onSubscribe(Disposable d) {
//              Log.i("test", "onSubscribe");
//            }
//
//            @Override
//            public void onNext(Bitmap bitmap) {
//              Log.i("test", "onNext");
//              initPictureOfDay(bitmap);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//              Log.i("test", "onError");
//            }
//
//            @Override
//            public void onComplete() {
//              Log.i("test", "onComplete");
//              isAllInit();
//            }
//          });

          isAllInit();
        } else if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i && settings.getBoolean(Constants.IS_OPEN_FROM_QUOTE_NOTIFICATION, false)) {

          //settings.edit().putBoolean(Constants.IS_OPEN_FROM_QUOTE_NOTIFICATION, false).commit();

//          if (settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false)) {
//            OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::initAuthorPresentationOfDayFromRealm);
//          } else {
//            OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::initBookPresentationOfDay);
//          }
//
//          OnelittleAngelApplication.instance.getServiceManager().getQuoteByIdQuoteService().loadQuoteByIdQuoteAsync(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1)).subscribe(this::initQuoteOfDayFromRealm);

          OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadBiographyOfDayAsync(true, false).subscribe(this::initAuthorPresentationOfDay);

          namePictureOfDay = settings.getString(Constants.PICTURE_OF_DAY_NAME, "");

          if (!settings.getBoolean(Constants.PICTURE_IS_NULL, false)) {

            File image = new File(OnelittleAngelApplication.instance.getFilesDir(), "/pictureofday.jpg");

            if (image.exists()) {
              BitmapFactory.Options bmOptions = new BitmapFactory.Options();

              this.pictureOfDay = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

              if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(settings.getString(Constants.PICTURE_OF_DAY_NAME, "")).subscribe(this::initAuthorPictureOfDayFromRealm);
              } else {
                OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.PICTURE_OF_DAY_NAME, "")).subscribe(this::initBookPictureOfDay);
              }
            } else {
              if (OnelittleAngelApplication.instance.isConnected()) {

                OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync(false).subscribe(new Observer<Bitmap>() {

                  @Override
                  public void onSubscribe(Disposable d) {
                    //Log.i("test", "onSubscribe");
                  }

                  @Override
                  public void onNext(Bitmap bitmap) {
                    //Log.i("test", "onNext");
                    initPictureOfDay(bitmap);
                  }

                  @Override
                  public void onError(Throwable e) {
                    //Log.i("test", "onError");
                  }

                  @Override
                  public void onComplete() {
                    //Log.i("test", "onComplete");
                    isAllInit();
                  }
                });
              }
            }
          } else {
            if (settings.getBoolean(Constants.PICTURE_IS_NULL, false) && OnelittleAngelApplication.instance.isConnected()) {

              OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync(false).subscribe(new Observer<Bitmap>() {

                @Override
                public void onSubscribe(Disposable d) {
                  //Log.i("test", "onSubscribe");
                }

                @Override
                public void onNext(Bitmap bitmap) {
                  //Log.i("test", "onNext");
                  initPictureOfDay(bitmap);
                }

                @Override
                public void onError(Throwable e) {
                  //Log.i("test", "onError");
                }

                @Override
                public void onComplete() {
                  //Log.i("test", "onComplete");
                  isAllInit();
                }
              });
            }
          }

        } else  {

          //settings.edit().putBoolean(Constants.IS_OPEN_FROM_QUOTE_NOTIFICATION, false).commit();

          if (settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false)) {
            OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::initAuthorPresentationOfDayFromRealm);
          } else {
            OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::initBookPresentationOfDay);
          }

          OnelittleAngelApplication.instance.getServiceManager().getQuoteByIdQuoteService().loadQuoteByIdQuoteAsync(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1)).subscribe(this::initQuoteOfDayFromRealm);

          namePictureOfDay = settings.getString(Constants.PICTURE_OF_DAY_NAME, "");

          if (!settings.getBoolean(Constants.PICTURE_IS_NULL, false)) {

            File image = new File(OnelittleAngelApplication.instance.getFilesDir(), "/pictureofday.jpg");

            if (image.exists()) {
              BitmapFactory.Options bmOptions = new BitmapFactory.Options();

              this.pictureOfDay = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

              if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
                OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(settings.getString(Constants.PICTURE_OF_DAY_NAME, "")).subscribe(this::initAuthorPictureOfDayFromRealm);
              } else {
                OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.PICTURE_OF_DAY_NAME, "")).subscribe(this::initBookPictureOfDay);
              }
            } else {
              if (OnelittleAngelApplication.instance.isConnected()) {

                OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync(false).subscribe(new Observer<Bitmap>() {

                  @Override
                  public void onSubscribe(Disposable d) {
                    //Log.i("test", "onSubscribe");
                  }

                  @Override
                  public void onNext(Bitmap bitmap) {
                    //Log.i("test", "onNext");
                    initPictureOfDay(bitmap);
                  }

                  @Override
                  public void onError(Throwable e) {
                    //Log.i("test", "onError");
                  }

                  @Override
                  public void onComplete() {
                    //Log.i("test", "onComplete");
                    isAllInit();
                  }
                });
              }
            }
          } else {
            if (settings.getBoolean(Constants.PICTURE_IS_NULL, false) && OnelittleAngelApplication.instance.isConnected()) {

              OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync(false).subscribe(new Observer<Bitmap>() {

                @Override
                public void onSubscribe(Disposable d) {
                  //Log.i("test", "onSubscribe");
                }

                @Override
                public void onNext(Bitmap bitmap) {
                  //Log.i("test", "onNext");
                  initPictureOfDay(bitmap);
                }

                @Override
                public void onError(Throwable e) {
                  //Log.i("test", "onError");
                }

                @Override
                public void onComplete() {
                  //Log.i("test", "onComplete");
                  isAllInit();
                }
              });
            }
          }
        }
      } else {
        OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadBiographyOfDayAsync(false, false).subscribe(this::initAuthorPresentationOfDay);
      }
    }
  }

  @Override
  public void removeResources() {
    homePageViewInterface = null;
    authorPresentationOfDay = null;
    bookPresentationOfDay = null;
    quoteOfDay = null;
    namePictureOfDay = null;
    pictureOfDay = null;
    authorPictureOfDay = null;
    bookPictureOfDay = null;
    settings = null;
    SharedPreferences.Editor editor = null;
  }

  @Override
  public Author getAuthorPresentationOfDay() {
    return authorPresentationOfDay;
  }

  @Override
  public Book getBookPresentationOfDay() {
    return bookPresentationOfDay;
  }

  @Override
  public Quote getQuoteOfDay() {
    return quoteOfDay;
  }

  @Override
  public String getNamePictureOfDay() {
    return namePictureOfDay;
  }

  @Override
  public Bitmap getPictureOfDay() {
    return pictureOfDay;
  }

  @Override
  public Author getAuthorPictureOfDay() {
    return authorPictureOfDay;
  }

  @Override
  public Book getBookPictureOfDay() {
    return bookPictureOfDay;
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  private void initQuoteOfDay(Quote quoteOfDay) {
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    this.quoteOfDay = quotesDaoInterface.findQuoteByIdQuote(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1));

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    if(settings.getInt(Constants.DAY_OF_MONTH, -1) != i && OnelittleAngelApplication.instance.isConnected() && !settings.getBoolean(Constants.IS_OPEN_FROM_PICTURE_NOTIFICATION, false)) {

      //  OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadQuoteOfDayAsync(false).subscribe(this::initQuoteOfDay);

      OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadPictureOfDayAsync(false).subscribe(new Observer<Bitmap>() {

        @Override
        public void onSubscribe(Disposable d) {
          //Log.i("test", "onSubscribe");
        }

        @Override
        public void onNext(Bitmap bitmap) {
          //Log.i("test", "onNext");
          initPictureOfDay(bitmap);
        }

        @Override
        public void onError(Throwable e) {
          //Log.i("test", "onError");
        }

        @Override
        public void onComplete() {
          //Log.i("test", "onComplete");
          isAllInit();
        }
      });
    } else {
      //initQuoteOfDay(null);

      File image = new File(OnelittleAngelApplication.instance.getFilesDir(), "/pictureofday.jpg");

      if (image.exists()) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        this.pictureOfDay = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

        if (settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
          OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(settings.getString(Constants.PICTURE_OF_DAY_NAME, "")).subscribe(this::initAuthorPictureOfDayFromRealm);
        } else {
          OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.PICTURE_OF_DAY_NAME, "")).subscribe(this::initBookPictureOfDay);
        }
      }
    }
  }

  private void initQuoteOfDayFromRealm(Quote quoteOfDay) {
    QuotesDaoInterface quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    this.quoteOfDay = quotesDaoInterface.findQuoteByIdQuote(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1));
  }

  private void initAuthorPresentationOfDay(Author authorPresentationOfDay) {
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    authorPresentationOfDay = authorsDaoInterface.findAuthorByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));
    if(authorPresentationOfDay == null) {
      OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")).subscribe(this::initBookPresentationOfDay);
    } else {
      this.authorPresentationOfDay = authorPresentationOfDay;
      OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadQuoteOfDayAsync(false).subscribe(this::initQuoteOfDay);
    }
  }

  private void initAuthorPresentationOfDayFromRealm(Author authorPresentationOfDay) {

    if(authorPresentationOfDay == null) {
    } else {
      this.authorPresentationOfDay = authorPresentationOfDay;
    }

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    if(settings.getInt(Constants.DAY_OF_MONTH, -1) != i && OnelittleAngelApplication.instance.isConnected()) {
      OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadQuoteOfDayAsync(false).subscribe(this::initQuoteOfDay);
    } else {
      initQuoteOfDay(null);
    }
  }

  private void initBookPresentationOfDay(Book bookPresentationOfDay) {

    if(bookPresentationOfDay != null) {
      this.bookPresentationOfDay = bookPresentationOfDay;
    }

    BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
    this.bookPresentationOfDay = booksDaoInterface.findBookByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    if(settings.getInt(Constants.DAY_OF_MONTH, -1) != i && OnelittleAngelApplication.instance.isConnected()) {
      OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadQuoteOfDayAsync(false).subscribe(this::initQuoteOfDay);
    } else {
      initQuoteOfDay(null);
    }
  }

  private void initPictureOfDay(Bitmap pictureOfDay) {
    if(pictureOfDay != null) this.pictureOfDay  =  pictureOfDay;


    OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadNameOfPictureOfDayAsync().subscribe(this::initNameOfPictureOfDay);
  }

  private void initNameOfPictureOfDay(String namePictureOfDay) {
    this.namePictureOfDay = namePictureOfDay;

    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    this.authorPictureOfDay = authorsDaoInterface.findAuthorByName(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));

    if(this.authorPictureOfDay != null) initAuthorPictureOfDay(authorPictureOfDay);
    else {
      BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
      this.bookPictureOfDay = booksDaoInterface.findBookByName(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
      initBookPictureOfDay(bookPictureOfDay);
    }
  }

  private void initAuthorPictureOfDay(Author authorPictureOfDay) {
    AuthorsDaoInterface authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    this.authorPictureOfDay = authorsDaoInterface.findAuthorByName(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));

    if(this.authorPictureOfDay == null ) {
      OnelittleAngelApplication.instance.getServiceManager().getHomePageService().loadBookPictureOfDayAsync().subscribe(this::initBookPictureOfDay);
    } else {
      isAllInit();
    }
  }

  private void initAuthorPictureOfDayFromRealm(Author authorPictureOfDay) {

    this.authorPictureOfDay = authorPictureOfDay;
    if(authorPictureOfDay.getName() == null ) {
    } else {
      isAllInit();
    }
  }

  private void initBookPictureOfDay(Book bookPictureOfDay) {
    if(bookPictureOfDay != null) {
      BooksDaoInterface booksDaoInterface = Injector.getDaoManager().getBooksDao();
      this.bookPictureOfDay = booksDaoInterface.findBookByName(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));

      // this.bookPictureOfDay = bookPictureOfDay;
    } else this.bookPictureOfDay = bookPictureOfDay;
    isAllInit();
  }

  private void isAllInit(){
    OnelittleAngelApplication.instance.manageConnectivityState();

    if (quoteOfDay != null && (authorPresentationOfDay != null || bookPresentationOfDay != null) /*&& (pictureOfDay != null || !OnelittleAngelApplication.instance.isConnected())*/) {
      Calendar calendar = Calendar.getInstance();
      int i = calendar.get(Calendar.DAY_OF_MONTH);
      OnelittleAngelApplication.instance.manageConnectivityState();

      //if(settings.getInt(Constants.DAY_OF_MONTH, -1) != i) {
      //isInit = false;
      if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i) settings.edit().putInt(Constants.DAY_OF_MONTH, i).apply();
      if (settings.getBoolean(Constants.WAS_NOTIFICATION_SENT_ON_DAY, false)) settings.edit().putBoolean(Constants.WAS_NOTIFICATION_SENT_ON_DAY, false).apply();

      if (settings.getBoolean(Constants.IS_OPEN_FROM_PICTURE_NOTIFICATION, false))
        settings.edit().putBoolean(Constants.IS_OPEN_FROM_PICTURE_NOTIFICATION, false).apply();

      if (settings.getBoolean(Constants.IS_OPEN_FROM_BIOGRAPHY_NOTIFICATION, false))
        settings.edit().putBoolean(Constants.IS_OPEN_FROM_BIOGRAPHY_NOTIFICATION, false).apply();

      if (settings.getBoolean(Constants.IS_OPEN_FROM_QUOTE_NOTIFICATION, false))
        settings.edit().putBoolean(Constants.IS_OPEN_FROM_QUOTE_NOTIFICATION, false).apply();

      homePageViewInterface.updateHomePage();
    }
  }
}
