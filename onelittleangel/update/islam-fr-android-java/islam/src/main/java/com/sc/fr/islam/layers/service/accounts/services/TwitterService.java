package com.sc.fr.islam.layers.service.accounts.services;

import com.sc.fr.islam.layers.dao.accounts.AccountsDaoInterface;
import com.sc.fr.islam.layers.service.MotherBusinessService;
import com.sc.fr.islam.layers.service.accounts.interfaces.TwitterServiceInterface;
import com.sc.fr.islam.transverse.orms.realm.models.Account;
import com.sc.fr.islam.injector.Injector;
import com.sc.fr.islam.layers.service.ServiceManagerInterface;

import io.reactivex.Observable;

public class TwitterService extends MotherBusinessService implements TwitterServiceInterface {

  private static final String TAG = "TwitterService";

  /**
   * The quotes to display (the cache)
   */
  private Account twitterAccount = null;

  /**
   *
   */
  private AccountsDaoInterface accountsDaoInterface = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public TwitterService(ServiceManagerInterface srvManager) {
    super(srvManager);
  //  account = new Account();
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Observable<Account> loadTwitterAccountAsync() {

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (twitterAccount != null) {
      reload = true;
    }

    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return Observable.just(twitterAccount);
    } else {
      return loadTwitterAccountSync();
    }
  }

  private Observable<Account> loadTwitterAccountSync() {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    twitterAccount = accountsDaoInterface.loadAccountByType("twitter");
    accountsDaoInterface = null;
    return Observable.just(twitterAccount);
  }

  @Override
  public Observable<Account> updateTwitterAccountAsync(String identifier, String password) {
    return updateTwitterAccountSync(identifier, password);
  }

  private Observable<Account> updateTwitterAccountSync(String identifier, String password) {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    twitterAccount = accountsDaoInterface.updateAccountByType("twitter", identifier, password);
    accountsDaoInterface = null;
    return Observable.just(twitterAccount);
  }

  private class DaoUpdateTwitterAccountRunnable implements Runnable {

    final String identifier;
    final String password;

    public DaoUpdateTwitterAccountRunnable(String identifier, String password) {
      this.identifier = identifier;
      this.password = password;
    }

//    @Override
//    public Observable<Account> call() throws Exception {
//      updateTwitterAccountSync(identifier,password);
//    }

    @Override
    public void run() {
      updateTwitterAccountSync(identifier,password);
    }
  }
}
