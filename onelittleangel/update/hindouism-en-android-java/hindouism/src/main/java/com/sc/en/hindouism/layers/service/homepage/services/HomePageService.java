package com.sc.en.hindouism.layers.service.homepage.services;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.sc.en.hindouism.OnelittleAngelApplication;
import com.sc.en.hindouism.layers.dao.authors.AuthorsDaoInterface;
import com.sc.en.hindouism.layers.dao.books.BooksDaoInterface;
import com.sc.en.hindouism.layers.mvp.common.utils.Constants;
import com.sc.en.hindouism.transverse.orms.realm.models.Picture;
import com.sc.en.hindouism.transverse.orms.realm.models.Presentation;
import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.transverse.orms.realm.models.Quote;
import com.sc.en.hindouism.injector.Injector;
import com.sc.en.hindouism.layers.dao.movements.MovementsDaoInterface;
import com.sc.en.hindouism.layers.dao.quotes.QuotesDaoInterface;
import com.sc.en.hindouism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;
import com.sc.en.hindouism.layers.service.homepage.interfaces.HomePageServiceInterface;
import com.sc.en.hindouism.transverse.orms.realm.models.Author;
import com.sc.en.hindouism.transverse.orms.realm.models.Book;
import com.sc.en.hindouism.transverse.orms.realm.models.Movement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.Observable;

public class HomePageService extends MotherBusinessService implements HomePageServiceInterface {

  private static final String TAG = "HomePageService";

  private Author biographyOfDay;
  private Author authorPictureOfDay;
  private Book bookPictureOfDay;
  private Quote quoteOfDay;
  private String nameOfPictureOfDay;
  private Observable<Bitmap> pictureOfDay;
  private String OnelittleAngelWebSite;
  private List<String> homePageResources;
  private String biographyName = null;
  private SharedPreferences settings;
  private SharedPreferences.Editor editor;

  /**
   *
   */
  private AuthorsDaoInterface authorsDaoInterface = null;

  /**
   *
   */
  private BooksDaoInterface booksDaoInterface = null;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public HomePageService(ServiceManagerInterface srvManager) {
    super(srvManager);

    //if (android.os.Build.VERSION.SDK_INT > 9) {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    //}

    if (homePageResources == null) {

      // homePageResources = loadOnelittleAngelWebsite();
    }
  }

  @Override
  public void onDestroy() {
    biographyOfDay = null;
  }

  @Override
  public Observable<Author> loadBiographyOfDayAsync(boolean isFirstOpenOfDay, boolean isForNotification) {
    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    /*
    To know if tha data has to be reloaded
   */
    boolean reloadBiographyOfDay = false;
    if (biographyOfDay != null && settings.getInt(Constants.DAY_OF_MONTH, -1) == i) {
      reloadBiographyOfDay = true;
    }

    if ((settings.getInt(Constants.DAY_OF_MONTH, -1) == i || (!isFirstOpenOfDay && !isForNotification)) ||
            settings.getBoolean(Constants.IS_OPEN_FROM_BIOGRAPHY_NOTIFICATION, false)) {

      biographyOfDay = Injector.getDaoManager().getAuthorsDao().findAuthorByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));

      if (biographyOfDay == null) {
        editor.putBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false);
        editor.putString(Constants.BIOGRAPHY_OF_DAY_NAME, settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));
//editor.commit();
        editor.apply();
        Author author = new Author();
        author.setName(biographyName);
