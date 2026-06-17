package com.sc.en.quotes.layers.mvp.tablecontents;

import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.mvp.MotherPresenter;

public class TableContentsPresenter extends MotherPresenter implements TableContentsPresenterInterface {

  public TableContentsPresenter(TableContentsViewInterface tableContentsViewInterface) {
  }

  @Override
  public void toggleQuoteOfDayIsFavorites(int idQuote) {
    OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(idQuote);
  }
}
