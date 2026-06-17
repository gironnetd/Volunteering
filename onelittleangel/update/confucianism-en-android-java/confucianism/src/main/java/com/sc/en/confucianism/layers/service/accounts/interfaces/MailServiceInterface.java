package com.sc.en.confucianism.layers.service.accounts.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.confucianism.transverse.orms.realm.models.Account;

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
