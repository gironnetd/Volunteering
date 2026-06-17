package com.sc.en.bouddhism.layers.mvp.tablecontents;

import com.sc.en.bouddhism.OnelittleAngelApplication;
import com.sc.en.bouddhism.layers.mvp.MotherPresenter;

public class TableContentsPresenter extends MotherPresenter implements TableContentsPresenterInterface {

  public TableContentsPresenter(TableContentsViewInterface tableContentsViewInterface) {
  }

  @Override
  public void toggleQuoteOfDayIsFavorites(int idQuote) {
    OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(idQuote);
  }

}
