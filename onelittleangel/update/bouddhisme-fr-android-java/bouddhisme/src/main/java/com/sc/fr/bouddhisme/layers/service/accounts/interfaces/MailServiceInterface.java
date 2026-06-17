package com.sc.fr.bouddhisme.layers.service.accounts.interfaces;

import com.sc.fr.bouddhisme.transverse.orms.realm.models.Account;
import com.sc.fr.bouddhisme.layers.service.MotherBusinessServiceInterface;

import io.reactivex.Observable;

public interface MailServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Account> loadMailAccountAsync();

  /**
   *
   * @return
   */
  Observable<Account> updateMaiAccountAsync(String identifier, String password);

}
