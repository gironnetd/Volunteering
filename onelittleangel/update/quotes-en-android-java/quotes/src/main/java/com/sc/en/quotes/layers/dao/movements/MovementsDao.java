package com.sc.en.quotes.layers.dao.movements;

import android.content.SharedPreferences;

import com.sc.en.quotes.OnelittleAngelApplication;
import com.sc.en.quotes.layers.mvp.common.utils.Constants;
import com.sc.en.quotes.layers.mvp.tablecontents.TableContentsActivity;
import com.sc.en.quotes.transverse.orms.realm.models.Movement;
import com.sc.en.quotes.layers.dao.DaoManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class MovementsDao implements MovementsDaoInterface {

  private static final String TAG = "MovementsDao";
  private Realm realm;
  private SharedPreferences settings;
  SharedPreferences.Editor editor;
  /***********************************************************
   * Constructor
   **********************************************************/
  public MovementsDao(DaoManager daoManager){
    //to ensure only DaoManager can instanciate this element

  }

  @Override
  public Movement findMovementByIdMovement(int idMovement) {
    realm = Realm.getDefaultInstance();
    return realm.where(Movement.class).equalTo("idMovement", idMovement).findFirst();
  }

  @Override
  public Movement findMovementByName(String name) {
    realm = Realm.getDefaultInstance();
    return realm.where(Movement.class).equalTo("movement", name).findFirst();
  }

  @Override
  public List<Movement> findMovementsByIdParent(int idParent) {
    realm = Realm.getDefaultInstance();
    if(idParent == 0) {
      return realm.where(Movement.class).isNull("parentMovement")
        .notEqualTo("nbTotalQuotes", 0).findAll().sort("movement");
    }else {
      return realm.where(Movement.class).equalTo("parentMovement.idMovement", idParent)
        .findAll().sort("movement");
    }
  }

  @Override
  public List<Movement> findAllMovements() {
    realm = Realm.getDefaultInstance();
    return realm.where(Movement.class).findAll();
  }

  @Override
  public List<Movement> findMovementsWithAuthors() {
    realm = Realm.getDefaultInstance();

    List<Movement> movements = realm.where(Movement.class).isNull("parentMovement")
      .notEqualTo("nbTotalAuthors",0).findAll().sort("movement");
    List<Movement> movements1 = new ArrayList<>();

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    String movementsList = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "").replaceAll("&amp;","&");

    for(int i = 0; i < movements.size(); i ++) {
      if(movementsList.contains(movements.get(i).getMovement())) {
        movements1.add(movements.get(i));
      }
    }

    List<Movement> movementsWithAuthors = new ArrayList<>(movements1.size());
    
    for(Movement movement : movements1){
      Movement m = new Movement();
      m.setMovement(movement.getMovement());
      m.setNbTotalAuthors(movement.getNbTotalAuthors());
      m.setNbAuthors(movement.getNbAuthors());
      
      if(movement.getAuthors() != null){
        m.setAuthors(new RealmList<>());
        m.setAuthors(movement.getAuthors());
      }
      
      if(movement.getMovements() != null) {
        m.setMovements(new RealmList<>());
        
        for (Movement movement1 : movement.getMovements()) {
          Movement m1 = new Movement();
          m1.setMovement(movement1.getMovement());
          m1.setNbTotalAuthors(movement1.getNbTotalAuthors());
          m1.setNbAuthors(movement1.getNbAuthors());
          
          if(movement1.getAuthors() != null){
            m1.setAuthors(new RealmList<>());
            m1.setAuthors(movement1.getAuthors());
          }
          
          if(movement1.getMovements() != null){
            m1.setMovements(new RealmList<>());
            
            for(Movement movement2 : movement1.getMovements()){
              Movement m2 = new Movement();
              m2.setMovement(movement2.getMovement());
              m2.setNbTotalAuthors(movement2.getNbTotalAuthors());
              m2.setNbAuthors(movement2.getNbAuthors());

              if(movement2.getAuthors() != null){
                m2.setAuthors(new RealmList<>());
                m2.setAuthors(movement2.getAuthors());
              }
              m1.getMovements().add(m2);
            }
          }
          m.getMovements().add(m1);
        }
      }
      movementsWithAuthors.add(m);
    }

    return movementsWithAuthors;
  }

  @Override
  public List<Movement> findMovementsWithBooks() {
    realm = Realm.getDefaultInstance();

    List<Movement> movements = realm.where(Movement.class).isNull("parentMovement")
      .notEqualTo("nbTotalBooks",0).findAll().sort("movement");

    List<Movement> movements1 = new ArrayList<>();
    //movements1.clear();

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    String movementsList = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "").replaceAll("&amp;","&");

    for(int i = 0; i < movements.size(); i ++) {
      if(movementsList.contains(movements.get(i).getMovement())) {
        movements1.add(movements.get(i));
      }
    }

    List<Movement> movementsWithBooks = new ArrayList<>(movements1.size());

    for(Movement movement : movements1){
      Movement m = new Movement();
      m.setMovement(movement.getMovement());
      m.setNbTotalBooks(movement.getNbTotalBooks());
      m.setNbBooks(movement.getNbBooks());

      if(movement.getBooks() != null){
        m.setBooks(new RealmList<>());
        m.setBooks(movement.getBooks());
      }

      if(movement.getMovements() != null) {
        m.setMovements(new RealmList<>());

        for (Movement movement1 : movement.getMovements()) {
          Movement m1 = new Movement();
          m1.setMovement(movement1.getMovement());
          m1.setNbTotalBooks(movement1.getNbTotalBooks());
          m1.setNbBooks(movement1.getNbBooks());

          if(movement1.getBooks() != null){
            m1.setBooks(new RealmList<>());
            m1.setBooks(movement1.getBooks());
          }

          if(movement1.getMovements() != null){
            m1.setMovements(new RealmList<>());

            for(Movement movement2 : movement1.getMovements()){
              Movement m2 = new Movement();
              m2.setMovement(movement2.getMovement());
              m2.setNbTotalBooks(movement2.getNbTotalBooks());
              m2.setNbBooks(movement2.getNbBooks());

              if(movement2.getBooks() != null){
                m2.setBooks(new RealmList<>());
                m2.setBooks(movement2.getBooks());
              }
              m1.getMovements().add(m2);
            }
          }
          m.getMovements().add(m1);
        }
      }
      movementsWithBooks.add(m);
    }
    return movementsWithBooks;
  }

  @Override
  public List<Movement> findMovementsWithMovements() {
    realm = Realm.getDefaultInstance();
    List<Movement> movements =  realm.where(Movement.class).isNull("parentMovement")
      .findAll().sort("movement");

    List<Movement> movements1 = new ArrayList<>();
    //movements1.clear();

    settings = OnelittleAngelApplication.instance.getSharedPreferences(TableContentsActivity.PREFS_NAME, 0);

    String movementsList = settings.getString(Constants.MOVEMENTSLIST_SELECTED, "").replaceAll("&amp;","&");

    for(int i = 0; i < movements.size(); i ++) {
      if(movementsList.contains(movements.get(i).getMovement())) {
        movements1.add(movements.get(i));
      }
    }

    List<Movement> movementsWithMovements = new ArrayList<>(movements1.size());

    for(Movement movement : movements1){
      Movement m = new Movement();
      m.setMovement(movement.getMovement());
      m.setNbSubcourants(movement.getNbSubcourants());
      m.setNbQuotes(movement.getNbQuotes());
      m.setNbTotalQuotes(movement.getNbTotalQuotes());

      if(movement.getMovements() != null) {
        m.setMovements(new RealmList<>());

        for (Movement movement1 : movement.getMovements()) {
          Movement m1 = new Movement();
          m1.setMovement(movement1.getMovement());
          m1.setNbSubcourants(movement1.getNbSubcourants());
          m1.setNbQuotes(movement1.getNbQuotes());
          m1.setNbTotalQuotes(movement1.getNbTotalQuotes());

          if(movement1.getMovements() != null){
            m1.setMovements(new RealmList<>());

            for(Movement movement2 : movement1.getMovements()){
              Movement m2 = new Movement();
              m2.setMovement(movement2.getMovement());
              m2.setNbSubcourants(movement2.getNbSubcourants());
              m2.setNbQuotes(movement2.getNbQuotes());
              m2.setNbTotalQuotes(movement2.getNbTotalQuotes());

              m1.getMovements().add(m2);
            }
          }
          m.getMovements().add(m1);
        }
      }
      movementsWithMovements.add(m);
    }
    return movementsWithMovements;
  }

  @Override
  public List<Movement> findAllMainMovements() {

    realm = Realm.getDefaultInstance();
    return realm.where(Movement.class).isNull("parentMovement").findAll();
  }

  /***********************************************************
   *  Business Methods
   **********************************************************/
}
