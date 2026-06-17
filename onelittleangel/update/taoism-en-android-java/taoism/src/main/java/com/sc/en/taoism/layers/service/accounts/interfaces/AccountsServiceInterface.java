package com.sc.en.taoism.layers.service.accounts.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.taoism.transverse.orms.realm.models.Account;

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
