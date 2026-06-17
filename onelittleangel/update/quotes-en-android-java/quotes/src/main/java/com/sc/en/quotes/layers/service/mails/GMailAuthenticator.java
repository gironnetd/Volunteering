package com.sc.en.quotes.layers.service.mails;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class GMailAuthenticator extends Authenticator {
  private final String user;
  private final String pw;
  public GMailAuthenticator()
  {
    super();
    this.user = "onelittleangellapplication@gmail.com";
    this.pw = "Shankarananda1234";
  }
  public PasswordAuthentication getPasswordAuthentication()
  {
    return new PasswordAuthentication(user, pw);
  }
}
