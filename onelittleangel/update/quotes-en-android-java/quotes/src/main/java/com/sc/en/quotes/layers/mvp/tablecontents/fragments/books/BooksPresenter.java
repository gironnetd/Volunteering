package com.sc.en.quotes.layers.mvp.tablecontents.fragments.books;

import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.mvp.tablecontents.models.BaseEntity;
import com.sc.en.quotes.layers.mvp.MotherPresenter;
import com.sc.en.quotes.layers.mvp.tablecontents.fragments.PresenterInterface;
import com.sc.en.quotes.layers.mvp.tablecontents.fragments.ViewInterface;
import com.sc.en.quotes.transverse.eventbus.events.movements.MovementsWithBooksLoadedEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class BooksPresenter extends MotherPresenter implements PresenterInterface {

  private List<BaseEntity> books;

  private ViewInterface booksViewInterface = null;

  public BooksPresenter(ViewInterface booksViewInterface) {
    this.booksViewInterface = booksViewInterface;
    if(books == null) {
      books = new ArrayList<>();
    }
  }

  /**
   *
   */
  @Override
  public void loadMovements() {
    OnelittleAngelApplication.instance.getServiceManager().getMovementsWithBooksService().loadMovementsWithBooksAsync();
  }

  /**
   * @return
   */
  @Override
  public List<BaseEntity> getMovements() {
    return books;
  }

  @Override
  public void removeMovementsOfFragment() {
    books = null;
    booksViewInterface = null;
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(MovementsWithBooksLoadedEvent event){
    books.addAll(event.getMovementsWithBooks());
    booksViewInterface.updateMovements();
  }
}
