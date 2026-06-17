package com.sc.fr.christianisme.layers.mvp.tablecontents.fragments.authors;

import com.sc.fr.christianisme.OnelittleAngelApplication;
import com.sc.fr.christianisme.layers.mvp.tablecontents.fragments.PresenterInterface;
import com.sc.fr.christianisme.layers.mvp.MotherPresenter;
import com.sc.fr.christianisme.layers.mvp.tablecontents.fragments.ViewInterface;
import com.sc.fr.christianisme.layers.mvp.tablecontents.models.BaseEntity;
import com.sc.fr.christianisme.transverse.eventbus.events.movements.MovementsWithAuthorsLoadedEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class AuthorsPresenter extends MotherPresenter implements PresenterInterface {

  private static final String TAG = "AuthorsPresenter";

  private ViewInterface authorsViewInterface = null;
  private List<BaseEntity> authors;

  public AuthorsPresenter(ViewInterface authorsViewInterface) {
    this.authorsViewInterface = authorsViewInterface;
    if(authors == null) {
      authors = new ArrayList<>();
    }
  }

  @Override
  public void loadMovements() {
    OnelittleAngelApplication.instance.getServiceManager().getMovementsWithAuthorsService().loadMovementsWithAuthorsAsync();
  }

  @Override
  public List<BaseEntity> getMovements() {
    return authors;
  }

  @Override
  public void removeMovementsOfFragment() {
    authors = null;
    authorsViewInterface = null;
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(MovementsWithAuthorsLoadedEvent event){
    authors.addAll(event.getAuthorBooks());
    authorsViewInterface.updateMovements();
  }
}
