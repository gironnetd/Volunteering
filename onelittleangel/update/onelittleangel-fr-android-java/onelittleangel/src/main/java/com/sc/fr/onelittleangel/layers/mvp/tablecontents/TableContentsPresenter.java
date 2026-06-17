package com.sc.fr.onelittleangel.layers.mvp.tablecontents;

import com.sc.fr.onelittleangel.OnelittleAngelApplication;
import com.sc.fr.onelittleangel.layers.mvp.MotherPresenter;

public class TableContentsPresenter extends MotherPresenter implements TableContentsPresenterInterface {

  public TableContentsPresenter(TableContentsViewInterface tableContentsViewInterface) {
  }

  @Override
  public void toggleQuoteOfDayIsFavorites(int idQuote) {
    OnelittleAngelApplication.instance.getServiceManager().getUpdateQuoteService().toggleQuoteIsFavoritesAsync(idQuote);
  }
}
