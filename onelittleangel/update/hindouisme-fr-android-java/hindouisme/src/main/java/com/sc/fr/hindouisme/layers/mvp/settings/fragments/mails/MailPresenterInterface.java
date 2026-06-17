package com.sc.fr.hindouisme.layers.mvp.settings.fragments.mails;

import com.sc.fr.hindouisme.transverse.orms.realm.models.Account;

public interface MailPresenterInterface {

  /**
   *
   */
  void loadMailAccount();

  /**
   *
   * @return
   */
  Account getMailAccount();

  /**
   *
   * @return
   */
  void updateMailAccount(String identifier, String password);
}
