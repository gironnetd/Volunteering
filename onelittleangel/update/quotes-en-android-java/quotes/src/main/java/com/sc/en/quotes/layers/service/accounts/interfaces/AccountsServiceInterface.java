package com.sc.en.quotes.layers.service.accounts.interfaces;

import com.sc.en.quotes.layers.service.MotherBusinessServiceInterface;
import com.sc.en.quotes.transverse.orms.realm.models.Account;

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