//
        return Observable.just(author);
      } else {
        editor.putBoolean(Constants.BIOGRAPHY_IS_AUTHOR, true);
        editor.commit();
        return Observable.just(biographyOfDay);
      }
    }

    // use the caching mechanism
    if (reloadBiographyOfDay) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);

      return Observable.just(biographyOfDay);
    } else {

      Observable<Author> authorObservable = loadBiographyOfDaySync(isFirstOpenOfDay, isForNotification);

      if (biographyOfDay == null) {
        editor.putBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false);
        editor.putString(Constants.BIOGRAPHY_OF_DAY_NAME, biographyName);
//editor.commit();
        editor.apply();
        Author author = new Author();
        author.setName(biographyName);
//
        return Observable.just(author);
      } else {
        editor.putBoolean(Constants.BIOGRAPHY_IS_AUTHOR, true);
        editor.commit();
        return Observable.just(biographyOfDay);
      }
    }
  }

  private Observable<Author> loadBiographyOfDaySync(boolean isFirstOpenOfDay, boolean isForNotification) {

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
    editor = settings.edit();

    boolean isConnectionFailed = false;
    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    if ((isForNotification || isFirstOpenOfDay) /*&& OnelittleAngelApplication.instance.isConnected()*/) {

      MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();

      Movement appMovement = movementsDaoInterface.findMovementByName(Constants.APPLICATION_MOVEMENT);

      List<Presentation> presentations = new ArrayList<>();

      //  if(appMovement.getPresentation() != null) presentations.add(appMovement.getPresentation());

      if (appMovement.getAuthors() != null) {
        for (Author author : appMovement.getAuthors())
          if (author.getPresentation() != null && author.getPresentation().getPresentation().length() >= 600)
            presentations.add(author.getPresentation());
      }

      if (appMovement.getBooks() != null) {
        for (Book book : appMovement.getBooks())
          if (book.getPresentation() != null && book.getPresentation().getPresentation().length() >= 600)
            presentations.add(book.getPresentation());
      }

      if (appMovement.getMovements().size() != 0) {

        for (Movement subMovement : appMovement.getMovements()) {

          //  if (subMovement.getPresentation() != null) presentations.add(subMovement.getPresentation());

          if (subMovement.getAuthors() != null) {
            for (Author author : subMovement.getAuthors())
              if (author.getPresentation() != null && author.getPresentation().getPresentation().length() >= 600)
                presentations.add(author.getPresentation());
          }

          if (subMovement.getBooks() != null) {
            for (Book book : subMovement.getBooks())
              if (book.getPresentation() != null && book.getPresentation().getPresentation().length() >= 600)
                presentations.add(book.getPresentation());
          }

          if (subMovement.getMovements().size() != 0) {

            for (Movement subSubMovement : subMovement.getMovements()) {

              //  if (subSubMovement.getPresentation() != null) presentations.add(subSubMovement.getPresentation());

              if (subSubMovement.getAuthors() != null) {
                for (Author author : subSubMovement.getAuthors())
                  if (author.getPresentation() != null && author.getPresentation().getPresentation().length() >= 600)
                    presentations.add(author.getPresentation());
              }

              if (subSubMovement.getBooks() != null) {
                for (Book book : subSubMovement.getBooks())
                  if (book.getPresentation() != null && book.getPresentation().getPresentation().length() >= 600)
                    presentations.add(book.getPresentation());
              }
            }
          }
        }
      }

      // random generator
      Random r = new Random(System.nanoTime());

      // generate first random number to be from the range [0..number of objects)
      int firstRandomNumber = r.nextInt(presentations.size());

      Presentation presentation = presentations.get(firstRandomNumber);

      authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
      biographyOfDay = authorsDaoInterface.findAuthorByIdPresentation((int) presentation.getIdPresentation());
      authorsDaoInterface = null;

      if (biographyOfDay != null) {
        biographyName = biographyOfDay.getName();
        editor.putString(Constants.BIOGRAPHY_OF_DAY_NAME, biographyName.trim());
        editor.commit();
        editor.putBoolean(Constants.BIOGRAPHY_IS_AUTHOR, true);
        editor.commit();
      } else {
        booksDaoInterface = Injector.getDaoManager().getBooksDao();
        Book book = booksDaoInterface.findBookByIdPresentation((int) presentation.getIdPresentation());
        biographyName = book.getName();
        editor.putString(Constants.BIOGRAPHY_OF_DAY_NAME, biographyName.trim());
        editor.commit();
        editor.putBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false);
        editor.commit();
      }

      //if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i && isForNotification) editor.putInt(Constants.DAY_OF_MONTH, i);
      if(isForNotification) editor.putBoolean(Constants.IS_OPEN_FROM_BIOGRAPHY_NOTIFICATION, true);

      editor.commit();

      if (biographyOfDay == null) return Observable.empty();
      return Observable.just(biographyOfDay);
    }
    return null;
    //  return null;
  }

  @Override
  public Observable<Quote> loadQuoteOfDayAsync(boolean isForNotification) {

    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();

    /*
    To know if tha data has to be reloaded
   */

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    boolean reloadQuoteOfDay = false;
    if (quoteOfDay != null && settings.getInt(Constants.DAY_OF_MONTH, -1) == i) {
      reloadQuoteOfDay = true;
    }

    if (settings.getInt(Constants.DAY_OF_MONTH, -1) == i || settings.getBoolean(Constants.IS_OPEN_FROM_QUOTE_NOTIFICATION, false)/*&& !isForNotification*/) {
      quoteOfDay = Injector.getDaoManager().getQuotesDao().findQuoteByIdQuote(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1));
      return Observable.just(quoteOfDay);
    }

    // use the caching mechanism
    if (reloadQuoteOfDay) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return Observable.just(quoteOfDay);
    } else {
      return loadQuoteOfDaySync(isForNotification);
    }
  }

  @Override
  public Observable<String> loadNameOfPictureOfDayAsync() {

    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();

    /*
    To know if tha data has to be reloaded
   */
    boolean reloadNameOfPictureOfDay = false;
//    if (nameOfPictureOfDay != null) {
//      reloadNameOfPictureOfDay = true;
//    }
//
//    // use the caching mechanism
//    if (reloadNameOfPictureOfDay) {
//      //send send back the answer using eventBus
//      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
//      return Observable.just(nameOfPictureOfDay);
//    } else {
    return Observable.just(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
//    }
  }

  private Observable<Quote> loadQuoteOfDaySync(boolean isForNotification) {

    int idQuote;

    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();

    /*

   */
    QuotesDaoInterface quotesDaoInterface = null;

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

//    if (isForNotification /*&& settings.getInt(Constants.QUOTE_OF_DAY_ID, -1) != -1*/
//            || settings.getInt(Constants.DAY_OF_MONTH, -1) != i /*&& OnelittleAngelApplication.instance.isConnected()*/) {

    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();

    Movement appMovement = movementsDaoInterface.findMovementByName(Constants.APPLICATION_MOVEMENT);

    List<Quote> quotes = new ArrayList<>();

    //  if(appMovement.getPresentation() != null) presentations.add(appMovement.getPresentation());

    if (appMovement.getAuthors() != null) {
      for (Author author : appMovement.getAuthors())
        if (author.getQuotes().size() != 0) quotes.addAll(author.getQuotes());
    }

    if (appMovement.getBooks() != null) {
      for (Book book : appMovement.getBooks())
        if (book.getQuotes().size() != 0) quotes.addAll(book.getQuotes());
    }

    if (appMovement.getMovements().size() != 0) {

      for (Movement subMovement : appMovement.getMovements()) {

        //  if (subMovement.getPresentation() != null) presentations.add(subMovement.getPresentation());

        if (subMovement.getAuthors() != null) {
          for (Author author : subMovement.getAuthors())
            if (author.getQuotes().size() != 0) quotes.addAll(author.getQuotes());
        }

        if (subMovement.getBooks() != null) {
          for (Book book : subMovement.getBooks())
            if (book.getQuotes().size() != 0) quotes.addAll(book.getQuotes());
        }

        if (subMovement.getMovements().size() != 0) {

          for (Movement subSubMovement : subMovement.getMovements()) {

            //  if (subSubMovement.getPresentation() != null) presentations.add(subSubMovement.getPresentation());

            if (subSubMovement.getAuthors() != null) {
              for (Author author : subSubMovement.getAuthors())
                if (author.getQuotes().size() != 0) quotes.addAll(author.getQuotes());
            }

            if (subSubMovement.getBooks() != null) {
              for (Book book : subSubMovement.getBooks())
                if (book.getQuotes().size() != 0) quotes.addAll(book.getQuotes());
            }
          }
        }
      }
    }

    // random generator

    Random r = new Random(System.nanoTime());

    // generate first random number to be from the range [0..number of objects)
    int firstRandomNumber = r.nextInt(quotes.size());

    //Presentation presentation = presentations.get(firstRandomNumber);
    quoteOfDay = quotes.get(firstRandomNumber);

    String nameOfQuote;

    if (quoteOfDay.getAuthor() != null) nameOfQuote = quoteOfDay.getAuthor().getName();
    else nameOfQuote = quoteOfDay.getBook().getName();

    if (nameOfQuote.equals(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""))) {

      do {
        //r = new Random(System.nanoTime());

        // generate first random number to be from the range [0..number of objects)
        firstRandomNumber = r.nextInt(quotes.size());

        //Presentation presentation = presentations.get(firstRandomNumber);
        quoteOfDay = quotes.get(firstRandomNumber);

        if (quoteOfDay.getAuthor() != null) nameOfQuote = quoteOfDay.getAuthor().getName();
        else nameOfQuote = quoteOfDay.getBook().getName();

      } while (nameOfQuote.equals(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, "")));
    }
    editor.putInt(Constants.QUOTE_OF_DAY_ID, (int) quoteOfDay.getIdQuote());

    if (quoteOfDay.getAuthor() != null) {
      editor.putBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, true);
      editor.putString(Constants.QUOTE_OF_DAY_NAME, quoteOfDay.getAuthor().getName());
    } else {
      editor.putBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false);
      editor.putString(Constants.QUOTE_OF_DAY_NAME, quoteOfDay.getBook().getName());
    }

    // if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i && isForNotification) editor.putInt(Constants.DAY_OF_MONTH, i);
    if(isForNotification) editor.putBoolean(Constants.IS_OPEN_FROM_QUOTE_NOTIFICATION, true);

    editor.commit();
    return Observable.just(quoteOfDay);
