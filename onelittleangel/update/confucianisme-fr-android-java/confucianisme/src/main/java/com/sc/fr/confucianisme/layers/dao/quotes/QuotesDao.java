package com.sc.fr.confucianisme.layers.dao.quotes;


import com.sc.fr.confucianisme.layers.dao.DaoManager;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Author;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Book;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Favorite;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Movement;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Quote;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class QuotesDao implements QuotesDaoInterface {

  private static final String TAG = "QuotesDao";
  private Realm realm;
  Quote quote;
  List<Quote> quotes;
  /***********************************************************
   * Constructor
   **********************************************************/
  public QuotesDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  @Override
  public Quote findQuoteByIdQuote(int idQuote) {
    realm = Realm.getDefaultInstance();
    return realm.where(Quote.class).equalTo("idQuote", idQuote).findFirst();
  }

  @Override
  public List<Quote> findQuotesByAuthor(String name) {
    realm = Realm.getDefaultInstance();
//    if(quotes == null) {
//      quotes = new ArrayList<Quote>();
//    }
//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        quotes = realm.where(Author.class).equalTo("name", name).findFirst().getQuotes();
//      }
//    });
//    return quotes;
    return realm.where(Author.class).equalTo("name", name).findFirst().getQuotes();

//    return realm.asObservable().map(new Func1<Realm, List<Quote>>() {
//      @Override
//      public List<Quote> call(Realm realm) {
//        return realm.where(Author.class).equalTo("name", name).findFirst().getQuotes();
//      }
//    });
  }

  @Override
  public Observable<List<Quote>> findQuotesByIdAuthor(final int idAuthor) {
    realm = Realm.getDefaultInstance();
    return Observable.just(realm.where(Author.class).equalTo("idAuthor", idAuthor).findFirst().getQuotes());
  }

  @Override
  public Observable<List<Quote>> findQuotesByBook(String name) {
    realm = Realm.getDefaultInstance();
  //  return Observable.just(realm.where(Book.class).equalTo("name", name).findFirst().getQuotes());
//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        if(quotes == null) {
//          quotes = new ArrayList<Quote>();
//        } else {
//          quotes.clear();
//        }
//        quotes = realm.where(Book.class).equalTo("name", name).findFirst().getQuotes();
//      }
//    });
    return Observable.just(realm.where(Book.class).equalTo("name", name).findFirst().getQuotes());
  }

  @Override
  public List<Quote> findQuotesByIdBook(int idBook) {
    realm = Realm.getDefaultInstance();
    Book book = realm.where(Book.class).equalTo("idBook", idBook).findFirst();
    return book.getQuotes();
  }

  @Override
  public Observable<List<Quote>> findQuotesByTheme(String theme) {
    realm = Realm.getDefaultInstance();

//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        if(quotes == null) {
//          quotes = new ArrayList<Quote>();
//        } else {
//          quotes.clear();
//        }
//        Theme theme1 = realm.where(Theme.class).equalTo("theme", theme).findFirst();
//        quotes.addAll(theme1.getQuotes());
//      }
//    });
    return Observable.just(realm.where(Theme.class).equalTo("theme", theme).findFirst().getQuotes());
  }

  @Override
  public List<Quote> findQuotesByIdTheme(int idTheme) {
    realm = Realm.getDefaultInstance();
  //  Theme theme = realm.where(Theme.class).equalTo("idTheme", idTheme).findFirst();
  //  return theme.getQuotes();

//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        if(quotes == null) {
//          quotes = new ArrayList<Quote>();
//        } else {
//          quotes.clear();
//        }
//        quotes = realm.where(Theme.class).equalTo("idTheme", idTheme).findFirst().getQuotes();
//      }
//    });
    return realm.where(Theme.class).equalTo("idTheme", idTheme).findFirst().getQuotes();
  }

  @Override
  public Observable<List<Quote>> findQuotesByMovement(String m) {
    realm = Realm.getDefaultInstance();

   // return Observable.just(quotes);
    List<Quote> quotes = new ArrayList<>();


    realm.executeTransaction(realm1 -> {
    //  quotes = realm.where(Theme.class).equalTo("theme", theme).findFirst().getQuotes();
      Movement movement = realm1.where(Movement.class).equalTo("movement", m).findFirst();

      for(int i = 0; i < movement.getAuthors().size(); i++){
        quotes.addAll(movement.getAuthors().get(i).getQuotes());
      }

      for(int i = 0; i < movement.getBooks().size(); i++){
        quotes.addAll(movement.getBooks().get(i).getQuotes());
      }
    });


    return Observable.just(quotes);
//    return realm.asObservable().map(new Func1<Realm, List<Quote>>() {
//      @Override
//      public List<Quote> call(Realm realm) {
//
//      }
//    });
  }

  @Override
  public List<Quote> findQuotesByIdMovement(int idMovement) {
    realm = Realm.getDefaultInstance();
    Movement movement = realm.where(Movement.class).equalTo("idMovement", idMovement).findFirst();
    List<Quote> quotes = new ArrayList<>();

    for(int i = 0; i < movement.getAuthors().size(); i++){
      quotes.addAll(movement.getAuthors().get(i).getQuotes());
    }

    for(int i = 0; i < movement.getBooks().size(); i++){
      quotes.addAll(movement.getBooks().get(i).getQuotes());
    }
    return quotes;
  }

  @Override
  public List<Quote> findAllQuotes() {
    realm = Realm.getDefaultInstance();
    return realm.where(Quote.class).findAll();
  }

  @Override
  public void toggleQuotesIsFavorites(int idQuote) {
    realm = Realm.getDefaultInstance();
  //  realm.setAutoRefresh(true);
    realm.beginTransaction();

//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//        Quote q = realm.where(Quote.class).equalTo("idQuote", idQuote).findFirst();
//
//        if(q.isFavorites()) {
//          q.setFavorites(false);
//          Favorite favorite =  realm.where(Favorite.class).equalTo("idQuote", idQuote).findFirst();
//          favorite.deleteFromRealm();
//        } else {
//          q.setFavorites(true);
//          // increatement index
//          long nextID = 0;
//          if((realm.where(Favorite.class).max("idFavorite")) != null)
//            nextID = (long) (realm.where(Favorite.class).max("idFavorite"));
//
//          Favorite favorite = realm.createObject(Favorite.class,(++nextID));
//          favorite.setIdQuote(idQuote);
//          realm.copyToRealm(favorite);
//        }
//      }
//    });

    Quote q = realm.where(Quote.class).equalTo("idQuote", idQuote).findFirst();

    if(q.isFavorites()) {
      q.setFavorites(false);
      Favorite favorite =  realm.where(Favorite.class).equalTo("idQuote", idQuote).findFirst();
      favorite.deleteFromRealm();
    } else {
      q.setFavorites(true);
      // increatement index
      long nextID = 0;
      if((realm.where(Favorite.class).max("idFavorite")) != null) {
        nextID = (long) (realm.where(Favorite.class).max("idFavorite"));
      }
    //  Favorite favorite = new Favorite();
    //  favorite.setIdFavorite(++nextID);
      Favorite favorite = realm.createObject(Favorite.class,(++nextID));
      favorite.setIdQuote(idQuote);
    //  Observable.just(favorite).observeOn(AndroidSchedulers.mainThread());
      realm.copyToRealm(favorite);
    }
    realm.commitTransaction();
  //  return q.asObservable();
  }

  @Override
  public Observable<List<Quote>> findAllFavoritesQuotes() {
    realm = Realm.getDefaultInstance();
  //  realm.beginTransaction();
//    List<Favorite> favorites = realm.where(Favorite.class).findAll();
    List<Quote> quotes = new ArrayList<>();
//    if(quotes == null) {
//      quotes = new ArrayList<Quote>();
//    } else {
//      quotes.clear();
//    }
    List<Favorite> favorites = realm.where(Favorite.class).findAll();
    for(int i = 0; i < favorites.size(); i++) {
      Quote quote = realm.where(Quote.class).equalTo("idQuote", favorites.get(i).getIdQuote()).findFirst();
      quotes.add(quote);
    }

//
//    realm.executeTransaction(new Realm.Transaction() {
//      @Override
//      public void execute(Realm realm) {
//
////        List<Favorite> favorites = realm.where(Favorite.class).findAll();
////        for(int i = 0; i < favorites.size(); i++) {
////          quotes.add(realm.where(Quote.class).equalTo("idQuote", favorites.get(i).getIdQuote()).findFirst());
////        }
//      }
//    });
  //  realm.commitTransaction();
    return Observable.just(quotes);
  //  return Observable.f

//    return realm.asObservable().map(new Func1<Realm, List<Quote>>() {
//      @Override
//      public List<Quote> call(Realm realm) {
//
//
//      }
//    });
  }

  @Override
  public Quote findQuoteByRandom() {
    Realm realm = Realm.getDefaultInstance();

// get all objects
    RealmResults<Quote> results = realm.where(Quote.class).findAll();

// random generator
    Random r = new Random(System.nanoTime());

// generate first random number to be from the range [0..number of objects)
    int firstRandomNumber = r.nextInt(results.size());

// get first object from results at position randomly generated above
    return results.get(firstRandomNumber);
  }
  /***********************************************************
   *  Business Methods
   **********************************************************/
}
