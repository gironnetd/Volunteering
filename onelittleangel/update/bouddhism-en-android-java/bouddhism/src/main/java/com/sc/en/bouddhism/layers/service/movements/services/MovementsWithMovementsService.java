package com.sc.en.bouddhism.layers.service.movements.services;

import android.content.SharedPreferences;

import com.sc.en.bouddhism.OnelittleAngelApplication;
import com.sc.en.bouddhism.layers.dao.movements.MovementsDaoInterface;
import com.sc.en.bouddhism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.bouddhism.layers.service.MotherBusinessService;
import com.sc.en.bouddhism.injector.Injector;
import com.sc.en.bouddhism.layers.service.movements.interfaces.MovementsWithMovementsServiceInterface;
import com.sc.en.bouddhism.layers.service.ServiceManagerInterface;
import com.sc.en.bouddhism.transverse.eventbus.events.movements.MovementsWithMovementsLoadedEvent;
import com.sc.en.bouddhism.transverse.orms.realm.models.Movement;
import com.sc.en.bouddhism.layers.mvp.tablecontents.models.Faith;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MovementsWithMovementsService extends MotherBusinessService implements MovementsWithMovementsServiceInterface {

  private static final String TAG = "MovementsWithMovementsService";

  /**
   *
   */
  private MovementsWithMovementsLoadedEvent movementsWithMovementsLoadedEvent;

  private SharedPreferences settings;

  /**
   *
   */
  private List<Faith> movements = null;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public MovementsWithMovementsService(ServiceManagerInterface srvManager) {
    super(srvManager);
    movements = new ArrayList<>();
  }
  
  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    movements = null;
    settings = null;
  }

  @Override
  public void loadMovementsWithMovementsAsync() {
  //  Log.d(TAG, "loadMovementsWithMovements() called with: " + "");

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;
    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

//    String[] movementsSelected = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "").replaceAll("&amp;","&").split(";");
//
//    if(movementsSelected.length == movements.size()) {
//      for(int i = 0; i < movementsSelected.length; i++) {
//        if(movementsSelected[i].toString().equals(movements.get(i).faith)){
//          reload = true;
//        } else {
//          reload = false;
//          movements.clear();
//          OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindMovementsWithMovementsRunnable);
//          return;
//        }
//      }
//    }
    // use the caching mechanism
    if (reload) {
      //send send back the answer using eventBus
      postMovementsWithMovementsLoadedEvent(movements);
    } else {
      movements.clear();
      OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindMovementsWithMovementsRunnable);
    }
  }

  private void loadMovementsWithMovementsSync(){
    // Load data from DB
    /*

   */
    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();
    //send back the answer using eventBus

  //  Log.d(TAG, "before loadMovementsWithMovementsSync() called with: " + "");
    postMovementsWithMovementsLoadedEvent(convertToFaith(movementsDaoInterface.findMovementsWithMovements()));
  //  Log.d(TAG, "after loadMovementsWithMovementsSync() called with: " + "");

    movementsDaoInterface =null;
  }

  private List<Faith> convertToFaith(List<Movement> movementsWithMovements){

    for (int i = 0; i < movementsWithMovements.size(); i++) {

      if (movementsWithMovements.get(i).getNbTotalQuotes() == 0) {
        continue;
      }

      Faith mainFaith = new Faith();
    //  mainFaith.idCourant = (int) movementsWithMovements.get(i).getIdMovement();
      mainFaith.faith = movementsWithMovements.get(i).getMovement();
      mainFaith.number = (int) movementsWithMovements.get(i).getNbSubcourants();

      if (movementsWithMovements.get(i).getNbQuotes() != 0 && movementsWithMovements.get(i).getMovements().size() != 0) {
        Faith b = new Faith(mainFaith);
        b.number = 0;
        mainFaith.baseEntities = new ArrayList<>();
        mainFaith.baseEntities.add(b);
      }

      //  RealmResults<Movement> secondMovements = realm.where(Movement.class).equalTo("parentMovement.idMovement", movementsWithMovements.get(i).getIdMovement()).findAllSorted("movement");

      if ((movementsWithMovements.get(i).getMovements() != null ? movementsWithMovements.get(i).getMovements().size() : 0) != 0 && mainFaith.baseEntities == null)
        mainFaith.baseEntities = new ArrayList<>();

      if (movementsWithMovements.get(i).getMovements() != null) {
        for (int j = 0; j < movementsWithMovements.get(i).getMovements().size(); j++) {

          if (movementsWithMovements.get(i).getMovements().get(j).getNbTotalQuotes() == 0) {
            //  mSecondFaithsCursor.moveToNext();
            //  continue;
          } else {

            Faith mSecondFaith = new Faith();

          //  mSecondFaith.idCourant = (int) movementsWithMovements.get(i).getMovements().get(j).getIdMovement();
          //  mSecondFaith.idParent = (int) movementsWithMovements.get(i).getMovements().get(j).getParentMovement().getIdMovement();
            mSecondFaith.faith = movementsWithMovements.get(i).getMovements().get(j).getMovement();
            mSecondFaith.number = (int) movementsWithMovements.get(i).getMovements().get(j).getNbSubcourants();

            if (movementsWithMovements.get(i).getMovements().get(j).getNbQuotes() != 0 && movementsWithMovements.get(i).getMovements().get(j).getNbQuotes()
              != movementsWithMovements.get(i).getMovements().get(j).getNbTotalQuotes()) {
              Faith a = new Faith(mSecondFaith);
              a.number = 0;
              mSecondFaith.baseEntities = new ArrayList<>();
              mSecondFaith.baseEntities.add(a);
            }

            //  RealmResults<Movement> thirdMovements = realm.where(Movement.class).equalTo("parentMovement.idMovement", movementsWithMovements.get(i).getMovements().get(j).getIdMovement()).findAllSorted("movement");

            if ((movementsWithMovements.get(i).getMovements().get(j).getMovements() != null ? movementsWithMovements.get(i).getMovements().get(j).getMovements().size() : 0) != 0 && mSecondFaith.baseEntities == null)
              mSecondFaith.baseEntities = new ArrayList<>();

            if (movementsWithMovements.get(i).getMovements().get(j).getMovements() != null) {
              for (int k = 0; k < movementsWithMovements.get(i).getMovements().get(j).getMovements().size(); k++) {

                if (movementsWithMovements.get(i).getMovements().get(j).getMovements().get(k).getNbQuotes() == 0) {
                  //  mLastFaithsCursor.moveToNext();
                  //  continue;
                } else {
                  Faith mLastFaith = new Faith();
                //  mLastFaith.idCourant = (int) movementsWithMovements.get(i).getMovements().get(j).getMovements().get(k).getIdMovement();
                //  mLastFaith.idParent = (int) movementsWithMovements.get(i).getMovements().get(j).getMovements().get(k).getParentMovement().getIdMovement();
                  mLastFaith.faith = movementsWithMovements.get(i).getMovements().get(j).getMovements().get(k).getMovement();
                  mLastFaith.number = (int) movementsWithMovements.get(i).getMovements().get(j).getMovements().get(k).getNbSubcourants();

                  mSecondFaith.baseEntities.add(mLastFaith);
                }
              }
            }
            mainFaith.baseEntities.add(mSecondFaith);
          }
        }
      }

      movements.add(mainFaith);
    }
    return movements;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindMovementsWithMovementsRunnable daoFindMovementsWithMovementsRunnable
    = new DaoFindMovementsWithMovementsRunnable();
  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindMovementsWithMovementsRunnable implements Runnable {
    @Override
    public void run() {
      loadMovementsWithMovementsSync();
    }
  }

  /**
   * Broadcast all the movements Loaded event
   */
  private void postMovementsWithMovementsLoadedEvent(List<Faith> movementsWithMovements) {

    if (movementsWithMovementsLoadedEvent == null) {
      movementsWithMovementsLoadedEvent = new MovementsWithMovementsLoadedEvent(movementsWithMovements);
    } else {
      movementsWithMovementsLoadedEvent.setMovementsWithMovements(movementsWithMovements);
    }
  //  Log.e(TAG, "postMovementsWithMovementsLoadedEvent posted");
    EventBus.getDefault().post(movementsWithMovementsLoadedEvent);
  }
}
