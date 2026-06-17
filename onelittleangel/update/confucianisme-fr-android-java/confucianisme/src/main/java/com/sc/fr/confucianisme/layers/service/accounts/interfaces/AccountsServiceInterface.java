package com.sc.fr.confucianisme.layers.service.accounts.interfaces;

import com.sc.fr.confucianisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.confucianisme.transverse.orms.realm.models.Account;

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
