package com.sc.en.philosophy.layers.service.accounts.services;

import com.sc.en.philosophy.injector.Injector;
import com.sc.en.philosophy.layers.dao.accounts.AccountsDaoInterface;
import com.sc.en.philosophy.layers.service.MotherBusinessService;
import com.sc.en.philosophy.layers.service.ServiceManagerInterface;
import com.sc.en.philosophy.layers.service.accounts.interfaces.GooglePlusServiceInterface;
import com.sc.en.philosophy.transverse.orms.realm.models.Account;

import io.reactivex.Observable;

public class GooglePlusService extends MotherBusinessService implements GooglePlusServiceInterface {

  private static final String TAG = "GooglePlusService";

  /**
   * The quotes to display (the cache)
   */
  private Account googlePlusAccount = null;

  /**
   *
   */
  private AccountsDaoInterface accountsDaoInterface = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public GooglePlusService(ServiceManagerInterface srvManager) {
    super(srvManager);
    //account = new Account();
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Observable<Account> loadGooglePlusAccountAsync() {

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (googlePlusAccount != null) {
      reload = true;
    }

    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return Observable.just(googlePlusAccount);
    } else {
      // then launch it
      return loadGooglePlusAccountSync();
    }
  }

  private Observable<Account> loadGooglePlusAccountSync() {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    googlePlusAccount = accountsDaoInterface.loadAccountByType("googleplus");
    accountsDaoInterface = null;
    return Observable.just(googlePlusAccount);
  }

  @Override
  public Observable<Account> updateGooglePlusAccountAsync(String identifier, String password) {
    return updateGooglePlusAccountSync(identifier, password);
  }

  private Observable<Account> updateGooglePlusAccountSync(String identifier, String password) {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    googlePlusAccount = accountsDaoInterface.updateAccountByType("googleplus", identifier, password);
    accountsDaoInterface = null;
    return Observable.just(googlePlusAccount);
  }
}
