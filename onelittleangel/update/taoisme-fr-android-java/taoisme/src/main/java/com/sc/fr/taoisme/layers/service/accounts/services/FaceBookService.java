package com.sc.fr.taoisme.layers.service.accounts.services;

import com.sc.fr.taoisme.injector.Injector;
import com.sc.fr.taoisme.layers.dao.accounts.AccountsDaoInterface;
import com.sc.fr.taoisme.layers.service.MotherBusinessService;
import com.sc.fr.taoisme.layers.service.ServiceManagerInterface;
import com.sc.fr.taoisme.layers.service.accounts.interfaces.FaceBookServiceInterface;
import com.sc.fr.taoisme.transverse.orms.realm.models.Account;

import io.reactivex.Observable;

public class FaceBookService extends MotherBusinessService implements FaceBookServiceInterface {

  private static final String TAG = "FaceBookService";

  /**
   * The quotes to display (the cache)
   */
  private Account facebookAccount = null;

  /**
   *
   */
  private AccountsDaoInterface accountsDaoInterface = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public FaceBookService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Observable<Account> loadFaceBookAccountAsync() {

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (facebookAccount != null) {
      reload = true;
    }

    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return Observable.just(facebookAccount);
      //return facebookAccount.asObservable();
    } else {
      return loadFaceBookAccountSync();
    }
  }

  private Observable<Account> loadFaceBookAccountSync() {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    facebookAccount = accountsDaoInterface.loadAccountByType("facebook");
    accountsDaoInterface = null;
    return Observable.just(facebookAccount);
  }

  @Override
  public Observable<Account> updateFaceBookAccountAsync(String identifier, String password) {
    return updateFaceBookAccountSync(identifier, password);
  }

  private Observable<Account> updateFaceBookAccountSync(String identifier, String password) {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    facebookAccount = accountsDaoInterface.updateAccountByType("facebook", identifier, password);
    accountsDaoInterface = null;
    return Observable.just(facebookAccount);
  }
}
