package com.sc.en.hindouism.layers.service.accounts.services;

import com.sc.en.hindouism.layers.service.MotherBusinessService;
import com.sc.en.hindouism.layers.service.ServiceManagerInterface;
import com.sc.en.hindouism.layers.service.accounts.interfaces.MailServiceInterface;
import com.sc.en.hindouism.transverse.orms.realm.models.Account;
import com.sc.en.hindouism.injector.Injector;
import com.sc.en.hindouism.layers.dao.accounts.AccountsDaoInterface;

import io.reactivex.Observable;

public class MailService extends MotherBusinessService implements MailServiceInterface {

  private static final String TAG = "MailService";

  /**
   * The quotes to display (the cache)
   */
  private Account mailAccount = null;

  /**
   *
   */
  private AccountsDaoInterface accountsDaoInterface = null;


  /**
   * Constructor
   *
   * @param srvManager
   */
  public MailService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Observable<Account> loadMailAccountAsync() {

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    if (mailAccount != null) {
      reload = true;
    }

    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      //  postQuotesByIdAuthorDataLoadedEvent(quotesByIdAuthorList.get(idAuthor),idAuthor);
      return Observable.just(mailAccount);
    } else {
      // then launch it
      return loadMailAccountSync();
    }
  }

  private Observable<Account> loadMailAccountSync() {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    mailAccount = accountsDaoInterface.loadAccountByType("mail");
    accountsDaoInterface = null;
    return Observable.just(mailAccount);
  }

  @Override
  public Observable<Account> updateMaiAccountAsync(String identifier, String password) {
    return updateMaiAccountSync(identifier, password);
  }

  private Observable<Account> updateMaiAccountSync(String identifier, String password) {
    accountsDaoInterface = Injector.getDaoManager().getAccountsDao();
    mailAccount = accountsDaoInterface.updateAccountByType("mail", identifier, password);
    accountsDaoInterface = null;
    return Observable.just(mailAccount);
  }
}
