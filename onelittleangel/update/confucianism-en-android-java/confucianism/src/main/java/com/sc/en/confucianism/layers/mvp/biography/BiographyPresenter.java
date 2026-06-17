package com.sc.en.confucianism.layers.mvp.biography;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.transverse.orms.realm.models.Book;
import com.sc.en.confucianism.transverse.orms.realm.models.Movement;
import com.sc.en.confucianism.R;
import com.sc.en.confucianism.layers.mvp.MotherPresenter;
import com.sc.en.confucianism.transverse.orms.realm.models.Author;

import java.util.ArrayList;
import java.util.List;

public class BiographyPresenter extends MotherPresenter implements BiographyPresenterInterface {

  private BiographyViewInterface biographyViewInterface = null;
  private Author author;
  private Book book;
  private Movement movement;
  private ArrayMap<Integer, ArrayMap<String, String>> presentations;
  private List<View> carouselViews;
  private List<Bitmap> bitmapsView;
  private boolean picturesAlreadyLoaded = false;


  public BiographyPresenter(BiographyViewInterface biographyViewInterface) {
    this.biographyViewInterface = biographyViewInterface;
    this.presentations = new ArrayMap<>();
    carouselViews = new ArrayList<>();
    bitmapsView = new ArrayList<>();
  }

  @Override
  public void loadAuthor(String author) {
    OnelittleAngelApplication.instance.getServiceManager().getAuthorByNameService().loadAuthorByNameAsync(author).subscribe(this::initAuthor);
  }

  @Override
  public Author getAuthor() {
    return author;
  }

  @Override
  public void setAuthor(Author author) {
    this.author = author;
  }

