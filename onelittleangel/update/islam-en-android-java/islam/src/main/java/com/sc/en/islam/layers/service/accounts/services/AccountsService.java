package com.sc.en.islam.layers.service.accounts.services;

import com.sc.en.islam.layers.service.MotherBusinessService;
import com.sc.en.islam.layers.service.ServiceManagerInterface;
import com.sc.en.islam.transverse.orms.realm.models.Account;
import com.sc.en.islam.layers.service.accounts.interfaces.AccountsServiceInterface;

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
