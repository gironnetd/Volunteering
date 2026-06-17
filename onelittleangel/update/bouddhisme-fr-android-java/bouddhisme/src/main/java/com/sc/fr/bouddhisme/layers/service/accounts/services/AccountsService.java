package com.sc.fr.bouddhisme.layers.service.accounts.services;

import com.sc.fr.bouddhisme.layers.service.MotherBusinessService;
import com.sc.fr.bouddhisme.layers.service.accounts.interfaces.AccountsServiceInterface;
import com.sc.fr.bouddhisme.transverse.orms.realm.models.Account;
import com.sc.fr.bouddhisme.layers.service.ServiceManagerInterface;

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
