package com.sc.en.confucianism.layers.mvp.tablecontents.models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class Theme extends BaseEntity {

  public static final Creator<Theme> CREATOR = new Creator<Theme>() {
    @Override
    public Theme createFromParcel(Parcel in) {
      return new Theme(in);
    }

    @Override
    public Theme[] newArray(int size) {
      return new Theme[size];
    }
  };

  public int idCategory;
  private int idParent;
  private String category;

  private int nbQuotes;

  private List<Theme> themes;

  public Theme() {
    super();
  }

  public Theme(int idCategory, int idParent, String category, String presentation, int alias,
               String sourcePresentation, String aliasUrl, String mainpictureName,
               int nbQuotes, List<Theme> themes) {
    this.idCategory = idCategory;
    this.idParent = idParent;
    this.category = category;
    this.nbQuotes = nbQuotes;
    this.themes = themes;
  }

  private Theme(Parcel in) {
    super(in);
    this.idCategory = in.readInt();
    this.idParent = in.readInt();
    this.category = in.readString();
    this.nbQuotes = in.readInt();
    this.themes = new ArrayList<>();
    in.readTypedList(themes, Theme.CREATOR);
  }



  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(idCategory);
    dest.writeInt(idParent);
    dest.writeString(category);
    dest.writeInt(nbQuotes);
  }
}
