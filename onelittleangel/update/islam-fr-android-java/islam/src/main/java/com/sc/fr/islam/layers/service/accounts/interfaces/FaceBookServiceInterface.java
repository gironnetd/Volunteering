package com.sc.fr.islam.layers.service.accounts.interfaces;

import com.sc.fr.islam.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.islam.transverse.orms.realm.models.Account;

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
