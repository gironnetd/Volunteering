package com.sc.en.bouddhism.layers.service.accounts.interfaces;

import com.sc.en.bouddhism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.bouddhism.transverse.orms.realm.models.Account;

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
