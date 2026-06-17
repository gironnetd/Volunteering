package com.sc.en.philosophy.layers.service.accounts.interfaces;

import com.sc.en.philosophy.layers.service.MotherBusinessServiceInterface;
import com.sc.en.philosophy.transverse.orms.realm.models.Account;

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
