package com.sc.fr.confucianisme.layers.service.accounts.services;

import com.sc.fr.confucianisme.layers.service.MotherBusinessService;
import com.sc.fr.confucianisme.layers.service.ServiceManagerInterface;
import com.sc.fr.confucianisme.layers.service.accounts.interfaces.AccountsServiceInterface;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Account;

import io.reactivex.Observable;

public class AccountsService extends MotherBusinessService implements AccountsServiceInterface {

  /**
   * Constructor
   *
   * @param srvManager
   */
  public AccountsService(ServiceManagerInterface srvManager) {
    super(srvManager);
  }

  @Override
  public void onDestroy() {

  }

  @Override
  public Observable<Account> loadAccountAsync(String type) {
    return null;
  }

  @Override
  public Observable<Account> updateAccountAsync(String type, String identifier, String password) {
    return null;
  }
}
