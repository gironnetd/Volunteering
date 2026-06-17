package com.sc.en.philosophy.layers.service.accounts.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;
import com.sc.en.philosophy.transverse.orms.realm.models.Account;

import io.reactivex.Observable;

public interface TwitterServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Account> loadTwitterAccountAsync();

  /**
   *
   * @return
   */
  Observable<Account> updateTwitterAccountAsync(String identifier, String password);
}
