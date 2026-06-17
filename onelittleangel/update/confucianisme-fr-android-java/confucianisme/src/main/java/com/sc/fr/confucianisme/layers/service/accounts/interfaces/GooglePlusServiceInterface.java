package com.sc.fr.confucianisme.layers.service.accounts.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Account;

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
