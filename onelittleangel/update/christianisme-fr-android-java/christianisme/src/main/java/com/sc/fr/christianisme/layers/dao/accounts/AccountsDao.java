package com.sc.fr.christianisme.layers.dao.accounts;

import com.sc.fr.christianisme.layers.dao.DaoManager;
import com.sc.fr.christianisme.transverse.orms.realm.models.Account;

import io.realm.Realm;

public class AccountsDao implements AccountsDaoInterface {

  private Realm realm;
  private Account account;
  /***********************************************************
   * Constructor
   **********************************************************/
  // private AuthorsDao(){};
  public AccountsDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element
  }

  @Override
  public Account loadAccountByType(String type) {
    realm = Realm.getDefaultInstance();

    realm.executeTransaction(realm1 -> account = realm1.where(Account.class).equalTo("type", type).findFirst());
    return account;
  }

  @Override
  public Account updateAccountByType(String type, String identifier, String password) {
    realm = Realm.getDefaultInstance();

    realm.executeTransaction(realm1 -> {
      account = realm1.where(Account.class).equalTo("type", type).findFirst();
      account.setIdentifier(identifier);
      account.setPassword(password);
      realm1.copyToRealmOrUpdate(account);
    });
    return account;
  }
}