//    } else {
//
//      quotesDaoInterface = Injector.getDaoManager().getQuotesDao();
//
////      if (settings.getInt(Constants.QUOTE_OF_DAY_ID, -1) != -1 && settings.getInt(Constants.DAY_OF_MONTH, -1) == i && OnelittleAngelApplication.instance.isConnected()) {
//        quoteOfDay = quotesDaoInterface.findQuoteByIdQuote(settings.getInt(Constants.QUOTE_OF_DAY_ID, -1));
////      } else {
////        quoteOfDay = quotesDaoInterface.findQuoteByRandom();
////      }
//
//      quotesDaoInterface = null;
////      }
//
//      editor.putInt(Constants.QUOTE_OF_DAY_ID, (int) quoteOfDay.getIdQuote());
//
//      if (quoteOfDay.getAuthor() != null) {
//        editor.putBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, true);
//        editor.putString(Constants.QUOTE_OF_DAY_NAME, quoteOfDay.getAuthor().getName());
//      } else {
//        editor.putBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false);
//        editor.putString(Constants.QUOTE_OF_DAY_NAME, quoteOfDay.getBook().getName());
//      }
//      editor.commit();
//      return Observable.just(quoteOfDay);
//    }
    //return null;
  }

  @Override
  public Observable<Bitmap> loadPictureOfDayAsync(boolean isForNotification) {

    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();

    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(Calendar.DAY_OF_MONTH);
    OnelittleAngelApplication.instance.manageConnectivityState();

    /*
    To know if tha data has to be reloaded
   */
    boolean reloadPictureOfDay = false;
    if (pictureOfDay != null && !pictureOfDay.equals(Observable.empty()) && settings.getInt(Constants.DAY_OF_MONTH, -1) == i ) {
      reloadPictureOfDay = true;
    }

    // use the caching mechanism
    if (reloadPictureOfDay) {

      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      if (pictureOfDay.equals(Observable.empty()))
        editor.putBoolean(Constants.PICTURE_IS_NULL, true);
//editor.commit();
      else
        editor.putBoolean(Constants.PICTURE_IS_NULL, false);

      editor.commit();
      return pictureOfDay;
    } else {
      pictureOfDay = loadPictureOfDaySync(isForNotification);
      if (pictureOfDay.equals(Observable.empty())) {
        editor.putBoolean(Constants.PICTURE_IS_NULL, true);
      } else {
        editor.putBoolean(Constants.PICTURE_IS_NULL, false);
      }
      editor.commit();
      return pictureOfDay;
    }
  }

  @Override
  public Observable<Author> loadAuthorPictureOfDayAsync() {

    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();


//    if (authorPictureOfDay != null) return Observable.just(authorPictureOfDay);
//    else {
    authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
    authorPictureOfDay = authorsDaoInterface.findAuthorByName(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));

    if (authorPictureOfDay != null) return Observable.just(authorPictureOfDay);
    else return Observable.empty();
