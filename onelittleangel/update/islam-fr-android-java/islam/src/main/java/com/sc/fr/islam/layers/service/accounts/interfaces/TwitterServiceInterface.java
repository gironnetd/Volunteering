package com.sc.fr.islam.layers.service.accounts.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.islam.transverse.orms.realm.models.Account;

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
