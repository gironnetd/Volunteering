package com.sc.en.hindouism.layers.mvp.tablecontents.models;

import android.os.Parcel;

public class AuthorBook extends BaseEntity {

  public static final Creator<AuthorBook> CREATOR = new Creator<AuthorBook>() {
    @Override
    public AuthorBook createFromParcel(Parcel in) {
      return new AuthorBook(in);
    }

    @Override
    public AuthorBook[] newArray(int size) {
      return new AuthorBook[size];
    }
  };

  private int idAuthor;
  private String surname;
  private String details;
  private String bibliographie;


  public AuthorBook(int idAuthor, int idCourant, String name, String surname, int number) {
    this.idAuthor = idAuthor;
    this.idCourant = idCourant;
    this.name = name;
    this.surname = surname;
    this.number = number;
  }

  public AuthorBook() {
    super();
  }

  public AuthorBook(AuthorBook a){
    this.idAuthor = a.idAuthor;
    this.idCourant = a.idCourant;
    this.faith = a.faith;
    this.name = a.name;
    this.surname = a.surname;
    this.details = a.details;
    this.bibliographie = a.bibliographie;

  }

  private AuthorBook(Parcel in) {
    super(in);
    this.idAuthor = in.readInt();
    this.idCourant = in.readInt();
    this.name = in.readString();
    this.surname = in.readString();
    this.details = in.readString();
    this.bibliographie = in.readString() ;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(idAuthor);
    dest.writeInt(idCourant);
    dest.writeString(name);
    dest.writeString(surname);
    dest.writeString(details);
    dest.writeString(bibliographie);

  }
}