//    }
  }

  @Override
  public Observable<Book> loadBookPictureOfDayAsync() {

    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      //editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();

    if (bookPictureOfDay != null) return Observable.just(bookPictureOfDay);
    else {
      booksDaoInterface = Injector.getDaoManager().getBooksDao();
      bookPictureOfDay = booksDaoInterface.findBookByName(settings.getString(Constants.PICTURE_OF_DAY_NAME, ""));
      return Observable.just(bookPictureOfDay);
    }
  }

  private Observable<Bitmap> loadPictureOfDaySync(boolean isForNotification) {

    if (settings == null) {
      settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
      editor = settings.edit();
    }

    if (editor == null) editor = settings.edit();

    try {

      Calendar calendar = Calendar.getInstance();
      int i = calendar.get(Calendar.DAY_OF_MONTH);
      OnelittleAngelApplication.instance.manageConnectivityState();

      if (/*isForNotification*/ /*&& settings.getInt(Constants.QUOTE_OF_DAY_ID, -1) != -1*/
                /*&&*/ /*settings.getInt(Constants.DAY_OF_MONTH, -1) != i &&*/ OnelittleAngelApplication.instance.isConnected()) {

        MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();

        Movement appMovement = movementsDaoInterface.findMovementByName(Constants.APPLICATION_MOVEMENT);

        List<Picture> pictures = new ArrayList<>();

        //  if(appMovement.getPresentation() != null) presentations.add(appMovement.getPresentation());

        if (appMovement.getAuthors() != null) {
          for (Author author : appMovement.getAuthors())
            if (author.getPictures().size() != 0 && author.getQuotes().size() != 0) pictures.addAll(author.getPictures());
        }

        if (appMovement.getBooks() != null) {
          for (Book book : appMovement.getBooks())
            if (book.getPictures().size() != 0 && book.getQuotes().size() != 0) pictures.addAll(book.getPictures());
        }

        if (appMovement.getMovements().size() != 0) {

          for (Movement subMovement : appMovement.getMovements()) {

            //  if (subMovement.getPresentation() != null) presentations.add(subMovement.getPresentation());

            if (subMovement.getAuthors() != null) {
              for (Author author : subMovement.getAuthors())
                if (author.getPictures().size() != 0 && author.getQuotes().size() != 0) pictures.addAll(author.getPictures());
            }

            if (subMovement.getBooks() != null) {
              for (Book book : subMovement.getBooks())
                if (book.getPictures().size() != 0 && book.getQuotes().size() != 0) pictures.addAll(book.getPictures());
            }

            if (subMovement.getMovements().size() != 0) {

              for (Movement subSubMovement : subMovement.getMovements()) {

                //  if (subSubMovement.getPresentation() != null) presentations.add(subSubMovement.getPresentation());

                if (subSubMovement.getAuthors() != null) {
                  for (Author author : subSubMovement.getAuthors())
                    if (author.getPictures().size() != 0 && author.getQuotes().size() != 0) pictures.addAll(author.getPictures());
                }

                if (subSubMovement.getBooks() != null) {
                  for (Book book : subSubMovement.getBooks())
                    if (book.getPictures().size() != 0 && book.getQuotes().size() != 0) pictures.addAll(book.getPictures());
                }
              }
            }
          }
        }

        // random generator

        Random r = new Random(System.nanoTime());

        // generate first random number to be from the range [0..number of objects)
        int firstRandomNumber = r.nextInt(pictures.size());

        //Presentation presentation = presentations.get(firstRandomNumber);
        //PicturesDaoInterface picturesDaoInterface = Injector.getDaoManager().getPicturesDao();
        //Picture pictureOfDay = picturesDaoInterface.findPictureByIdPicture(564);
        Picture pictureOfDay = pictures.get(firstRandomNumber);

        boolean isPictureIsOtherThanQuoteAndBiographyOfDay = false;

        if (settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false)) {
          authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
          Author author = authorsDaoInterface.findAuthorByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));
          if (author.getPictures().contains(pictureOfDay))
            isPictureIsOtherThanQuoteAndBiographyOfDay = true;
        } else {
          booksDaoInterface = Injector.getDaoManager().getBooksDao();
          Book book = booksDaoInterface.findBookByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));

          if (book.getPictures().contains(pictureOfDay))
            isPictureIsOtherThanQuoteAndBiographyOfDay = true;
        }

        if (settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false)) {
          authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
          Author author = authorsDaoInterface.findAuthorByName(settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));
          if (author.getPictures().contains(pictureOfDay))
            isPictureIsOtherThanQuoteAndBiographyOfDay = true;
        } else {
          booksDaoInterface = Injector.getDaoManager().getBooksDao();
          Book book = booksDaoInterface.findBookByName(settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));

          if (book.getPictures().contains(pictureOfDay))
            isPictureIsOtherThanQuoteAndBiographyOfDay = true;
        }

        if (isPictureIsOtherThanQuoteAndBiographyOfDay) {

          do {
            //r = new Random(System.nanoTime());

            // generate first random number to be from the range [0..number of objects)
            firstRandomNumber = r.nextInt(pictures.size());

            boolean b = false;

            //Presentation presentation = presentations.get(firstRandomNumber);
            pictureOfDay = pictures.get(firstRandomNumber);

            if (settings.getBoolean(Constants.BIOGRAPHY_IS_AUTHOR, false)) {
              authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
              Author author = authorsDaoInterface.findAuthorByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));
              if (author.getPictures().contains(pictureOfDay))
                b = true;
            } else {
              booksDaoInterface = Injector.getDaoManager().getBooksDao();
              Book book = booksDaoInterface.findBookByName(settings.getString(Constants.BIOGRAPHY_OF_DAY_NAME, ""));

              if (book.getPictures().contains(pictureOfDay))
                b = true;
            }

            if (settings.getBoolean(Constants.QUOTE_OF_DAY_IS_AUTHOR, false)) {
              authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
              Author author = authorsDaoInterface.findAuthorByName(settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));
              if (author.getPictures().contains(pictureOfDay))
                b = true;
            } else {
              booksDaoInterface = Injector.getDaoManager().getBooksDao();
              Book book = booksDaoInterface.findBookByName(settings.getString(Constants.QUOTE_OF_DAY_NAME, ""));

              if (book.getPictures().contains(pictureOfDay))
                b = true;
            }

            isPictureIsOtherThanQuoteAndBiographyOfDay = b;
          } while (isPictureIsOtherThanQuoteAndBiographyOfDay);
        }

        settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);
        editor = settings.edit();

        authorsDaoInterface = Injector.getDaoManager().getAuthorsDao();
        List<Author> authors = authorsDaoInterface.findAllAuthors();

        for (Author author : authors) {
          if (author.getPictures().contains(pictureOfDay)) {
            nameOfPictureOfDay = author.getName();
            editor.putBoolean(Constants.PICTURE_IS_AUTHOR, true);
            editor.putString(Constants.PICTURE_OF_DAY_NAME, nameOfPictureOfDay);
            editor.commit();
          }
        }

        //  if (!settings.getBoolean(Constants.PICTURE_IS_AUTHOR, false)) {
        booksDaoInterface = Injector.getDaoManager().getBooksDao();
        List<Book> books = booksDaoInterface.findAllBooks();

        for (Book book : books) {
          if (book.getPictures().contains(pictureOfDay)) {
            nameOfPictureOfDay = book.getName();
            editor.putBoolean(Constants.PICTURE_IS_AUTHOR, false);
            editor.putString(Constants.PICTURE_OF_DAY_NAME, nameOfPictureOfDay);
            editor.commit();
          }
        }
        //  }

        //  editor.putString(Constants.PICTURE_OF_DAY_NAME, nameOfPictureOfDay);
