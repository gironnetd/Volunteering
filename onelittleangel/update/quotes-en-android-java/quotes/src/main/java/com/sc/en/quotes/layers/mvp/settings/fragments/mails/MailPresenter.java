package com.sc.en.quotes.layers.mvp.settings.fragments.mails;

import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.transverse.orms.realm.models.Account;
import com.sc.en.quotes.layers.mvp.MotherPresenter;

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
