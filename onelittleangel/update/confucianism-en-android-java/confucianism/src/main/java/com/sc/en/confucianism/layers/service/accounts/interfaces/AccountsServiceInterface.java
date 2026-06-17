package com.sc.en.confucianism.layers.service.accounts.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.confucianism.transverse.orms.realm.models.Account;

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
