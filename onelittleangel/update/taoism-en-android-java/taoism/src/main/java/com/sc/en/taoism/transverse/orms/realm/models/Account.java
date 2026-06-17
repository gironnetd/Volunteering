package com.sc.en.taoism.transverse.orms.realm.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Account extends RealmObject {

  @PrimaryKey
  private long idAccount;
  private String type;
  private String identifier;
  private String password;

  public Account() {
  }

  public long getIdAccount() {
    return idAccount;
  }

  public void setIdAccount(int idAccount) {
    this.idAccount = idAccount;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
