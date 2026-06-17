package com.sc.fr.philosophie.layers.mvp.settings.fragments.mails;

import com.sc.fr.philosophie.OnelittleAngelApplication;
import com.sc.fr.philosophie.layers.mvp.MotherPresenter;
import com.sc.fr.philosophie.transverse.orms.realm.models.Account;

public class MailPresenter extends MotherPresenter implements MailPresenterInterface {

  private Account mailAccount;
  private MailViewInterface mailViewInterface = null;

  public MailPresenter(MailViewInterface mailViewInterface) {
    this.mailViewInterface = mailViewInterface;
  }

  @Override
  public void loadMailAccount() {
    OnelittleAngelApplication.instance.getServiceManager().getMailService().loadMailAccountAsync().subscribe(this::initMailAccount);
  }

  @Override
  public Account getMailAccount() {
    return mailAccount;
  }

  @Override
  public void updateMailAccount(String identifier, String password) {
    OnelittleAngelApplication.instance.getServiceManager().getMailService().updateMaiAccountAsync(identifier, password).subscribe(this::initMailAccount);
  }


  /***********************************************************
   *  Listening for services response
   **********************************************************/

  private void initMailAccount(Account mailAccount) {
    this.mailAccount = mailAccount;
    mailViewInterface.updateMailAccount();
  }
}
