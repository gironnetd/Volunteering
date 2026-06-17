package com.sc.en.quotes.layers.service.movements.services;

import android.content.SharedPreferences;

import com.sc.en.quotes.layers.service.MotherBusinessService;
import com.sc.en.quotes.injector.Injector;
import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.dao.movements.MovementsDaoInterface;
import com.sc.en.quotes.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.quotes.layers.service.movements.interfaces.MovementsWithAuthorsServiceInterface;
import com.sc.en.quotes.layers.service.ServiceManagerInterface;
import com.sc.en.quotes.transverse.eventbus.events.movements.MovementsWithAuthorsLoadedEvent;
import com.sc.en.quotes.transverse.orms.realm.models.Movement;
import com.sc.en.quotes.layers.mvp.tablecontents.models.AuthorBook;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MovementsWithAuthorsService extends MotherBusinessService implements MovementsWithAuthorsServiceInterface {

  private static final String TAG = "MovementsWithAuthorsService";

  /**
   *
   */
  private MovementsWithAuthorsLoadedEvent movementsWithAuthorsLoadedEvent;

  /**
   *
   */
  private List<AuthorBook> authors = null;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public MovementsWithAuthorsService(ServiceManagerInterface srvManager) {
    super(srvManager);
    authors = new ArrayList<>();
  }


  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    authors = null;
  }

  /**
   *
   */
  @Override
  public void loadMovementsWithAuthorsAsync() {
  //  Log.d(TAG, "loadMovementsWithAuthorsAsync() called with: " + "");
    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;

    SharedPreferences settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

//    String[] movementsSelected = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "").replaceAll("&amp;","&").split(";");
//
//    if(movementsSelected.length == authors.size()) {
//      for(int i = 0; i < movementsSelected.length; i++) {
//        if(movementsSelected[i].toString().equals(authors.get(i).faith)){
//          reload = true;
//        } else {
//          reload = false;
//          authors.removeAll(authors);
//          OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindMovementsWithAuthorsRunnable);
//          return;
//        }
//      }
//    }

//    if (!authors.isEmpty()) {
//      reload = true;
//    }
    // use the caching mechanism
    if (reload) {

      //send send back the answer using eventBus
      postMovementsWithAuthorsLoadedEvent(authors);
    } else {
      authors.clear();
      OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindMovementsWithAuthorsRunnable);
    }
  }

  private void loadMovementsWithAuthorsSync(){
    // Load data from DB
    /*

   */
    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();
    //send back the answer using eventBus

    postMovementsWithAuthorsLoadedEvent(convertToAuthorBooks(movementsDaoInterface.findMovementsWithAuthors()));
    movementsDaoInterface =null;
  }

  private List<AuthorBook> convertToAuthorBooks(List<Movement> movementsWithAuthors){

    for (int i = 0; i < movementsWithAuthors.size(); i++) {
      //    Log.v("onelittleangel", " : " + movementsWithAuthors.get(i).getMovement());

      if (movementsWithAuthors.get(i).getNbTotalAuthors() == 0) {
        continue;
      }

      AuthorBook mMainAuthor = new AuthorBook();

    //  mMainAuthor.idCourant = (int) movementsWithAuthors.get(i).getIdMovement();
      mMainAuthor.faith = movementsWithAuthors.get(i).getMovement();
      mMainAuthor.number = (int) movementsWithAuthors.get(i).getNbTotalAuthors();
      mMainAuthor.nbAuthors = (int) movementsWithAuthors.get(i).getNbAuthors();

      if ((movementsWithAuthors.get(i).getMovements() != null ? movementsWithAuthors.get(i).getMovements().size() : 0) != 0 && mMainAuthor.baseEntities == null)
        mMainAuthor.baseEntities = new ArrayList<>();

      for (int j = 0; j < movementsWithAuthors.get(i).getMovements().size(); j++) {

        if (movementsWithAuthors.get(i).getMovements().get(j).getNbTotalAuthors() == 0) {
          continue;
        }

        AuthorBook mSecondAuthor = new AuthorBook();

      //  mSecondAuthor.idCourant = (int) movementsWithAuthors.get(i).getMovements().get(j).getIdMovement();
        mSecondAuthor.faith = movementsWithAuthors.get(i).getMovements().get(j).getMovement();
        mSecondAuthor.number = (int) movementsWithAuthors.get(i).getMovements().get(j).getNbTotalAuthors();
        mSecondAuthor.nbAuthors = (int) movementsWithAuthors.get(i).getMovements().get(j).getNbAuthors();

        //  RealmResults<Movement> thirdMovements = realm.where(Movement.class).equalTo("parentMovement.idMovement",movementsWithAuthors.get(i).getMovements().get(j).getIdMovement()).findAllSorted("movement");

        if ((movementsWithAuthors.get(i).getMovements().get(j).getMovements() != null ? movementsWithAuthors.get(i).getMovements().get(j).getMovements().size() : 0) != 0 && mSecondAuthor.baseEntities == null)
          mSecondAuthor.baseEntities = new ArrayList<>();

        for (int k = 0; k < movementsWithAuthors.get(i).getMovements().get(j).getMovements().size(); k++) {

          if (movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getNbTotalAuthors() == 0) {
            continue;
          }

          AuthorBook mThirdAuthor = new AuthorBook();

        //  mThirdAuthor.idCourant = (int) movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getIdMovement();
          //  mThirdAuthor.idParent = mThirdAuthorsCursor.getInt(1);
          mThirdAuthor.faith = movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getMovement();
          mThirdAuthor.number = (int) movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getNbTotalAuthors();
          mThirdAuthor.nbAuthors = (int) movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getNbAuthors();

          if(movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors() != null){
            if (mThirdAuthor.baseEntities == null) {

              mThirdAuthor.authorBooks = new ArrayList<>();

              if (movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors() != null) {

                for (int d = 0; d < movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors().size(); d++) {

                  if (movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors().get(d).getQuotes().size() == 0) {
                    continue;
                  }

                  mThirdAuthor.authorBooks.add(new AuthorBook((int) movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors().get(d).getIdAuthor(),
                    (int) movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors().get(d).getMovement().getIdMovement(),
                    movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors().get(d).getName(),
                    movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors().get(d).getSurname(),
                    movementsWithAuthors.get(i).getMovements().get(j).getMovements().get(k).getAuthors().get(d).getQuotes().size()));
                }
              }
            }
          }

          mSecondAuthor.baseEntities.add(mThirdAuthor);
        }

        if(movementsWithAuthors.get(i).getMovements().get(j).getAuthors() != null){
          if (mSecondAuthor.baseEntities == null) {

            mSecondAuthor.authorBooks = new ArrayList<>();

            if (movementsWithAuthors.get(i).getMovements().get(j).getAuthors() != null) {

              for (int d = 0; d < movementsWithAuthors.get(i).getMovements().get(j).getAuthors().size(); d++) {

                if (movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getQuotes().size() == 0) {
                  continue;
                }

                mSecondAuthor.authorBooks.add(new AuthorBook((int) movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getIdAuthor(),
                  (int) movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getMovement().getIdMovement(), movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getName(),
                  movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getSurname(), movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getQuotes().size()));
              }
            }
          }
        }

        if (mSecondAuthor.baseEntities != null) {

          if (movementsWithAuthors.get(i).getMovements().get(j).getNbAuthors() != 0 &&
            movementsWithAuthors.get(i).getMovements().get(j).getNbTotalAuthors() != movementsWithAuthors.get(i).getMovements().get(j).getNbAuthors()) {
            AuthorBook a = new AuthorBook(mSecondAuthor);
            a.number = (int) movementsWithAuthors.get(i).getMovements().get(j).getNbAuthors();
            if (mSecondAuthor.baseEntities == null) mSecondAuthor.baseEntities = new ArrayList<>();
            mSecondAuthor.baseEntities.add(0, a);

            if (mSecondAuthor.baseEntities.get(0).baseEntities == null) {

              mSecondAuthor.baseEntities.get(0).authorBooks = new ArrayList<>();

              //  RealmResults<Author> results = realm.where(Author.class).equalTo("movement.idMovement",mSecondAuthor.baseEntities.get(r).idCourant).findAllSorted("name");

              if (movementsWithAuthors.get(i).getMovements().get(j).getAuthors() != null) {

                for (int d = 0; d < movementsWithAuthors.get(i).getMovements().get(j).getAuthors().size(); d++) {

                  if (movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getQuotes().size() == 0) {
                    continue;
                  }

                  mSecondAuthor.baseEntities.get(0).authorBooks.add(
                    new AuthorBook((int) movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getIdAuthor(),
                      (int) movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getMovement().getIdMovement(),
                      movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getName(),
                      movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getSurname(),
                      movementsWithAuthors.get(i).getMovements().get(j).getAuthors().get(d).getQuotes().size()));
                }
              }
            }

          }
        }
        mMainAuthor.baseEntities.add(mSecondAuthor);
      }

      if (mMainAuthor.baseEntities != null) {

        if(movementsWithAuthors.get(i).getNbAuthors() != 0 &&
          movementsWithAuthors.get(i).getNbTotalAuthors() != movementsWithAuthors.get(i).getNbAuthors()){

          AuthorBook a = new AuthorBook(mMainAuthor);
          a.number =(int) movementsWithAuthors.get(i).getNbAuthors();
          if(mMainAuthor.baseEntities == null) mMainAuthor.baseEntities = new ArrayList<>();
          mMainAuthor.baseEntities.add(0, a);

          if (mMainAuthor.baseEntities.get(0).baseEntities == null) {

            mMainAuthor.baseEntities.get(0).authorBooks = new ArrayList<>();

            if (movementsWithAuthors.get(i).getAuthors() != null) {

              for (int d = 0; d < movementsWithAuthors.get(i).getAuthors().size(); d++) {

                if (movementsWithAuthors.get(i).getAuthors().get(d).getQuotes().size() == 0) {
                  continue;
                }

                mMainAuthor.baseEntities.get(0).authorBooks.add(new AuthorBook((int) movementsWithAuthors.get(i).getAuthors().get(d).getIdAuthor(),
                  (int) movementsWithAuthors.get(i).getAuthors().get(d).getMovement().getIdMovement(), movementsWithAuthors.get(i).getAuthors().get(d).getName(),
                  null, movementsWithAuthors.get(i).getAuthors().get(d).getQuotes().size()));
              }
            }
          }
        }
      }else {

          mMainAuthor.authorBooks = new ArrayList<>();

          if (movementsWithAuthors.get(i).getAuthors() != null) {
            for (int d = 0; d < movementsWithAuthors.get(i).getAuthors().size(); d++) {

              if (movementsWithAuthors.get(i).getAuthors().get(d).getQuotes().size() == 0) {
                continue;
              }
              mMainAuthor.authorBooks.add(new AuthorBook((int) movementsWithAuthors.get(i).getAuthors().get(d).getIdAuthor(),
                (int) movementsWithAuthors.get(i).getAuthors().get(d).getMovement().getIdMovement(), movementsWithAuthors.get(i).getAuthors().get(d).getName(),
                movementsWithAuthors.get(i).getAuthors().get(d).getSurname(), movementsWithAuthors.get(i).getAuthors().get(d).getQuotes().size()));
            }
          }
        }
      authors.add(mMainAuthor);
    }

    return authors;
  }
  
  /**
   * The runnable to execute
   */
  private final DaoFindMovementsWithAuthorsRunnable daoFindMovementsWithAuthorsRunnable = new MovementsWithAuthorsService.DaoFindMovementsWithAuthorsRunnable();
/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to implements a Runnable
 */
private class DaoFindMovementsWithAuthorsRunnable implements Runnable {
  @Override
  public void run() {
    loadMovementsWithAuthorsSync();
  }
}

  /**
   * Broadcast all the authors Loaded event
   */
  private void postMovementsWithAuthorsLoadedEvent(List<AuthorBook> movementsWithAuthors) {
  //  Log.d(TAG, " called with: " + movementsWithAuthors.size());

  //  Log.v("onelittleangel", " : " + movementsWithAuthors.size());

    if(movementsWithAuthorsLoadedEvent ==null){
      movementsWithAuthorsLoadedEvent = new MovementsWithAuthorsLoadedEvent(movementsWithAuthors);
    }else{
      movementsWithAuthorsLoadedEvent.setAuthorBooks(movementsWithAuthors);
    }
  //  Log.e(TAG, "postMovementsWithAuthorsLoadedEvent posted" );
    EventBus.getDefault().post(movementsWithAuthorsLoadedEvent);
  }
}