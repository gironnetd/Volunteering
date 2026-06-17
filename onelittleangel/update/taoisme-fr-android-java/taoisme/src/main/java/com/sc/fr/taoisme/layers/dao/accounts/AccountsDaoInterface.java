package com.sc.fr.taoisme.layers.dao.accounts;

import com.sc.fr.taoisme.transverse.orms.realm.models.Account;

public interface AccountsDaoInterface {

  /**
   *
   * @param type
   * @return
   */
  Account loadAccountByType(String type);

  /**
   *
   * @param type
   * @param identifier
   * @param password
   * @return
   */
  Account updateAccountByType(String type, String identifier, String password);
}
