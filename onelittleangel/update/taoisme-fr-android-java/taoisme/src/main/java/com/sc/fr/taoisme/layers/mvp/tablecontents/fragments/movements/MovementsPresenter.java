package com.sc.fr.taoisme.layers.mvp.tablecontents.fragments.movements;

import com.sc.fr.taoisme.OnelittleAngelApplication;
import com.sc.fr.taoisme.layers.mvp.MotherPresenter;
import com.sc.fr.taoisme.layers.mvp.tablecontents.fragments.PresenterInterface;
import com.sc.fr.taoisme.layers.mvp.tablecontents.fragments.ViewInterface;
import com.sc.fr.taoisme.layers.mvp.tablecontents.models.Faith;
import com.sc.fr.taoisme.transverse.eventbus.events.movements.MovementsWithMovementsLoadedEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MovementsPresenter extends MotherPresenter implements PresenterInterface {

  private static final String TAG = "MovementsPresenter";

  private List<Faith> movements;

  private ViewInterface movementsViewInterface = null;

  public MovementsPresenter(ViewInterface movementsViewInterface) {
    this.movementsViewInterface = movementsViewInterface;
    if(movements == null) {
      movements = new ArrayList<>();
    }
  }

  /**
   *
   */
  @Override
  public void loadMovements() {
    OnelittleAngelApplication.instance.getServiceManager()
      .getMovementsWithMovementsService().loadMovementsWithMovementsAsync();
  }

  /**
   * @return
   */
  @Override
  public List<Faith> getMovements() {
    return movements;
  }

  @Override
  public void removeMovementsOfFragment() {
    movements =  null;
    movementsViewInterface = null;
  }

  /***********************************************************
   *  Listening for services response
   **********************************************************/

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(MovementsWithMovementsLoadedEvent event){
    movements.addAll(event.getMovementsWithMovements());
    movementsViewInterface.updateMovements();
  }
}
