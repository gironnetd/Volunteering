package com.sc.fr.bouddhisme.layers.service.accounts.interfaces;

import com.sc.fr.bouddhisme.transverse.orms.realm.models.Account;
import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

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
