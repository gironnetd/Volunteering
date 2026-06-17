package com.sc.fr.bouddhisme.layers.mvp.tablecontents;

import com.sc.fr.bouddhisme.OnelittleAngelApplication;
import com.sc.fr.bouddhisme.layers.mvp.MotherPresenter;

public class TableContentsPresenter extends MotherPresenter implements TableContentsPresenterInterface {

  public TableContentsPresenter(TableContentsViewInterface tableContentsViewInterface) {
  }

  @Override
  public void toggleQuoteOfDayIsFavorites(int idQuote) {
    OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(idQuote);
  }

}
