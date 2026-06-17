package com.sc.fr.hindouisme.layers.service.accounts.interfaces;

import com.sc.fr.hindouisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.hindouisme.transverse.orms.realm.models.Account;

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
