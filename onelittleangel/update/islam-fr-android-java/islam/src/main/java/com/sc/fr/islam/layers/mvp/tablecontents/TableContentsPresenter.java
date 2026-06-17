package com.sc.fr.islam.layers.mvp.tablecontents;

import com.sc.fr.islam.OnelittleAngelApplication;
import com.sc.fr.islam.layers.mvp.MotherPresenter;

public class TableContentsPresenter extends MotherPresenter implements TableContentsPresenterInterface {

  public TableContentsPresenter(TableContentsViewInterface tableContentsViewInterface) {
  }

  @Override
  public void toggleQuoteOfDayIsFavorites(int idQuote) {
    OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(idQuote);
  }

}