//editor.commit();
        //  editor.apply();
//          if (pictureOfDay.getNameSmall().contains("Roshi_Yamada"))
//            splitedSrc[5] = splitedSrc[5].replace("Roshi_Yamada", "Roshi Yamada");
//          if (splitedSrc[5].contains("Roshi_Suzuki"))
//            splitedSrc[5] = splitedSrc[5].replace("Roshi_Suzuki", "Roshi Suzuki");
        editor.putString(Constants.PICTURE_OF_DAY_JPEGNAME, pictureOfDay.getNameSmall() + "_" + pictureOfDay.getIdPicture() + "." + pictureOfDay.getExtension());
        //if (settings.getInt(Constants.DAY_OF_MONTH, -1) != i && isForNotification) editor.putInt(Constants.DAY_OF_MONTH, i);
        if(isForNotification) editor.putBoolean(Constants.IS_OPEN_FROM_PICTURE_NOTIFICATION, true);
        editor.commit();

        Future<Observable<Bitmap>> bm = OnelittleAngelApplication.instance.getServiceManager().getKeepAliveThreadsExecutor().submit(new DaoLoadPictureOfDayLoadRunnable(pictureOfDay.getNameSmall() + "_" + pictureOfDay.getIdPicture() + "." + pictureOfDay.getExtension()));

        return bm.get();
      } else {
        return Observable.empty();
      }
