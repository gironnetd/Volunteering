package com.sc.fr.philosophie.layers.service.accounts.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Account;

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
