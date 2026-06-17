package com.sc.en.confucianism.layers.service.movements.services;

import android.content.SharedPreferences;

import com.sc.en.confucianism.OnelittleAngelApplication;
import com.sc.en.confucianism.layers.dao.movements.MovementsDaoInterface;
import com.sc.en.confucianism.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.confucianism.layers.mvp.tablecontents.models.AuthorBook;
import com.sc.en.confucianism.layers.service.MotherBusinessService;
import com.sc.en.confucianism.layers.service.ServiceManagerInterface;
import com.sc.en.confucianism.transverse.eventbus.events.movements.MovementsWithBooksLoadedEvent;
import com.sc.en.confucianism.transverse.orms.realm.models.Movement;
import com.sc.en.confucianism.injector.Injector;
import com.sc.en.confucianism.layers.service.movements.interfaces.MovementsWithBooksServiceInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MovementsWithBooksService extends MotherBusinessService implements MovementsWithBooksServiceInterface {

  private static final String TAG = "MovementsWithBooksService";

  /**
   *
   */
  private MovementsWithBooksLoadedEvent movementsWithBooksLoadedEvent;

  /**
   *
   */
  private List<AuthorBook> books = null;

  /**
   * Constructor
   *
   * @param srvManager
   */
  public MovementsWithBooksService(ServiceManagerInterface srvManager) {
    super(srvManager);
    books = new ArrayList<>();
  }

  /**
   * Clean your resource when your service die
   */
  @Override
  public void onDestroy() {
    books = null;
  }

  /**
   *
   */
  @Override
  public void loadMovementsWithBooksAsync() {

    /*
    To know if tha data has to be reloaded
   */
    boolean reload = false;

    SharedPreferences settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

//    String[] movementsSelected = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "").replaceAll("&amp;","&").split(";");
//
//    if(movementsSelected.length == books.size()) {
//      for(int i = 0; i < movementsSelected.length; i++) {
//        if(movementsSelected[i].toString().equals(books.get(i).faith)){
//          reload = true;
//        } else {
//          reload = false;
//          books.clear();
//          //  Log.d(TAG, "loadMovementsWithBooks() called with: " + "");
//          OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindMovementsWithBooksRunnable);
//          return;
//        }
//      }
//    }

    // use the caching mechanism
    if (reload) {

      //send send back the answer using eventBus
      postMovementsWithBooksLoadedEvent(books);
    } else {
      books.clear();
      //  Log.d(TAG, "loadMovementsWithBooks() called with: " + "");
      OnelittleAngelApplication.instance.getServiceManager().getCancelableThreadsExecutor().submit(daoFindMovementsWithBooksRunnable);
    }
  }

  private void loadMovementsWithBooksSync(){
    // Load data from DB
    /*

   */
    MovementsDaoInterface movementsDaoInterface = Injector.getDaoManager().getMovementsDao();

    //send back the answer using eventBus
    postMovementsWithBooksLoadedEvent(convertToAuthorBooks(movementsDaoInterface.findMovementsWithBooks()));
    movementsDaoInterface =null;
  }

  private List<AuthorBook> convertToAuthorBooks(List<Movement> movementsWithBooks){

    for(int i = 0; i < movementsWithBooks.size(); i++){

      if(movementsWithBooks.get(i).getNbTotalBooks() == 0){
        continue;
      }

      AuthorBook mMainAuthor = new AuthorBook();

      mMainAuthor.idCourant = (int) movementsWithBooks.get(i).getIdMovement();
      mMainAuthor.faith = movementsWithBooks.get(i).getMovement();
      mMainAuthor.number =(int) movementsWithBooks.get(i).getNbTotalBooks();
      mMainAuthor.nbBooks = (int) movementsWithBooks.get(i).getNbBooks();

      if((movementsWithBooks.get(i).getMovements() != null ? movementsWithBooks.get(i).getMovements().size() : 0) != 0 && mMainAuthor.baseEntities == null
        && movementsWithBooks.get(i).getNbTotalBooks() != movementsWithBooks.get(i).getNbBooks())
        mMainAuthor.baseEntities = new ArrayList<>();

      for(int j = 0; j < movementsWithBooks.get(i).getMovements().size(); j++){

        if(movementsWithBooks.get(i).getMovements().get(j).getNbTotalBooks() == 0){
          continue;
        }

        AuthorBook mSecondAuthor = new AuthorBook();

        mSecondAuthor.idCourant = (int) movementsWithBooks.get(i).getMovements().get(j).getIdMovement();
        mSecondAuthor.faith = movementsWithBooks.get(i).getMovements().get(j).getMovement();
        mSecondAuthor.number =(int) movementsWithBooks.get(i).getMovements().get(j).getNbTotalBooks();
        mSecondAuthor.nbBooks = (int) movementsWithBooks.get(i).getMovements().get(j).getNbBooks();

        if ((movementsWithBooks.get(i).getMovements().get(j).getMovements() != null ? movementsWithBooks.get(i).getMovements().get(j).getMovements().size() : 0) != 0 && mSecondAuthor.baseEntities == null)
          mSecondAuthor.baseEntities = new ArrayList<>();

        for(int k = 0; k < movementsWithBooks.get(i).getMovements().get(j).getMovements().size(); k++){

          if(movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getNbTotalBooks() == 0){
            continue;
          }

          AuthorBook mThirdAuthor = new AuthorBook();

          mThirdAuthor.idCourant = (int) movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getIdMovement();
          mThirdAuthor.faith = movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getMovement();
          mThirdAuthor.number =(int) movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getNbTotalBooks();
          mThirdAuthor.nbBooks = (int) movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getNbBooks();

          if(movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks() != null){
            if (mThirdAuthor.baseEntities == null) {

              mThirdAuthor.authorBooks = new ArrayList<>();

              if (movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks() != null) {

                for (int d = 0; d < movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks().size(); d++) {

                  if (movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks().get(d).getQuotes().size() == 0) {
                    continue;
                  }

                  mThirdAuthor.authorBooks.add(new AuthorBook((int) movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks().get(d).getIdBook(),
                    (int) movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks().get(d).getMovement().getIdMovement(),
                    movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks().get(d).getName(),
                    null, movementsWithBooks.get(i).getMovements().get(j).getMovements().get(k).getBooks().get(d).getQuotes().size()));
                }
              }
            }
          }

          mSecondAuthor.baseEntities.add(mThirdAuthor);
        }

        if(movementsWithBooks.get(i).getMovements().get(j).getBooks() != null){
          if (mSecondAuthor.baseEntities == null) {

            mSecondAuthor.authorBooks = new ArrayList<>();

            if (movementsWithBooks.get(i).getMovements().get(j).getBooks() != null) {

              for (int d = 0; d < movementsWithBooks.get(i).getMovements().get(j).getBooks().size(); d++) {

                if (movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getQuotes().size() == 0) {
                  continue;
                }

                mSecondAuthor.authorBooks.add(new AuthorBook((int) movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getIdBook(),
                  (int) movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getMovement().getIdMovement(), movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getName(),
                  null, movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getQuotes().size()));
              }
            }
          }
        }

        if(movementsWithBooks.get(i).getMovements().get(j).getNbBooks() != 0 &&
          movementsWithBooks.get(i).getMovements().get(j).getNbTotalBooks()
            != movementsWithBooks.get(i).getMovements().get(j).getNbBooks()){

          AuthorBook a = new AuthorBook(mSecondAuthor);
          a.number =(int) movementsWithBooks.get(i).getMovements().get(j).getNbBooks();
          if(mMainAuthor.baseEntities == null) mMainAuthor.baseEntities = new ArrayList<>();
          mSecondAuthor.baseEntities.add(0, a);

          if (mSecondAuthor.baseEntities.get(0).baseEntities == null) {

            mSecondAuthor.baseEntities.get(0).authorBooks = new ArrayList<>();

            if (movementsWithBooks.get(i).getMovements().get(j).getBooks() != null) {

              for (int d = 0; d < movementsWithBooks.get(i).getMovements().get(j).getBooks().size(); d++) {

                if (movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getQuotes().size() == 0) {
                  continue;
                }

                mSecondAuthor.baseEntities.get(0).authorBooks.add(new AuthorBook((int) movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getIdBook(),
                  (int) movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getMovement().getIdMovement(),
                  movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getName(),
                  null, movementsWithBooks.get(i).getMovements().get(j).getBooks().get(d).getQuotes().size()));
              }
            }
          }
        }
        mMainAuthor.baseEntities.add(mSecondAuthor);
      }

      if(mMainAuthor.baseEntities != null) {

        if(movementsWithBooks.get(i).getNbBooks() != 0 &&
          movementsWithBooks.get(i).getNbTotalBooks() != movementsWithBooks.get(i).getNbBooks()){

          AuthorBook a = new AuthorBook(mMainAuthor);
          a.number =(int) movementsWithBooks.get(i).getNbBooks();
          if(mMainAuthor.baseEntities == null) mMainAuthor.baseEntities = new ArrayList<>();
          mMainAuthor.baseEntities.add(0, a);

          if (mMainAuthor.baseEntities.get(0).baseEntities == null) {

            mMainAuthor.baseEntities.get(0).authorBooks = new ArrayList<>();

            if (movementsWithBooks.get(i).getBooks() != null) {

              for (int d = 0; d < movementsWithBooks.get(i).getBooks().size(); d++) {

                if (movementsWithBooks.get(i).getBooks().get(d).getQuotes().size() == 0) {
                  continue;
                }

                mMainAuthor.baseEntities.get(0).authorBooks.add(new AuthorBook((int) movementsWithBooks.get(i).getBooks().get(d).getIdBook(),
                  (int) movementsWithBooks.get(i).getBooks().get(d).getMovement().getIdMovement(), movementsWithBooks.get(i).getBooks().get(d).getName(),
                  null, movementsWithBooks.get(i).getBooks().get(d).getQuotes().size()));
              }
            }
          }
        }
      } else {

        mMainAuthor.authorBooks = new ArrayList<>();

        if (movementsWithBooks.get(i).getBooks() != null) {
          for (int d = 0; d < movementsWithBooks.get(i).getBooks().size(); d++) {

            if (movementsWithBooks.get(i).getBooks().get(d).getQuotes().size() == 0) {
              continue;
            }
            mMainAuthor.authorBooks.add(new AuthorBook((int)movementsWithBooks.get(i).getBooks().get(d).getIdBook(),
              (int)movementsWithBooks.get(i).getBooks().get(d).getMovement().getIdMovement(), movementsWithBooks.get(i).getBooks().get(d).getName(),
              null, movementsWithBooks.get(i).getBooks().get(d).getQuotes().size()));
          }
        }
      }
      books.add(mMainAuthor);
    }


    return books;
  }

  /**
   * The runnable to execute
   */
  private final DaoFindMovementsWithBooksRunnable daoFindMovementsWithBooksRunnable = new DaoFindMovementsWithBooksRunnable();
  /**
   * @author Mathias Seguy (Android2EE)
   * @goals
   *        This class aims to implements a Runnable
   */
  private class DaoFindMovementsWithBooksRunnable implements Runnable {
    @Override
    public void run() {
      loadMovementsWithBooksSync();
    }
  }

  /**
   * Broadcast all the authors Loaded event
   */
  private void postMovementsWithBooksLoadedEvent(List<AuthorBook> movementsWithBooks) {

    if(movementsWithBooksLoadedEvent ==null){
      movementsWithBooksLoadedEvent = new MovementsWithBooksLoadedEvent(movementsWithBooks);
    }else{
      movementsWithBooksLoadedEvent.setMovementsWithBooks(movementsWithBooks);
    }
  //  Log.e(TAG, "postMovementsWithBooksLoadedEvent posted" );
    EventBus.getDefault().post(movementsWithBooksLoadedEvent);
  }
}