//        } catch(InterruptedException | ExecutionException e){
//          e.printStackTrace();
//          return Observable.empty();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return Observable.empty();
    } catch (ExecutionException e) {
      e.printStackTrace();
      return Observable.empty();
    }
    //return pictureOfDay;
    //  }

  }

  private void saveBitmap(Bitmap bitmap, String path) {
    if (bitmap != null) {
      try {
        FileOutputStream outputStream = null;
        try {
          outputStream = new FileOutputStream(path); //here is set your file path where you want to save or also here you can set file object directly

          bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
          // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            if (outputStream != null) {
              outputStream.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * @quotes Mathias Seguy (Android2EE)
   * @goals This class aims to implements a Runnable with an Handler
   */
  private class DaoLoadPictureOfDayLoadRunnable implements Callable<Observable<Bitmap>> {

    final String pictureName;

    public DaoLoadPictureOfDayLoadRunnable(String pictureName) {
      this.pictureName = pictureName;
    }

    @Override
    public Observable<Bitmap> call() throws Exception {
      return loadPictureOfDay(pictureName);
    }
  }

  private Observable<Bitmap> loadPictureOfDay(String pictureName) {


    Bitmap bm;

    if (pictureName.contains("Roshi_Yamada"))
      pictureName = pictureName.replace("Roshi_Yamada", "Roshi Yamada");
    if (pictureName.contains("Roshi_Suzuki"))
      pictureName = pictureName.replace("Roshi_Suzuki", "Roshi Suzuki");
    //Jesus_Christ_138.jpg
    //Log.i("test", "before request");
    ANRequest request = AndroidNetworking.get("http://www.onelittleangel.com/common/images/auteur/" + pictureName)
            .setTag("imageRequestTag")
            .setPriority(Priority.IMMEDIATE)
            .build();
//    try {
//      throw new InterruptedException();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }

    ANResponse response = request.executeForBitmap();

    //Log.i("test", "after request");

    bm = (Bitmap) response.getResult();

    Drawable d = new BitmapDrawable(OnelittleAngelApplication.instance.getResources(), bm);

    saveBitmap(bm, OnelittleAngelApplication.instance.getFilesDir() + "/pictureofday.jpg");

    if (bm == null) pictureOfDay = Observable.empty();
    else pictureOfDay = Observable.just(bm);
    return pictureOfDay;
  }
}
