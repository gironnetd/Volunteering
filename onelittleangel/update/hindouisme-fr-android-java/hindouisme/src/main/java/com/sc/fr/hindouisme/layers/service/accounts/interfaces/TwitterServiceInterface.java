package com.sc.fr.hindouisme.layers.service.accounts.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Account;

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
