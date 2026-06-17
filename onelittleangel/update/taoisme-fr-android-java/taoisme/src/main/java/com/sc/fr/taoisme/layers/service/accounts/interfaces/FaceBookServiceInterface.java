package com.sc.fr.taoisme.layers.service.accounts.interfaces;

import com.sc.fr.taoisme.layers.service.MotherBusinessServiceInterface;
import com.sc.fr.taoisme.transverse.orms.realm.models.Account;

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
