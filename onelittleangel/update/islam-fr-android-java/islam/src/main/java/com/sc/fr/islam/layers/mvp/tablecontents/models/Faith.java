package com.sc.fr.islam.layers.mvp.tablecontents.models;

import android.os.Parcel;

import java.util.ArrayList;

public class Faith extends BaseEntity {

  public static final Creator<Faith> CREATOR = new Creator<Faith>() {
    @Override
    public Faith createFromParcel(Parcel in) {
      return new Faith(in);
    }

    @Override
    public Faith[] newArray(int size) {
      return new Faith[size];
    }
  };

  public Faith() {
    super();
  }

  public Faith(Faith f){
    {
      this.idCourant = f.idCourant;
      this.idParent = f.idParent;
      this.name = f.name;
      this.faith = f.faith;
      this.nbQuotes = f.nbQuotes;
      this.nbAuthors = f.nbAuthors;
      this.nbAuthorsQuotes = f.nbAuthorsQuotes;
      this.nbBooks = f.nbBooks;
      this.nbBooksQuotes = f.nbBooksQuotes;
      this.checked = f.checked;
      this.nbTotalQuotes = f.nbTotalQuotes;
      this.nbTotalAuthors = f.nbTotalAuthors;
      this.nbTotalBooks = f.nbTotalBooks;
      this.nbSubcourants = f.nbSubcourants;
      this.nbAuthorsSubcourants = f.nbAuthorsSubcourants;
      this.faiths = f.faiths;
      this.authorBooks = f.authorBooks;
    }
  }

  private Faith(Parcel in) {
    super(in);
    this.idCourant = in.readInt();
    this.idParent = in.readInt();
    this.nbQuotes = in.readInt();
    this.nbAuthors = in.readInt();
    this.nbAuthorsQuotes = in.readInt();
    this.nbBooks = in.readInt();
    this.nbBooksQuotes = in.readInt();
    this.checked = in.readInt();
    this.nbTotalQuotes = in.readInt();
    this.nbTotalAuthors = in.readInt();
    this.nbTotalBooks = in.readInt();
    this.nbSubcourants = in.readInt();
    this.nbAuthorsSubcourants = in.readInt();
//    this.faiths = new ArrayList<>();
//    in.readTypedList(faiths, Faith.CREATOR);
    this.authorBooks = new ArrayList<>();
    in.readTypedList(authorBooks, AuthorBook.CREATOR);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(idCourant);
    dest.writeInt(idParent);
    dest.writeInt(nbQuotes);
    dest.writeInt(nbAuthors);
    dest.writeInt(nbAuthorsQuotes);
    dest.writeInt(nbBooks);
    dest.writeInt(nbBooksQuotes);
    dest.writeInt(checked);
    dest.writeInt(nbTotalQuotes);
    dest.writeInt(nbTotalAuthors);
    dest.writeInt(nbTotalBooks);
    dest.writeInt(nbSubcourants);
    dest.writeInt(nbAuthorsSubcourants);
  }
}
