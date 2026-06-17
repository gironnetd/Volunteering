package com.sc.fr.taoisme.layers.service.accounts.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.taoisme.transverse.orms.realm.models.Account;

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
