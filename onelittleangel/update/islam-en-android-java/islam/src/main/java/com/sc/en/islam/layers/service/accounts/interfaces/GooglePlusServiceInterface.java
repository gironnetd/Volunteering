package com.sc.en.islam.layers.service.accounts.interfaces;

import com.sc.en.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.en.islam.transverse.orms.realm.models.Account;

import io.reactivex.Observable;

public interface GooglePlusServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Account> loadGooglePlusAccountAsync();

  /**
   *
   * @return
   */
  Observable<Account> updateGooglePlusAccountAsync(String identifier, String password);
}