  @Override
  public void loadAuthorPictures(String author) {
    picturesAlreadyLoaded = true;
    OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.authors), author).subscribe(this::initCarouselView);
  }

  @Override
  public void loadBook(String book) {
    OnelittleAngelApplication.instance.getServiceManager().getBookByNameService().loadBookByNameAsync(book).subscribe(this::initBook);
  }

  @Override
  public Book getBook() {
    return book;
  }

  @Override
  public void setBook(Book book) {
    this.book = book;
  }

  @Override
  public void loadBookPictures(String book) {
    picturesAlreadyLoaded = true;
    OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.books), book).subscribe(this::initCarouselView);
  }

  @Override
  public void loadMovement(String movement) {
    OnelittleAngelApplication.instance.getServiceManager().getMovementByNameService().loadMovementByNameAsync(movement).subscribe(this::initMovement);
  }

  @Override
  public Movement getMovement() {
    return movement;
  }

  @Override
  public void setMovement(Movement movement) {
    this.movement = movement;
  }

  @Override
  public void loadMovementPictures(String movement) {
    picturesAlreadyLoaded = true;
    OnelittleAngelApplication.instance.getServiceManager().getPicturesForCarouselService().loadBitmapsForCarouselAsync(OnelittleAngelApplication.instance.getResources().getString(R.string.movements), movement).subscribe(this::initCarouselView);
  }

  @Override
  public ArrayMap<Integer, ArrayMap<String, String>> getPresentations() {
    return presentations;
  }

  @Override
  public void setPresentations(ArrayMap<Integer, ArrayMap<String, String>> presentations) {
    this.presentations = presentations;
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  private void initMovement(Movement movement) {
    this.movement = movement;

    ArrayMap<String, String> presentation = new ArrayMap<>();
    presentation.put("", movement.getPresentation().getPresentation());
    presentations.put(0, presentation);

    if (movement.getPresentation().getPresentationTitle1() != null && !movement.getPresentation().getPresentationTitle1().equals("")) {
      ArrayMap<String, String> presentation1 = new ArrayMap<>();
      presentation1.put(movement.getPresentation().getPresentationTitle1(), movement.getPresentation().getPresentation1());
      presentations.put(1, presentation1);
    }

    if (movement.getPresentation().getPresentationTitle2() != null && !movement.getPresentation().getPresentationTitle2().equals("")) {
      ArrayMap<String, String> presentation2 = new ArrayMap<>();
      presentation2.put(movement.getPresentation().getPresentationTitle2(), movement.getPresentation().getPresentation2());
      presentations.put(2, presentation2);
    }

    if (movement.getPresentation().getPresentationTitle3() != null && !movement.getPresentation().getPresentationTitle3().equals("")) {
      ArrayMap<String, String> presentation3 = new ArrayMap<>();
      presentation3.put(movement.getPresentation().getPresentationTitle3(), movement.getPresentation().getPresentation3());
      presentations.put(3, presentation3);
    }

    if (movement.getPresentation().getPresentationTitle4() != null && !movement.getPresentation().getPresentationTitle4().equals("")) {
      ArrayMap<String, String> presentation4 = new ArrayMap<>();
      presentation4.put(movement.getPresentation().getPresentationTitle4(), movement.getPresentation().getPresentation4());
      presentations.put(4, presentation4);
    }
    biographyViewInterface.updatePresentation();
  }

  private void initAuthor(Author author) {
    this.author = author;

    ArrayMap<String, String> presentation = new ArrayMap<>();
    presentation.put("", author.getPresentation().getPresentation());
    presentations.put(0, presentation);

    if (author.getPresentation().getPresentationTitle1() != null && !author.getPresentation().getPresentationTitle1().equals("")) {
      ArrayMap<String, String> presentation1 = new ArrayMap<>();
      presentation1.put(author.getPresentation().getPresentationTitle1(), author.getPresentation().getPresentation1());
      presentations.put(1, presentation1);
    }

    if (author.getPresentation().getPresentationTitle2() != null && !author.getPresentation().getPresentationTitle2().equals("")) {
      ArrayMap<String, String> presentation2 = new ArrayMap<>();
      presentation2.put(author.getPresentation().getPresentationTitle2(), author.getPresentation().getPresentation2());
      presentations.put(2, presentation2);
    }

    if (author.getPresentation().getPresentationTitle3() != null && !author.getPresentation().getPresentationTitle3().equals("")) {
      ArrayMap<String, String> presentation3 = new ArrayMap<>();
      presentation3.put(author.getPresentation().getPresentationTitle3(), author.getPresentation().getPresentation3());
      presentations.put(3, presentation3);
    }

    if (author.getPresentation().getPresentationTitle4() != null && !author.getPresentation().getPresentationTitle4().equals("")) {
      ArrayMap<String, String> presentation4 = new ArrayMap<>();
      presentation4.put(author.getPresentation().getPresentationTitle4(), author.getPresentation().getPresentation4());
      presentations.put(4, presentation4);
    }

    if (author.getBibliographie() != null && !author.getBibliographie().equals("")) {
      ArrayMap<String, String> bibliography = new ArrayMap<>();
      bibliography.put(OnelittleAngelApplication.instance.getResources().getString(R.string.bibliography), author.getBibliographie());
      presentations.put(5, bibliography);
    }
    biographyViewInterface.updatePresentation();
  }

  private void initCarouselView(List<Bitmap> carouselViews) {
    this.bitmapsView = carouselViews;
  }

  private void initBook(Book book) {
    this.book = book;
    ArrayMap<String, String> presentation = new ArrayMap<>();
    presentation.put("", book.getPresentation().getPresentation());
    presentations.put(0, presentation);
    if (book.getPresentation().getPresentationTitle1() != null && !book.getPresentation().getPresentationTitle1().equals("")) {
      ArrayMap<String, String> presentation1 = new ArrayMap<>();
      presentation1.put(book.getPresentation().getPresentationTitle1(), book.getPresentation().getPresentation1());
      presentations.put(1, presentation1);
    }

    if (book.getPresentation().getPresentationTitle2() != null && !book.getPresentation().getPresentationTitle2().equals("")) {
      ArrayMap<String, String> presentation2 = new ArrayMap<>();
      presentation2.put(book.getPresentation().getPresentationTitle2(), book.getPresentation().getPresentation2());
      presentations.put(2, presentation2);
    }

    if (book.getPresentation().getPresentationTitle3() != null && !book.getPresentation().getPresentationTitle3().equals("")) {
      ArrayMap<String, String> presentation3 = new ArrayMap<>();
      presentation3.put(book.getPresentation().getPresentationTitle3(), book.getPresentation().getPresentation3());
      presentations.put(3, presentation3);
    }

    if (book.getPresentation().getPresentationTitle4() != null && !book.getPresentation().getPresentationTitle4().equals("")) {
      ArrayMap<String, String> presentation4 = new ArrayMap<>();
      presentation4.put(book.getPresentation().getPresentationTitle4(), book.getPresentation().getPresentation4());
      presentations.put(4, presentation4);
    }
    biographyViewInterface.updatePresentation();
  }

  public BiographyViewInterface getBiographyViewInterface() {
    return biographyViewInterface;
  }

  @Override
  public void setBiographyViewInterface(BiographyViewInterface biographyViewInterface) {
    this.biographyViewInterface = biographyViewInterface;
  }

  @Override
  public List<View> getCarouselView() {
    return carouselViews;
  }

  @Override
  public void setCarouselView(List<View> carouselView) {
    this.carouselViews = carouselView;
  }

  @Override
  public List<Bitmap> getBitmapsView() {
    return bitmapsView;
  }

  @Override
  public void setBitmapsView(List<Bitmap> bitmapsView) {
    this.bitmapsView = bitmapsView;
  }

  @Override
  public void releaseBitmapsCarousel() {
  }

  @Override
  public boolean picturesAlreadyLoaded() {
    return picturesAlreadyLoaded;
  }
}
