package com.sc.fr.onelittleangel.layers.mvp.settings.fragments.mails;

import com.sc.fr.onelittleangel.transverse.orms.realm.models.Account;

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
