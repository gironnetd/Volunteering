package com.sc.en.taoism.layers.service.accounts.interfaces;

import com.sc.en.taoism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.taoism.transverse.orms.realm.models.Account;

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
