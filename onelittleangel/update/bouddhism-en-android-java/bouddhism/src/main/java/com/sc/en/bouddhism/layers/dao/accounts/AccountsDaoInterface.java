package com.sc.en.bouddhism.layers.dao.accounts;

import com.sc.en.bouddhism.transverse.orms.realm.models.Account;

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
