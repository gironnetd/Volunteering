package com.sc.en.confucianism.layers.service.accounts.interfaces;

import com.sc.en.confucianism.layers.service.MotherBusinessServiceInterface;
import com.sc.en.confucianism.transverse.orms.realm.models.Account;

import io.reactivex.Observable;

public interface FaceBookServiceInterface extends MotherBusinessServiceInterface {

  /**
   *
   * @return
   */
  Observable<Account> loadFaceBookAccountAsync();

  /**
   *
   * @return
   */
  Observable<Account> updateFaceBookAccountAsync(String identifier, String password);
}
