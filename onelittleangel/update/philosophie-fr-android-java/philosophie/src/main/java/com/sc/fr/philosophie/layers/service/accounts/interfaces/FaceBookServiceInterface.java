package com.sc.fr.philosophie.layers.service.accounts.interfaces;

import com.sc.fr.philosophie.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.philosophie.transverse.orms.realm.models.Account;

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
