package com.sc.en.philosophy.layers.mvp.tablecontents.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public  class BaseEntity implements Parcelable {

  public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
    @Override
    public BaseEntity createFromParcel(Parcel in) {
      return new BaseEntity(in);
    }

    @Override
    public BaseEntity[] newArray(int size) {
      return new BaseEntity[size];
    }
  };

  public int idCourant;
  int idParent;
  public String faith;

  int nbQuotes;
  public int nbAuthors;
  int nbAuthorsQuotes;
  public int nbBooks;
  int nbBooksQuotes;
  int checked;
  int nbTotalQuotes;
  int nbTotalAuthors;
  int nbTotalBooks;
  int nbSubcourants;
  int nbAuthorsSubcourants;
  public String name;
  public int number;

  List<Faith> faiths;

  public List<AuthorBook> authorBooks;

  public List<BaseEntity> baseEntities;

  BaseEntity() {
  }

  BaseEntity(Parcel in) {
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }
}
