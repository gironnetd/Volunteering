package com.sc.en.islam.layers.service.accounts.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.en.islam.transverse.orms.realm.models.Account;

import io.reactivex.Observable;

public interface AccountsServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Account> loadAccountAsync(String type);

  /**
   *
   * @return
   */
  Observable<Account> updateAccountAsync(String type, String identifier, String password);
}
