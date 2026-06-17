package com.sc.en.taoism.layers.mvp.settings.fragments.mails;

import com.sc.en.taoism.transverse.orms.realm.models.Account;

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
