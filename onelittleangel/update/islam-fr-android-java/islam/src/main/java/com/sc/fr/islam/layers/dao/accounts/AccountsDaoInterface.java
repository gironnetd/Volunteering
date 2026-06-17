package com.sc.fr.islam.layers.dao.accounts;

import com.sc.fr.islam.transverse.orms.realm.models.Account;

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
